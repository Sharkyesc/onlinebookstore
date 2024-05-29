package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.dao.OrderDao;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public List<Order> findOrdersByUser(User user) {
        return orderDao.findByUser(user);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderDao.findAll();
    }

    @Override
    public void addOrder(Order order) {
        order.setOrderTime(LocalDateTime.now()); // Set the order time to current time
        orderDao.save(order);
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.save(order);
    }
}