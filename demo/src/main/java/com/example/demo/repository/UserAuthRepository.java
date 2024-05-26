package com.example.demo.repository;

import com.example.demo.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {
    UserAuth findByUsername(String username);
}
