package com.example.demo.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserAuth;
import com.example.demo.repository.UserAuthRepository;
import com.example.demo.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @GetMapping("/current")
    public ResponseEntity<Object> getCurrentUser() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object user = session.getAttribute("user");
            if (user != null) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(401).body("No user logged in");
    }

    @GetMapping("/userinfo")
    public ResponseEntity<Object> getUserInfo() {
        HttpSession session = request.getSession(false);
        Map<String, String> response = new HashMap<>();
        if (session != null) {
            String username = (String) session.getAttribute("user");
            if (username != null) {
                UserAuth userAuth = userAuthRepository.findByUsername(username);
                if (userAuth != null) {
                    User user = userAuth.getUser();
                    if (user != null) {
                        response.put("avatarSrc", user.getAvatarSrc());
                        response.put("nickname", user.getNickname());
                        System.out.println(user.getNickname());
                        System.out.println(user.getAvatarSrc());
                        return ResponseEntity.ok(response);
                    }
                }
            }
        }
        response.put("avatarSrc",
                "https://img0.baidu.com/it/u=1849651366,4275781386&fm=253&fmt=auto&app=138&f=JPEG?w=585&h=500");
        response.put("nickname", "请登录");
        return ResponseEntity.ok(response);
    }

}
