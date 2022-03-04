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

@Entity(name = "Transaction")
@DynamicUpdate
@Table(name = "transaction")
public class Transaction {

    @Id
    @SequenceGenerator(
            name = "transaction_sequence",
            sequenceName = "transaction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_sequence"
    )
    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @Column(name = "amount", nullable = false, precision = 6, scale = 2)
    private double amount;

    @Column(name = "date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate date;

    @Column(name = "commission", nullable = false, precision = 6, scale = 2)
    private double commission;

    @ManyToOne
    @JoinColumn(name = "transaction_label_id")
    private TransactionLabel transactionLabel;

//    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
//    @JoinColumn(name = "creditor_id")
//    Long userIdCreditor;
//
//    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
//    @JoinColumn(name = "debtor_id")
//    Long userIdDebtor;

    @ManyToOne
    @JoinColumn(name = "creditor_id")
    //Long userIdCreditor;
    User userIdCreditor;

    @ManyToOne
    @JoinColumn(name = "debtor_id")
    User userIdDebtor;

    public Transaction() {

    }

    public Transaction(double amount, LocalDate date, double commission, TransactionLabel transactionLabelId) {
        this.amount = amount;
        this.date = date;
        this.commission = commission;
        this.transactionLabel = transactionLabelId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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

    public TransactionLabel getTransactionLabel() {
        return transactionLabel;
    }

    public void setTransactionLabel(TransactionLabel transactionLabel) {
        this.transactionLabel = transactionLabel;
    }

    public User getUserIdCreditor() {
        return userIdCreditor;
    }

    public void setUserIdCreditor(User userIdCreditor) {
        this.userIdCreditor = userIdCreditor;
    }

    public User getUserIdDebtor() {
        return userIdDebtor;
    }

    public void setUserIdDebtor(User userIdDebtor) {
        this.userIdDebtor = userIdDebtor;
    }

}
