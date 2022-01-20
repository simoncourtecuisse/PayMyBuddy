package com.payMyBuddy.controllers;

import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.services.TransactionLabelService;
import com.payMyBuddy.services.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
public class TransactionController {

    Logger LOGGER = LogManager.getLogger(Transaction.class);
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionLabelService transactionLabelService;

    @PostMapping(value = "/transaction")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(transaction);
        return new ResponseEntity<>("Transaction Created", HttpStatus.CREATED);
//        if (transactionService.getTransactionById(transaction.getTransactionId()).isEmpty()) {
//            transactionService.createTransaction(transaction);
//            LOGGER.info("Transaction created successfully");
//            return new ResponseEntity<>("Transaction Created", HttpStatus.CREATED);
//        }
//        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/transaction")
    public ResponseEntity<?> getTransactionById(@RequestParam("transactionId") Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        if (transaction.isEmpty()) {
            LOGGER.error("Can't find the transaction");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LOGGER.info("Success find transaction");
        return new ResponseEntity<>("Transaction found", HttpStatus.OK);
    }

    @PutMapping(value = "/transaction/{transactionId}")
    public ResponseEntity<?> updateTransaction(@PathVariable("transactionId") Long id, @RequestBody Transaction transaction) {
        if (transactionService.getTransactionById(id).isPresent()) {
            transaction.setTransactionId(id);
            transactionService.updateTransaction(transaction);
            LOGGER.info("Transaction updated successfully");
            return new ResponseEntity<>("Transaction updated", HttpStatus.OK);
        }
        LOGGER.error("Failed to update transaction because the transaction was not found");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/transaction/{transactionId}")
    public ResponseEntity<?> deleteTransaction(@PathVariable("transactionId") Long id) {
        if (transactionService.getTransactionById(id).isPresent()) {
            Transaction transaction = transactionService.getTransactionById(id).get();
            transactionService.deleteTransaction(transaction);
            LOGGER.info("Transaction deleted successfully");
            return new ResponseEntity<>("Successful Operation", HttpStatus.OK);
        }
        LOGGER.error("Failed to delete transaction because of a BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping(value = "/transaction/payment")
    private ResponseEntity<?> makeTransaction(@RequestBody Transaction transaction) {
        if (transactionService.payment(transaction)) {
            LOGGER.info("Transaction success");
            return new ResponseEntity<>("Successful Transaction", HttpStatus.OK);
        }
        LOGGER.error("Failed to make the payment because of a BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


//    @PutMapping(value = "/addTransactionLabel")
//    public ResponseEntity<?> addTransactionLabel(@RequestParam int transactionId, @RequestParam int transactionLabelId) {
//        if (transactionService.getTransactionById(transactionId).isEmpty() || transactionLabelService.getTransactionLabelById(transactionLabelId).isEmpty()) {
//            return new ResponseEntity<>("Transaction doesn't exist in DB", HttpStatus.BAD_REQUEST);
//        }
//        Transaction transaction = transactionService.getTransactionById(transactionId).get();
//        TransactionLabel transactionLabel = transactionLabelService.getTransactionLabelById(transactionLabelId).get();
//        if (!transaction.getTransactionLabels().contains(transactionLabel)) {
//            transactionService.addTransactionLabel(transaction, transactionLabel);
//            return new ResponseEntity<>("Transaction Label Added", HttpStatus.CREATED);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }
//
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