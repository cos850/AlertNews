package com.alertnews.email;

import com.alertnews.common.TemplateLoader;
import com.alertnews.news.News;
import com.alertnews.news.NewsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class EmailServiceIntegrationTest {

    @Autowired
    EmailService service;

    @Autowired
    NewsService newsService;

    @Autowired
    TemplateLoader templateLoader;

    @Test
    void sendSimpleEmail(){
        // given
        String email = "forproject.hr.001@gmail.com";
        String subject = "Subject";
        String text = "hello!";

        // when, then
        assertDoesNotThrow(() -> service.sendSimpleMessage(new EmailDTO(email, subject, text)));
    }

    @Test
    void sendSimpleEmailForKorean(){
        // given
        String email = "forproject.hr.001@gmail.com";
        String subject = "한글 테스트";
        String text = "한글 테스트!";

        // when, then
        assertDoesNotThrow(() -> service.sendSimpleMessage(new EmailDTO(email, subject, text)));
    }

    @Test
    void sendNewsEmail(){
        // given
        String nation = "미국";
        LocalDate publishDate = LocalDate.now().minusDays(2);

        News news = newsService.getNewsFirst(publishDate, nation);
        EmailDTO email = makeTestNewsEmail(news);

        // when, then
        assertDoesNotThrow(() -> service.sendHtmlMessage(email));
    }

    private EmailDTO makeTestNewsEmail(News news){
        String email = "forproject.hr.001@gmail.com";
        String subject = "테스트 뉴스";
        String content = new String(news.getContentHtml(), StandardCharsets.UTF_8);
        System.out.println("content = " + content);

        String emailContent = templateLoader.readTemplateToString("email-template.html")
                .replace("{{content}}", content)
                .replace("{{title}}", news.getTitle())
                .replace("{{name}}", "테스트사용자");

        return new EmailDTO(email, subject, emailContent);
    }
}
