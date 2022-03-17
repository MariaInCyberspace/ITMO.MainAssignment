package com.mariaincyberspace.lostandfound_1.domain.model;

public class Item {

    // todo: figure out how to store pictures
    private String name;
    private double latitude;
    private double longitude;
    private String photoUri;


    public Item(String name, double latitude, double longitude, String photoUri) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoUri = photoUri;
    }

    public String getName() {
        return name;
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPhotoUri() { return photoUri; }

}
