package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.service.UserService;
import com.example.demo.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("")
    public List<Cart> viewCart() {
        return cartService.findCartsByUser(userService.getCurUser());
    }

    @PutMapping("")
    public Confirmation addCartItem(@RequestParam("bookId") int bookId) {
        Confirmation confirmation = new Confirmation();

        Book book = bookService.findBookById(bookId);
        cartService.addCartItem(book, userService.getCurUser());

        confirmation.setMessage("《" + book.getTitle() + "》已成功加入购物车");

        return confirmation;
    }

    @PutMapping("/{id}")
    public Confirmation changeCartItemNumber(@PathVariable("id") int id, @RequestParam("number") int number) {
        Confirmation confirmation = new Confirmation();
        cartService.changeCartItemNumber(id, number);
        Book bookChanged = cartService.getByCartId(id).getBook();
        confirmation.setMessage("《" + bookChanged.getTitle() + "》加购数量已更新为：" + number + "本");

        return confirmation;
    }

    @DeleteMapping("/{id}")
    public Confirmation deleteCartItem(@PathVariable int id) {
        Confirmation confirmation = new Confirmation();
        cartService.deleteCartItem(id);

        confirmation.setMessage("已删除！");
        return confirmation;
    }

}
