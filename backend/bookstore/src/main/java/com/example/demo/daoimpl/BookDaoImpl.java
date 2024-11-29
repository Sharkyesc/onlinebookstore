package com.example.demo.daoimpl;

import com.example.demo.dao.BookDao;
import com.example.demo.entity.Book;
import com.example.demo.entity.BookDetail;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BookDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson2.JSON;

import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookDetailsRepository bookDetailsRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String BOOK_KEY_PREFIX = "book:";

    @Override
    public Book findOne(Integer id) {
        String redisKey = BOOK_KEY_PREFIX + id;
        System.out.println("Searching Book: " + id + " in Redis");

        Book book;
        try {
            String bookJson = redisTemplate.opsForValue().get(redisKey);
            if (bookJson == null) {
                System.out.println("Book: " + id + " not found in Redis, searching in DB");
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

                if (book != null) {
                    redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(book));
                    System.out.println("Book: " + id + " has been cached in Redis");
                }
            } else {
                book = JSON.parseObject(bookJson, Book.class);
                System.out.println("Book: " + id + " found in Redis");
            }
        } catch (Exception e) {
            System.out.println("Redis unavailable. Fallback to DB for Book: " + id);
            e.printStackTrace();
            book = bookRepository.findById(id).orElse(null);
            Optional<BookDetail> detail = bookDetailsRepository.findByBookName(book.getTitle());
            if (detail.isPresent()){
                System.out.println("Not Null " + id);
                book.setDescription(detail.get().getDescription());
            } else {
                book.setDescription(null);
                System.out.println("It's Null");
            }
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

}