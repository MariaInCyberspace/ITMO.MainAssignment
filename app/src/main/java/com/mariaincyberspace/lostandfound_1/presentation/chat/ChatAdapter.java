package com.mariaincyberspace.lostandfound_1.presentation.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.data.repository.UserRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.domain.model.Chat;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {

    Context context;
    List<Chat> chatList;
    OnChatClickListener mOnChatClickListener;
    UserRepositoryImpl userRepository;

    public ChatAdapter(Context context, List<Chat> chatList, OnChatClickListener mOnChatClickListener) {
        this.context = context;
        this.chatList = chatList;
        this.mOnChatClickListener = mOnChatClickListener;
        userRepository = new UserRepositoryImpl();
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.chat, parent, false);
        return new ChatHolder(v, mOnChatClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
        Chat chat = chatList.get(position);
        userRepository.getUserName(chat.getOwnerId(), name -> holder.chatIdentifier.setText(name));
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView chatIdentifier;
        OnChatClickListener onChatClickListener;

        public ChatHolder(@NonNull View itemView, OnChatClickListener onChatClickListener) {
            super(itemView);
            chatIdentifier = itemView.findViewById(R.id.textView_ChatIdentifier);
            this.onChatClickListener = onChatClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onChatClickListener.onChatClick(getAdapterPosition());
        }
    }

    public void updateChats(List<Chat> newList) {
        chatList.clear();
        chatList.addAll(newList);
        this.notifyDataSetChanged();
    }

    public interface OnChatClickListener {
        void onChatClick(int position);
    }
}
