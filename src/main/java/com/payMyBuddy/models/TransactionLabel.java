package com.payMyBuddy.models;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DynamicUpdate
@Table(name = "transaction_label")
public class TransactionLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_label_id", nullable = false)
    private int transactionLabelId;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

   @OneToMany(/*mappedBy = "transactionLabelsId",*/ cascade = CascadeType.ALL)
   @JoinColumn(name = "transaction_label_id")
   private List<Transaction> transaction;

//    @ManyToOne(
//            cascade = CascadeType.ALL
//    )
//    @JoinColumn(name = "transaction_id")
//    private Transaction transaction;

    public TransactionLabel(){}

    public TransactionLabel(int transactionLabelId, String description) {
        this.transactionLabelId = transactionLabelId;
        this.description = description;
//        this.transaction = transaction;
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

    public List<Transaction> getTransactions() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }
}
