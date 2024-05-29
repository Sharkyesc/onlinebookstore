package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "userauth")
public class UserAuth {
    @Id
    @Column(name = "user_id")
    private int user_id;

    private String username;
    private String password;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public UserAuth() {
    }

    public UserAuth(int user_id, String username, String password, User user) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.user = user;
    }
}
