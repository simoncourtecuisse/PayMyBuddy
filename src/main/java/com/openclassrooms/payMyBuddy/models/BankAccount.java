package com.openclassrooms.payMyBuddy.models;

import javax.persistence.*;

@Entity
@Table(name = "bank account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id")
    private int bankAccountId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "balance")
    private int balance;

    @Column(name = "iban")
    private int iban;
}
