package com.payMyBuddy.controllers;


import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.BankAccountRepository;
import com.payMyBuddy.services.BankAccountService;
import com.payMyBuddy.services.TransactionService;
import com.payMyBuddy.services.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    Logger LOGGER = LogManager.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()).isEmpty()) {
            userService.createUser(user);
            LOGGER.info("User {} created successfully", user.getEmail());
            return new ResponseEntity<>("User Created", HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

//    @RolesAllowed("ADMIN")
    @GetMapping
    public ResponseEntity<?> getUserByEmail(@RequestParam("email") String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            LOGGER.error("Can't find the user based on this email");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LOGGER.info("Success find user by email");
        return new ResponseEntity<>("User found", HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/contacts/{userId}")
    public ResponseEntity<?> getAllFriendsByUser(@PathVariable("userId") Long id) {
        if (userService.getUserById(id).isPresent()) {
        User user = userService.getUserById(id).get();
        return new ResponseEntity<>(user.getFriendList(),HttpStatus.OK);
    }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getAllBankAccountByUser(@PathVariable("userId") Long id) {
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            return new ResponseEntity<>(user.getBankAccountList(),HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long id, @RequestBody User user) {
        if (userService.getUserById(id).isPresent()) {
            user.setUserId(id);
            userService.updateUser(user);
            LOGGER.info("User updated successfully");
            return new ResponseEntity<>("User updated", HttpStatus.OK);
        }
        LOGGER.error("Failed to update user because the user was not found");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long id) {
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            userService.deleteUser(user);
            LOGGER.info("User deleted successfully");
            return new ResponseEntity<>("Successful Operation", HttpStatus.OK);
        }
        LOGGER.error("Failed to delete user because of a BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/contacts/{userId}/addFriend/{friendUserId}")
    public ResponseEntity<?> addFriend(@PathVariable("userId") Long fromUser, @PathVariable("friendUserId") Long toUser) {
        if (userService.getUserById(fromUser).isEmpty() || userService.getUserById(toUser).isEmpty()) {
            LOGGER.error("User doesn't exist in DB");
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User user = userService.getUserById(fromUser).get();
        User friendUser = userService.getUserById(toUser).get();
        if (!user.getFriendList().contains(friendUser)) {
            userService.addFriend(user, friendUser);
            userService.addFriend(friendUser,user);
            LOGGER.info("Add friend success");
            return new ResponseEntity<>(user.getFriendList(), HttpStatus.CREATED);
        }
        LOGGER.error("Add friend failed because of a bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/contacts/{userId}/removeFriend/{friendUserId}")
//    public ResponseEntity<?> removeFriend(@RequestParam Long fromUser, @RequestParam Long toUser) {
    public ResponseEntity<?> removeFriend(@PathVariable("userId") Long fromUser, @PathVariable("friendUserId") Long toUser) {
        if (userService.getUserById(fromUser).isEmpty() || userService.getUserById(toUser).isEmpty()) {
            LOGGER.error("User doesn't exist in DB");
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User user = userService.getUserById(fromUser).get();
        User friendUser = userService.getUserById(toUser).get();
        if (user.getFriendList().contains(friendUser)) {
//            user.setFriendList(List.of(user));
            userService.removeFriend(user, friendUser);
            userService.removeFriend(friendUser, user);
            LOGGER.info("Remove friend success");
            System.out.println(user.getFriendList());
            return new ResponseEntity<>(user.getFriendList(), HttpStatus.OK);
        }
        LOGGER.error("Remove friend failed because of a bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/profile/{userId}/addBankAccount")
    public ResponseEntity<?> addBankAccount(@PathVariable("userId") Long userId, @RequestBody BankAccount bankAccount) {
        if (userService.getUserById(userId).isEmpty()) {
            LOGGER.error("User doesn't exist in DB");
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User user = userService.getUserById(userId).get();
        //BankAccount bankAccount = bankAccountService.getBankAccountById(bankAccountId).get();
        if (!user.getBankAccountList().contains(bankAccount)) {
            bankAccount.setUser(user);
            userService.addBankAccount(user, bankAccount);


            System.out.println(bankAccount);
//            bankAccountRepository.save(bankAccount);
//            bankAccount.setUser(user);
            LOGGER.info("Add bank account success");
            return new ResponseEntity<>(user.getBankAccountList(), HttpStatus.CREATED);
        }
        LOGGER.error("Add bank account failed because of a bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/profile/{userId}/removeBankAccount/{bankAccountId}")
    public ResponseEntity<?> removeBankAccount(@PathVariable("userId") Long userId, @PathVariable("bankAccountId") Long bankAccountId) {
        if (userService.getUserById(userId).isEmpty() || bankAccountService.getBankAccountById(bankAccountId).isEmpty()) {
            LOGGER.error("User doesn't exist in DB");
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User user = userService.getUserById(userId).get();
        BankAccount bankAccount = bankAccountService.getBankAccountById(bankAccountId).get();
        if (user.getBankAccountList().contains(bankAccount)) {
            userService.removeBankAccount(user, bankAccount);
            bankAccountService.deleteBankAccount(bankAccount);
            LOGGER.info("Remove bank account success");
            return new ResponseEntity<>(user.getBankAccountList(), HttpStatus.OK);
        }
        LOGGER.error("Remove bank account failed because of a bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/addCreditorToTransaction")
    public ResponseEntity<?> addCreditorToTransaction(@RequestParam Long creditorId, @RequestParam Long transactionId) {
        if (userService.getUserById(creditorId).isEmpty() || transactionService.getTransactionById(transactionId).isEmpty()) {
            LOGGER.error("User doesn't exist in DB");
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User creditor = userService.getUserById(creditorId).get();
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        if (!creditor.getCreditorList().contains(transaction)) {
            userService.addCreditorToTransaction(creditor, transaction);
            LOGGER.info("Add creditor to transaction success");
            return new ResponseEntity<>("Creditor Added to Transaction", HttpStatus.CREATED);
        }
        LOGGER.error("Add creditor to transaction failed because of a bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/removeCreditorToTransaction")
    public ResponseEntity<?> removeCreditorToTransaction(@RequestParam Long creditorId, @RequestParam Long transactionId) {
        if (userService.getUserById(creditorId).isEmpty() || transactionService.getTransactionById(transactionId).isEmpty()) {
            LOGGER.error("User doesn't exist in DB");
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User creditor = userService.getUserById(creditorId).get();
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        if (creditor.getCreditorList().contains(transaction)) {
            userService.removeCreditorToTransaction(creditor, transaction);
            LOGGER.info("Remove creditor to transaction success");
            return new ResponseEntity<>("Creditor Removed to Transaction", HttpStatus.OK);
        }
        LOGGER.error("Remove creditor to transaction failed because of a bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/addDebtorToTransaction")
    public ResponseEntity<?> addDebtorToTransaction(@RequestParam Long debtorId, @RequestParam Long transactionId) {
        if (userService.getUserById(debtorId).isEmpty() || transactionService.getTransactionById(transactionId).isEmpty()) {
            LOGGER.error("User doesn't exist in DB");
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User debtor = userService.getUserById(debtorId).get();
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        if (!debtor.getDebtorList().contains(transaction)) {
            userService.addDebtorToTransaction(debtor, transaction);
            LOGGER.info("Add debtor to transaction success");
            return new ResponseEntity<>("Debtor Added to Transaction", HttpStatus.CREATED);
        }
        LOGGER.error("Add debtor to transaction failed because of a bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/removeDebtorToTransaction")
    public ResponseEntity<?> removeDebtorToTransaction(@RequestParam Long debtorId, @RequestParam Long transactionId) {
        if (userService.getUserById(debtorId).isEmpty() || transactionService.getTransactionById(transactionId).isEmpty()) {
            LOGGER.error("User doesn't exist in DB");
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User debtor = userService.getUserById(debtorId).get();
        Transaction transaction = transactionService.getTransactionById(transactionId).get();
        if (debtor.getDebtorList().contains(transaction)) {
            userService.removeDebtorToTransaction(debtor, transaction);
            LOGGER.info("Remove debtor to transaction success");
            return new ResponseEntity<>("Debtor Removed to Transaction", HttpStatus.OK);
        }
        LOGGER.error("Remove debtor to transaction failed because of a bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody User user) {
        if(userService.getUserByEmail(user.getEmail()).isPresent()) {
            User authenticatedUser = userService.getUserByEmail(user.getEmail()).get();
            LOGGER.info("Authentication success");
            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
       } else {
            LOGGER.error("Authentication failed");

           return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
       }
    }

//    @GetMapping(value = "/transactions")
//    private ResponseEntity<?> getAllTransactionsByUser(@RequestBody User user) {
//        List<Transaction> userTransactions = transactionService.getAllTransactionsByUser(user);
//        if (userTransactions.isEmpty()) {
//            LOGGER.error("Can't find transactions for this user");
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        LOGGER.info("Success find user's Transactions ");
//        return new ResponseEntity<>("User found", HttpStatus.OK);
//    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<?> authenticate(@RequestParam String email, @RequestParam String password, @RequestBody User user) {
//        if(userService.getUserByEmail(email).isPresent() && user.getEmail().equals(email) && user.getPassword().equals(password)) {
//            LOGGER.info("Authentication success");
//            return new ResponseEntity<>("Authentication is successful", HttpStatus.OK);
//        } else {
//            LOGGER.error("Authentication failed");
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
}