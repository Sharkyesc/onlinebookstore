package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.kafka.core.KafkaTemplate;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import com.example.demo.dto.BookSalesDTO;
import com.example.demo.dto.BookStatisticsDTO;
import com.example.demo.dto.CheckoutRequest;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.Kafka_OrderDTO;
import com.example.demo.dto.OrderItemDTO;
import com.example.demo.dto.Kafka_OrderItemDTO;
import com.example.demo.dto.UserPurchaseDTO;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate<String, Kafka_OrderDTO> kafkaTemplate;

    @PostMapping("/api/orders/checkoutfromcart")
    public ResponseEntity<String> placeOrder(@RequestBody List<CheckoutRequest> checkoutRequests) {
        try {
            LocalDateTime orderTime = LocalDateTime.now();

            List<Kafka_OrderItemDTO> orderItems = checkoutRequests.stream().map(checkoutRequest -> {
                Kafka_OrderItemDTO orderItemDTO = new Kafka_OrderItemDTO();
                orderItemDTO.setCartId(checkoutRequest.getCartId());
                orderItemDTO.setBookId(checkoutRequest.getBook().getId());
                orderItemDTO.setBookName(checkoutRequest.getBook().getTitle());
                orderItemDTO.setQuantity(checkoutRequest.getQuantity());
                orderItemDTO.setPrice(checkoutRequest.getBook().getPrice());

                return orderItemDTO;
            }).collect(Collectors.toList());

            // int totalPrice = orderItems.stream().mapToInt(item -> item.getQuantity() *
            // item.getPrice()).sum();

            int totalPrice = orderItems.stream()
                    .map(orderItemDTO -> {
                        Integer[] response = restTemplate.postForObject(
                                "http://localhost:8888/bookPrice",
                                new Integer[] { orderItemDTO.getPrice(), orderItemDTO.getQuantity() },
                                Integer[].class);
                        return (response != null && response.length > 0) ? response[0] : 0;
                    })
                    .reduce(0, Integer::sum);

            Kafka_OrderDTO orderDTO = new Kafka_OrderDTO();
            orderDTO.setUserId(userService.getCurUser().getUser_id());
            orderDTO.setUsername(userService.getCurUser().getUserAuth().getUsername());
            orderDTO.setOrderTime(orderTime);
            orderDTO.setTotalPrice(totalPrice);
            orderDTO.setOrderItems(orderItems);

            kafkaTemplate.send("orderTopic", orderDTO);

            return ResponseEntity.ok("Order created successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create order: " + e.getMessage());
        }
    }

    @GetMapping("/api/orders/all")
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderService.findOrdersByUser(userService.getCurUser());
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderDTOs.add(convertDTO(order));
        }
        return orderDTOs;
    }

    @GetMapping("/api/orders")
    public List<OrderDTO> getOrders(
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : null;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : null;

        List<Order> orders = orderService.findOrders(bookName, start, end, userService.getCurUser());
        if ("admin".equals(userService.getCurUser().getNickname())) {
            orders = orderService.findAllOrders(bookName, start, end);
        }

        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            System.out.println(order.toString());
            orderDTOs.add(convertDTO(order));
        }
        return orderDTOs;
    }

    @PostMapping("/api/orders/checkout")
    public ResponseEntity<String> checkout(@RequestBody int id) {

        Book book = bookService.findBookById(id);

        if (book.getStocks() < 1) {
            return ResponseEntity.badRequest().body("《" + book.getTitle() + "》库存不足，无法购买");
        }

        Order order = orderService.addOrder(book, userService.getCurUser());

        System.out.println("订单信息：" + order);
        return ResponseEntity.ok("请确认订单信息：" + order.toString());
    }

    /*
     * @PostMapping("/api/orders/checkoutfromcart")
     * public ResponseEntity<String> createOrder(@RequestBody List<CheckoutRequest>
     * checkoutRequests) {
     * 
     * List<Cart> cartItems = new ArrayList<>();
     * 
     * for (CheckoutRequest checkoutRequest : checkoutRequests) {
     * Cart cartItem = new Cart(checkoutRequest.getCartId(),
     * userService.getCurUser(),
     * checkoutRequest.getQuantity(), checkoutRequest.getBook());
     * Book book = cartItem.getBook();
     * if (book.getStocks() < cartItem.getQuantity()) {
     * return ResponseEntity.badRequest()
     * .body(" 《" + book.getTitle() + "》 的库存不足" + cartItem.getQuantity() +
     * " 本，无法下单！");
     * }
     * cartItems.add(cartItem);
     * }
     * orderService.createOrder(userService.getCurUser(), cartItems);
     * return ResponseEntity.ok("已成功下单，可至订单页面查看详情");
     * 
     * }
     */

    @GetMapping("/api/orders/statistics")
    public List<BookStatisticsDTO> getStatistics(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : null;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : null;

        return orderService.getStatistics(start, end, userService.getCurUser());
    }

    private OrderDTO convertDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDestination(order.getDestination());
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderTime(order.getOrderTime());
        orderDTO.setRecipient(order.getRecipient());
        int totalPrice = 0;

        List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream().map(item -> {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setItemId(item.getId().getItemId());
            itemDTO.setBookId(item.getBookId());
            itemDTO.setBookName(item.getBookTitle());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());

            return itemDTO;
        }).collect(Collectors.toList());

        for (OrderItem orderItem : order.getOrderItems()) {
            totalPrice += (orderItem.getPrice());
        }

        orderDTO.setTotalPrice(totalPrice);
        orderDTO.setOrderItems(orderItemDTOs);

        return orderDTO;
    }

    @GetMapping("/admin/orders/booksales")
    public List<BookSalesDTO> getBookSales(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : null;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : null;

        return orderService.getBookSales(start, end);
    }

    @GetMapping("/admin/orders/userpurchases")
    public List<UserPurchaseDTO> getUserPurchases(@RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : null;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : null;

        return orderService.getUserPurchases(start, end);
    }
}