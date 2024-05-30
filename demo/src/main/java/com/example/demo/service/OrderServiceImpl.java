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
import com.example.demo.dao.CartDao;
import com.example.demo.dao.BookDao;
import com.example.demo.dto.BookSalesDTO;
import com.example.demo.dto.BookStatisticsDTO;
import com.example.demo.dto.UserPurchaseDTO;

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
    private CartDao cartDao;

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Order> findOrdersByUser(User user) {
        return orderDao.findByUser(user);
    }

    @Override
    public List<Order> findOrders(String bookName, LocalDateTime start, LocalDateTime end, User user) {
        return orderDao.findOrders(bookName, start, end, user);
    }

    @Override
    public List<Order> findAllOrders(String bookName, LocalDateTime start, LocalDateTime end) {
        return orderDao.findOrdersByBookAndTimeRange(bookName, start, end);
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

        book.setStocks(book.getStocks() - 1);
        book.setSalesvolume(book.getSalesvolume() + 1);
        bookDao.save(book);

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
    public void createOrder(User user, List<Cart> cartItems) {

        Order order = new Order();
        order.setUser(user);
        order.setOrderTime(LocalDateTime.now());
        order.setRecipient(user.getNickname());
        order.setContactPhone(user.getPhonenumber());
        order.setDestination(user.getAddress());

        orderDao.saveOrder(order);

        int totalPrice = 0, id = 0;
        List<OrderItem> orderItems = new ArrayList<>();
        for (Cart item : cartItems) {
            id++;
            OrderItemId orderItemId = new OrderItemId(order.getOrderId(), id);
            Book book = item.getBook();
            book.setStocks(book.getStocks() - item.getQuantity());
            book.setSalesvolume(book.getSalesvolume() + item.getQuantity());
            bookDao.save(book);

            int price = item.getPrice() * item.getQuantity();
            OrderItem orderItem = new OrderItem(order, book, item.getQuantity(), price,
                    orderItemId);

            orderDao.saveOrderItem(orderItem);
            cartDao.delete(item.getCartId());
            orderItems.add(orderItem);
            totalPrice += price;
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

    @Override
    public List<BookSalesDTO> getBookSales(LocalDateTime startDate, LocalDateTime endDate) {
        return orderDao.findBookSales(startDate, endDate);
    }

    @Override
    public List<UserPurchaseDTO> getUserPurchases(LocalDateTime startDate, LocalDateTime endDate) {
        return orderDao.findUserPurchases(startDate, endDate);
    }

}