package com.alertnews.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    public static final String URI = "/user";

    @ResponseBody
    @GetMapping("/user")
    public String mypage(){
        return "user";
    }
}
