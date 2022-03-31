package com.payMyBuddy.repositories;

import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllTransactionsByUserDebtorOrCreditor(User debtor, User creditor);
}
