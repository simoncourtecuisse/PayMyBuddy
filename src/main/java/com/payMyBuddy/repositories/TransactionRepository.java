package com.payMyBuddy.repositories;

import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllTransactionsByDebtorOrCreditorOrderByDateDesc(User debtor, User creditor);
}
