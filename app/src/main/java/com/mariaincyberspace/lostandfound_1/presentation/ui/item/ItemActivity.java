package com.mariaincyberspace.lostandfound_1.presentation.ui.item;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.data.repository.AuthenticationRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.data.repository.ItemRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import com.mariaincyberspace.lostandfound_1.presentation.MainActivity;
import com.mariaincyberspace.lostandfound_1.utils.Literals;


public class ItemActivity extends AppCompatActivity {
    private Item item;
    private ImageView imageView;
    private TextView itemName;
    private TextView itemDescription;
    private ImageView deleteItemButton;
    private AuthenticationRepositoryImpl authenticationRepository;
    private ItemRepositoryImpl itemRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        authenticationRepository = new AuthenticationRepositoryImpl(getApplication());
        itemRepository = new ItemRepositoryImpl(getApplication(),
                FirebaseDatabase.getInstance().getReference().child(Literals.Nodes.ITEM_KEY));
        this.item = getIntent().getParcelableExtra(Literals.BundleName.SELECTED_ITEM);
        imageView = findViewById(R.id.imageView_ItemPictureIndividual);
        itemName = findViewById(R.id.textView_ItemNameIndividual);
        itemDescription = findViewById(R.id.textView_ItemDescriptionIndividual);
        deleteItemButton = findViewById(R.id.imageViewButton_DeleteItemIndividual);
        checkIfItemBelongsToCurrentUser();
        Glide.with(getApplication()).load(item.getPhotoUri()).into(imageView);

        setWithValues();

    }

    public void setWithValues() {
        itemName.setText(item.getName());
        CharSequence sequence = "Lost in " + item.getAddress();
        itemDescription.setText(sequence);
        Log.d("TAG: ", item.toString());
    }


    private void checkIfItemBelongsToCurrentUser() {
        if (!item.getUserId().equals(authenticationRepository.getCurrentUserId())) {
            deleteItemButton.setVisibility(View.INVISIBLE);
        } else {
            deleteItemButton.setOnClickListener(v -> {
                itemRepository.deleteItem(item);
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            });
        }
    }

}