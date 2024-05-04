package com.example.demo.controller;

import com.example.demo.model.Order;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:3000")

public class orderController {

    private List<Order> orders = new ArrayList<>();

    public orderController() {
        orders.add(new Order(1, "2024-04-29 20:50", "绝叫", 2, "Shanghai Jiaotong University", "￥20.00"));
        orders.add(new Order(2, "2024-04-29 20:51", "太白金星有点烦", 1, "Shanghai", "￥15.00"));
        orders.add(new Order(3, "2024-04-29 20:52", "Flipped", 1, "Shanghai", "￥15.00"));
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orders;
    }

    @PostMapping("/checkout")
    public OrderConfirmation checkout(@RequestBody Order orderData) {

        OrderConfirmation confirmation = new OrderConfirmation();

        orders.add(orderData);
        confirmation.setMessage("请确认订单信息：" + orderData);

        System.out.println("订单信息：" + orderData);

        return confirmation;
    }

}
