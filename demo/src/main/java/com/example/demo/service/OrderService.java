package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.Book;
import com.example.demo.entity.Cart;
import com.example.demo.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    List<Order> findOrdersByUser(User user);

    List<Order> findOrders(String bookName, LocalDateTime start, LocalDateTime end, User user);

    Order addOrder(Book book, User user);

    void updateOrder(Order order);

    public void createOrder(String username, List<Cart> cartItems);

}