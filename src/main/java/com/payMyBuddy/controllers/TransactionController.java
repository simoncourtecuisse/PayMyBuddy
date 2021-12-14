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
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionLabelService transactionLabelService;

    @PostMapping(value = "/transaction")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(transaction);
        return new ResponseEntity<>("Transaction Created", HttpStatus.CREATED);
    }

    @GetMapping(value = "/transaction")
    public ResponseEntity<?> getTransactionById(@RequestParam("transactionId")int id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        if (transaction.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>("Transaction found", HttpStatus.OK);
    }

    @PutMapping(value = "/transaction")
    public ResponseEntity<?> updateTransaction(@RequestBody Transaction transaction) {
        if (transactionService.getTransactionById(transaction.getTransactionId()).isPresent()) {
            transactionService.updateTransaction(transaction);
            return new ResponseEntity<>("Transaction updated", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/transaction")
    public ResponseEntity<?> deleteTransaction(@RequestBody Transaction transaction) {
        transactionService.deleteTransaction(transaction);
        return new ResponseEntity<>("Successful Operation", HttpStatus.OK);
    }

    @PutMapping(value = "/addTransactionLabel")
    public ResponseEntity<?> addTransactionLabel(@RequestParam int transactionId, @RequestParam int transactionLabelId) {
        if (transactionService.getTransactionById(transactionId).isEmpty() || transactionLabelService.getTransactionLabelById(transactionLabelId).isEmpty()) {
            return new ResponseEntity<>("Transaction doesn't exist in DB", HttpStatus.BAD_REQUEST);
        }
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        TransactionLabel transactionLabel = transactionLabelService.getTransactionLabelById(transactionLabelId).get();
        if (!transaction.getTransactionLabels().contains(transactionLabel)) {
            transactionService.addTransactionLabel(transaction, transactionLabel);
            return new ResponseEntity<>("Transaction Label Added", HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/removeTransactionLabel")
    public ResponseEntity<?> removeTransactionLabel(@RequestParam int transactionId, @RequestParam int transactionLabelId) {
        if (transactionService.getTransactionById(transactionId).isEmpty() || transactionLabelService.getTransactionLabelById(transactionLabelId).isEmpty()) {
            return new ResponseEntity<>("Transaction doesn't exist in DB", HttpStatus.BAD_REQUEST);
        }
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        TransactionLabel transactionLabel = transactionLabelService.getTransactionLabelById(transactionLabelId).get();
        if (transaction.getTransactionLabels().contains(transactionLabel)) {
            transactionService.removeTransactionLabel(transaction, transactionLabel);
            return new ResponseEntity<>("Transaction Label Removed", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
