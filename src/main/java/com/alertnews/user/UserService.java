package com.alertnews.user;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(long userId) {
        return this.userRepository.findById(userId);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
