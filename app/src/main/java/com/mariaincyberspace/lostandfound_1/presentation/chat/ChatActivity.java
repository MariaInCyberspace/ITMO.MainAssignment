package com.mariaincyberspace.lostandfound_1.presentation.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.data.repository.MessageRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.domain.model.Message;
import com.mariaincyberspace.lostandfound_1.utils.Literals;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMessages;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private String chatId;
    private String ownerId;
    private String finderId;
    private MessageRepositoryImpl messageRepository;
    private Button sendMessageButton;
    private Message message;
    private EditText messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageRepository = new MessageRepositoryImpl(getApplication());
        messageList = new ArrayList<>();
        chatId = getIntent().getStringExtra(Literals.ChatFields.CHAT_ID);
        ownerId = getIntent().getStringExtra(Literals.ChatFields.OWNER_ID);
        finderId = getIntent().getStringExtra(Literals.ChatFields.FINDER_ID);
        sendMessageButton = findViewById(R.id.button_sendMessage);
        messageText = findViewById(R.id.editText_EnterMessageToSend);
        recyclerViewMessages = findViewById(R.id.recyclerView_Chat);
        recyclerViewMessages.setHasFixedSize(true);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(this, messageList, finderId);
        recyclerViewMessages.setAdapter(messageAdapter);

        getData();
        sendMessageButton.setOnClickListener(getOnClickListenerSendMessage());
    }

    public void getData() {
        messageRepository.getMessages(chatId, messages -> {
            messageList = messages;
            messageAdapter.updateMessages(messageList);
        });
    }

    public View.OnClickListener getOnClickListenerSendMessage() {
        return v -> {
            message = new Message(chatId, messageText.getText().toString(), System.currentTimeMillis());
            messageRepository.addMessage(message);
            getData();
            Log.d("ClickLog: ", "clicked");
        };
    }
}