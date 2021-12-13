package com.payMyBuddy.controllers;

import com.payMyBuddy.models.TransactionLabel;
import com.payMyBuddy.services.TransactionLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class TransactionLabelController {

    @Autowired
    private TransactionLabelService transactionLabelService;

    @PostMapping(value = "/transaction_label")
    public ResponseEntity<?> createTransactionLabel(@RequestBody TransactionLabel transactionLabel) {
        transactionLabelService.createTransactionLabel(transactionLabel);
        return new ResponseEntity<>("Transaction Label Created", HttpStatus.CREATED);
    }

    @GetMapping(value = "/transaction_label")
    public ResponseEntity<?> getTransactionLabel(@RequestParam("transactionLabelId")int id) {
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
}
