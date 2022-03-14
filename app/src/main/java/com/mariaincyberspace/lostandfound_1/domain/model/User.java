package com.mariaincyberspace.lostandfound_1.domain.model;

public class User {
    public User() { }

    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
