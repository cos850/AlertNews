package com.alertnews.config.batch;

import com.alertnews.common.TemplateLoader;
import com.alertnews.email.EmailDTO;
import com.alertnews.email.EmailService;
import com.alertnews.news.News;
import com.alertnews.news.NewsService;
import com.alertnews.user.User;
import com.alertnews.user.UserService;
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
public class EmailBatchConfig {

    private final EmailService emailService;
    private final UserService userService;
    private final NewsService newsService;
    private final TemplateLoader templateLoader;

    @Autowired
    public EmailBatchConfig(EmailService emailService, UserService userService, NewsService newsService, TemplateLoader templateLoader) {
        this.emailService = emailService;
        this.userService = userService;
        this.newsService = newsService;
        this.templateLoader = templateLoader;
    }

    @Bean
    public Job emailJob(JobRepository jobRepository, Step emailStep) {
        return new JobBuilder("emailJob", jobRepository)
                .start(emailStep)
                .build();
    }

    @Bean
    public Step emailStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("emailStep", jobRepository)
                .tasklet(sendEmailTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Tasklet sendEmailTasklet() {
        return (contribution, chunkContext) -> {
            String nation = "미국";
            LocalDate publishDate = LocalDate.now().minusDays(1);
            News news = newsService.getNewsFirst(publishDate, nation);

            String emailSubject = "[" + LocalDate.now() + "] 글로벌 뉴스";
            String emailContent = templateLoader.readTemplateToString("email-template.html")
                    .replace("{{content}}", new String(news.getContentHtml()))
                    .replace("{{title}}", news.getTitle());

            for(User user : userService.getUsers()) {
                EmailDTO emailDTO = EmailDTO.builder()
                        .to(user.getEmail())
                        .subject(emailSubject)
                        .content(emailContent.replace("{{name}}", user.getName()))
                        .build();
                emailService.sendSimpleMessage(emailDTO);
            }

            return RepeatStatus.FINISHED;
        };
    }


}
