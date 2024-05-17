package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Cart;
import java.util.List;

public interface CartService {
    Book getBookByBookId(int bookId);

    Cart getByCartId(int cartId);

    List<Cart> getAllCartItems();

    void addCartItem(int bookId);

    void changeCartItemNumber(int id, int number);

    void deleteCartItem(int id);
}