package com.mariaincyberspace.lostandfound_1.domain.model;

public class Item {

    // todo: figure out how to store pictures
    private String name;
    private User user;
    private Location location;

    public Item(String name, User user, Location location) {
        this.name = name;
        this.user = user;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public Location getLocation() {
        return location;
    }
}
