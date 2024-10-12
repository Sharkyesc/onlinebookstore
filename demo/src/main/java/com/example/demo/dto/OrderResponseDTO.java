package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDTO {
    private String status; // "SUCCESS"/"FAILURE"
    private String message;
}
