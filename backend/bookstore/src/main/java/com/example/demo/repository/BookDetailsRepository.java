package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.entity.BookDetail;

import java.util.Optional;
import org.bson.types.ObjectId;

public interface BookDetailsRepository extends MongoRepository<BookDetail, ObjectId> {
    Optional<BookDetail> findByBookName(String bookName);
}
