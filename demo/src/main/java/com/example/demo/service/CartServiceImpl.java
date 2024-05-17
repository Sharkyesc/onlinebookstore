package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.Cart;
import com.example.demo.repository.CartRepository;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookService bookService;

    @Override
    public Book getBookByBookId(int bookId) {
        return bookService.findBookById(bookId);
    }

    @Override
    public Cart getByCartId(int cartId) {
        return cartRepository.findByCartId(cartId);
    }

    @Override
    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }

    @Override
    public void addCartItem(int bookId) {
        Cart cartItem = cartRepository.findByBookId(bookId);
        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartRepository.save(cartItem);
        } else {
            Cart newCartItem = new Cart();
            Book book = getBookByBookId(bookId);
            newCartItem.setBook(book);
            newCartItem.setPrice(book.getPrice());
            newCartItem.setTitle(book.getTitle());
            newCartItem.setCoverSrc(book.getCoverSrc());
            newCartItem.setUserId(1);
            newCartItem.setQuantity(1);
            cartRepository.save(newCartItem);
        }
    }

    @Override
    public void changeCartItemNumber(int id, int number) {
        Cart cartItem = cartRepository.findByCartId(id);
        if (cartItem != null) {
            cartItem.setQuantity(number);
            cartRepository.save(cartItem);
        }
    }

    @Override
    public void deleteCartItem(int id) {
        cartRepository.deleteById(id);
    }
}