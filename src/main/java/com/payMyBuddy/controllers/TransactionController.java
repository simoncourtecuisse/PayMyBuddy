package com.payMyBuddy.controllers;

import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.TransactionLabel;
import com.payMyBuddy.models.User;
import com.payMyBuddy.services.TransactionLabelService;
import com.payMyBuddy.services.TransactionService;
import com.payMyBuddy.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    Logger LOGGER = LogManager.getLogger(Transaction.class);
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TransactionLabelService transactionLabelService;
    @Autowired
    private UserService userService;

    @PostMapping
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

    @GetMapping
    public ResponseEntity<?> getTransactionById(@RequestParam("transactionId") Long id) {
//    public ResponseEntity<?> getTransactionById(@RequestParam("userId") Long id) {
//        transactionService.getAllTransactionsByUserId(id);
//        return new ResponseEntity<>("Transaction found", HttpStatus.OK);

        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        if (transaction.isEmpty()) {
            LOGGER.error("Can't find the transaction");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LOGGER.info("Success find transaction");
        return new ResponseEntity<>("Transaction found", HttpStatus.OK);
    }

    @GetMapping("/transfers/{userId}")
    public ResponseEntity<?> getAllTransactionsByUser(@PathVariable("userId") Long id) {
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            return new ResponseEntity<>(transactionService.getAllTransactionsByUser(user), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/{transactionId}")
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

    @DeleteMapping("/{transactionId}")
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

    @PostMapping("/transfers/{userId}/payment/{userIdCreditor}")
    private ResponseEntity<?> makeTransaction(@PathVariable("userId") Long debtorId, @PathVariable("userIdCreditor") Long creditorId,  @RequestBody Transaction transaction) {
        if (userService.getUserById(debtorId).isEmpty() || userService.getUserById(creditorId).isEmpty()) {
            LOGGER.error("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User debtor = userService.getUserById(debtorId).get();
        User creditor = userService.getUserById(creditorId).get();
//        TransactionLabel label = transactionLabelService.getTransactionLabelById(transactionLabelId).get();
        TransactionLabel label = transaction.getTransactionLabel();
        Transaction paymentTransaction = transactionService.createTransaction(debtor, creditor, transaction.getAmount(), label);

        if (!debtor.getFriendList().contains(creditor)) {
            LOGGER.error("Creditor is not in debtor's Friend list");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (debtor.getWalletBalance().compareTo(BigDecimal.valueOf(paymentTransaction.getCommission())) < 0) {
            LOGGER.error("Not enough found in wallet");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (transactionService.processUserTransaction(paymentTransaction)) {
            transactionService.saveTransaction(paymentTransaction);
            LOGGER.info("Transaction success");
            return new ResponseEntity<>("Successful Transaction", HttpStatus.OK);
        }
        LOGGER.error("Failed to make the payment because of a BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

//   @PostMapping(value = "/transaction/payment")
//    private ResponseEntity<?> makeTransaction(@RequestBody Transaction transaction) {
//        if (transactionService.payment(transaction)) {
//            LOGGER.info("Transaction success");
//            return new ResponseEntity<>("Successful Transaction", HttpStatus.OK);
//        }
//        LOGGER.error("Failed to make the payment because of a BAD REQUEST");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }


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