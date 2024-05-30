package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Order;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import com.example.demo.dto.BookStatisticsDTO;
import com.example.demo.dto.CheckoutRequest;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.OrderItemDTO;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderService.findOrdersByUser(userService.getCurUser());
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            orderDTOs.add(convertDTO(order));
        }
        return orderDTOs;
    }

    @GetMapping("")
    public List<OrderDTO> getOrders(
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : null;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : null;

        List<Order> orders = orderService.findOrders(bookName, start, end, userService.getCurUser());
        if ("ADMIN".equals(userService.getCurUser().getRole())) {
            orders = orderService.findAllOrders(bookName, start, end);
        }

        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            System.out.println(order.toString());
            orderDTOs.add(convertDTO(order));
        }
        return orderDTOs;
    }

    @PostMapping("/checkout")
    public Confirmation checkout(@RequestBody int id) {

        Confirmation confirmation = new Confirmation();

        Book book = bookService.findBookById(id);

        if (book.getStocks() < 1) {
            confirmation.setMessage("该书库存不足，无法购买");
            return confirmation;
        }

        Order order = orderService.addOrder(book, userService.getCurUser());
        confirmation.setMessage("请确认订单信息：" + order.toString());

        System.out.println("订单信息：" + order);

        return confirmation;
    }

    @PostMapping("/checkoutfromcart")
    public Confirmation createOrder(@RequestBody List<CheckoutRequest> checkoutRequests) {

        List<Cart> cartItems = new ArrayList<>();
        Confirmation confirmation = new Confirmation();

        for (CheckoutRequest checkoutRequest : checkoutRequests) {
            Cart cartItem = new Cart(checkoutRequest.getCartId(), checkoutRequest.getBook(),
                    userService.getCurUser(), checkoutRequest.getQuantity());
            Book book = cartItem.getBook();
            if (book.getStocks() < cartItem.getQuantity()) {
                confirmation.setMessage(" 《" + book.getTitle() + "》 的库存不足，无法下单！");
                return confirmation;
            }
            cartItems.add(cartItem);
        }
        orderService.createOrder(userService.getCurUser(), cartItems);
        confirmation.setMessage("已成功下单，可至订单页面查看详情");
        return confirmation;

    }

    @GetMapping("/statistics")
    public List<BookStatisticsDTO> getStatistics(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        return orderService.getStatistics(start, end, userService.getCurUser());
    }

    private OrderDTO convertDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDestination(order.getDestination());
        orderDTO.setOrderId(order.getOrderId());
        orderDTO.setOrderTime(order.getOrderTime());
        orderDTO.setRecipient(order.getRecipient());
        orderDTO.setTotalPrice(order.getTotalPrice());

        List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream().map(item -> {
            OrderItemDTO itemDTO = new OrderItemDTO();
            itemDTO.setItemId(item.getId().getItemId());
            itemDTO.setBookName(item.getBook().getTitle());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setPrice(item.getPrice());
            return itemDTO;
        }).collect(Collectors.toList());

        orderDTO.setOrderItems(orderItemDTOs);

        return orderDTO;
    }

}