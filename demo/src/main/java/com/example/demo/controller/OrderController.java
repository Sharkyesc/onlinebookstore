package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Order;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import com.example.demo.dto.CheckoutRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public List<Order> getOrders() {
        return orderService.findOrdersByUser(userService.getCurUser());
    }

    @PostMapping("/checkout")
    public Confirmation checkout(@RequestBody int id) {

        Confirmation confirmation = new Confirmation();

        Book book = bookService.findBookById(id);

        Order order = orderService.addOrder(book, userService.getCurUser());
        confirmation.setMessage("请确认订单信息：" + order.toString());

        System.out.println("订单信息：" + order);

        return confirmation;
    }

    @PostMapping("/checkoutfromcart")
    public ResponseEntity<Object> createOrder(@RequestBody List<CheckoutRequest> checkoutRequests,
            HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            String username = (String) session.getAttribute("user");
            if (username != null) {
                List<Cart> cartItems = new ArrayList<>();
                for (CheckoutRequest checkoutRequest : checkoutRequests) {
                    Cart cartItem = new Cart();
                    cartItem.setBook(checkoutRequest.getBook());
                    cartItem.setCartId(checkoutRequest.getCartId());
                    cartItem.setCoverSrc(checkoutRequest.getCoverSrc());
                    cartItem.setPrice(checkoutRequest.getPrice());
                    cartItem.setQuantity(checkoutRequest.getQuantity());
                    cartItem.setTitle(checkoutRequest.getTitle());
                    cartItem.setUser(userService.getCurUser());
                    System.out.println(cartItem.toString());
                    cartItems.add(cartItem);
                }
                orderService.createOrder(username, cartItems);
                return ResponseEntity.ok("Order created successfully");
            }
        }
        return ResponseEntity.status(401).body("User not logged in");
    }

}