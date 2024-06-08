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
    public ResponseEntity<String> addCartItem(@RequestParam("bookId") int bookId) {

        Book book = bookService.findBookById(bookId);

        if (book.getStocks() < 1) {
            return ResponseEntity.badRequest().body("《" + book.getTitle() + "》库存数量不足，无法加购！");
        }

        cartService.addCartItem(book, userService.getCurUser());

        return ResponseEntity.ok("《" + book.getTitle() + "》已成功加入购物车");

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> changeCartItemNumber(@PathVariable("id") int id, @RequestParam("number") int number) {
        cartService.changeCartItemNumber(id, number);
        Book bookChanged = cartService.getByCartId(id).getBook();

        return ResponseEntity.ok("《" + bookChanged.getTitle() + "》加购数量已更新为：" + number + "本");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable int id) {
        cartService.deleteCartItem(id);

        return ResponseEntity.ok("已删除！");
    }

}
