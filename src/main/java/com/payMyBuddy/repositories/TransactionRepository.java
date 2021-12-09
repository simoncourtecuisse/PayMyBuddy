package com.payMyBuddy.repositories;

import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
   List<Transaction> findAllByCreditorIdOrDebtorId(User creditorId, User debtorId);
}

