package com.example.demo.dao;

import com.example.demo.entity.Book;
import com.example.demo.entity.BookTag;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookDao {
    Book findOne(Integer id);

    List<Book> findAll();

    void save(Book book);

    void update(Book book);

    void delete(Integer id);

    Page<Book> findByTitle(String search, Pageable pageable);

    List<String> searchByTag(String tag);

    List<Book> findByTag(List<String> tags);
}
