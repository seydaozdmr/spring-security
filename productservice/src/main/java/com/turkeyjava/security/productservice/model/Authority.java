package com.turkeyjava.security.productservice.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Authority {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id ;
    private String authority;

    @ManyToMany(mappedBy = "authorities")
    private List<Role> roles=new ArrayList<>();

    public Authority() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public void addRole(Role role){
        this.roles.add(role);
    }
}
