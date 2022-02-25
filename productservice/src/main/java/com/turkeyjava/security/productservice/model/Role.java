package com.turkeyjava.security.productservice.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Role {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id ;
    private String roleName;

    @ManyToMany(cascade= CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorities" , joinColumns = @JoinColumn(
            name = "role_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn (name="authority_id",referencedColumnName = "id")
    )
    private List<Authority> authorities=new ArrayList<>();

    @ManyToMany(mappedBy = "roles")
    private List<User> users=new ArrayList<>();

    public Role() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    public void addAuthority(Authority authority){
        this.authorities.add(authority);
    }
    public void addUser(User user){
        this.users.add(user);
    }
}
