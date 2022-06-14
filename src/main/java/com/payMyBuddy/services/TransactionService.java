package com.payMyBuddy.services;

import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.TransactionRepository;
import com.payMyBuddy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public Iterable<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public void createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactionsByUser(User user) {
        return transactionRepository.findAllTransactionsByDebtorOrCreditorOrderByDateDesc(user, user);
    }

    public void updateTransaction(Transaction transaction) {
        Optional<Transaction> maj = transactionRepository.findById(transaction.getTransactionId());
        if (maj.isPresent()) {
            transactionRepository.save(transaction);
        }
    }

    public void deleteTransaction(Transaction transaction) {
        Optional<Transaction> removeTransaction = transactionRepository.findById(transaction.getTransactionId());
        if (removeTransaction.isPresent()) {
            transactionRepository.deleteById(transaction.getTransactionId());
        }
    }

    public Transaction createTransaction(User debtor, User creditor, double amount, String description) {
        Transaction paymentTransaction = new Transaction();
        var amount1 = amount;
        var rate = 0.05;
        var fare = amount1 * rate;
        var com = amount1 + fare;

        paymentTransaction.setDebtor(debtor);
        paymentTransaction.setCreditor(creditor);
        paymentTransaction.setAmount(amount);
        paymentTransaction.setDate(LocalDate.now());
        paymentTransaction.setCommission(com);
        paymentTransaction.setDescription(description);
        return paymentTransaction;
    }

    public void saveTransaction(Transaction paymentTransaction) {
        transactionRepository.save(paymentTransaction);
    }

    public boolean processUserTransaction(Transaction transaction) {
        var amount = transaction.getAmount();
        var com = transaction.getCommission();

        if (amount <= 0) {
            return false;
        }

        User debtor = userRepository.findById(transaction.getDebtor().getUserId()).get();
        User creditor = userRepository.findById(transaction.getCreditor().getUserId()).get();
        double absTotal = Math.abs(com);

        if (amount > 0) {
            debtor.setWalletBalance(debtor.getWalletBalance().subtract(BigDecimal.valueOf(absTotal)));
            creditor.setWalletBalance(creditor.getWalletBalance().add(BigDecimal.valueOf(amount)));
        } else {
            return false;
        }
        userService.updateUser(debtor);
        userService.updateUser(creditor);
        return true;
    }
}
