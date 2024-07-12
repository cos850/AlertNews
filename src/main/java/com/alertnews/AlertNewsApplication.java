package com.alertnews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@RestController
public class AlertNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlertNewsApplication.class, args);
    }
//
//    @GetMapping("/user")
//    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
//        return Collections.singletonMap("name", principal.getAttribute("name"));
//    }

}
