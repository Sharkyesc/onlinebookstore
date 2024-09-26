package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne
    @JsonIgnore
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime orderTime;
    private String recipient, contactPhone, destination;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n订单号: ").append(orderId).append("\n");
        sb.append("下单时间: ").append(orderTime).append("\n");
        sb.append("收件人: ").append(recipient).append("\n");
        sb.append("联系电话: ").append(contactPhone).append("\n");
        sb.append("收货地址: ").append(destination).append("\n");
        sb.append("订单明细:\n");
        for (OrderItem item : orderItems) {
            sb.append(" - ").append(item.getQuantity()).append(" 本 《").append(item.getBookTitle()).append("》，单价: ¥")
                    .append(String.format("%.2f", item.getPrice() / 100.00)).append("\n");
        }
        return sb.toString();
    }

}
