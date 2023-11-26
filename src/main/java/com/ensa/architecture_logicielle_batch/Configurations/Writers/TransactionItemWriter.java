package com.ensa.architecture_logicielle_batch.Configurations.Writers;

import com.ensa.architecture_logicielle_batch.Models.TransactionCsv;
import com.ensa.architecture_logicielle_batch.services.TransactionService;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionItemWriter implements ItemWriter<TransactionCsv> {
    @Autowired
    private TransactionService transactionService;
    @Override
    public void write(Chunk<? extends TransactionCsv> items) {
        for(TransactionCsv item : items){
            transactionService.debiter(item);
        }
    }
}
