package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.dto.BookDTO;

import java.util.List;

public interface BookService {
    List<Book> findAllBooks();

    Book findBookById(Integer id);

    Book addBook(BookDTO bookDTO);

    Book updateBook(int id, BookDTO bookDTO);

    void deleteBook(Integer id);

    List<Book> findByTitle(String search);
}
