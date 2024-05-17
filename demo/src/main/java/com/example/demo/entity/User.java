package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    private int user_id;

    private String nickname, address, phonenumber, email;

    public User() {
    }

    public User(int user_id, String nickname, String email, String address, String phonenumber) {
        this.user_id = user_id;
        this.nickname = nickname;
        this.email = email;
        this.address = address;
        this.phonenumber = phonenumber;
    }

}