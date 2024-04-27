package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Book;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class bookController {

    @GetMapping
    public List<Book> getAllBooks() {
        // 硬编码书籍列表
        return Arrays.asList(
                new Book(1, "无声告白", "￥45.00", "伍绮诗", "江苏文艺出版社", "2015-07-01", 100),
                new Book(2, "绝叫", "￥58.00", "叶真中显", "北京联合出版公司", "2020-07-01", 100),
                new Book(3, "这么多年（全三册）", "￥128.00", "八月长安", "江苏凤凰文艺出版社", "2021-08-01", 100),
                new Book(4, "Flipped", "￥35.00", "Wendelin Van Draanen", "Knopf Books for Young Readers", "2001-01-01",
                        100));

    }
}