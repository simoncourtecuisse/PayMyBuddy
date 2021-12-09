package com.payMyBuddy.services;

import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void addFriend(User user, User friendUser) {
        user.getFriendList().add(friendUser);
        userRepository.save(user);
    }

    public void removeFriend(User user, User friendUser) {
        user.getFriendList().remove(friendUser);
        userRepository.save(user);
    }
}
