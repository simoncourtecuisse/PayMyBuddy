package com.payMyBuddy.repositories;

import com.payMyBuddy.models.TransactionLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface TransactionLabelRepository extends JpaRepository<TransactionLabel, Long> {
}
