package com.payMyBuddy.services;

import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.BankTransaction;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.BankAccountRepository;
import com.payMyBuddy.repositories.BankTransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    Logger LOGGER = LogManager.getLogger(BankAccount.class);

    public BankTransaction createBankAccountTransaction(User userId, BankAccount bankAccountId, double amount) {
        BankTransaction bankAccountTransaction = new BankTransaction();
        var amount1 = amount;
        double rate = 0.05;
        var fare = amount1 * rate;
        var com = amount1 + fare;

        bankAccountTransaction.setUser(userId);
        bankAccountTransaction.setBankAccount(bankAccountId);
        bankAccountTransaction.setAmount(amount);
        bankAccountTransaction.setDate(LocalDate.now());
        bankAccountTransaction.setCommission(com);

        return bankAccountTransaction;
    }

    public BankTransaction saveBankTransaction(BankTransaction bankAccountTransaction) {
        return bankTransactionRepository.save(bankAccountTransaction);
    }

    public boolean processBankTransaction(BankTransaction bankTransaction) {
        var amount = bankTransaction.getAmount();
        var com = bankTransaction.getCommission();

        if (amount == 0) {
            return false;
        }

        User user = bankTransaction.getUser();
        //BankAccount bankAccount = bankTransaction.getBankAccount();
        double absTotal = Math.abs(com);

        if (amount > 0) {
            user.setWalletBalance(user.getWalletBalance().add(BigDecimal.valueOf(absTotal)));
        } else if (amount < 0 && BigDecimal.valueOf(absTotal).compareTo(user.getWalletBalance()) <= 0) {
            user.setWalletBalance(user.getWalletBalance().subtract(BigDecimal.valueOf(absTotal)));
        }
//        else if (amount < 0 && BigDecimal.valueOf(absTotal).compareTo(user.getWalletBalance()) > 0) {
//            LOGGER.error("Not enough found in wallet");        }
        else {
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
