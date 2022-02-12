package com.payMyBuddy.models;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Transaction Label")
@DynamicUpdate
@Table(name = "transaction_label")
public class TransactionLabel {

    @Id
    @SequenceGenerator(
            name = "transaction_label_sequence",
            sequenceName = "transaction_label_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "transaction_label_sequence"
    )
    @Column(name = "transaction_label_id", nullable = false)
    private Long transactionLabelId;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

//    @OneToMany(mappedBy = "transactionLabelId")
//    private List<Transaction> transaction;

    @OneToMany(
            mappedBy = "transactionLabelId",
            cascade = CascadeType.ALL)
    List<Transaction> transaction = new ArrayList<>();

    public TransactionLabel(){}

    public TransactionLabel(String description, List<Transaction> transaction) {
        this.description = description;
        this.transaction = transaction;
    }

    public Long getTransactionLabelId() {
        return transactionLabelId;
    }

    public void setTransactionLabelId(Long transactionLabelId) {
        this.transactionLabelId = transactionLabelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Transaction> getTransactions() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }
}
