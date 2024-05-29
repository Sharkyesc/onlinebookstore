package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User findById(Integer id);

    User findByUsername(String username);

    void registerUser(String nickname, String username, String password);

    public String encodePassword(String rawPassword);

    public boolean matchesPassword(String rawPassword, String hashedPassword);

    User getCurUser();

    void updateUser(User user);
}
