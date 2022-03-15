package com.mariaincyberspace.lostandfound_1.domain.model;

public class User {

    private String uid;
    private String email;
    private String password;

    public User(String uid, String email, String password) {
        this.uid = uid;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUid() {
        return uid;
    }
}
