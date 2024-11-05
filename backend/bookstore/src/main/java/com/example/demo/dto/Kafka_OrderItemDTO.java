package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Kafka_OrderItemDTO {
    private int cartId;
    private int bookId;
    private String bookName;
    private int quantity;
    private int price;

}
