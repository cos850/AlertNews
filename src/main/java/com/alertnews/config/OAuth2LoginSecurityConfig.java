package com.alertnews.config;

import com.alertnews.user.OAuth2KakaoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class OAuth2LoginSecurityConfig {
    private final OAuth2KakaoUserService oAuth2KakaoUserSerivce;

    @Autowired
    public OAuth2LoginSecurityConfig(OAuth2KakaoUserService oAuth2KakaoUserSerivce) {
        this.oAuth2KakaoUserSerivce = oAuth2KakaoUserSerivce;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/user").hasRole("USER")   // "ROLE_" 접두사 제외되도록 변경됨
                        .requestMatchers("/user").authenticated()
                        .anyRequest().permitAll()
                )
                .logout(withDefaults())
                .oauth2Login(oauth -> oauth.loginPage("/login.html"));
        return http.build();
    }
}