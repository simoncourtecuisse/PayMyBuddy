package com.payMyBuddy.models;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Column(name = "date", nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate date;

    @Column(name = "commission", nullable = false, precision = 6, scale = 2)
    private BigDecimal commission;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccountId;

    public BankTransaction(){

    }

//    public BankTransaction(double amount) {
//        this.amount = amount;
//        this.date = new InstantDAte
//    }


    public BankTransaction(double amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
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

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public BankAccount getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(BankAccount bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
}
