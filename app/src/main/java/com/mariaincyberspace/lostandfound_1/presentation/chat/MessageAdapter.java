package com.mariaincyberspace.lostandfound_1.presentation.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mariaincyberspace.lostandfound_1.MyApp;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.data.repository.UserRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.domain.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    Context context;
    List<Message> messagesList;
    UserRepositoryImpl userRepository;
    static String mName;

    public MessageAdapter(Context context, List<Message> messagesList, String userId) {
        this.context = context;
        this.messagesList = messagesList;
        userRepository = new UserRepositoryImpl(MyApp.getInstance());
        userRepository.getUserName(userId, name -> mName = name);
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.message, parent, false);
        return new MessageHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        Message message = messagesList.get(position);
        holder.userName.setText(mName);
        holder.messageText.setText(message.getMessageText());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }


    public static class MessageHolder extends RecyclerView.ViewHolder {

        TextView userName, messageText;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.textView_UserNameMessage);
            messageText = itemView.findViewById(R.id.textView_UserMessage);
        }
    }

    public void updateMessages(List<Message> newMessageList) {
        messagesList.clear();
        messagesList.addAll(newMessageList);
        this.notifyDataSetChanged();
    }
}
