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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Iterable<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public void createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

//    public List<Transaction> getAllTransactionsByUserId(User user) {
//        return transactionRepository.findAllTransactionsByUser(user, user);
//    }

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

    public boolean authorizedPayment(User debtor, User creditor, BigDecimal total) {
        return debtor.getBalance().compareTo(total) > 0 && debtor.getFriendList().contains(creditor);
    }

    public boolean payment(Transaction transaction) {
        if (userRepository.findById(transaction.getUserIdDebtor()).isEmpty() || userRepository.findById(transaction.getUserIdCreditor()).isEmpty() || transaction.getAmount().signum() <= 0) {
            return false;
        }
        var amount = transaction.getAmount();
        var com = new BigDecimal("0.05");
        var fare = amount.multiply(com);
        var total = amount.add(fare);

        User debtor = userRepository.findById(transaction.getUserIdDebtor()).get();
        User creditor = userRepository.findById(transaction.getUserIdCreditor()).get();

        if (authorizedPayment(debtor, creditor, total)) {
            debtor.setBalance(debtor.getBalance().subtract(total));
            creditor.setBalance(creditor.getBalance().add(total));
            Transaction transactionAuthorized = new Transaction();
            transactionAuthorized.setTransactionId(transaction.getTransactionId());
            transactionAuthorized.setUserIdDebtor(debtor.getUserId());
            transactionAuthorized.setUserIdCreditor(creditor.getUserId());
            transactionAuthorized.setAmount(amount);
            transactionAuthorized.setDate(LocalDate.now());
            transactionAuthorized.setTransactionLabels(transaction.getTransactionLabels());
            transactionRepository.save(transactionAuthorized);
            userRepository.save(debtor);
            userRepository.save(creditor);
            return true;
        }
        return false;
    }


//    public void addTransactionLabel(Transaction transaction, TransactionLabel transactionLabel) {
//        transaction.getTransactionLabels().add(transactionLabel);
//        transactionRepository.save(transaction);
//    }
//
//    public void removeTransactionLabel(Transaction transaction, TransactionLabel transactionLabel) {
//        transaction.getTransactionLabels().remove(transactionLabel);
//        transactionRepository.save(transaction);
//    }
}
