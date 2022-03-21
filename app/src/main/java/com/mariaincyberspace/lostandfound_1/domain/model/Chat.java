package com.mariaincyberspace.lostandfound_1.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Chat implements Parcelable {
    private String chatId;
    private String ownerId;
    private String finderId;

    public Chat() { }

    public Chat(String chatId, String ownerId, String finderId) {
        this.chatId = chatId;
        this.ownerId = ownerId;
        this.finderId = finderId;
    }

    protected Chat(Parcel in) {
        chatId = in.readString();
        ownerId = in.readString();
        finderId = in.readString();
    }

    public static final Creator<Chat> CREATOR = new Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };

    public String getChatId() {
        return chatId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getFinderId() {
        return finderId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setFinderId(String finderId) {
        this.finderId = finderId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chatId);
        dest.writeString(ownerId);
        dest.writeString(finderId);
    }
}
