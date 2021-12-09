package com.payMyBuddy.controllers;

import com.payMyBuddy.models.User;
import com.payMyBuddy.services.BankAccountService;
import com.payMyBuddy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UserService userService;

//    @PostMapping(value = "/bankAccount")
//    public ResponseEntity<?> addBankAccount(@RequestParam("userBankAccount") User userBankAccount, @RequestParam("iban") int iban) {
//        bankAccountService.addBankAccount(userBankAccount);
//    }

}
