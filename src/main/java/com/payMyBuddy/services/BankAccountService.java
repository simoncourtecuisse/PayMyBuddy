package com.payMyBuddy.services;

import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void addBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }


//    public BankAccount createBankTransaction (int bankAccountId, int iban, User userBankAccount) {
//        BankAccount bankTransaction = new BankAccount(bankAccountId, iban, userBankAccount);
//        bankTransaction.setBankAccountId(bankAccountId);
//        bankTransaction.setIban(iban);
//        bankTransaction.setUserBankAccount(userBankAccount);
//        return bankTransaction;
//    }
//
//    public List<BankAccount> findByUser(User userBankAccount){
//        return bankAccountRepository.findAllByUserBankAccount(userBankAccount);
//    }
//
//    public BankAccount saveBankTransaction(BankAccount bankTransaction) {
//        return bankAccountRepository.save(bankTransaction);
//    }
}
