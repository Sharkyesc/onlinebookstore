package com.example.demo.controller;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserAuth;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.entity.User;
import com.example.demo.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("")
public class UserController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/current")
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

    @GetMapping("/api/userinfo")
    public ResponseEntity<Object> getUserInfo() {
        HttpSession session = request.getSession(false);
        Map<String, String> response = new HashMap<>();
        if (session != null) {
            String username = (String) session.getAttribute("user");
            if (username != null) {
                UserAuth userAuth = userRepository.findUserAuthByUsername(username);
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

    @GetMapping("/api/usercompleteinfo")
    public ResponseEntity<Object> getUserCompleteInfo() {
        User user = userService.getCurUser();
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setAddress(user.getAddress());
            userDTO.setAvatarSrc(user.getAvatarSrc());
            userDTO.setEmail(user.getEmail());
            userDTO.setNickname(user.getNickname());
            userDTO.setPhonenumber(user.getPhonenumber());
            userDTO.setUsername(user.getUserAuth().getUsername());

            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.status(401).body("User not logged in");
    }

    @PutMapping("/api/updateuserinfo")
    public ResponseEntity<String> updateUserInfo(@RequestBody UserDTO userDetails) {
        try {
            User user = userService.getCurUser();
            user.setAddress(userDetails.getAddress());
            user.setAvatarSrc(userDetails.getAvatarSrc());
            user.setEmail(userDetails.getEmail());
            user.setNickname(userDetails.getNickname());
            user.setPhonenumber(userDetails.getPhonenumber());

            System.out.println(user.toString());
            userService.updateUser(user);

            return ResponseEntity.ok(" " + userDetails.getUsername() + " 的个人信息已更新！");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("更新用户信息时出错: " + e.getMessage());
        }
    }

    @GetMapping("/admin/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/admin/users/{userId}/disable")
    public ResponseEntity<Void> disableUser(@PathVariable int userId) {
        userService.disableUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admin/users/{userId}/enable")
    public ResponseEntity<Void> enableUser(@PathVariable int userId) {
        userService.enableUser(userId);
        return ResponseEntity.ok().build();
    }

}
