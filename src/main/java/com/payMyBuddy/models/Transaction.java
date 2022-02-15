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
    private BigDecimal amount;

    @Column(name = "date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate date;

    @Column(name = "commission", nullable = false, precision = 6, scale = 2)
    private BigDecimal commission;

    @ManyToOne
    @JoinColumn(name = "transaction_label_id")
    TransactionLabel transactionLabel;

//    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
//    @JoinColumn(name = "creditor_id")
//    Long userIdCreditor;
//
//    @ManyToOne(cascade = CascadeType.ALL, targetEntity = User.class)
//    @JoinColumn(name = "debtor_id")
//    Long userIdDebtor;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "creditor_id")
    Long userIdCreditor;
    //User userIdCreditor;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "debtor_id")
    Long userIdDebtor;

    public Transaction() {

    }

    public Transaction(BigDecimal amount, LocalDate date, BigDecimal commission, TransactionLabel transactionLabelId) {
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

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public TransactionLabel getTransactionLabels() {
        return transactionLabel;
    }

    public void setTransactionLabels(TransactionLabel transactionLabels) {
        this.transactionLabel = transactionLabels;
    }

    public Long getUserIdCreditor() {
        return userIdCreditor;
    }

    public void setUserIdCreditor(Long userIdCreditor) {
        this.userIdCreditor = userIdCreditor;
    }

    public Long getUserIdDebtor() {
        return userIdDebtor;
    }

    public void setUserIdDebtor(Long userIdDebtor) {
        this.userIdDebtor = userIdDebtor;
    }

}
