package com.ensa.architecture_logicielle_batch.Configurations.Processors;

import com.ensa.architecture_logicielle_batch.Models.TransactionCsv;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransactionItemProcessor implements ItemProcessor<TransactionCsv, TransactionCsv> {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    @Override
    public TransactionCsv process(TransactionCsv item) {
        item.setDateTransaction(LocalDateTime.parse(item.getStrDateTransaction(), dateFormat));
        return item;
    }
}
