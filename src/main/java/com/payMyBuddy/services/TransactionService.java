package com.payMyBuddy.services;

import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.TransactionLabel;
import com.payMyBuddy.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Iterable<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public void createTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
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
