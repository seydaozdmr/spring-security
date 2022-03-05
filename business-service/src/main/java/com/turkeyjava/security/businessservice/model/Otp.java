package com.turkeyjava.security.businessservice.model;

public class Otp {
    private String code;
    private User user;

    public Otp() {
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
}
