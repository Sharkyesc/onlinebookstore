package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/orders")
public class orderController {

    // 模拟订单数据
    private List<Order> orders = new ArrayList<>();

    // 构造函数初始化订单数据
    public orderController() {
        orders.add(new Order(1, "2024-04-29 20:50", "Book A", 2, 20));
        orders.add(new Order(2, "2024-04-29 20:51", "Book B", 1, 15));
        // 添加其他订单...
    }

    // 获取所有订单
    @GetMapping
    public List<Order> getAllOrders() {
        return orders;
    }

}
