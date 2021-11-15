package com.openclassrooms.payMyBuddy.repositories;

import com.openclassrooms.payMyBuddy.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
