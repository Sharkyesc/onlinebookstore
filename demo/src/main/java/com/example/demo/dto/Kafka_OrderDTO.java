package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class Kafka_OrderDTO {
    private int userId;
    private String username;
    private LocalDateTime orderTime;
    private int totalPrice;
    private List<Kafka_OrderItemDTO> orderItems;

}