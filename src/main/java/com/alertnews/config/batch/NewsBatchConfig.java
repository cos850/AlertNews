package com.alertnews.config.batch;

import com.alertnews.news.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
public class NewsBatchConfig {

    private final NewsService newsService;

    @Bean
    public Job newsJob(JobRepository jobRepository, Step newsStep) {
        return new JobBuilder("newsJob", jobRepository)
                .start(newsStep)
                .build();
    }

    @Bean
    public Step newsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("newsStep", jobRepository)
                .tasklet(fetchYesterdayNewsTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet fetchYesterdayNewsTasklet() {
        return (contribution, chunkContext) -> {
            LocalDate yesterday = LocalDate.now().minusDays(1);
            newsService.fetchNewsForDate(yesterday);
            System.out.println("Fetched news for: " + yesterday);

            return RepeatStatus.FINISHED;
        };
    }
}
