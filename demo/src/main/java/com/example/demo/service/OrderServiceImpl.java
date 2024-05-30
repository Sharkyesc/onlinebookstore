package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import com.example.demo.entity.OrderItemId;
import com.example.demo.entity.Book;
import com.example.demo.entity.OrderItem;
import com.example.demo.dao.OrderDao;
import com.example.demo.dao.UserDao;
import com.example.demo.dto.BookStatisticsDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Override
    public List<Order> findOrdersByUser(User user) {
        return orderDao.findByUser(user);
    }

    @Override
    public List<Order> findOrders(String bookName, LocalDateTime start, LocalDateTime end, User user) {
        return orderDao.findOrders(bookName, start, end, user);
    }

    @Override
    public Order addOrder(Book book, User user) {
        Order order = new Order();
        order.setOrderTime(LocalDateTime.now());
        order.setUser(user);
        order.setContactPhone(user.getPhonenumber());
        order.setDestination(user.getAddress());
        order.setRecipient(user.getNickname());
        order.setTotalPrice(book.getPrice());

        OrderItemId id = new OrderItemId(order.getOrderId(), 1);
        OrderItem orderItem = new OrderItem(order, book, 1, book.getPrice(), id);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);

        orderDao.saveOrder(order);
        orderDao.saveOrderItem(orderItem);
        return order;
    }

    @Override
    public void updateOrder(Order order) {
        orderDao.saveOrder(order);
    }

    @Override
    public void createOrder(String username, List<Cart> cartItems) {
        User user = userDao.findByUsername(username);

        Order order = new Order();
        order.setUser(user);
        order.setOrderTime(LocalDateTime.now());
        order.setRecipient(user.getNickname());
        order.setContactPhone(user.getPhonenumber());
        order.setDestination(user.getAddress());

        int totalPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (Cart item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(item.getBook());
            orderItem.setPrice(item.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderDao.saveOrderItem(orderItem);

            orderItems.add(orderItem);

            totalPrice += orderItem.getPrice() * item.getQuantity();
        }
        order.setOrderItems(orderItems);
        order.setTotalPrice(totalPrice);

        orderDao.saveOrder(order);
    }

    @Override
    public List<BookStatisticsDTO> getStatistics(LocalDateTime start, LocalDateTime end, User user) {
        List<Order> orders = orderDao.findOrdersByUserAndTimeRange(user, start, end);
        List<BookStatisticsDTO> res = new ArrayList<>();
        for (Order order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                res.add(new BookStatisticsDTO(
                        orderItem.getBook().getTitle(),
                        orderItem.getQuantity(),
                        orderItem.getQuantity() * orderItem.getBook().getPrice()));

            }
        }

        Map<String, BookStatisticsDTO> bookStatsMap = new HashMap<>();

        for (BookStatisticsDTO bookStat : res) {
            if (bookStatsMap.containsKey(bookStat.getBookTitle())) {
                BookStatisticsDTO existingStat = bookStatsMap.get(bookStat.getBookTitle());
                existingStat.setTotalQuantity(existingStat.getTotalQuantity() + bookStat.getTotalQuantity());
                existingStat.setTotalPrice(existingStat.getTotalPrice() + bookStat.getTotalPrice());
            } else {
                bookStatsMap.put(bookStat.getBookTitle(), bookStat);
            }
        }

        return new ArrayList<>(bookStatsMap.values());
    }
}