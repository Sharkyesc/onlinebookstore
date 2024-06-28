package com.example.demo.service;

import com.example.demo.dao.BookDao;
import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

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
    public Book addBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setAuthor(bookDTO.getAuthor());
        book.setCoverSrc(bookDTO.getCoverSrc());
        book.setDescription(bookDTO.getDescription());
        book.setIsbn(bookDTO.getIsbn());
        book.setPrice(bookDTO.getPrice());
        book.setStocks(bookDTO.getStocks());
        book.setTitle(bookDTO.getTitle());

        bookDao.save(book);
        return book;
    }

    @Override
    public Book updateBook(int id, BookDTO bookDTO) {
        Book book = bookDao.findOne(id);
        book.setAuthor(bookDTO.getAuthor());
        book.setDescription(bookDTO.getDescription());
        book.setIsbn(bookDTO.getIsbn());
        book.setPrice(bookDTO.getPrice());
        book.setStocks(bookDTO.getStocks());
        book.setTitle(bookDTO.getTitle());

        bookDao.save(book);
        return book;
    }

    @Override
    public void deleteBook(Integer id) {
        bookDao.delete(id);
    }

    @Override
    public Page<Book> findByTitle(String search, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookDao.findByTitle(search, pageable);
    }
}
