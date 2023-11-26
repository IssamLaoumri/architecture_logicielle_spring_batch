package com.ensa.architecture_logicielle_batch.Repositories;

import com.ensa.architecture_logicielle_batch.Models.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
}
