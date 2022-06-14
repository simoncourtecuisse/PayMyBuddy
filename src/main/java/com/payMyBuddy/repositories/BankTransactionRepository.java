package com.payMyBuddy.repositories;

import com.payMyBuddy.models.BankTransaction;
import com.payMyBuddy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {
    List<BankTransaction> findAllBankTransactionsByUser(User user);
}
