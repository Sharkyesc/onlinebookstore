package com.example.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
@Getter
@Setter
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    private int userId, quantity;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private String title, coverSrc;
    private BigDecimal price;

    public Cart() {
    }

    public Cart(int cartId, Book book, int userId, int quantity) {
        this.cartId = cartId;
        this.book = book;
        this.userId = userId;
        this.quantity = quantity;
        this.title = book.getTitle();
        this.price = book.getPrice();
        this.coverSrc = book.getCoverSrc();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", bookId=" + book.getId() +
                ", userId=" + userId +
                ", quantity=" + quantity +
                '}';
    }

    public void setTitle() {
        this.title = book.getTitle();
    }

    public void setPrice() {
        this.price = book.getPrice();
    }

    public void setCoverSrc() {
        this.coverSrc = book.getCoverSrc();
    }

}
