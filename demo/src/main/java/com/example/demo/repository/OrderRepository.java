package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
        List<Order> findByUser(User user);

        @Query("SELECT o FROM Order o WHERE o.user = :user AND o.orderTime BETWEEN :start AND :end")
        List<Order> findByUserAndTimeRange(User user, @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end);

        @Query("SELECT o FROM Order o JOIN o.orderItems i WHERE " +
                        "(:bookName IS NULL OR LOWER(i.book.title) LIKE LOWER(CONCAT('%', :bookName, '%'))) AND " +
                        "(:start IS NULL OR o.orderTime >= :start) AND " +
                        "(:end IS NULL OR o.orderTime <= :end) AND " +
                        "(:user IS NULL OR o.user = :user)")
        List<Order> findOrders(@Param("bookName") String bookName,
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end, User user);

        @Query("SELECT o FROM Order o JOIN o.orderItems i WHERE " +
                        "(:bookName IS NULL OR LOWER(i.book.title) LIKE LOWER(CONCAT('%', :bookName, '%'))) AND " +
                        "(:start IS NULL OR o.orderTime >= :start) AND " +
                        "(:end IS NULL OR o.orderTime <= :end)" +
                        "ORDER BY o.orderId")
        List<Order> findOrdersByBookAndTimeRange(@Param("bookName") String bookName,
                        @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

}