package com.example.demo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "book_details")
public class BookDetail {

    @Id
    private int id;

    private String bookName;
    private String description;

    public BookDetail(int id, String bookName, String description) {
        this.id = id;
        this.bookName = bookName;
        this.description = description;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
