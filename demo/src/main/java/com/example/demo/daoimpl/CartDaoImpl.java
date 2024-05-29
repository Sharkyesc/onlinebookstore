package com.example.demo.daoimpl;

import com.example.demo.dao.CartDao;
import com.example.demo.dao.BookDao;
import com.example.demo.entity.Cart;
import com.example.demo.entity.User;
import com.example.demo.entity.Book;
import com.example.demo.repository.CartRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDaoImpl implements CartDao {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Cart> findByUser(User user) {
        return cartRepository.findByUser(user);
    }

    @Override
    public Cart findByCartId(int cartId) {
        return cartRepository.findByCartId(cartId);
    }

    @Override
    public Cart findByBookId(int bookId) {
        Book book = bookDao.findOne(bookId);
        return cartRepository.findByBook(book);
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void update(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void delete(Integer id) {
        cartRepository.deleteById(id);
    }
}
