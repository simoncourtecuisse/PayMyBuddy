package com.payMyBuddy.controllers;

import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
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

@CrossOrigin(origins = "http://localhost:4200/", allowedHeaders = "*")

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    Logger LOGGER = LogManager.getLogger(Transaction.class);
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        transactionService.createTransaction(transaction);
        return new ResponseEntity<>("Transaction Created", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getTransactionById(@RequestParam("transactionId") Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        if (transaction.isEmpty()) {
            LOGGER.error("Can't find the transaction");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LOGGER.info("Success find transaction");
        return new ResponseEntity<>("Transaction found", HttpStatus.OK);
    }

    @GetMapping("/transfers")
    public ResponseEntity<?> getAllTransactions() {
        return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
    }


    @GetMapping("/transfers/{userId}")
    public ResponseEntity<?> getAllTransactionsByUser(@PathVariable("userId") Long id) {
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            var transactions = transactionService.getAllTransactionsByUser(user);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
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

    @PostMapping("/transfers/{userId}/payment")
    public ResponseEntity<?> makeTransaction(@PathVariable("userId") Long id, @RequestBody Transaction transaction) {
        if (userService.getUserById(id).isEmpty()) {
            LOGGER.error("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User debtor = userService.getUserById(id).get();
        User creditorId = transaction.getCreditor();
        User creditor = userService.getUserById(creditorId.getUserId()).get();
        System.out.println(creditor);
        Transaction paymentTransaction = transactionService.createTransaction(debtor, creditor, transaction.getAmount(), transaction.getDescription());
        System.out.println(paymentTransaction);

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
            var transactions = transactionService.getAllTransactionsByUser(debtor);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        }
        LOGGER.error("Failed to make the payment because of a BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}