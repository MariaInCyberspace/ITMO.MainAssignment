package com.mariaincyberspace.lostandfound_1.domain.model;

public class Chat {
    private String chatId;
    private String ownerId;
    private String finderId;

    public Chat() { }

    public Chat(String chatId, String ownerId, String finderId) {
        this.chatId = chatId;
        this.ownerId = ownerId;
        this.finderId = finderId;
    }

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
}
