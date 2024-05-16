package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    private int userId;
    private BigDecimal totalPrice;
    private LocalDateTime orderTime;
    private String recipient, contactPhone, destination;

    public Order() {
    }

    public Order(int orderId, int userId, BigDecimal totalPrice, LocalDateTime orderTime,
            String recipient, String contactPhone, String destination) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
        this.recipient = recipient;
        this.contactPhone = contactPhone;
        this.destination = destination;
    }

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    public void setTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            totalPrice = totalPrice.add(orderItem.getPrice());
        }
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "\n" +
                "订单编号：" + orderId +
                "\n用户ID：" + userId +
                "\n总价：" + totalPrice +
                "\n下单时间：" + orderTime +
                "\n收货人：'" + recipient + '\'' +
                "\n联系电话：'" + contactPhone + '\'' +
                "\n收货地址：'" + destination + '\'';
    }

}