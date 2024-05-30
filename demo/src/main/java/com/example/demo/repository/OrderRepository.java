package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.BookSalesDTO;
import com.example.demo.dto.UserPurchaseDTO;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
        List<Order> findByUser(User user);

        @Query("SELECT o FROM Order o WHERE o.user = :user AND "
                        + "(:start IS NULL OR o.orderTime >= :start) AND "
                        + "(:end IS NULL OR o.orderTime <= :end)")
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

        @Query("SELECT new com.example.demo.dto.BookSalesDTO(b.title, SUM(i.quantity)) " +
                        "FROM Order o JOIN o.orderItems i JOIN i.book b " +
                        "WHERE o.orderTime BETWEEN :startDate AND :endDate " +
                        "GROUP BY b.title " +
                        "ORDER BY SUM(i.quantity) DESC")
        List<BookSalesDTO> findBookSales(@Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

        @Query("SELECT new com.example.demo.dto.UserPurchaseDTO(u.nickname, SUM(o.totalPrice)) " +
                        "FROM Order o JOIN o.user u " +
                        "WHERE o.orderTime BETWEEN :startDate AND :endDate " +
                        "GROUP BY u.nickname " +
                        "ORDER BY SUM(o.totalPrice) DESC")
        List<UserPurchaseDTO> findUserPurchases(@Param("startDate") LocalDateTime startDate,
                        @Param("endDate") LocalDateTime endDate);

}