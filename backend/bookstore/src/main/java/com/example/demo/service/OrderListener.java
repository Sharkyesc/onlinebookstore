package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.example.demo.dto.*;

@Component
public class OrderListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private KafkaTemplate<String, OrderResponseDTO> kafkaTemplate;

    @KafkaListener(topics = "orderTopic", groupId = "order_group")
    public void listenOrder(Kafka_OrderDTO orderDTO, Acknowledgment acknowledgment) {
        try {
            OrderResponseDTO orderResponse = orderService.processOrder(orderDTO);

            kafkaTemplate.send("orderResponseTopic", orderResponse);
            acknowledgment.acknowledge();

        } catch (Exception e) {
            OrderResponseDTO errorResponse = new OrderResponseDTO();
            errorResponse.setStatus("FAILURE");
            errorResponse.setUsername(orderDTO.getUsername());
            errorResponse.setMessage("Failed to process order: " + e.getMessage());
            kafkaTemplate.send("orderResponseTopic", errorResponse);
            acknowledgment.acknowledge();

        }
    }

}
