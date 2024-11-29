package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.BookDetail;

import java.util.Optional;

public interface BookDetailsRepository extends MongoRepository<BookDetail, Integer> {
    Optional<BookDetail> findByBookName(String bookName);
}
