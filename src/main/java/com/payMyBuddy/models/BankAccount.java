package com.payMyBuddy.models;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "Bank Account")
@DynamicUpdate
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @SequenceGenerator(
            name = "bank_account_sequence",
            sequenceName = "bank_account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bank_account_sequence"
    )
    @Column(name = "bank_account_id")
    private Long bankAccountId;

    @Column(name = "iban", nullable = false)
    private int iban;

    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
    private BigDecimal balance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User userId;

    public BankAccount(){}

    public BankAccount(int iban, BigDecimal balance) {
        this.iban = iban;
        this.balance = balance;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public int getIban() {
        return iban;
    }

    public void setIban(int iban) {
        this.iban = iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

}
