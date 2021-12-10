package com.payMyBuddy.models;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@DynamicUpdate
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "balance", nullable = false, precision = 6, scale = 2)
    private BigDecimal balance;

    public User() {
    }

//    @OneToMany(
//            mappedBy = "userBankAccount",
//            cascade = CascadeType.ALL)
//    List<BankAccount> bankAccountList = new ArrayList<>();
//
//    @OneToMany(
//            mappedBy = "creditorId",
//            cascade = CascadeType.ALL)
//    List<Transaction> creditorList = new ArrayList<>();
//
//    @OneToMany(
//            mappedBy = "debtorId",
//            cascade = CascadeType.ALL)
//    List<Transaction> debtorList = new ArrayList<>();
//
    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "contact", joinColumns =
    @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_user_id"))
    private List<User> friendList;

    public User(int userId, String firstName, String lastName, String email, String password, BigDecimal balance) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<User> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<User> friendList) {
        this.friendList = friendList;
    }

//    public void addBankAccount(BankAccount bankAccount) {
//        bankAccountList.add(bankAccount);
//        bankAccount.setUserBankAccount(this);
//    }
//    public void removeBankAccount(BankAccount bankAccount) {
//        bankAccountList.remove(bankAccount);
//        bankAccount.setUserBankAccount(null);
//    }
}
