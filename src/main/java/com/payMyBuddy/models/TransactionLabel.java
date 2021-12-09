package com.payMyBuddy.models;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Table(name = "transaction_label")
public class TransactionLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_label_id", nullable = false)
    private int transactionLabelId;

    @Column(name = "description",nullable = false, length = 1000)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "label_id")
    private Transaction transactionLabel;

    public TransactionLabel(int transactionLabelId, String description, Transaction transactionLabel) {
        this.transactionLabelId = transactionLabelId;
        this.description = description;
        this.transactionLabel = transactionLabel;
    }

    public int getTransactionLabelId() {
        return transactionLabelId;
    }

    public void setTransactionLabelId(int transactionLabelId) {
        this.transactionLabelId = transactionLabelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Transaction getTransactionLabel() {
        return transactionLabel;
    }

    public void setTransactionLabel(Transaction transactionLabel) {
        this.transactionLabel = transactionLabel;
    }
}
