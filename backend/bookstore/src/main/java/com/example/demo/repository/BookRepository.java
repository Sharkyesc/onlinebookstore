package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.example.demo.entity.Book;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT b FROM Book b WHERE (:search IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%'))) AND b.stocks >= 0")
    Page<Book> findByTitleContainingIgnoreCase(@Param("search") String search, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE (b.id >= 1 AND b.id <= 15)")
    List<Book> findFirst15();

    List<Book> findByTag(String tag);

}
