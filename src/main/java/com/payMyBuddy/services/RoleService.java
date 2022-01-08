package com.payMyBuddy.services;

import com.payMyBuddy.models.Role;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.RoleRepository;
import com.payMyBuddy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface RoleService {

    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    User getUser(String email);
    List<User>getUsers();
}
