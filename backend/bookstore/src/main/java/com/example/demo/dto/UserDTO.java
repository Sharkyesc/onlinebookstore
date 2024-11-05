package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username, nickname;
    private String email, address, phonenumber, avatarSrc;
}
