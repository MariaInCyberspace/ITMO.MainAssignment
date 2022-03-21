package com.mariaincyberspace.lostandfound_1.domain.repository;

import com.mariaincyberspace.lostandfound_1.domain.model.Chat;


public interface ChatRepository {
    void addChat(Chat chat);
    void getChats(String userId, OnChatCallBack onChatCallBack);
}
