package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.entity.BookTag;

import java.util.List;

import org.springframework.data.domain.Page;

public interface BookService {
    List<Book> findAllBooks();

    Book findBookById(Integer id);

    Book addBook(BookDTO bookDTO);

    Book updateBook(int id, BookDTO bookDTO);

    void deleteBook(Integer id);

    Page<Book> findByTitle(String search, int page, int size);

    List<Book> searchByTag(String tag);
}
