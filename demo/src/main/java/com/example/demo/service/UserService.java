package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    User findById(Integer id);

    User findByUsername(String username);
}
