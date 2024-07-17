package com.alertnews.user;

import lombok.Getter;

@Getter
public class User {
    private long id;
    private String name;
    private String password;
    private String email;

    public User(long id, String name, String password, String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
