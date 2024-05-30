package com.example.demo.dao;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.entity.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao {
    List<Order> findByUser(User user);

    List<Order> findAll();

    List<Order> findOrders(String bookName, LocalDateTime start, LocalDateTime end, User user);

    void saveOrder(Order order);

    void saveOrderItem(OrderItem orderItem);

    List<Order> findOrdersByUserAndTimeRange(User user, LocalDateTime start, LocalDateTime end);

    List<Order> findOrdersByBookAndTimeRange(String bookName, LocalDateTime start, LocalDateTime end);

}