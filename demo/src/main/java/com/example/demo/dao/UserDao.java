package com.example.demo.dao;

import com.example.demo.entity.User;
import com.example.demo.entity.UserAuth;

public interface UserDao {
    User findById(Integer id);

    User findByUsername(String username);

    void saveUser(User user);

    void saveAuth(UserAuth userAuth);
}
