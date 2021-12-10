package com.payMyBuddy.controllers;

import com.payMyBuddy.models.Transaction;
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
}
