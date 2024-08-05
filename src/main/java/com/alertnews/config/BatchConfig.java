package com.alertnews.config;

import com.alertnews.news.NewsService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;

@Configuration
public class BatchConfig {

    @Autowired
    private NewsService newsService;

    @Bean
    public Job newsJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("newsJob", jobRepository)  // Job 이름과 JobRepository 설정
                .start(step1)  // 첫 번째 스텝 설정
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("newsStep", jobRepository)  // Step 이름과 JobRepository 설정
                .tasklet(fetchYesterdayNewsTasklet(), transactionManager)  // Tasklet 설정 및 트랜잭션 매니저 설정
                .build();
    }

    @Bean
    public Tasklet fetchYesterdayNewsTasklet() {
        return (contribution, chunkContext) -> {
            LocalDate yesterday = LocalDate.now().minusDays(1);
            newsService.fetchNewsForDate(yesterday);
            System.out.println("Fetched news for: " + yesterday);
            return RepeatStatus.FINISHED;  // Tasklet 완료 상태 반환
        };
    }
}
