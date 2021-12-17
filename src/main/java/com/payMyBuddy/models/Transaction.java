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

    @Column(name = "amount", nullable = false, precision = 6, scale = 2)
    private BigDecimal amount;

    @Column(name = "date", nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDate date;

//    @Column(name = "label_id", nullable = false)
//    private int labelId;

    @Column(name = "commission", nullable = false, precision = 6, scale = 2)
    private BigDecimal commission;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_label_id")
    private TransactionLabel transactionLabelsId;

//
//    @OneToMany(/*mappedBy = "transaction",*/ cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "transaction_id")
//    private List<TransactionLabel> transactionLabels;

//    @OneToMany(
//            mappedBy = "transaction",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<TransactionLabel> transactionLabels = new ArrayList<>();

    public Transaction() {

    }

//    public Transaction(int transactionId, BigDecimal amount, LocalDate date, int labelId, BigDecimal commission) {
//        this.transactionId = transactionId;
//        this.amount = amount;
//        this.date = date;
//        this.labelId = labelId;
//        this.commission = commission;
//    }

    public Transaction(int transactionId, BigDecimal amount, LocalDate date, BigDecimal commission, TransactionLabel transactionLabelsId) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = date;
//        this.labelId = labelId;
        this.commission = commission;
        this.transactionLabelsId = transactionLabelsId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
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

//    public int getLabelId() {
//        return labelId;
//    }
//
//    public void setLabelId(int labelId) {
//        this.labelId = labelId;
//    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public TransactionLabel getTransactionLabels() {
        return transactionLabelsId;
    }

    public void setTransactionLabels(TransactionLabel transactionLabels) {
        this.transactionLabelsId = transactionLabels;
    }

}
