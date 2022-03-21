package com.mariaincyberspace.lostandfound_1.domain.repository;

import com.mariaincyberspace.lostandfound_1.domain.model.Chat;


public interface ChatRepository {
    void addChat(Chat chat);
    void getOwnerChats(String userId, OnChatCallBack onChatCallBack);
    void getFinderChats(String userId, OnChatCallBack onChatCallBack);
}
