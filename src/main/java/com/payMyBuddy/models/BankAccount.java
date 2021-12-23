package com.payMyBuddy.models;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User userId;

    public BankAccount(){}

    public BankAccount(int iban) {
        this.iban = iban;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
