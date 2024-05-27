package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import java.util.List;

public interface CartService {
    Book getBookByBookId(int bookId);

    List<Cart> findCartsByUser(User user);

    Cart getByCartId(int cartId);

    void addCartItem(int bookId);

    void changeCartItemNumber(int id, int number);

    void deleteCartItem(int id);

}