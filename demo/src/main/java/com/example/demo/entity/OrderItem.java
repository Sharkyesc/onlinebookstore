package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @ToString.Exclude
    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /*
     * @ManyToOne(fetch = FetchType.EAGER)
     * 
     * @JoinColumn(name = "book_id", nullable = false)
     * private Book book;
     */

    private int quantity;

    @Column(name = "book_id")
    private int bookId;

    private String bookTitle;

    private int price;

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
