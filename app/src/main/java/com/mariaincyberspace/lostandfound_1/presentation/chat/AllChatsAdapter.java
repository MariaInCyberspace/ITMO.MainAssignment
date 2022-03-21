package com.mariaincyberspace.lostandfound_1.presentation.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.domain.model.Chat;

import java.util.List;

public class AllChatsAdapter extends RecyclerView.Adapter<AllChatsAdapter.ChatHolder> {

    Context context;
    List<Chat> chatList;
    OnChatClickListener mOnChatClickListener;

    public AllChatsAdapter(Context context, List<Chat> chatList, OnChatClickListener mOnChatClickListener) {
        this.context = context;
        this.chatList = chatList;
        this.mOnChatClickListener = mOnChatClickListener;
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
        // todo: bind to elements
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnChatClickListener onChatClickListener;

        // todo: declare view elements
        // todo: bind view elements


        public ChatHolder(@NonNull View itemView, OnChatClickListener onChatClickListener) {
            super(itemView);
            this.onChatClickListener = onChatClickListener;
        }

        @Override
        public void onClick(View v) {
            onChatClickListener.onChatClick(getBindingAdapterPosition());
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
