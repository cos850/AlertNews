package com.alertnews.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    Map<Long, User> users = new HashMap<>();

    public List<User> findAll(){
        return users.values().stream().toList();
    }

    public User save(User user) {
        return users.put(user.getId(), user);
    }

    public User findById(long userId) {
        return users.get(userId);
    }

}
