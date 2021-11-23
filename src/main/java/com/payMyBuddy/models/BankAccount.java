package com.payMyBuddy.models;

import javax.persistence.*;

@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id", nullable = false)
    private int bankAccountId;

    @Column(name = "iban", nullable = false)
    private int iban;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User userBankAccount;

    public BankAccount(int bankAccountId, int iban, User userBankAccount) {
        this.bankAccountId = bankAccountId;
        this.iban = iban;
        this.userBankAccount = userBankAccount;
    }

    public int getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(int bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public int getIban() {
        return iban;
    }

    public void setIban(int iban) {
        this.iban = iban;
    }

    public User getUserBankAccount() {
        return userBankAccount;
    }

    public void setUserBankAccount(User userBankAccount) {
        this.userBankAccount = userBankAccount;
    }
}
