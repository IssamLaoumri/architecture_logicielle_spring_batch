package com.ensa.architecture_logicielle_batch.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    private Long idTransaction;
    private Double montant;
    private LocalDateTime dateTransaction;
    private LocalDateTime dateDebit;

    @ManyToOne
    @JoinColumn(name = "idCompte")
    private Compte compte;
}
