
package com.example.controller;

import com.alibaba.fastjson2.JSON;
import com.example.repository.BookRepository;
import com.example.dto.ResultDTO;
import com.example.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/{bookName}")
    public ResultDTO getBookAuthorByName(@PathVariable("bookName") String bookName) {
        Optional<Book> book = bookRepository.findByTitle(bookName);

        if (book.isPresent()) {
            String author = book.get().getAuthor();
            return new ResultDTO("success", "作者：" + author);
        } else {
            return new ResultDTO("failure", "未找到该书");
        }
    }

}
