package com.mariaincyberspace.lostandfound_1.data.repository;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mariaincyberspace.lostandfound_1.domain.model.Chat;
import com.mariaincyberspace.lostandfound_1.domain.model.Message;
import com.mariaincyberspace.lostandfound_1.domain.repository.MessageRepository;
import com.mariaincyberspace.lostandfound_1.domain.repository.OnMessageCallBack;
import com.mariaincyberspace.lostandfound_1.utils.Literals;
import java.util.ArrayList;
import java.util.List;

public class MessageRepositoryImpl implements MessageRepository {
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
            .child(Literals.Nodes.MESSAGE_KEY);
    private List<Message> messages;


    public MessageRepositoryImpl() {
        messages = new ArrayList<>();
    }

    private interface FirebaseCallback {
        void onCallback(List<Message> messages);
    }

    @Override
    public void addMessage(Message message) {
        String id = reference.push().getKey();
        assert id != null;
        reference.child(message.getChatId()).child(id).setValue(message).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Chat chat = new Chat(message.getChatId(), message.getRecipientId(),
                        message.getSenderId(), message.getTimestamp());
                ChatRepositoryImpl chatRepository = new ChatRepositoryImpl();
                chatRepository.addChat(chat);
                Log.d("MessageRepoLog: ", "message added");
            } else {
                Log.d("MessageRepoLog: ", "something went wrong");
            }
        });
    }

    @Override
    public void getMessages(String chatId, OnMessageCallBack onMessageCallBack) {
        Query query = reference.child(chatId).orderByChild(Literals.MessageFields.TIMESTAMP);
        readData(onMessageCallBack::onCallBack, query);
    }

    private void readData(final FirebaseCallback firebaseCallback, Query query) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Message message = ds.getValue(Message.class);
                    messages.add(message);
                }

                firebaseCallback.onCallback(messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        query.addValueEventListener(valueEventListener);
    }

}
