package com.example.demo.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson2.JSON;
import com.example.demo.dao.BookDao;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookDetail;
import com.example.demo.repository.BookDetailsRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BookTagRepository;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Autowired 
    private BookTagRepository bookTagRepository;

    @Autowired
    private BookDetailsRepository bookDetailsRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String BOOK_KEY_PREFIX = "book:";

    @Override
    public Book findOne(Integer id) {
        Book book;
        Optional<Book> optionalBook = bookRepository.findById(id);
        book = optionalBook.orElse(null);

        Optional<BookDetail> detail = bookDetailsRepository.findByBookName(book.getTitle());
        if (detail.isPresent()){
            System.out.println("Not Null " + id);
            book.setDescription(detail.get().getDescription());
        } else {
            book.setDescription(null);
            System.out.println("It's Null");
        }

        return book;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findFirst15();
    }

    @Override
    public void save(Book book) {
        String redisKey = BOOK_KEY_PREFIX + book.getId();
        bookRepository.save(book);
        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(book));
        System.out.println("Book: " + book.getId() + " saved in DB and cached in Redis");
    }

    @Override
    public void update(Book book) {
        String redisKey = BOOK_KEY_PREFIX + book.getId();
        bookRepository.save(book);
        redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(book));
        System.out.println("Book: " + book.getId() + " updated in DB and cached in Redis");
    }

    @Override
    public void delete(Integer id) {
        String redisKey = BOOK_KEY_PREFIX + id;
        bookRepository.deleteById(id);
        redisTemplate.delete(redisKey);
        System.out.println("Book: " + id + " removed from DB and Redis");
    }

    @Override
    public Page<Book> findByTitle(String search, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(search, pageable);
    }

    @Override
    public List<String> searchByTag(String tag) {
        return bookTagRepository.findRelatedTags(tag);
    }

    @Override
    public List<Book> findByTag(List<String> tags) {
        List<Book> allBooks = new ArrayList<>();

        for (String tag : tags) {
            List<Book> books = bookRepository.findByTag(tag);
            System.out.println("dao:" + tag);
            allBooks.addAll(books);
        }

        return allBooks;
    }

}