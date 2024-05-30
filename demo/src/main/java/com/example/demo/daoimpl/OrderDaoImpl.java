package com.example.demo.daoimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.OrderDao;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.User;

import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.OrderItemRepository;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findOrders(String bookName, LocalDateTime start, LocalDateTime end, User user) {
        return orderRepository.findOrders(bookName, start, end, user);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    @Override
    public List<Order> findOrdersByUserAndTimeRange(User user, LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByUserAndTimeRange(user, start, end);
    }

}