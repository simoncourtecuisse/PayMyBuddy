package com.payMyBuddy.models;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@DynamicUpdate
@Table(
        name = "user",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique",columnNames = "email")
        }
)
public class User {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "wallet_balance", precision = 6, scale = 2)
    private BigDecimal walletBalance;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL)
    List<BankAccount> bankAccountList = new ArrayList<>();

//    @OneToMany(
//            mappedBy = "user",
//            cascade = CascadeType.ALL)
//    List<BankAccount> bankAccountList = new ArrayList<>();

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "creditor_id")
//    List<Transaction> creditorList = new ArrayList<>();

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "debtor_id")
//    List<Transaction> debtorList = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL)
    List<BankTransaction> bankTransactionsList = new ArrayList<>();

//    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
//    List<Transaction> creditorList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
//    List<Transaction> debtorList = new ArrayList<>();

    @OneToMany(
            mappedBy = "userIdCreditor",
            cascade = CascadeType.ALL)
    List<Transaction> creditorList = new ArrayList<>();

    @OneToMany(
            mappedBy = "userIdDebtor",
            cascade = CascadeType.ALL)
    List<Transaction> debtorList = new ArrayList<>();


    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "contact", joinColumns =
    @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_user_id"))
    private List<User> friendList;

    public User() {}

    public User(String firstName, String lastName, String email, String password, BigDecimal walletBalance, List<User> friendList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.walletBalance = walletBalance;
        this.friendList = friendList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(BigDecimal walletBalance) {
        this.walletBalance = walletBalance;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
    }

//    public List<BankAccount> getUserBankAccount() {
//        return bankAccountId;
//    }
//
//    public void setUserBankAccount(List<BankAccount> bankAccountId) {
//        this.bankAccountId = bankAccountId;
//    }

    public List<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(List<BankAccount> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }

    public List<Transaction> getDebtorList() {
        return debtorList;
    }

    public void setDebtorList(List<Transaction> debtorList) {
        this.debtorList = debtorList;
    }

    public List<Transaction> getCreditorList() {
        return creditorList;
    }

    public void setCreditorList(List<Transaction> creditorList) {
        this.creditorList = creditorList;
    }

//    public List<BankAccount> getBankAccountId() {
//        return bankAccountId;
//    }
//
//    public void setBankAccountId(List<BankAccount> bankAccountId) {
//        this.bankAccountId = bankAccountId;
//    }

    public List<BankTransaction> getBankTransactionsList() {
        return bankTransactionsList;
    }

    public void setBankTransactionsList(List<BankTransaction> bankTransactionsList) {
        this.bankTransactionsList = bankTransactionsList;
    }
}
