package com.example.demo.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.OrderItemDao;
import com.example.demo.entity.OrderItem;

import com.example.demo.repository.OrderItemRepository;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveOrderItem(OrderItem orderItem) {
        // int result = 10 / 0;
        orderItemRepository.save(orderItem);
    }

}