package com.payMyBuddy.controllers;

import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.TransactionLabel;
import com.payMyBuddy.services.TransactionLabelService;
import com.payMyBuddy.services.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    Logger LOGGER = LogManager.getLogger(TransactionLabel.class);

    @PostMapping(value = "/transaction_label")
    public ResponseEntity<?> createTransactionLabel(@RequestBody TransactionLabel transactionLabel) {
        if (transactionLabelService.getTransactionLabelById(transactionLabel.getTransactionLabelId()).isEmpty()) {
            transactionLabelService.createTransactionLabel(transactionLabel);
            LOGGER.info("Transaction Label created successfully");
            return new ResponseEntity<>("Transaction Label Created", HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/transaction_label")
    public ResponseEntity<?> getTransactionLabel(@RequestParam("transactionLabelId")Long id) {
        Optional<TransactionLabel> transactionLabel = transactionLabelService.getTransactionLabelById(id);
        if (transactionLabel.isEmpty()) {
            LOGGER.error("Can't find the transaction label");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LOGGER.info("Success find transaction label");
        return new ResponseEntity<>("Transaction Label found", HttpStatus.OK);
    }

    @PutMapping(value = "/transaction_label{transactionLabelId}")
    public ResponseEntity<?> updateTransactionLabel(@PathVariable("transactionLabelId") Long id, @RequestBody TransactionLabel transactionLabel) {
        if (transactionLabelService.getTransactionLabelById(id).isPresent()) {
            transactionLabel.setTransactionLabelId(id);
            transactionLabelService.updateTransactionLabel(transactionLabel);
            LOGGER.info("Transaction Label updated successfully");
            return new ResponseEntity<>("Transaction Label updated", HttpStatus.OK);
        }
        LOGGER.error("Failed to update transaction label because the user was not found");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/transaction_label/{transactionLabelId}")
    public ResponseEntity<?> deleteTransactionLabel(@PathVariable("transactionLabelId") Long id) {
        if (transactionLabelService.getTransactionLabelById(id).isPresent()) {
            TransactionLabel transactionLabel = transactionLabelService.getTransactionLabelById(id).get();
            transactionLabelService.deleteTransactionLabel(transactionLabel);
            LOGGER.info("Transaction Label deleted successfully");
            return new ResponseEntity<>("Successful Operation", HttpStatus.OK);
        }
        LOGGER.error("Failed to delete transaction label because the user was not found");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(value = "/addTransactionLabel")
    public ResponseEntity<?> addTransactionLabel(@RequestParam Long transactionLabelId, @RequestParam Long transactionId) {
        if (transactionLabelService.getTransactionLabelById(transactionLabelId).isEmpty() || transactionService.getTransactionById(transactionId).isEmpty()) {
            LOGGER.error("Transaction Label doesn't exist in DB");
            return new ResponseEntity<>("Transaction Label doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        TransactionLabel transactionLabel = transactionLabelService.getTransactionLabelById(transactionLabelId).get();
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        if (!transactionLabel.getTransactions().contains(transaction)) {
            transactionLabelService.addTransactionLabel(transactionLabel, transaction);
            LOGGER.info("Add transaction label success");
            return new ResponseEntity<>("Transaction Added", HttpStatus.CREATED);
        }
        LOGGER.error("Add transaction label failed because of a bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/removeTransactionLabel")
    public ResponseEntity<?> removeTransactionLabel(@RequestParam Long transactionLabelId, @RequestParam Long transactionId) {
        if (transactionLabelService.getTransactionLabelById(transactionLabelId).isEmpty() || transactionService.getTransactionById(transactionId).isEmpty()) {
            LOGGER.error("Transaction Label doesn't exist in DB");
            return new ResponseEntity<>("Transaction Label doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        TransactionLabel transactionLabel = transactionLabelService.getTransactionLabelById(transactionLabelId).get();
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        if (transactionLabel.getTransactions().contains(transaction)) {
            transactionLabelService.removeTransactionLabel(transactionLabel, transaction);
            LOGGER.info("Remove transaction label success");
            return new ResponseEntity<>("Transaction Removed", HttpStatus.OK);
        }
        LOGGER.error("Remove transaction label failed because of a bad request");
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
