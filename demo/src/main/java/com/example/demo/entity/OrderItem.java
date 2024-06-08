package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderitems")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @JsonIgnore
    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private int quantity;
    private int price;

    public OrderItem(Order order, Book book, int quantity, int price, OrderItemId id) {
        this.order = order;
        this.book = book;
        this.quantity = quantity;
        this.price = price;
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = new OrderItemId();
        }
        id.setOrderId(order.getOrderId());

        if (id.getItemId() == 0) {
            int maxItemId = order.getOrderItems().stream()
                    .mapToInt(item -> item.getId().getItemId())
                    .max()
                    .orElse(0);
            id.setItemId(maxItemId + 1);
        }
    }

}
