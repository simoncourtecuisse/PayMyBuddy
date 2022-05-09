package com.payMyBuddy.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "bankName")
    private String bankName;

//    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
//    private BigDecimal balance;

    //@JsonIgnoreProperties("bankAccountList")
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @JsonIgnoreProperties("bankAccount")
    @OneToMany(
            mappedBy = "bankAccount",
            cascade = CascadeType.MERGE)
    List<BankTransaction> bankTransactionsList = new ArrayList<>();

    public BankAccount(){}

    public BankAccount(int iban, String bankName) {
        this.iban = iban;
        this.bankName = bankName;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "bankAccountId=" + bankAccountId +
                ", iban=" + iban +
                ", bankName='" + bankName + '\'' +
                ", user=" + user +
                //", bankTransactionsList=" + bankTransactionsList +
                '}';
    }
}

