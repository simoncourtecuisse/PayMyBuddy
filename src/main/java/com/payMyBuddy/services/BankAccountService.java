package com.payMyBuddy.services;

import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public Iterable<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public Optional<BankAccount> getBankAccountById(Long id) {
        return bankAccountRepository.findById(id);
    }

    public Optional<BankAccount> getBankAccountByIban(Integer iban) {
        return bankAccountRepository.findByIban(iban);
    }

    public void addBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }

    public void updateBankAccount(BankAccount bankAccount) {
        Optional<BankAccount> maj = bankAccountRepository.findByIban(bankAccount.getIban());
        if (maj.isPresent()){
            bankAccountRepository.save(bankAccount);
        }
    }

    public void deleteBankAccount(BankAccount bankAccount) {
        Optional<BankAccount> removeBankAccount = bankAccountRepository.findByIban(bankAccount.getIban());
        if(removeBankAccount.isPresent()) {
            bankAccountRepository.deleteById(bankAccount.getBankAccountId());
        }
    }
}
