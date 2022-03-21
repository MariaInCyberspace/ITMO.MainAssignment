package com.mariaincyberspace.lostandfound_1.domain.repository;

import com.mariaincyberspace.lostandfound_1.domain.model.Chat;

import java.util.List;

public interface OnChatCallBack {
    void onCallBack(List<Chat> chats);
}
