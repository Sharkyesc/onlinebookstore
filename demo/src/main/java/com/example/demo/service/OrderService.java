package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;

import java.util.List;

public interface OrderService {
    List<Order> findAllOrders();

    List<Order> findOrdersByUser(User user);

    void addOrder(Order order);

    void updateOrder(Order order);
}