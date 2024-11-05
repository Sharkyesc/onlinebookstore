package com.example.demo.repository;

import com.example.demo.entity.User;
import com.example.demo.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT ua FROM UserAuth ua WHERE ua.username = :username")
    UserAuth findUserAuthByUsername(String username);
}
