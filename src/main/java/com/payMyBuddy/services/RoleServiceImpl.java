package com.payMyBuddy.services;

import com.payMyBuddy.controllers.UserController;
import com.payMyBuddy.models.Role;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.RoleRepository;
import com.payMyBuddy.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    Logger LOGGER = LogManager.getLogger(RoleServiceImpl.class);

    private  UserRepository userRepository;
    private final RoleRepository roleRepository;

    public RoleServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User saveUser(User user) {
        LOGGER.info("Saving new user {} to the database", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        LOGGER.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        LOGGER.info("Adding role {} to user {}", roleName, email);
        User user = userRepository.findUserByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);

    }

    @Override
    public User getUser(String email) {
        LOGGER.info("Fetching user {}", email);
        return userRepository.findUserByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        LOGGER.info("Fetching all users");
        return userRepository.findAll();
    }
}
