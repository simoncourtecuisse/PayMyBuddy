package com.payMyBuddy.services;

import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserService userService;

    public BankAccount createBankAccountTransaction(User userId, int iban, BigDecimal amount) {
        BankAccount bankAccountTransaction = new BankAccount();
        bankAccountTransaction.setUserId(userId);
        bankAccountTransaction.setIban(iban);
        bankAccountTransaction.setBalance(amount);
        return bankAccountTransaction;
    }

    public boolean processBankTransaction(BankAccount bankTransaction) {
        if (bankTransaction.getBalance().signum() <= 0) {
            return false;
        }

        User user = bankTransaction.getUserId();
        BigDecimal absBalance = new BigDecimal(String.valueOf(bankTransaction.getBalance().abs()));

        if (bankTransaction.getBalance().compareTo(BigDecimal.ZERO) > 0) {
            user.setBalance(user.getBalance().add(absBalance));
        } else if (bankTransaction.getBalance().compareTo(BigDecimal.ZERO) < 0 && absBalance.compareTo(user.getBalance()) <= 0) {
            user.setBalance(user.getBalance().subtract(absBalance));
        } else {
            return false;
        }
        userService.updateUser(user);
        return true;
    }

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
        Optional<BankAccount> maj = bankAccountRepository.findById(bankAccount.getBankAccountId());
        if (maj.isPresent()){
            bankAccountRepository.save(bankAccount);
        }
    }

    public void deleteBankAccount(BankAccount bankAccount) {
        Optional<BankAccount> removeBankAccount = bankAccountRepository.findById(bankAccount.getBankAccountId());
        if(removeBankAccount.isPresent()) {
            bankAccountRepository.deleteById(bankAccount.getBankAccountId());
        }
    }
}
