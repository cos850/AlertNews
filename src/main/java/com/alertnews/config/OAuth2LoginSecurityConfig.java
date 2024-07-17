package com.alertnews.config;

import com.alertnews.auth.CustomOAuth2UserService;
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
    private final CustomOAuth2UserService oAuth2UserService;

    @Autowired
    public OAuth2LoginSecurityConfig(CustomOAuth2UserService oAuth2UserService) {
        this.oAuth2UserService = oAuth2UserService;
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