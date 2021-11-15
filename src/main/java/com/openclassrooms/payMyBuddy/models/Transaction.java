package com.openclassrooms.payMyBuddy.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transcation_id")
    private int transactionId;

    @Column(name = "debitor_id")
    private int debitorId;

    @Column(name = "creditor_id")
    private int creditorId;

    @Column(name = "amount")
    private int amount;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "label_id")
    private int labelId;

    @Column(name = "commission")
    private BigDecimal commission;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getDebitorId() {
        return debitorId;
    }

    public void setDebitorId(int debitorId) {
        this.debitorId = debitorId;
    }

    public int getCreditorId() {
        return creditorId;
    }

    public void setCreditorId(int creditorId) {
        this.creditorId = creditorId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }
}
