package com.mariaincyberspace.lostandfound_1.domain.repository;

import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import java.util.List;

public interface ItemRepository {
    public List<Item> getCurrentUsersItems(String userId);
    public List<Item> getAllItems();
    public void addItem(Item item, String userId);
    public void deleteItem(Item item);
}
