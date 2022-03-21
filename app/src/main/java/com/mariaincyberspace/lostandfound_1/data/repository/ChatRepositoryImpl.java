package com.mariaincyberspace.lostandfound_1.data.repository;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mariaincyberspace.lostandfound_1.domain.model.Chat;
import com.mariaincyberspace.lostandfound_1.domain.repository.ChatRepository;
import com.mariaincyberspace.lostandfound_1.domain.repository.OnChatCallBack;
import com.mariaincyberspace.lostandfound_1.utils.Literals;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatRepositoryImpl implements ChatRepository {

    private Application application;
    List<Chat> chats;

    public ChatRepositoryImpl(Application application) {
        this.application = application;
        chats = new ArrayList<>();
    }

    private interface FirebaseCallback {
        void onCallback(List<Chat> chats);
    }

    @Override
    public void addChat(Chat chat) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child(Literals.Nodes.CHAT_KEY);
        String id = chat.getOwnerId() + chat.getFinderId();
        assert id != null;
        reference.child(id).setValue(chat).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("ChatRepoLog: ", "chat added");
            } else {
                Log.d("ChatRepoLog: ", "something went wrong");

            }
        });
    }

    @Override
    public void getOwnerChats(String userId, OnChatCallBack onChatCallBack) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference((Literals.Nodes.CHAT_KEY));
        Query query = reference.orderByChild(Literals.ChatFields.OWNER_ID).equalTo(userId);
        readData(onChatCallBack::onCallBack, query);
        Log.d("AllChatsLog:: ", userId);
    }

    @Override
    public void getFinderChats(String userId, OnChatCallBack onChatCallBack) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Literals.Nodes.CHAT_KEY);
        Query query = reference.orderByChild(Literals.ChatFields.FINDER_ID).equalTo(userId);
        readData(onChatCallBack::onCallBack, query);
    }


    public void readData(final FirebaseCallback firebaseCallback, Query query) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();

                for (DataSnapshot ds: snapshot.getChildren()) {
                    Chat chat = new Chat();
                    GenericTypeIndicator<HashMap<String, Object>> objType =
                            new GenericTypeIndicator<HashMap<String, Object>>() {};
                    HashMap<String, Object> f = ds.getValue(objType);
                    assert f != null;
                    chat.setChatId((String) f.get(Literals.ChatFields.CHAT_ID));
                    chat.setOwnerId((String) f.get(Literals.ChatFields.OWNER_ID));
                    chat.setFinderId((String) f.get(Literals.ChatFields.FINDER_ID));
                    chats.add(chat);
                    Log.d("AllChatLog:: ", chat.getOwnerId() + " " + chat.getFinderId());
                }

                firebaseCallback.onCallback(chats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);
    }

}
