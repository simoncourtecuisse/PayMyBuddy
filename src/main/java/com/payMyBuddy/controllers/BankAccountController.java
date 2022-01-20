package com.payMyBuddy.controllers;

import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.services.BankAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    Logger LOGGER = LogManager.getLogger(BankAccount.class);

    @PostMapping(value = "/bankAccount")
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

    @GetMapping(value = "/bankAccount")
    public ResponseEntity<?> getBankAccount(@RequestParam(value = "bankAccountId") Long id) {
        Optional<BankAccount> bankAccount = bankAccountService.getBankAccountById(id);
        if (bankAccount.isEmpty()) {
            LOGGER.error("Can't find the bank account");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LOGGER.info("Success find bank account");
        return new ResponseEntity<>("Bank Account found", HttpStatus.OK);
    }

    @PutMapping(value = "/bankAccount/{bankAccountId}")
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

    @DeleteMapping("/bankAccount/{bankAccountId}")
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

}