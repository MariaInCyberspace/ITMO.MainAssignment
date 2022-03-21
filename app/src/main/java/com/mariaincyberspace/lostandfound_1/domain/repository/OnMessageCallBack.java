package com.mariaincyberspace.lostandfound_1.domain.repository;

import com.mariaincyberspace.lostandfound_1.domain.model.Message;

import java.util.List;

public interface OnMessageCallBack {
    void onCallBack(List<Message> messages);
}
