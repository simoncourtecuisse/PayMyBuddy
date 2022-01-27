package com.payMyBuddy.repositories;

import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByIban(int iban);
   // List<BankAccount> findAllByUserBankAccount(User user);
}
