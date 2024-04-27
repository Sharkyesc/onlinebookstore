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
                        100),
                new Book(5, "吉祥寺的朝日奈", "￥56.30", "中田永一", "台海出版社", "2022-04-01", 100),
                new Book(6, "山之四季", "￥32.00", "高村光太郎", "云南人民出版社", "2017-01-01", 100),
                new Book(7, "非理性的人", "￥45.00", "威廉·巴雷特", "上海译文出版社", "2011-12-01", 100),
                new Book(8, "跃动青春1", "￥32.00", "高松美咲", "湖南文艺出版社", "2023-03-01", 100));
    }
}