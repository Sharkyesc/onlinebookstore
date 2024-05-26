package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.UserAuth;
import com.example.demo.repository.UserAuthRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody UserAuth userAuth) {
        Map<String, Object> response = new HashMap<>();
        String rawPassword = userAuth.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        userAuth.setPassword(encodedPassword);
        userAuthRepository.save(userAuth);
        response.put("success", true);
        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestBody UserAuth userAuth) {
        Map<String, Object> response = new HashMap<>();
        UserAuth foundUserAuth = userAuthRepository.findByUsername(userAuth.getUsername());
        if (foundUserAuth != null && passwordEncoder.matches(userAuth.getPassword(), foundUserAuth.getPassword())) {
            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return response;
    }
}
