package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int totalPrice;
    private LocalDateTime orderTime;
    private String recipient, contactPhone, destination;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public Order() {
    }

    public Order(int orderId, User user, int totalPrice, LocalDateTime orderTime,
            String recipient, String contactPhone, String destination) {
        this.orderId = orderId;
        this.user = user;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
        this.recipient = recipient;
        this.contactPhone = contactPhone;
        this.destination = destination;
    }

    public void setTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += (orderItem.getPrice());
        }
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "\n" +
                "订单编号：" + orderId +
                "\n用户ID：" + user.getUser_id() +
                "\n总价：" + totalPrice +
                "\n下单时间：" + orderTime +
                "\n收货人：'" + recipient + '\'' +
                "\n联系电话：'" + contactPhone + '\'' +
                "\n收货地址：'" + destination + '\'';
    }
}
