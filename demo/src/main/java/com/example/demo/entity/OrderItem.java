package com.example.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orderitems")
public class OrderItem {
    @Id
    @Column(name = "item_id")
    private int itemId;

    @Id
    @Column(name = "order_id")
    private int orderId;

    private int bookId;
    private int quantity;
    private BigDecimal price;

}
