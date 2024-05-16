package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Cart;
import com.example.demo.repository.CartRepository;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookService bookService;

    public Book getBookByBookId(int bookId) {
        return bookService.findBookById(bookId);
    }

    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }

    public void addCartItem(int bookId) {
        Cart cartItem = cartRepository.findByBookId(bookId);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartRepository.save(cartItem);
        } else {
            Cart newCartItem = new Cart();
            newCartItem.setBook(getBookByBookId(bookId));
            newCartItem.setPrice();
            newCartItem.setTitle();
            newCartItem.setUserId(1);
            newCartItem.setQuantity(1);
            cartRepository.save(newCartItem);
        }
    }

    public void changeCartItemNumber(int id, int number) {
        Cart cartItem = cartRepository.findByCartId(id);
        if (cartItem != null) {
            cartItem.setQuantity(number);
            cartRepository.save(cartItem);
        }
    }

    public void deleteCartItem(int id) {
        cartRepository.deleteById(id);
    }
}
