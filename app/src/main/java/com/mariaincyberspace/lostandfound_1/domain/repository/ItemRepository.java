package com.mariaincyberspace.lostandfound_1.domain.repository;

import com.mariaincyberspace.lostandfound_1.domain.model.Item;

public interface ItemRepository {
    public void getCurrentUsersItems(String userId, OnCallBack onCallBack);
    public void getAllItems(OnCallBack onCallBack);
    public void addItem(Item item, String userId);
    public void deleteItem(Item item);
}
