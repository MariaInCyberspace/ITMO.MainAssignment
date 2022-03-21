package com.mariaincyberspace.lostandfound_1.domain.repository;

import com.mariaincyberspace.lostandfound_1.domain.model.Item;

public interface ItemRepository {
    void getAllItems(OnItemCallBack onItemCallBack);
    void getCurrentUsersItems(String userId, OnItemCallBack onItemCallBack);
    void addItem(Item item, String userId);
    void deleteItem(Item item);
}
