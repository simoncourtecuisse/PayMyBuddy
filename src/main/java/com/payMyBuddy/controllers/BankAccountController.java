package com.payMyBuddy.controllers;

import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.BankTransaction;
import com.payMyBuddy.models.User;
import com.payMyBuddy.services.BankAccountService;
import com.payMyBuddy.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200/", allowedHeaders = "*")
//@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RestController
//@RequestMapping("/bankAccount")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UserService userService;

    Logger LOGGER = LogManager.getLogger(BankAccount.class);

    @PostMapping
    public ResponseEntity<?> createBankAccount(@RequestBody BankAccount bankAccount) {
        bankAccountService.addBankAccount(bankAccount);
        return new ResponseEntity<>("Bank Account Created", HttpStatus.CREATED);
//        if (bankAccountService.getBankAccountById(bankAccount.getBankAccountId()).isPresent()) {
//            bankAccountService.addBankAccount(bankAccount);
//            LOGGER.info("Bank Account created successfully");
//            return new ResponseEntity<>("Bank Account Created", HttpStatus.CREATED);
//        }
//        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<?> getBankAccount(@RequestParam(value = "bankAccountId") Long id) {
        Optional<BankAccount> bankAccount = bankAccountService.getBankAccountById(id);
        if (bankAccount.isEmpty()) {
            LOGGER.error("Can't find the bank account");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LOGGER.info("Success find bank account");
        return new ResponseEntity<>("Bank Account found", HttpStatus.OK);
    }

    @PutMapping("/{bankAccountId}")
    public ResponseEntity<?> updateBankAccount(@PathVariable("bankAccountId") Long id, @RequestBody BankAccount bankAccount) {
        if (bankAccountService.getBankAccountById(id).isPresent()) {
            bankAccount.setBankAccountId(id);
            bankAccountService.updateBankAccount(bankAccount);
            LOGGER.info("Bank Account updated successfully");
            return new ResponseEntity<>("Bank Account updated", HttpStatus.OK);
        }
        LOGGER.error("Failed to update bank account because the bank account was not found");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{bankAccountId}")
    public ResponseEntity<?> deleteBankAccount(@PathVariable("bankAccountId") Long id) {
        if (bankAccountService.getBankAccountById(id).isPresent()) {
            BankAccount bankAccount = bankAccountService.getBankAccountById(id).get();
            bankAccountService.deleteBankAccount(bankAccount);
            LOGGER.info("Bank Account deleted successfully");
            return new ResponseEntity<>("Successful Operation", HttpStatus.OK);
        }
        LOGGER.error("Failed to delete bank account because of a BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/user/profile/bankAccounts/{userId}")
    public ResponseEntity<?> getAllBankAccountByUser(@PathVariable("userId") Long id) {
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            return new ResponseEntity<>(user.getBankAccountList(),HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("user/profile/{userId}/addBankAccount")
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

    @DeleteMapping("user/profile/{userId}/removeBankAccount/{bankAccountId}")
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

    @GetMapping("/user/profile/{userId}")
    public ResponseEntity<?> getAllBankTransactionsByUser(@PathVariable("userId") Long id) {
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            List<BankTransaction> bankTransactionList = user.getBankTransactionsList();
            var listOrder = bankTransactionList.stream()
                    .sorted(Comparator.comparing(BankTransaction::getDate).reversed())
                    .collect(Collectors.toList());
            return new ResponseEntity<>(listOrder,HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/user/profile/{userId}/credit")
    public ResponseEntity<?> bankToWallet(@PathVariable("userId") Long id, @RequestBody BankTransaction bankTransaction) {
        if (userService.getUserById(id).isEmpty()) {
            LOGGER.error("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userService.getUserById(id).get();
        BankAccount bankAccount = bankTransaction.getBankAccount();
        BankTransaction bankAccountTransaction = bankAccountService.createBankAccountTransaction(user, bankAccount, bankTransaction.getAmount());

        if (bankAccountService.bankToWallet(bankAccountTransaction)) {
            bankAccountService.saveBankTransaction(bankAccountTransaction);
            LOGGER.info("Transaction to wallet successfully processed");
            return new ResponseEntity<>(user.getBankTransactionsList(), HttpStatus.OK);
        } else {
            LOGGER.error("Failed to proceed bank account transaction");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/user/profile/{userId}/withdraw")
    public ResponseEntity<?> bankDeposit(@PathVariable("userId") Long id, @RequestBody BankTransaction bankTransaction) {
        if (userService.getUserById(id).isEmpty()) {
            LOGGER.error("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        User user = userService.getUserById(id).get();
        BankAccount bankAccount = bankTransaction.getBankAccount();
        BankTransaction bankAccountTransaction = bankAccountService.createBankAccountTransaction(user, bankAccount, bankTransaction.getAmount());

        var commission = bankAccountTransaction.getCommission();
        var userBalance = user.getWalletBalance();
        if (userBalance.compareTo(BigDecimal.valueOf(commission)) <  0) {
            LOGGER.error("Not enough found in wallet");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (bankAccountService.bankDeposit(bankAccountTransaction)) {
            bankAccountService.saveBankTransaction(bankAccountTransaction);
            LOGGER.info("Transaction to bank account successfully processed");
            return new ResponseEntity<>(user.getBankTransactionsList(), HttpStatus.OK);
        } else {
            LOGGER.error("Failed to proceed bank account transaction");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}