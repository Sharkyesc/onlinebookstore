package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@Table(name = "orderitems")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @JsonIgnore
    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private int quantity;
    private int price;

    public OrderItem() {
    }

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

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + id.getOrderId() +
                ", bookId=" + book.getId() +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
