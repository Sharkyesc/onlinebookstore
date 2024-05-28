package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.entity.UserAuth;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Transactional
    @Override
    public void registerUser(String name, String username, String password) {

        User user = new User();
        user.setNickname(name);
        userDao.saveUser(user);

        UserAuth userAuth = new UserAuth();
        userAuth.setUser(user);
        userAuth.setUsername(username);
        userAuth.setPassword(passwordEncoder.encode(password));
        userDao.saveAuth(userAuth);
    }
}