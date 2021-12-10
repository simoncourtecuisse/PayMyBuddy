package com.payMyBuddy.models;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private int bankAccountId;

    @Column(name = "iban", nullable = false)
    private int iban;

    public BankAccount(){}

    public BankAccount(int bankAccountId, int iban) {
        this.bankAccountId = bankAccountId;
        this.iban = iban;
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
}
