package com.mariaincyberspace.lostandfound_1.presentation.ui.item;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.data.repository.AuthenticationRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.data.repository.ChatRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.data.repository.ItemRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.domain.model.Chat;
import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import com.mariaincyberspace.lostandfound_1.presentation.MainActivity;
import com.mariaincyberspace.lostandfound_1.presentation.chat.ChatActivity;
import com.mariaincyberspace.lostandfound_1.utils.Literals;


public class ItemActivity extends AppCompatActivity {
    private Item item;
    private ImageView imageView;
    private TextView itemName;
    private TextView itemDescription;
    private ImageView deleteItemButton;
    private Button notifyOwnerButton;
    private ImageView goBackButton;
    private AuthenticationRepositoryImpl authenticationRepository;
    private ItemRepositoryImpl itemRepository;
    private ChatRepositoryImpl chatRepository;

    private void setFields() {
        authenticationRepository = new AuthenticationRepositoryImpl();
        itemRepository = new ItemRepositoryImpl();
        chatRepository = new ChatRepositoryImpl();
        this.item = getIntent().getParcelableExtra(Literals.BundleName.SELECTED_ITEM);
        imageView = findViewById(R.id.imageView_ItemPictureIndividual);
        itemName = findViewById(R.id.textView_ItemNameIndividual);
        itemDescription = findViewById(R.id.textView_ItemDescriptionIndividual);
        deleteItemButton = findViewById(R.id.imageViewButton_DeleteItemIndividual);
        notifyOwnerButton = findViewById(R.id.button_MarkFoundItemIndividual);
        goBackButton = findViewById(R.id.imageView_GoBackButton);
        goBackButton.setOnClickListener(getOnClickListenerGoBackButton());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        setFields();
        checkIfItemBelongsToCurrentUser();
        Glide.with(getApplication()).load(item.getPhotoUri()).into(imageView);

        setItemNameAndDescription();

    }

    private void setItemNameAndDescription() {
        itemName.setText(item.getName());
        CharSequence sequence = "Lost in " + item.getAddress();
        itemDescription.setText(sequence);
        Log.d("TAG: ", item.toString());
    }


    private void checkIfItemBelongsToCurrentUser() {
        if (!item.getUserId().equals(authenticationRepository.getCurrentUserId())) {
            deleteItemButton.setVisibility(View.INVISIBLE);
            notifyOwnerButton.setOnClickListener(getOnClickListenerNotifyOwner());
        } else {
            notifyOwnerButton.setVisibility(View.INVISIBLE);
            deleteItemButton.setOnClickListener(v -> {
                itemRepository.deleteItem(item);
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            });
        }
    }

    private View.OnClickListener getOnClickListenerNotifyOwner () {
        return v -> {
            Intent intent = new Intent(getApplication(), ChatActivity.class);
            String ownerId = item.getUserId();
            String finderId = authenticationRepository.getCurrentUserId();
            String chatId = ownerId + finderId;
            Chat chat = new Chat(chatId, ownerId, finderId, System.currentTimeMillis());
            chatRepository.addChat(chat);
            intent.putExtra(Literals.ChatFields.CHAT_ID, chat.getChatId());
            intent.putExtra(Literals.ChatFields.OWNER_ID, chat.getOwnerId());
            intent.putExtra(Literals.ChatFields.FINDER_ID, chat.getFinderId());
            startActivity(intent);
        };
    }

    private View.OnClickListener getOnClickListenerGoBackButton() {
        return v -> {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
        };
    }

}