package com.mariaincyberspace.lostandfound_1.presentation.chat;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.data.repository.AuthenticationRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.data.repository.ChatRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.domain.model.Chat;
import com.mariaincyberspace.lostandfound_1.utils.Literals;
import java.util.ArrayList;
import java.util.List;

public class AllChatsFragment extends Fragment implements ChatAdapter.OnChatClickListener {

    private RecyclerView recyclerView;
    private AuthenticationRepositoryImpl authenticationRepository;
    private ChatRepositoryImpl chatRepository;
    private ChatAdapter chatAdapter;
    private List<Chat> chatList;
    private List<Chat> allChats;


    public AllChatsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authenticationRepository = new AuthenticationRepositoryImpl(requireActivity().getApplication());
        chatRepository = new ChatRepositoryImpl(requireActivity().getApplication());
        chatList = new ArrayList<>();
        allChats = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_chats, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_AllChatsFragment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        chatAdapter = new ChatAdapter(getContext(), chatList, this);
        recyclerView.setAdapter(chatAdapter);
        getData();
        // Inflate the layout for this fragment
        return view;
    }

    public void getData() {
        chatRepository.getOwnerChats(authenticationRepository.getCurrentUserId(), chats -> {
            chatList = chats;
            if (!chatList.isEmpty()) {
                allChats.addAll(chatList);
                chatAdapter.updateChats(allChats);
                chatList.clear();
            }

            Log.d("AllChatsLog:: ", chatList.toString());
        });
        chatRepository.getFinderChats(authenticationRepository.getCurrentUserId(), chats -> {
            chatList = chats;
            if (!chatList.isEmpty()) {
                allChats.addAll(chatList);
                chatAdapter.updateChats(allChats);
                chatList.clear();
            }
            Log.d("AllChatsLog:::", chatList.toString());
        });
    }

    @Override
    public void onChatClick(int position) {
        Chat chat = allChats.get(position);
        Intent intent = new Intent(requireActivity(), ChatActivity.class);
        intent.putExtra(Literals.ChatFields.CHAT_ID, chat.getChatId());
        intent.putExtra(Literals.ChatFields.OWNER_ID, chat.getOwnerId());
        intent.putExtra(Literals.ChatFields.FINDER_ID, chat.getFinderId());
        startActivity(intent);
        Log.d("onChatClickLog:: ", "clicked");
    }
}