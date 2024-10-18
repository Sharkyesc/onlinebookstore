package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.Acknowledgment;

import com.example.demo.dto.OrderResponseDTO;
import com.example.demo.dao.UserDao;

@Service
public class OrderResponseListener {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    UserDao userDao;

    public OrderResponseListener(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "orderResponseTopic", groupId = "order_group")
    public void listenOrderResponse(OrderResponseDTO orderResponse, Acknowledgment acknowledgment) {
        try {
            messagingTemplate.convertAndSend("/topic/order-status/" + orderResponse.getUsername(),
                    orderResponse.getMessage());

            acknowledgment.acknowledge();
            System.out.println("订单处理结果：" + orderResponse.getMessage());

        } catch (Exception e) {
            System.err.println("订单处理出错：" + e.getMessage());
            acknowledgment.acknowledge();
        }
    }
}
