package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookStatisticsDTO {
    private String bookTitle;
    private int totalQuantity;
    private int totalPrice;

    public BookStatisticsDTO(String title, int quantity, int price) {
        this.bookTitle = title;
        this.totalQuantity = quantity;
        this.totalPrice = price;
    }

}
