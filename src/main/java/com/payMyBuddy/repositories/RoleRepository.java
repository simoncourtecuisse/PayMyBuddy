package com.payMyBuddy.repositories;

import com.payMyBuddy.models.ERole;
import com.payMyBuddy.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
