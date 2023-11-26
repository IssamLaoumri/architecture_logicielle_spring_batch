package com.ensa.architecture_logicielle_batch.services;

import com.ensa.architecture_logicielle_batch.Models.Compte;
import com.ensa.architecture_logicielle_batch.Models.Transaction;
import com.ensa.architecture_logicielle_batch.Models.TransactionCsv;
import com.ensa.architecture_logicielle_batch.Repositories.CompteRepository;
import com.ensa.architecture_logicielle_batch.Repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CompteRepository compteRepository;
    public void debiter(TransactionCsv item){
        if(transactionRepository.existsById(item.getIdTransaction()))
            return;
        Transaction transaction = new Transaction();
        transaction.setIdTransaction(item.getIdTransaction());
        transaction.setDateTransaction(item.getDateTransaction());
        transaction.setMontant(item.getMontant());
        transaction.setDateDebit(LocalDateTime.now());

        Compte compte = compteRepository.findById(item.getIdCompte())
                .orElseThrow(() -> new IllegalArgumentException("Le compte avec l'ID : "+item.getIdCompte()+" est introuvable."));

        compte.setSolde(compte.getSolde()-item.getMontant());

        transaction.setCompte(compte);

        transactionRepository.save(transaction);
        compteRepository.save(compte);
        System.out.println("iteration Done !");

    }
}
