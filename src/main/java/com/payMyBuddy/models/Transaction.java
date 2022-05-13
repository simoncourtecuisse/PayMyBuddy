package com.payMyBuddy.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
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

//    @JsonIgnoreProperties("transactionList")
//    @ManyToOne
//    @JoinColumn(name = "transaction_label_id")
//    //@JsonIgnore
//    private TransactionLabel transactionLabel;

    @Column(name = "description", nullable = false)
    private String description;


    @JsonIgnoreProperties("creditorList")
    @ManyToOne
    @JoinColumn(name = "creditor_id")
    //@JsonIgnore
    //Long userIdCreditor;
    private User creditor;

    @JsonIgnoreProperties("debtorList")
    @ManyToOne
    @JoinColumn(name = "debtor_id")
    //@JsonIgnore
    private User debtor;

    public Transaction() {

    }

    public Transaction(double amount, LocalDate date, double commission, String description) {
        this.amount = amount;
        this.date = date;
        this.commission = commission;
        this.description = description;
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

//    public TransactionLabel getTransactionLabel() {
//        return transactionLabel;
//    }
//
//    public void setTransactionLabel(TransactionLabel transactionLabel) {
//        this.transactionLabel = transactionLabel;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreditor() {
        return creditor;
    }

    public void setCreditor(User creditor) {
        this.creditor = creditor;
    }

    public User getDebtor() {
        return debtor;
    }

    public void setDebtor(User debtor) {
        this.debtor = debtor;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", amount=" + amount +
                ", date=" + date +
                ", commission=" + commission +
                ", description=" + description +
                ", creditor=" + creditor +
                ", debtor=" + debtor +
                '}';
    }
}
