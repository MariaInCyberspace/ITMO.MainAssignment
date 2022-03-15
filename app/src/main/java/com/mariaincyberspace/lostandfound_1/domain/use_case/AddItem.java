package com.mariaincyberspace.lostandfound_1.domain.use_case;

import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import com.mariaincyberspace.lostandfound_1.domain.repository.ItemRepository;

public class AddItem {
    private ItemRepository repository;
    public AddItem(ItemRepository repository) {
        this.repository = repository;
    }

    public void addItem(Item item) {
        repository.addItem(item);
    }
}
