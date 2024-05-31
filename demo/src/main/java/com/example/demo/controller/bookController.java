package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.demo.entity.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.service.BookService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public Book findBook(@PathVariable("id") Integer id) {
        System.out.println("Searching Book: " + id);
        return bookService.findBookById(id);
    }

    @GetMapping("")
    public List<Book> getBooks(@RequestParam(required = false) String search) {
        if (search == null || search.isEmpty()) {
            return bookService.findAllBooks();
        } else {
            return bookService.findByTitle(search);
        }
    }

    @PostMapping("")
    public ResponseEntity<Book> addBook(@RequestBody BookDTO bookDTO) {
        Book createdBook = bookService.addBook(bookDTO);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        Book updatedBook = bookService.updateBook(id, bookDTO);
        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

}
