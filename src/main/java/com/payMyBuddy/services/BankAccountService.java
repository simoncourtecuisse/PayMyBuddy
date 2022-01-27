package com.payMyBuddy.services;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.BankTransaction;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.BankAccountRepository;
import com.payMyBuddy.repositories.BankTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @Autowired
    private UserService userService;

    public BankTransaction createBankAccountTransaction(User userId, BankAccount bankAccountId, double amount) {
        BankTransaction bankAccountTransaction = new BankTransaction();
        bankAccountTransaction.setUserId(userId);
        bankAccountTransaction.setBankAccountId(bankAccountId);
        bankAccountTransaction.setAmount(amount);
        return bankAccountTransaction;
    }

    public BankTransaction saveBankTransaction(BankTransaction bankAccountTransaction) {
        return bankTransactionRepository.save(bankAccountTransaction);
    }

    public boolean processBankTransaction(BankTransaction bankTransaction) {
        var amount = bankTransaction.getAmount();
        double com = 0.05;
        var fare = amount * com;
        var total = amount + fare;


        if (amount == 0) {
            return false;
        }

        User user = bankTransaction.getUserId();
        BankAccount bankAccount = bankTransaction.getBankAccountId();
        double absTotal = Math.abs(total);

        if (amount > 0) {
            user.setWalletBalance(user.getWalletBalance().add(BigDecimal.valueOf(absTotal)));
        } else if (amount < 0 && BigDecimal.valueOf(absTotal).compareTo(user.getWalletBalance()) <= 0) {
            user.setWalletBalance(user.getWalletBalance().subtract(BigDecimal.valueOf(absTotal)));
        } else {
            return false;
        }
        userService.updateUser(user);
        return true;
    }
//    public boolean processBankTransaction(BankTransaction bankTransaction) {
//        if (bankTransaction.getBalance().signum() <= 0) {
//            return false;
//        }
//
//        User user = bankTransaction.getUserId();
//        BigDecimal absBalance = new BigDecimal(String.valueOf(bankTransaction.getBalance().abs()));
//
//        if (bankTransaction.getBalance().compareTo(BigDecimal.ZERO) > 0) {
//            user.setWalletBalance(user.getWalletBalance().add(absBalance));
//        } else if (bankTransaction.getBalance().compareTo(BigDecimal.ZERO) < 0 && absBalance.compareTo(user.getWalletBalance()) <= 0) {
//            user.setWalletBalance(user.getWalletBalance().subtract(absBalance));
//        } else {
//            return false;
//        }
//        userService.updateUser(user);
//        return true;
//    }

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
