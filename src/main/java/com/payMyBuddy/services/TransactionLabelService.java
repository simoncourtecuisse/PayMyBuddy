//package com.payMyBuddy.services;
//
//import com.payMyBuddy.models.Transaction;
//import com.payMyBuddy.models.TransactionLabel;
//import com.payMyBuddy.repositories.TransactionLabelRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//
//@Service
//@Transactional(propagation = Propagation.REQUIRES_NEW)
//public class TransactionLabelService {
//
//    @Autowired
//    private TransactionLabelRepository transactionLabelRepository;
//
//    public Iterable<TransactionLabel> getTransactionLabels() {
//        return transactionLabelRepository.findAll();
//    }
//
//    public Optional<TransactionLabel> getTransactionLabelById(Long id) {
//        return transactionLabelRepository.findById(id);
//    }
//
//    public void createTransactionLabel(TransactionLabel transactionLabel) {
//        transactionLabelRepository.save(transactionLabel);
//    }
//
//    public void updateTransactionLabel(TransactionLabel transactionLabel) {
//        Optional<TransactionLabel> maj = transactionLabelRepository.findById(transactionLabel.getTransactionLabelId());
//        if (maj.isPresent()) {
//            transactionLabelRepository.save(transactionLabel);
//        }
//    }
//
//    public void deleteTransactionLabel(TransactionLabel transactionLabel) {
//        Optional<TransactionLabel> removeTransactionLabel = transactionLabelRepository.findById(transactionLabel.getTransactionLabelId());
//        if (removeTransactionLabel.isPresent()) {
//            transactionLabelRepository.deleteById(transactionLabel.getTransactionLabelId());
//        }
//    }
//
//    public void addTransactionLabel(TransactionLabel transactionLabel, Transaction transaction) {
//        transactionLabel.getTransactionList().add(transaction);
//        transactionLabelRepository.save(transactionLabel);
//    }
//
//    public void removeTransactionLabel(TransactionLabel transactionLabel, Transaction transaction) {
//        transactionLabel.getTransactionList().remove(transaction);
//        transactionLabelRepository.save(transactionLabel);
//    }
//}
