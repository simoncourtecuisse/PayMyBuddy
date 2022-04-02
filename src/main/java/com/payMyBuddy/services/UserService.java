package com.payMyBuddy.services;


import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {
        Optional<User> maj = userRepository.findById(user.getUserId());
        if (maj.isPresent()){
              userRepository.save(user);
        }
//        Optional<User> majUser = userRepository.findById(user.getUserId());
//        if (majUser.isPresent()){
//            userRepository.save(user);
//        }
    }

    public void deleteUser(User user) {
        Optional<User> removeUser = userRepository.findByEmail(user.getEmail());
        if(removeUser.isPresent()) {
            userRepository.deleteById(user.getUserId());
        }
    }

    public void addFriend(User user, User friendUser) {
        user.getFriendList().add(friendUser);
        userRepository.save(user);
    }

    public void removeFriend(User user, User friendUser) {
        user.getFriendList().remove(friendUser);
        userRepository.save(user);
    }

    public void addBankAccount(User user, BankAccount bankAccount) {
        user.getBankAccountList().add(bankAccount);
        userRepository.save(user);
    }

    public void removeBankAccount(User user, BankAccount bankAccount) {
        user.getBankAccountList().remove(bankAccount);
        userRepository.save(user);
    }

    public void addCreditorToTransaction(User creditor, Transaction transaction) {
        creditor.getCreditorList().add(transaction);
        userRepository.save(creditor);
    }

    public void removeCreditorToTransaction(User creditor, Transaction transaction) {
        creditor.getCreditorList().remove(transaction);
        userRepository.save(creditor);
    }

    public void addDebtorToTransaction(User debtor, Transaction transaction) {
        debtor.getDebtorList().add(transaction);
        userRepository.save(debtor);
    }

    public void removeDebtorToTransaction(User debtor, Transaction transaction) {
        debtor.getDebtorList().remove(transaction);
        userRepository.save(debtor);
    }

}
