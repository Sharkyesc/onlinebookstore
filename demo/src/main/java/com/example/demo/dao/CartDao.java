package com.example.demo.dao;

import com.example.demo.entity.Cart;
import com.example.demo.entity.User;

import java.util.List;

public interface CartDao {
    List<Cart> findByUser(User user);

    Cart findByCartId(int cartId);

    void save(Cart cart);

    void update(Cart cart);

    void delete(Integer id);

}
