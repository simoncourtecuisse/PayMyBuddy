package com.payMyBuddy.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "Bank Transaction")
@DynamicUpdate
@Table(name = "bank_transaction")
public class BankTransaction {

    @Id
    @SequenceGenerator(
            name = "bank_transaction_sequence",
            sequenceName = "bank_transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bank_transaction_sequence"
    )
    @Column(name = "bank_transaction_id", nullable = false)
    private Long bankTransactionId;

    @Column(name = "amount", nullable = false, precision = 6, scale = 2)
    private double amount;

    @Column(name = "date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate date;

    @Column(name = "commission", precision = 6, scale = 2)
    private double commission;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    @JsonIgnore
    private BankAccount bankAccount;

    public BankTransaction(){

    }

    public BankTransaction(double amount) {
        this.amount = amount;
        this.date = LocalDate.now();
    }

    public Long getBankTransactionId() {
        return bankTransactionId;
    }

    public void setBankTransactionId(Long bankTransactionId) {
        this.bankTransactionId = bankTransactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
