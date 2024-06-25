package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;

import java.util.List;

import com.example.demo.entity.Book;
import com.example.demo.dto.BookDTO;
import com.example.demo.service.BookService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/api/books/{id}")
    public Book findBook(@PathVariable("id") Integer id) {
        System.out.println("Searching Book: " + id);
        return bookService.findBookById(id);
    }

    @GetMapping("/api/books/all")
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/api/books")
    public Page<Book> getBooks(@RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size) {
        return bookService.findByTitle(search, page, size);
    }

    @GetMapping("/admin/books")
    public Page<Book> getBooks_admin(@RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size) {
        return bookService.findByTitle(search, page, size);
    }

    @PostMapping("/api/books")
    public ResponseEntity<Book> addBook(@RequestBody BookDTO bookDTO) {
        Book createdBook = bookService.addBook(bookDTO);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("/api/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody BookDTO bookDTO) {
        Book updatedBook = bookService.updateBook(id, bookDTO);
        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

}
