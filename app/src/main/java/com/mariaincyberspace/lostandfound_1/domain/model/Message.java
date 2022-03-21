package com.mariaincyberspace.lostandfound_1.domain.model;

public class Message {
    private String chatId;
    private String messageText;
    private Long timestamp;
    private String senderId;
    private String recipientId;

    public Message() { }

    public Message(String chatId, String messageText, Long timestamp, String senderId, String recipientId) {
        this.chatId = chatId;
        this.messageText = messageText;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getChatId() {
        return chatId;
    }

    public String getMessageText() {
        return messageText;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "chatId='" + chatId + '\'' +
                ", messageText='" + messageText + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
