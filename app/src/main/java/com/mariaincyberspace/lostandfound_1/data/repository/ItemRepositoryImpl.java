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
import com.mariaincyberspace.lostandfound_1.utils.Literals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemRepositoryImpl implements ItemRepository {
    private Application application;
    private final DatabaseReference itemRef;
    private Query query;
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
    public List<Item> getCurrentUsersItems(String userId) {

        // todo: figure out how to get all items from the database

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child(Literals.Nodes.ITEM_KEY).child(userId);


        readFromDatabase(items -> {
            Log.d("My log: returned: ", "");
            for (Item i: items) {
                Log.d("Item: ", "'" + i.getName() + "', " + i.getPhotoUri());
            }

        }, reference);

        return items;
    }

    @Override
    public List<Item> getAllItems() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child(Literals.Nodes.ITEM_KEY);
        readAllFromDatabase(items -> {

        }, reference);
        return items;
    }

    @Override
    public void addItem(Item item, String userId) {
        itemRef.child(userId).child(item.getName()).setValue(item).addOnCompleteListener(task -> {
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

    public void readAllFromDatabase(final FirebaseCallback firebaseCallback, DatabaseReference reference) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                items.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    for (DataSnapshot d: ds.getChildren()) {
                        try {
                            Item retrievedItem = new Item();
                            GenericTypeIndicator<HashMap<String, Object>> objType =
                                    new GenericTypeIndicator<HashMap<String, Object>>() {};
                            HashMap<String, Object> f = d.getValue(objType);
                            retrievedItem.setName((String)f.get(Literals.ItemFields.NAME));
                            retrievedItem.setLatitude((Double) f.get(Literals.ItemFields.LATITUDE));
                            retrievedItem.setLongitude((Double) f.get(Literals.ItemFields.LONGITUDE));
                            retrievedItem.setPhotoUri((String) f.get(Literals.ItemFields.PHOTO_URI));
                            Log.d("My Log: ", retrievedItem.toString());
                            items.add(retrievedItem);
                            Log.d("List Log: ", items.toString());
                        }
                        catch (NullPointerException ex) {
                            Log.d("My Log: ", ex.getMessage());
                        }
                    }

                }

                firebaseCallback.onCallback(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("My Log, Database: ", error.getMessage());
            }
        };

        reference.addValueEventListener(valueEventListener);
    }

    public void readFromDatabase(final FirebaseCallback firebaseCallback, DatabaseReference reference) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                items.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    try {
                        Item retrievedItem = new Item();
                        GenericTypeIndicator<HashMap<String, Object>> objType =
                                new GenericTypeIndicator<HashMap<String, Object>>() {};
                        HashMap<String, Object> f = ds.getValue(objType);
                        retrievedItem.setName((String)f.get(Literals.ItemFields.NAME));
                        retrievedItem.setLatitude((Double) f.get(Literals.ItemFields.LATITUDE));
                        retrievedItem.setLongitude((Double) f.get(Literals.ItemFields.LONGITUDE));
                        retrievedItem.setPhotoUri((String) f.get(Literals.ItemFields.PHOTO_URI));
                        Log.d("My Log: ", retrievedItem.toString());
                        items.add(retrievedItem);
                        Log.d("List Log: ", items.toString());
                    }
                    catch (NullPointerException ex) {
                        Log.d("My Log: ", ex.getMessage());
                    }
                }

                firebaseCallback.onCallback(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("My Log, Database: ", error.getMessage());
            }
        };

        reference.addValueEventListener(valueEventListener);
    }

    @Override
    public void deleteItem(Item item) {

    }
}
