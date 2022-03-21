package com.mariaincyberspace.lostandfound_1.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

public class Item implements Parcelable {

    public Item() { }

    // todo: figure out how to store pictures
    private String name;
    private String userId;



    private double latitude;
    private double longitude;
    private String photoUri;
    private String address;
    private Long timestamp;

    protected Item(Parcel in) {
        name = in.readString();
        userId = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        photoUri = in.readString();
        address = in.readString();
        if (in.readByte() == 0) {
            timestamp = null;
        } else {
            timestamp = in.readLong();
        }
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
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

    public Item(String name, String userId, double latitude, double longitude, String photoUri, String address, Long timestamp) {
        this.name = name;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoUri = photoUri;
        this.address = address;
        this.timestamp = timestamp;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", photoUri='" + photoUri + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(userId);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(photoUri);
        dest.writeString(address);
        if (timestamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(timestamp);
        }
    }
}
