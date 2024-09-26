package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
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
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.service.TimerService;

@Scope("prototype")
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TimerService timerService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        try {
            if (userRepository.findUserAuthByUsername(registerRequest.getUsername()) != null)
                return ResponseEntity.status(500).body("用户名不可用！");

            userService.registerUser(registerRequest.getNickname(), registerRequest.getUsername(),
                    registerRequest.getEmail(), registerRequest.getPassword());

            return ResponseEntity.ok("注册成功！请登录");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("注册失败");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        UserAuth userAuth = userRepository.findUserAuthByUsername(loginRequest.getUsername());

        if (userAuth == null)
            return ResponseEntity.badRequest().body("该用户不存在");

        if (!userAuth.getUser().isEnabled()) {
            return ResponseEntity.badRequest().body("你的账号已被禁用！");
        }
        if (userAuth != null && userService.matchesPassword(loginRequest.getPassword(), userAuth.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", loginRequest.getUsername());

            timerService.startTimer();

            return ResponseEntity.ok("登录成功");
        } else {
            return ResponseEntity.badRequest().body("用户名或密码错误");
        }
    }

    @GetMapping("logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {

        long elapsedTime = timerService.stopTimer();
        System.out.println(elapsedTime + "ms");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok("Logout successful. Time: " + elapsedTime + " ms.");
    }
}
