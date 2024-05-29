package com.example.demo.dao;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.entity.OrderItem;

import java.util.List;

public interface OrderDao {
    List<Order> findByUser(User user);

    List<Order> findAll();

    void saveOrder(Order order);

    void saveOrderItem(OrderItem orderItem);
}