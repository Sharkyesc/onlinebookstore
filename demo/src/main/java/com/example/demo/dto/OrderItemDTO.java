package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private int itemId;
    private int bookId;
    private String bookName;
    private int quantity;
    private int price;

}
