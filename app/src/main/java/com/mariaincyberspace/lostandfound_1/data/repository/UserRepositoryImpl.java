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
import com.mariaincyberspace.lostandfound_1.MyApp;
import com.mariaincyberspace.lostandfound_1.domain.model.User;
import com.mariaincyberspace.lostandfound_1.domain.repository.OnUserCallBack;
import com.mariaincyberspace.lostandfound_1.domain.repository.UserRepository;
import com.mariaincyberspace.lostandfound_1.utils.Literals;

import java.util.HashMap;

public class UserRepositoryImpl implements UserRepository {

    private Application application;
    private final DatabaseReference reference =
            FirebaseDatabase.getInstance().getReference().child(Literals.Nodes.USER_KEY);

    public UserRepositoryImpl() {
        this.application = MyApp.getInstance();
    }

    private interface FirebaseCallBack {
        void onCallBack(String name);
    }

    @Override
    public void addUser(User user) {
        String id = reference.push().getKey();
        assert id != null;
        reference.child(id).setValue(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(application,
                        Literals.Toasts.USER_ADDED, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(application,
                        Literals.Toasts.USER_NOT_ADDED, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void deleteUser(User user) {
        Query query = reference.orderByChild(user.getUid()).equalTo(user.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    ds.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("UserRepoLog: ", error.getMessage());
            }
        });
    }


    private void readFromDatabase(FirebaseCallBack firebaseCallBack, Query query) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            String userName;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    GenericTypeIndicator<HashMap<String, Object>> objType =
                            new GenericTypeIndicator<HashMap<String, Object>>() {};
                    HashMap<String, Object> f = ds.getValue(objType);
                    assert f != null;
                    userName = (String)f.get(Literals.UserFields.NAME);
                }
                firebaseCallBack.onCallBack(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    public void getUserName(String userId, OnUserCallBack onUserCallBack) {
        Query query = reference.orderByChild(Literals.UserFields.UID).equalTo(userId);
        readFromDatabase(onUserCallBack::onCallBack, query);
    }
}
