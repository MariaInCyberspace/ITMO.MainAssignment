package com.mariaincyberspace.lostandfound_1.domain.model;

public class User {

    private String uid;
    private String name;

    public String getName() {
        return name;
    }

    public User(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public String getUid() {
        return uid;
    }
}
