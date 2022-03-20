package com.mariaincyberspace.lostandfound_1.domain.model;

public class Item {

    public Item() { }

    // todo: figure out how to store pictures
    private String name;
    private double latitude;
    private double longitude;
    private String photoUri;

    // todo: remove setters later
    // Setters for testing


    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

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


    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", photoUri='" + photoUri + '\'' +
                '}';
    }
}
