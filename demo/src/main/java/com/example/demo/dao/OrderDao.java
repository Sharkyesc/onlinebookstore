package com.example.demo.dao;

import com.example.demo.dto.BookSalesDTO;
import com.example.demo.dto.UserPurchaseDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao {
    List<Order> findByUser(User user);

    List<Order> findAll();

    List<Order> findOrders(String bookName, LocalDateTime start, LocalDateTime end, User user);

    void saveOrder(Order order);

    List<Order> findOrdersByUserAndTimeRange(User user, LocalDateTime start, LocalDateTime end);

    List<Order> findOrdersByBookAndTimeRange(String bookName, LocalDateTime start, LocalDateTime end);

    List<BookSalesDTO> findBookSales(LocalDateTime startDate, LocalDateTime endDate);

    List<UserPurchaseDTO> findUserPurchases(LocalDateTime startDate, LocalDateTime endDate);

}