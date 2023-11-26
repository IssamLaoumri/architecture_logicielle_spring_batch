package com.ensa.architecture_logicielle_batch.Repositories;

import com.ensa.architecture_logicielle_batch.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
