package com.ensa.architecture_logicielle_batch.Configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class SchedulerConfig {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    //@Scheduled(cron = "0 0 0 1 * *") // second, minute, hour, day, month, weekday : donc c'est a minuit de chaque mois
    @Scheduled(fixedDelay = 10000, initialDelay = 2000) //utiliser ceci à des fins de débogage
    public void scheduler() throws  JobInstanceAlreadyCompleteException,
                                    JobExecutionAlreadyRunningException,
                                    JobParametersInvalidException,
                                    JobRestartException {
        log.info("Job Scheduler Started.");
        jobLauncher.run(job, new JobParameters());
        log.info("Job Scheduler Ended.");
    }
}
