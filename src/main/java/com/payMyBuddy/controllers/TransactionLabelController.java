package com.payMyBuddy.controllers;

import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.TransactionLabel;
import com.payMyBuddy.services.TransactionLabelService;
import com.payMyBuddy.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TransactionLabelController {

    @Autowired
    private TransactionLabelService transactionLabelService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/transaction_label")
    public ResponseEntity<?> createTransactionLabel(@RequestBody TransactionLabel transactionLabel) {
        transactionLabelService.createTransactionLabel(transactionLabel);
        return new ResponseEntity<>("Transaction Label Created", HttpStatus.CREATED);
    }

    @GetMapping(value = "/transaction_label")
    public ResponseEntity<?> getTransactionLabel(@RequestParam("transactionLabelId")Long id) {
        Optional<TransactionLabel> transactionLabel = transactionLabelService.getTransactionLabelById(id);
        if (transactionLabel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>("Transaction Label found", HttpStatus.OK);
    }

    @PutMapping(value = "/transaction_label")
    public ResponseEntity<?> updateTransactionLabel(@RequestBody TransactionLabel transactionLabel) {
        if (transactionLabelService.getTransactionLabelById(transactionLabel.getTransactionLabelId()).isPresent()) {
            transactionLabelService.updateTransactionLabel(transactionLabel);
            return new ResponseEntity<>("Transaction Label updated", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/transaction_label")
    public ResponseEntity<?> deleteTransactionLabel(@RequestBody TransactionLabel transactionLabel) {
        transactionLabelService.deleteTransactionLabel(transactionLabel);
        return new ResponseEntity<>("Successful Operation", HttpStatus.OK);
    }

    @PutMapping(value = "/addTransactionLabel")
    public ResponseEntity<?> addTransactionLabel(@RequestParam Long transactionLabelId, @RequestParam Long transactionId) {
        if (transactionLabelService.getTransactionLabelById(transactionLabelId).isEmpty() || transactionService.getTransactionById(transactionId).isEmpty()) {
            return new ResponseEntity<>("Transaction Label doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        TransactionLabel transactionLabel = transactionLabelService.getTransactionLabelById(transactionLabelId).get();
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        if (!transactionLabel.getTransactions().contains(transaction)) {
            transactionLabelService.addTransactionLabel(transactionLabel, transaction);
            return new ResponseEntity<>("Transaction Added", HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/removeTransactionLabel")
    public ResponseEntity<?> removeTransactionLabel(@RequestParam Long transactionLabelId, @RequestParam Long transactionId) {
        if (transactionLabelService.getTransactionLabelById(transactionLabelId).isEmpty() || transactionService.getTransactionById(transactionId).isEmpty()) {
            return new ResponseEntity<>("Transaction Label doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        TransactionLabel transactionLabel = transactionLabelService.getTransactionLabelById(transactionLabelId).get();
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        if (transactionLabel.getTransactions().contains(transaction)) {
            transactionLabelService.removeTransactionLabel(transactionLabel, transaction);
            return new ResponseEntity<>("Transaction Removed", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

//    @DeleteMapping(value = "/removeTransactionLabel")
//    public ResponseEntity<?> removeTransactionLabel(@RequestParam int transactionId, @RequestParam int transactionLabelId) {
//        if (transactionService.getTransactionById(transactionId).isEmpty() || transactionLabelService.getTransactionLabelById(transactionLabelId).isEmpty()) {
//            return new ResponseEntity<>("Transaction doesn't exist in DB", HttpStatus.BAD_REQUEST);
//        }
//        Transaction transaction = transactionService.getTransactionById(transactionId).get();
//        TransactionLabel transactionLabel = transactionLabelService.getTransactionLabelById(transactionLabelId).get();
//        if (transaction.getTransactionLabels().contains(transactionLabel)) {
//            transactionService.removeTransactionLabel(transaction, transactionLabel);
//            return new ResponseEntity<>("Transaction Label Removed", HttpStatus.OK);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }
}
