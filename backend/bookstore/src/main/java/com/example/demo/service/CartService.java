package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import com.example.demo.entity.Book;
import java.util.List;

public interface CartService {

    List<Cart> findCartsByUser(User user);

    Cart getByCartId(int cartId);

    void addCartItem(Book book, User user);

    void changeCartItemNumber(int id, int number);

    void deleteCartItem(int id);

}