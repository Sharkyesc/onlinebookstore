package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.example.demo.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b WHERE :search IS NULL OR b.title LIKE %:search%")
    Page<Book> findByTitleContainingIgnoreCase(@Param("search") String search, Pageable pageable);
}