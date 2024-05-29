package com.example.demo.dto;

import com.example.demo.entity.Book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutRequest {
    private int cartId;
    private int quantity;
    private Book book;
    private String title, coverSrc;
    private int price;

}
