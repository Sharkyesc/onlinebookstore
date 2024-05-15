package com.example.demo.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Id
    private int id;

    private int stocks, salesvolume;
    private String isbn, title, coverSrc, author, price, press, description;
    private Date pubTime;

    public Book() {
    }

    public Book(int id, String isbn, String title, String coverSrc, String author,
            String price, String press, Date pubTime, int stocks, int salesvolume, String description) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalesVolume() {
        return salesvolume;
    }

    public void setSalesVolume(int salesvolume) {
        this.salesvolume = salesvolume;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getCoverSrc() {
        return coverSrc;
    }

    public void setCoverSrc(String coverSrc) {
        this.coverSrc = coverSrc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public int getStocks() {
        return stocks;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }
}