package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPurchaseDTO {
    private String nickname;
    private int totalAmount;
}