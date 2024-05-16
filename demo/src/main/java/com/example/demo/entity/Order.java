package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    private int orderId;

    private int userId, totalPrice;
    private LocalDateTime orderTime;
    private String recipient, contactPhone, destination;

    public Order() {
    }

    public Order(int orderId, int userId, int totalPrice, LocalDateTime orderTime,
            String recipient, String contactPhone, String destination) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderTime = orderTime;
        this.recipient = recipient;
        this.contactPhone = contactPhone;
        this.destination = destination;
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
