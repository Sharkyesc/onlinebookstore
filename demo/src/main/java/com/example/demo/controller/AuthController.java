package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.UserAuth;
import com.example.demo.repository.UserAuthRepository;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            if (userAuthRepository.findByUsername(registerRequest.getUsername()) != null)
                return ResponseEntity.status(500).body("用户名不可用！");

            userService.registerUser(registerRequest.getNickname(), registerRequest.getUsername(),
                    registerRequest.getEmail(), registerRequest.getPassword());

            return ResponseEntity.ok("注册成功！请登录");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("注册失败");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        UserAuth userAuth = userAuthRepository.findByUsername(loginRequest.getUsername());

        if (userAuth != null && userService.matchesPassword(loginRequest.getPassword(), userAuth.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", loginRequest.getUsername());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
