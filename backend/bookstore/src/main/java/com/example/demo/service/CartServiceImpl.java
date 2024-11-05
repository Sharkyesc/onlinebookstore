package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import com.example.demo.dao.CartDao;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Override
    public List<Cart> findCartsByUser(User user) {
        return cartDao.findByUser(user);
    }

    @Override
    public Cart getByCartId(int cartId) {
        return cartDao.findByCartId(cartId);
    }

    @Override
    public void addCartItem(Book book, User user) {
        Cart cartItem = cartDao.findByBookandUser(book, user);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartDao.save(cartItem);
        } else {
            Cart newCartItem = new Cart();
            newCartItem.setBook(book);
            newCartItem.setUser(user);/*
                                       * newCartItem.setPrice(book.getPrice());
                                       * newCartItem.setTitle(book.getTitle());
                                       * newCartItem.setCoverSrc(book.getCoverSrc());
                                       */
            newCartItem.setQuantity(1);
            cartDao.save(newCartItem);
        }
    }

    @Override
    public void changeCartItemNumber(int id, int number) {
        Cart cartItem = cartDao.findByCartId(id);
        if (cartItem != null) {
            cartItem.setQuantity(number);
            cartDao.save(cartItem);
        }
    }

    @Override
    public void deleteCartItem(int id) {
        cartDao.delete(id);
    }

}