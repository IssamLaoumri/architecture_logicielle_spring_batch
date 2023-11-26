package com.ensa.architecture_logicielle_batch.Models;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCsv {
    private Long idTransaction;
    private Long idCompte;
    private Double montant;
    private LocalDateTime dateTransaction;
    @Transient
    private String strDateTransaction;
}
