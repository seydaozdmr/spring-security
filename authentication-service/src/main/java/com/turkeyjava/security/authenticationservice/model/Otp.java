package com.turkeyjava.security.authenticationservice.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Otp {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public Otp() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Otp{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", user=" + user +
                '}';
    }
}
