package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            totalPrice = totalPrice.add(orderItem.getPrice());
        }
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", totalPrice=" + totalPrice +
                ", orderTime=" + orderTime +
                ", recipient='" + recipient + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

}