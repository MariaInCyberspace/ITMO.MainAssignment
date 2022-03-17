package com.mariaincyberspace.lostandfound_1.domain.model;

public class Item {

    // todo: figure out how to store pictures
    private String name;
    private User user;
    private String userId;
    private double latitude;
    private double longitude;
    private String photoUri;


    public Item(String name, String userId, double latitude, double longitude, String photoUri) {
        this.name = name;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoUri = photoUri;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPhotoUri() { return photoUri; }

    public String getUserId() { return userId; }
}
