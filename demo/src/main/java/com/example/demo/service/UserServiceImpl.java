package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.entity.UserAuth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import org.mindrot.jbcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public String encodePassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean matchesPassword(String rawPassword, String hashedPassword) {
        return BCrypt.checkpw(rawPassword, hashedPassword);
    }

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
    public void registerUser(String name, String username, String email, String password) {

        User user = new User();
        user.setNickname(name);
        user.setEmail(email);
        userDao.saveUser(user);

        UserAuth userAuth = new UserAuth();
        userAuth.setUser(user);
        userAuth.setUsername(username);
        userAuth.setPassword(encodePassword(password));
        userDao.saveAuth(userAuth);
    }

    @Autowired
    private HttpServletRequest request;

    @Override
    public User getCurUser() {
        HttpSession session = request.getSession(false);

        String username = (String) session.getAttribute("user");

        return findByUsername(username);
    }

    @Override
    public void updateUser(User user) {
        userDao.saveUser(user);
    }

}