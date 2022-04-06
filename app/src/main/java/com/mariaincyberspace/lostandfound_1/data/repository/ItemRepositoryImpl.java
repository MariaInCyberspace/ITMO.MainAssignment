package com.mariaincyberspace.lostandfound_1.data.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mariaincyberspace.lostandfound_1.MyApp;
import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import com.mariaincyberspace.lostandfound_1.domain.repository.ItemRepository;
import com.mariaincyberspace.lostandfound_1.domain.repository.OnItemCallBack;
import com.mariaincyberspace.lostandfound_1.utils.Literals;
import java.util.ArrayList;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {
    private Application application;
    private final DatabaseReference itemRef =
            FirebaseDatabase.getInstance().getReference().child(Literals.Nodes.ITEM_KEY);
    List<Item> items;

    public ItemRepositoryImpl() {
        this.application = MyApp.getInstance();
        items = new ArrayList<>();
    }

    private interface FirebaseCallback {
        void onCallback(List<Item> items);
    }


    @Override
    public void getAllItems(OnItemCallBack onItemCallBack) {
        Query query = itemRef.orderByChild(Literals.ItemFields.TIMESTAMP);
        Log.d("ItemRepoLog: ", "");
        readFromDatabase(onItemCallBack::onCallBack, query);
    }

    @Override
    public void getCurrentUsersItems(String userId, OnItemCallBack onItemCallBack) {
        Query query = itemRef.orderByChild(Literals.ItemFields.USER_ID)
                .equalTo(userId);
        Log.d("ItemRepoLog: ", "");
        readFromDatabase(onItemCallBack::onCallBack, query);
    }

    @Override
    public void addItem(Item item, String userId) {
        itemRef.child(item.getTimestamp().toString()).setValue(item).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(application,
                        Literals.Toasts.ITEM_ADDED_SUCCESS, Toast.LENGTH_LONG).show();
                Log.d("MyLog", "Item added");
            }
            else {
                Toast.makeText(application,
                        Literals.Toasts.ITEM_NOT_ADDED, Toast.LENGTH_LONG).show();
                Log.d("MyLog", "Item not added");
            }
        });
    }

    private void readFromDatabase(final FirebaseCallback firebaseCallback, Query query) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        Item retrievedItem = ds.getValue(Item.class);
                        items.add(retrievedItem);
                    }
                    catch (NullPointerException ex) {
                        Log.d("My Log: ", ex.getMessage());
                    }

                    Log.d("Items: ", items.toString());

                }

                firebaseCallback.onCallback(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("My Log, Database: ", error.getMessage());
            }
        };

        query.addValueEventListener(valueEventListener);
    }


    @Override
    public void deleteItem(Item item) {
        Query query = itemRef.orderByChild(Literals.ItemFields.TIMESTAMP)
                .equalTo(item.getTimestamp());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    ds.getRef().removeValue();
                    Toast.makeText(application, Literals.Toasts.ITEM_DELETED, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("ItemRepoLog: ", error.getMessage());
            }
        });
    }
}
