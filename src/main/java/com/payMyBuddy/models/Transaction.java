package com.payMyBuddy.models;


import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicUpdate
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private int transactionId;

//    @Column(name = "debtor_id", nullable = false)
//    private int debtorId;
//
//    @Column(name = "creditor_id", nullable = false)
//    private int creditorId;

    @Column(name = "amount", nullable = false, precision = 6, scale = 2)
    private BigDecimal amount;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "label_id", nullable = false)
    private int labelId;

    @Column(name = "commission", nullable = false, precision = 6, scale = 2)
    private BigDecimal commission;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creditor_id")
    private User creditorId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "debtor_id")
    private User debtorId;

    @OneToMany(
            mappedBy = "transactionLabel",
            cascade = CascadeType.ALL)
    List<TransactionLabel> transactionLabels = new ArrayList<>();

    public Transaction(int transactionId, BigDecimal amount, LocalDate date, int labelId, BigDecimal commission, User creditorId, User debtorId, List<TransactionLabel> transactionLabels) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = date;
        this.labelId = labelId;
        this.commission = commission;
        this.creditorId = creditorId;
        this.debtorId = debtorId;
        this.transactionLabels = transactionLabels;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

//    public int getDebtorId() {
//        return debtorId;
//    }
//
//    public void setDebtorId(int debtorId) {
//        this.debtorId = debtorId;
//    }
//
//    public int getCreditorId() {
//        return creditorId;
//    }
//
//    public void setCreditorId(int creditorId) {
//        this.creditorId = creditorId;
//    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    public User getCreditorId() {
        return creditorId;
    }

    public void setCreditorId(User creditorId) {
        this.creditorId = creditorId;
    }

    public User getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(User debtorId) {
        this.debtorId = debtorId;
    }

    public List<TransactionLabel> getTransactionLabels() {
        return transactionLabels;
    }

    public void setTransactionLabels(List<TransactionLabel> transactionLabels) {
        this.transactionLabels = transactionLabels;
    }

    public void addTransactionLabel(TransactionLabel transactionLabel) {
        transactionLabels.add(transactionLabel);
        transactionLabel.setTransactionLabel(this);
    }

    public void removeTransactionLabel(TransactionLabel transactionLabel) {
        transactionLabels.remove(transactionLabel);
        transactionLabel.setTransactionLabel(null);
    }
}
