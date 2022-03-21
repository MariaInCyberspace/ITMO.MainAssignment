package com.mariaincyberspace.lostandfound_1.domain.repository;

import com.mariaincyberspace.lostandfound_1.domain.model.Item;

public interface ItemRepository {
    void getAllItems(OnCallBack onCallBack);
    void getCurrentUsersItems(String userId, OnCallBack onCallBack);
    void addItem(Item item, String userId);
    void deleteItem(Item item);
}
