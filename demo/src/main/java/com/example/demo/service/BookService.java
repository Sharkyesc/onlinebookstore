package com.example.demo.service;

import com.example.demo.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> findAllBooks();

    Book findBookById(Integer id);

    void addBook(Book book);

    void updateBook(Book book);

    void deleteBook(Integer id);
}
