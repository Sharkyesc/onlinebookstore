package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.entity.UserAuth;
import com.example.demo.repository.UserAuthRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.dto.UserRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody UserRequest registrationRequest) {

        System.out.println(registrationRequest.getUsername());
        System.out.println(registrationRequest.getPassword());

        Map<String, Object> response = new HashMap<>();

        User newUser = new User();
        userRepository.save(newUser);

        System.out.println("New User ID: " + newUser.getUser_id());

        UserAuth userAuth = new UserAuth();
        userAuth.setUser_id(newUser.getUser_id());
        userAuth.setUser(newUser);
        userAuth.setUsername(registrationRequest.getUsername());

        String rawPassword = registrationRequest.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        userAuth.setPassword(encodedPassword);

        System.out.println("UserAuth ID: " + userAuth.getUser_id());
        System.out.println("UserAuth Username: " + userAuth.getUsername());
        System.out.println("UserAuth Password: " + userAuth.getPassword());
        System.out.println("UserAuth User: " + userAuth.getUser());

        userAuthRepository.save(userAuth);
        response.put("success", true);

        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestBody UserRequest registrationRequest) {
        Map<String, Object> response = new HashMap<>();
        UserAuth foundUserAuth = userAuthRepository.findByUsername(registrationRequest.getUsername());
        if (foundUserAuth != null
                && passwordEncoder.matches(registrationRequest.getPassword(), foundUserAuth.getPassword())) {

            response.put("success", true);
        } else {
            response.put("success", false);
        }
        return response;
    }
    /*
     * @PostMapping("/login")
     * public Map<String, Object> loginUser() {
     * Map<String, Object> response = new HashMap<>();
     * response.put("success", true);
     * return response;
     * }
     */

}
