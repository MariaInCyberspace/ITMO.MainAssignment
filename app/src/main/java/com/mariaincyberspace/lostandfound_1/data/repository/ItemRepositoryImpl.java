package com.mariaincyberspace.lostandfound_1.data.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import com.mariaincyberspace.lostandfound_1.domain.repository.ItemRepository;
import com.mariaincyberspace.lostandfound_1.domain.repository.OnCallBack;
import com.mariaincyberspace.lostandfound_1.utils.Literals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {
    private Application application;
    private final DatabaseReference itemRef;
    List<Item> items;

    public ItemRepositoryImpl(Application application, DatabaseReference itemRef) {
        this.application = application;
        this.itemRef = itemRef;
        items = new ArrayList<>();
    }

    private interface FirebaseCallback {
        void onCallback(List<Item> items);
    }


    @Override
    public void getAllItems(OnCallBack onCallBack) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child(Literals.Nodes.ITEM_KEY);
        Query query = reference.orderByChild(Literals.ItemFields.TIMESTAMP);
        Log.d("ItemRepoLog: ", "");
        readFromDatabase(onCallBack::onCallBack, query);
    }

    @Override
    public void getCurrentUsersItems(String userId, OnCallBack onCallBack) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child(Literals.Nodes.ITEM_KEY);
        Query query = reference.orderByChild(Literals.ItemFields.USER_ID)
                .equalTo(userId);
        Log.d("ItemRepoLog: ", "");
        readFromDatabase(onCallBack::onCallBack, query);
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

    public void readFromDatabase(final FirebaseCallback firebaseCallback, Query query) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        Item retrievedItem = new Item();
                        GenericTypeIndicator<HashMap<String, Object>> objType =
                                new GenericTypeIndicator<HashMap<String, Object>>() {};
                        HashMap<String, Object> f = ds.getValue(objType);
                        retrievedItem.setName((String)f.get(Literals.ItemFields.NAME));
                        retrievedItem.setUserId((String) f.get(Literals.ItemFields.USER_ID));
                        retrievedItem.setLatitude((Double) f.get(Literals.ItemFields.LATITUDE));
                        retrievedItem.setLongitude((Double) f.get(Literals.ItemFields.LONGITUDE));
                        retrievedItem.setPhotoUri((String) f.get(Literals.ItemFields.PHOTO_URI));
                        retrievedItem.setAddress((String) f.get(Literals.ItemFields.ADDRESS));
                        retrievedItem.setTimestamp((Long) f.get(Literals.ItemFields.TIMESTAMP));
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
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child(Literals.Nodes.ITEM_KEY);
        Query query = reference.orderByChild(Literals.ItemFields.TIMESTAMP)
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
