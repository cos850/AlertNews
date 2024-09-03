package com.alertnews.email;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailService emailService ;

    @Test
    void 메일_전송_테스트() {
        // given
        EmailDTO emailDTO = EmailDTO.builder()
                .to("forproject.hr.001@gmail.com")
                .subject("Subject")
                .content("hello!")
                .build();

        // when
        emailService.sendSimpleMessage(emailDTO);

        // then
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}