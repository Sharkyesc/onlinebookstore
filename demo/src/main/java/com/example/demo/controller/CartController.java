package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Book;
import com.example.demo.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    // Get cart data
    @GetMapping
    public List<Cart> getAllCartItems() {
        return cartService.getAllCartItems();
    }

    // Add to cart
    @PutMapping
    public Confirmation addCartItem(@RequestParam("bookId") int bookId) {
        Confirmation confirmation = new Confirmation();

        cartService.addCartItem(bookId);
        Book bookAdded = cartService.getBookByBookId(bookId);

        confirmation.setMessage("《" + bookAdded.getTitle() + "》已成功加入购物车");

        return confirmation;
    }

    // Change cart item number
    @PutMapping("/{id}")
    public ResponseEntity<Void> changeCartItemNumber(@PathVariable("id") int id, @RequestParam("number") int number) {
        cartService.changeCartItemNumber(id, number);
        return ResponseEntity.ok().build();
    }

    // Delete cart item
    @DeleteMapping("/{id}")
    public Confirmation deleteCartItem(@PathVariable int id) {
        Confirmation confirmation = new Confirmation();
        cartService.deleteCartItem(id);

        confirmation.setMessage("已删除！");
        return confirmation;
    }

}
