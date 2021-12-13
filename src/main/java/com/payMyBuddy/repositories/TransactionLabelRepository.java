package com.payMyBuddy.repositories;

import com.payMyBuddy.models.TransactionLabel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLabelRepository extends CrudRepository<TransactionLabel, Integer> {
}
