package com.example.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "books")
public class Book {
    @Id
    private int id;

    private int stocks, salesvolume, price;
    private String isbn, title, coverSrc, author, press, description;
    private Date pubTime;

    public Book() {
    }

    public Book(int id, String isbn, String title, String coverSrc, String author,
            int price, String press, Date pubTime, int stocks, int salesvolume, String description) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.coverSrc = coverSrc;
        this.author = author;
        this.price = price;
        this.press = press;
        this.pubTime = pubTime;
        this.stocks = stocks;
        this.salesvolume = salesvolume;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", stocks=" + stocks +
                ", salesvolume=" + salesvolume +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", coverSrc='" + coverSrc + '\'' +
                ", author='" + author + '\'' +
                ", price='" + price + '\'' +
                ", press='" + press + '\'' +
                ", description='" + description + '\'' +
                ", pubTime=" + pubTime +
                '}';
    }

}