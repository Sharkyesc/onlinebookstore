package com.example.demo.daoimpl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.entity.UserAuth;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        UserAuth userAuth = userRepository.findUserAuthByUsername(username);
        return userAuth != null ? userAuth.getUser() : null;
    }

    @Override
    public void saveUser(User user) {
        if (user.getUserAuth() != null) {
            user.getUserAuth().setUser(user);
        }
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
