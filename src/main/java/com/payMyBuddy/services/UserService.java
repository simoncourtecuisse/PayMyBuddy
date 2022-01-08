package com.payMyBuddy.services;


import com.payMyBuddy.controllers.UserController;
import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    Logger LOGGER = LogManager.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            LOGGER.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            LOGGER.info("User found in the database: {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        user.
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findByEmail(email);
//        if (user == null) {
//            LOGGER.error("User not found in the database");
//            throw new UsernameNotFoundException("User not found in the database");
//        } else {
//            LOGGER.info("User found in the database: {}", email);
//        }
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        user.
//        return new org.springframework.security.core.userdetails.User();
//    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getOneUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
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
        user.getUserBankAccount().add(bankAccount);
        userRepository.save(user);
    }

    public void removeBankAccount(User user, BankAccount bankAccount) {
        user.getUserBankAccount().remove(bankAccount);
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
