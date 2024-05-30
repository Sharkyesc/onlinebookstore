package com.example.demo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int user_id;

    private String nickname, address, phonenumber, email, avatarSrc, role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserAuth userAuth;

    public User() {
    }

    public User(int user_id, String nickname, String email, String address, String phonenumber, String avatarSrc) {
        this.user_id = user_id;
        this.nickname = nickname;
        this.email = email;
        this.address = address;
        this.phonenumber = phonenumber;
        this.avatarSrc = avatarSrc;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", nickname='" + nickname + '\'' +
                ", address='" + address + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", email='" + email + '\'' +
                ", avatarSrc='" + avatarSrc + '\'' +
                '}';
    }

}
