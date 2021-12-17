package com.payMyBuddy.controllers;

import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping(value = "/bankAccount")
    public ResponseEntity<?> createBankAccount(@RequestBody BankAccount bankAccount) {
        bankAccountService.addBankAccount(bankAccount);
        return new ResponseEntity<>("Bank Account Created", HttpStatus.CREATED);
    }

    @GetMapping(value = "/bankAccount")
    public ResponseEntity<?> getBankAccount(@RequestParam(value = "bankAccountId") Long id) {
        Optional<BankAccount> bankAccount = bankAccountService.getBankAccountById(id);
        if (bankAccount.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>("Bank Account found", HttpStatus.OK);
    }

    @PutMapping(value = "/bankAccount")
    public ResponseEntity<?> updateBankAccount(@RequestBody BankAccount bankAccount) {
        if (bankAccountService.getBankAccountById(bankAccount.getBankAccountId()).isPresent()) {
            bankAccountService.updateBankAccount(bankAccount);
            return new ResponseEntity<>("Bank Account updated", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/bankAccount")
    public ResponseEntity<?> deleteBankAccount(@RequestParam(value = "bankAccountId") Long id) {
        BankAccount bankAccount = bankAccountService.getBankAccountById(id).get();
        bankAccountService.deleteBankAccount(bankAccount);
        return new ResponseEntity<>("Successful Operation", HttpStatus.OK);
    }

}
