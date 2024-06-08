package com.example.demo.daoimpl;

import com.example.demo.dao.BookDao;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book findOne(Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void update(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void delete(Integer id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> findByTitle(String search, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(search, pageable);
    }
}
