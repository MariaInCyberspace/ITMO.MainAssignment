package com.mariaincyberspace.lostandfound_1.domain.repository;

import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import java.util.List;

public interface ItemRepository {
    public List<Item> getItems();
    public void addItem(Item item);
    public void deleteItem(Item item);
}
