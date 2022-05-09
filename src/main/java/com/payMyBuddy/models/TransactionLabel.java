package com.payMyBuddy.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @JsonIgnoreProperties("transactionLabel")
    @OneToMany(
            mappedBy = "transactionLabel",
            cascade = CascadeType.ALL)
    List<Transaction> transactionList = new ArrayList<>();

    public TransactionLabel(){}

    public TransactionLabel(String description) {
        this.description = description;
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

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
