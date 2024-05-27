package com.example.demo.daoimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;

import com.example.demo.repository.OrderRepository;

public class OrderDaoImpl {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }
}
