package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private int orderId;
    private LocalDateTime orderTime;
    private String destination;
    private int totalPrice;
    private List<OrderItemDTO> orderItems;

}