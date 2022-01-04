package com.payMyBuddy.controllers;


import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
import com.payMyBuddy.services.BankAccountService;
import com.payMyBuddy.services.TransactionService;
import com.payMyBuddy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> getUserByEmail(@RequestParam("email") String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>("User found", HttpStatus.OK);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long id, @RequestBody User user) {
        if(userService.getUserById(id).isPresent()) {
            user = userService.getUserById(id).get();
//        if(userService.getUserById(user.getUserId()).isPresent()) {
            userService.updateUser(user);
            return new ResponseEntity<>("User updated", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

//    @PutMapping(value = "/user")
//    public ResponseEntity<?> updateUser(@RequestBody User user) {
//        if(userService.getUserById(user.getUserId()).isPresent()) {
//            userService.updateUser(user);
//            return new ResponseEntity<>("User updated", HttpStatus.OK);
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long id){
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            userService.deleteUser(user);
            return new ResponseEntity<>("Successful Operation", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(value = "/addFriend")
    public ResponseEntity<?> addFriend(@RequestParam Long fromUser, @RequestParam Long toUser) {
        if (userService.getUserById(fromUser).isEmpty() || userService.getUserById(toUser).isEmpty()) {
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User user = userService.getUserById(fromUser).get();
        User friendUser = userService.getUserById(toUser).get();
        if (!user.getFriendList().contains(friendUser)) {
            userService.addFriend(user, friendUser);
            return new ResponseEntity<>("Friend Added", HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/removeFriend")
    public ResponseEntity<?> removeFriend(@RequestParam Long fromUser, @RequestParam Long toUser) {
        if (userService.getUserById(fromUser).isEmpty() || userService.getUserById(toUser).isEmpty()) {
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User user = userService.getUserById(fromUser).get();
        User friendUser = userService.getUserById(toUser).get();
        if (user.getFriendList().contains(friendUser)) {
            userService.removeFriend(user, friendUser);
            return new ResponseEntity<>("Friend Removed", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(value = "/addBankAccount")
    public ResponseEntity<?> addBankAccount(@RequestParam Long userId, @RequestParam Long bankAccountId) {
        if (userService.getUserById(userId).isEmpty() || bankAccountService.getBankAccountById(bankAccountId).isEmpty()){
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User user = userService.getUserById(userId).get();
        BankAccount bankAccount = bankAccountService.getBankAccountById(bankAccountId).get();
        if (!user.getUserBankAccount().contains(bankAccount)) {
            userService.addBankAccount(user, bankAccount);
            return new ResponseEntity<>("Bank Account Added", HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/removeBankAccount")
    public ResponseEntity<?> removeBankAccount(@RequestParam Long userId, @RequestParam Long bankAccountId) {
        if (userService.getUserById(userId).isEmpty() || bankAccountService.getBankAccountById(bankAccountId).isEmpty()){
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User user = userService.getUserById(userId).get();
        BankAccount bankAccount = bankAccountService.getBankAccountById(bankAccountId).get();
        if (user.getUserBankAccount().contains(bankAccount)) {
            userService.removeBankAccount(user, bankAccount);
            return new ResponseEntity<>("Bank Account Removed", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(value = "/addCreditorToTransaction")
    public ResponseEntity<?> addCreditorToTransaction(@RequestParam Long creditorId, @RequestParam Long transactionId) {
        if (userService.getUserById(creditorId).isEmpty() || transactionService.getTransactionById(transactionId).isEmpty()) {
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User creditor = userService.getUserById(creditorId).get();
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        if (!creditor.getCreditorList().contains(transaction)) {
            userService.addCreditorToTransaction(creditor, transaction);
            return new ResponseEntity<>("Creditor Added to Transaction", HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/removeCreditorToTransaction")
    public ResponseEntity<?> removeCreditorToTransaction(@RequestParam Long creditorId, @RequestParam Long transactionId) {
        if (userService.getUserById(creditorId).isEmpty() || transactionService.getTransactionById(transactionId).isEmpty()) {
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User creditor = userService.getUserById(creditorId).get();
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        if (creditor.getCreditorList().contains(transaction)) {
            userService.removeCreditorToTransaction(creditor, transaction);
            return new ResponseEntity<>("Creditor Removed to Transaction", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping(value = "/addDebtorToTransaction")
    public ResponseEntity<?> addDebtorToTransaction(@RequestParam Long debtorId, @RequestParam Long transactionId) {
        if (userService.getUserById(debtorId).isEmpty() || transactionService.getTransactionById(transactionId).isEmpty()) {
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User debtor = userService.getUserById(debtorId).get();
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        if (!debtor.getDebtorList().contains(transaction)) {
            userService.addDebtorToTransaction(debtor, transaction);
            return new ResponseEntity<>("Debtor Added to Transaction", HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/removeDebtorToTransaction")
    public ResponseEntity<?> removeDebtorToTransaction(@RequestParam Long debtorId, @RequestParam Long transactionId) {
        if (userService.getUserById(debtorId).isEmpty() || transactionService.getTransactionById(transactionId).isEmpty()) {
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User debtor = userService.getUserById(debtorId).get();
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        if (debtor.getDebtorList().contains(transaction)) {
            userService.removeDebtorToTransaction(debtor, transaction);
            return new ResponseEntity<>("Debtor Removed to Transaction", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
