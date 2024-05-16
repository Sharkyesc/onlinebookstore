package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    private int cartId;

    private int userId, quantity;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Cart() {
    }

    public Cart(int cartId, Book book, int userId, int quantity) {
        this.cartId = cartId;
        this.book = book;
        this.userId = userId;
        this.quantity = quantity;
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

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
