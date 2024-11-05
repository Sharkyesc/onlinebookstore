package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.dto.BookSalesDTO;
import com.example.demo.dto.BookStatisticsDTO;
import com.example.demo.dto.Kafka_OrderDTO;
import com.example.demo.dto.OrderResponseDTO;
import com.example.demo.dto.UserPurchaseDTO;
import com.example.demo.entity.Book;
import com.example.demo.entity.Cart;
import com.example.demo.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    List<Order> findOrdersByUser(User user);

    List<Order> findOrders(String bookName, LocalDateTime start, LocalDateTime end, User user);

    List<Order> findAllOrders(String bookName, LocalDateTime start, LocalDateTime end);

    Order addOrder(Book book, User user);

    void updateOrder(Order order);

    public void createOrder(User user, List<Cart> cartItems);

    List<BookStatisticsDTO> getStatistics(LocalDateTime start, LocalDateTime end, User user);

    List<BookSalesDTO> getBookSales(LocalDateTime startDate, LocalDateTime endDate);

    List<UserPurchaseDTO> getUserPurchases(LocalDateTime startDate, LocalDateTime endDate);

    public OrderResponseDTO processOrder(Kafka_OrderDTO orderDTO);
}