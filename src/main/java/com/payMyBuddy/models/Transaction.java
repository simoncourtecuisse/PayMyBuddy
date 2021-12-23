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

    @Column(name = "date", nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate date;

    @Column(name = "commission", nullable = false, precision = 6, scale = 2)
    private BigDecimal commission;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_label_id")
    TransactionLabel transactionLabelId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "creditor_id")
    User userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "debtor_id")
    User userIdDebtor;

    public Transaction() {

    }

    public Transaction(BigDecimal amount, LocalDate date, BigDecimal commission, TransactionLabel transactionLabelId) {
        this.amount = amount;
        this.date = date;
        this.commission = commission;
        this.transactionLabelId = transactionLabelId;
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
        return transactionLabelId;
    }

    public void setTransactionLabels(TransactionLabel transactionLabels) {
        this.transactionLabelId = transactionLabels;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public User getUserIdDebtor() {
        return userIdDebtor;
    }

    public void setUserIdDebtor(User userId) {
        this.userId = userIdDebtor;
    }
}
