package com.mariaincyberspace.lostandfound_1.data.repository;

import android.app.Application;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import com.mariaincyberspace.lostandfound_1.domain.repository.ItemRepository;
import com.mariaincyberspace.lostandfound_1.utils.Literals;

import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {
    private Application application;
    private final DatabaseReference itemRef;
    private Query query;

    public ItemRepositoryImpl(Application application, DatabaseReference itemRef) {
        this.application = application;
        this.itemRef = itemRef;
    }


    @Override
    public List<Item> getItems() {
        // todo: figure out how to get all items from the database
        return null;
    }

    @Override
    public void addItem(Item item) {
        itemRef.child(item.getUser().getUid()).setValue(item).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(application,
                        Literals.TOAST_ITEM_ADDED_SUCCESS, Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(application,
                        Literals.TOAST_ITEM_NOT_ADDED, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void deleteItem(Item item) {

    }
}
