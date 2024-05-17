package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.LoginRequest;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @PostMapping("")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        if ("luoyiyu".equals(loginRequest.getUsername()) && "123456".equals(loginRequest.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", loginRequest.getUsername());
            return ResponseEntity.ok(new LoginResponse(true, "Login successful"));
        } else {
            return ResponseEntity.badRequest().body(new LoginResponse(false, "Invalid username or password"));
        }
    }

}
