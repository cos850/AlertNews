package com.alertnews.config.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailBatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job emailJob;

    @Autowired
    public EmailBatchScheduler(JobLauncher jobLauncher, Job emailJob) {
        this.jobLauncher = jobLauncher;
        this.emailJob = emailJob;
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void runEmailJob() {
        try {
            jobLauncher.run(emailJob, new JobParametersBuilder().toJobParameters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
