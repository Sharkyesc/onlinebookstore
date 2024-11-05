package com.example.demo.dao;

import com.example.demo.entity.User;

import java.util.List;

public interface UserDao {
    User findById(int id);

    User findByUsername(String username);

    List<User> findAll();

    void saveUser(User user);

}
