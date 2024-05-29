package com.example.demo.repository;

import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import com.example.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUser(User user);

    Cart findByCartId(int cartId);

    Cart findByBook(Book book);
}
