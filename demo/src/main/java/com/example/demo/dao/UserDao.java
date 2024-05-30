package com.example.demo.dao;

import com.example.demo.entity.User;
import com.example.demo.entity.UserAuth;

import java.util.List;

public interface UserDao {
    User findById(int id);

    User findByUsername(String username);

    List<User> findAll();

    void saveUser(User user);

    void saveAuth(UserAuth userAuth);
}
