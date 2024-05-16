package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    @PostMapping("/checkout")
    public Confirmation checkout(@RequestBody Order orderData) {

        Confirmation confirmation = new Confirmation();

        orderService.addOrder(orderData);
        confirmation.setMessage("请确认订单信息：" + orderData.toString());

        System.out.println("订单信息：" + orderData);

        return confirmation;
    }

}
