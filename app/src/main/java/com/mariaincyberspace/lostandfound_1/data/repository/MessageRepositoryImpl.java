package com.mariaincyberspace.lostandfound_1.data.repository;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mariaincyberspace.lostandfound_1.domain.model.Message;
import com.mariaincyberspace.lostandfound_1.domain.repository.MessageRepository;
import com.mariaincyberspace.lostandfound_1.domain.repository.OnMessageCallBack;
import com.mariaincyberspace.lostandfound_1.utils.Literals;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageRepositoryImpl implements MessageRepository {
    private Application application;
    private List<Message> messages;


    public MessageRepositoryImpl(Application application) {
        this.application = application;
        messages = new ArrayList<>();
    }

    private interface FirebaseCallback {
        void onCallback(List<Message> messages);
    }

    @Override
    public void addMessage(Message message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child(Literals.Nodes.MESSAGE_KEY);
        String id = reference.push().getKey();
        assert id != null;
        reference.child(id).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("MessageRepoLog: ", "message added");
                } else {
                    Log.d("MessageRepoLog: ", "something went wrong");
                }
            }
        });
    }

    @Override
    public void getMessages(String chatId, OnMessageCallBack onMessageCallBack) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Literals.Nodes.MESSAGE_KEY);
        Query query = reference.orderByChild(Literals.MessageFields.CHAT_ID).equalTo(chatId);
        readData(onMessageCallBack::onCallBack, query);
    }

    public void readData(final FirebaseCallback firebaseCallback, Query query) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    Message message = new Message();
                    GenericTypeIndicator<HashMap<String, Object>> objType =
                            new GenericTypeIndicator<HashMap<String, Object>>() {};
                    HashMap<String, Object> f = ds.getValue(objType);
                    assert f != null;
                    message.setChatId((String) f.get(Literals.MessageFields.CHAT_ID));
                    message.setMessageText((String) f.get(Literals.MessageFields.MESSAGE_TEXT));
                    message.setTimestamp((Long) f.get(Literals.MessageFields.TIMESTAMP));
                    message.setSenderId((String) f.get(Literals.MessageFields.SENDER_ID));
                    message.setRecipientId((String) f.get(Literals.MessageFields.RECIPIENT_ID));
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
