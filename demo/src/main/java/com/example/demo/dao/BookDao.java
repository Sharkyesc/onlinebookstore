package com.example.demo.dao;

import com.example.demo.entity.Book;

import java.util.List;

public interface BookDao {
    Book findOne(Integer id);

    List<Book> findAll();

    void save(Book book);

    void update(Book book);

    void delete(Integer id);

    List<Book> findByTitle(String search);
}
