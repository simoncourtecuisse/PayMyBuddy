package com.payMyBuddy.services;

import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.TransactionLabel;
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

//    public List<Transaction> getAllTransactionsByUser(User user) {
//        return transactionRepository.findAllTransactionsByUserDebtorOrCreditor(user, user);
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

//    public boolean authorizedPayment(User debtor, User creditor, double total) {
//        return debtor.getWalletBalance().compareTo(BigDecimal.valueOf(total)) > 0 && debtor.getFriendList().contains(creditor);
//    }
//
//    public Transaction saveTransaction(Transaction paymentTransaction) {
//        return transactionRepository.save(paymentTransaction);
//    }
//
//    public boolean payment(Transaction transaction) {
//        if (userRepository.findById(transaction.getUserIdDebtor().getUserId()).isEmpty() || userRepository.findById(transaction.getUserIdCreditor().getUserId()).isEmpty() || transaction.getAmount() <= 0) {
//            return false;
//        }
//
//        var amount = transaction.getAmount();
//        var rate = 0.05;
//        var fare = amount * rate;
//        var com = amount + fare;
//
////        User debtor = userRepository.findById(transaction.getUserIdDebtor().getUserId()).get();
////        User creditor = userRepository.findById(transaction.getUserIdCreditor().getUserId()).get();
//        User debtor = transaction.getUserIdDebtor();
//        User creditor = transaction.getUserIdCreditor();
//        double absTotal = Math.abs(com);
//
//        if (authorizedPayment(debtor, creditor, absTotal)) {
//            debtor.setWalletBalance(debtor.getWalletBalance().subtract(BigDecimal.valueOf(absTotal)));
//            creditor.setWalletBalance(creditor.getWalletBalance().add(BigDecimal.valueOf(absTotal)));
//            Transaction transactionAuthorized = new Transaction();
//            transactionAuthorized.setTransactionId(transaction.getTransactionId());
//            transactionAuthorized.setUserIdDebtor(debtor);
//            transactionAuthorized.setUserIdCreditor(creditor);
//            transactionAuthorized.setAmount(amount);
//            transactionAuthorized.setDate(LocalDate.now());
//           // transactionAuthorized.setTransactionLabels(transaction.getTransactionLabels());
//            transactionRepository.save(transactionAuthorized);
//            userService.updateUser(debtor);
//            userService.updateUser(creditor);
//            return true;
//        }
//        return false;
//    }

    public Transaction createTransaction(User debtor, User creditor, double amount, TransactionLabel label) {
        Transaction paymentTransaction = new Transaction();
        var amount1 = amount;
        var rate = 0.05;
        var fare = amount1 * rate;
        var com = amount1 + fare;

        paymentTransaction.setUserIdDebtor(debtor);
        paymentTransaction.setUserIdCreditor(creditor);
        paymentTransaction.setAmount(amount);
        paymentTransaction.setDate(LocalDate.now());
        paymentTransaction.setCommission(com);
        paymentTransaction.setTransactionLabel(label);
//        paymentTransaction.setTransactionLabels();
        return paymentTransaction;
    }

    public void saveTransaction(Transaction paymentTransaction) {
        transactionRepository.save(paymentTransaction);
    }

//    public Transaction saveTransaction(Transaction paymentTransaction) {
//        return transactionRepository.save(paymentTransaction);
//    }

    public boolean processUserTransaction(Transaction transaction) {
        var amount = transaction.getAmount();
        var com = transaction.getCommission();

        if (amount <= 0) {
            return false;
        }

        User debtor = userRepository.findById(transaction.getUserIdDebtor().getUserId()).get();
        User creditor = userRepository.findById(transaction.getUserIdCreditor().getUserId()).get();
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
