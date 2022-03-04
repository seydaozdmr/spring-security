package com.turkeyjava.security.authenticationservice.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class User {
    //TODO To solve the exercise, you might find it useful to review the example we worked on in section 9.2.
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String username;
    private String password;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Otp> otpList;

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Otp> getOtpList() {
        return otpList;
    }

    public void setOtpList(List<Otp> otpList) {
        this.otpList = otpList;
    }
}
