package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Integer id) {
        System.out.println(bookRepository.findById(id).orElse(null).toString());
        return bookRepository.findById(id).orElse(null);
    }

    // 添加书籍
    public void addBook(Book book) {
        bookRepository.save(book);
    }

    // 更新书籍信息
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    // 删除书籍
    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }
}
