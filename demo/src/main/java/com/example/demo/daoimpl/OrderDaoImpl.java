package com.example.demo.daoimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.dao.OrderDao;
import com.example.demo.dto.BookSalesDTO;
import com.example.demo.dto.UserPurchaseDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;

import com.example.demo.repository.OrderRepository;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    OrderRepository orderRepository;

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
    public List<Order> findOrdersByBookAndTimeRange(String bookName, LocalDateTime start, LocalDateTime end) {
        return orderRepository.findOrdersByBookAndTimeRange(bookName, start, end);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> findOrdersByUserAndTimeRange(User user, LocalDateTime start, LocalDateTime end) {
        return orderRepository.findByUserAndTimeRange(user, start, end);
    }

    @Override
    public List<BookSalesDTO> findBookSales(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findBookSales(startDate, endDate);
    }

    @Override
    public List<UserPurchaseDTO> findUserPurchases(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findUserPurchases(startDate, endDate);
    }

}