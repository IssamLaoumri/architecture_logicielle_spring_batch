package com.ensa.architecture_logicielle_batch.Configurations;

import com.ensa.architecture_logicielle_batch.Configurations.exceptions.ResourceNotFound;
import com.ensa.architecture_logicielle_batch.Models.TransactionCsv;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Slf4j
public class BatchConfig {

   @Autowired
   private ItemWriter<TransactionCsv> transactionItemWriter;
    @Autowired
    private ItemProcessor<TransactionCsv,TransactionCsv> transactionItemProcessor;

    @Bean
    public Resource inputFileResource() {
        return new FileSystemResource("src\\main\\resources\\transactions.csv");
    }

    @Bean
    public Step transactionStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("step-data-load", jobRepository)
                .<TransactionCsv, TransactionCsv>chunk(10, transactionManager)
                .reader(reader())
                .processor(transactionItemProcessor)
                .writer(transactionItemWriter)
                .faultTolerant()
                .retryLimit(5)
                .retry(ResourceNotFound.class)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Job transactionJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder("job-data-load", jobRepository)
                .start(transactionStep(jobRepository, transactionManager))
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .build();
    }



    @Bean
    public ItemReader<TransactionCsv> reader() {
        FlatFileItemReader<TransactionCsv> reader = new FlatFileItemReader<>();
        reader.setName("CSV-READER");
        reader.setResource(inputFileResource());
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper());
        return reader;
    }


    @Bean
    public LineMapper<TransactionCsv> lineMapper() {
        DefaultLineMapper<TransactionCsv> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"idTransaction","idCompte","montant","strDateTransaction"});
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        BeanWrapperFieldSetMapper<TransactionCsv> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(TransactionCsv.class);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobExecutionListener() {
            @SneakyThrows
            @Override
            public void beforeJob(JobExecution jobExecution) {
                Resource resource = inputFileResource();
                if (!resource.exists()) {
                    jobExecution.setStatus(BatchStatus.STOPPED);
                    log.error("Input file introuvable verifier le path suivant : " + resource.getURL());
                    throw new ResourceNotFound("Input resource must exist");
                }
            }
        };
    }

}
