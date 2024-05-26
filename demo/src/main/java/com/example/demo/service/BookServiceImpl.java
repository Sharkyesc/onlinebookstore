package com.example.demo.service;

import com.example.demo.dao.BookDao;
import com.example.demo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> findAllBooks() {
        return bookDao.findAll();
    }

    @Override
    public Book findBookById(Integer id) {
        return bookDao.findOne(id);
    }

    @Override
    public void addBook(Book book) {
        bookDao.save(book);
    }

    @Override
    public void updateBook(Book book) {
        bookDao.update(book);
    }

    @Override
    public void deleteBook(Integer id) {
        bookDao.delete(id);
    }
}
