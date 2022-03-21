package com.mariaincyberspace.lostandfound_1.domain.repository;

import com.mariaincyberspace.lostandfound_1.domain.model.Message;


public interface MessageRepository {
    void addMessage(Message message);
    void getMessages(String chatId, OnMessageCallBack onMessageCallBack);
}
