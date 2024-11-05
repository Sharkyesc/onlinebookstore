package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookSalesDTO {
    private String bookTitle;
    private Long totalQuantity;
}
