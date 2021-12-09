package com.payMyBuddy.repositories;

import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Integer> {
    List<BankAccount> findAllByUserBankAccount(User userBankAccount);

}
