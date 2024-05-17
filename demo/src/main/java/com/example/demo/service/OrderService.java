package com.example.demo.service;

import com.example.demo.entity.Order;
import java.util.List;

public interface OrderService {
    List<Order> findAllOrders();

    void addOrder(Order order);

    void updateOrder(Order order);
}