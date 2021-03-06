package com.mariaincyberspace.lostandfound_1.presentation.ui.item;

import android.app.Application;
import android.content.ContentResolver;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mariaincyberspace.lostandfound_1.data.repository.AuthenticationRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.data.repository.ItemRepositoryImpl;
import com.mariaincyberspace.lostandfound_1.domain.model.Item;
import com.mariaincyberspace.lostandfound_1.domain.model.Location;
import com.mariaincyberspace.lostandfound_1.services.CoordinatesToPlaceService;


public class AddItemViewModel extends AndroidViewModel {
    private MutableLiveData<String> mText;
    private AuthenticationRepositoryImpl authenticationRepository;
    private ItemRepositoryImpl itemRepository;
    private StorageReference storage;


    public AddItemViewModel(@NonNull Application application) {
        super(application);
        storage = FirebaseStorage.getInstance().getReference();
        authenticationRepository = new AuthenticationRepositoryImpl();
        itemRepository = new ItemRepositoryImpl();
        mText = new MutableLiveData<>();

        mText.setValue("Please provide a picture of what you lost and it's last known location");

    }


    public LiveData<String> getText() {
        return mText;
    }

    public void uploadPicture(Uri uri, String itemName, Location location) {
        StorageReference fileRef = storage.child(System.currentTimeMillis() + "aaa." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(taskSnapshot
                -> fileRef.getDownloadUrl()
                .addOnSuccessListener(uri1
                        -> {
                                String address = CoordinatesToPlaceService.getPlace(location.getLatitude(),
                                        location.getLongitude());
                                Item item = new Item(itemName,
                                        authenticationRepository.getCurrentUserId(),
                                        location.getLatitude(), location.getLongitude(),
                                        uri1.toString(), address,
                                        System.currentTimeMillis()
                                        );
                                itemRepository.addItem(item, authenticationRepository.getCurrentUserId());

                                Toast.makeText(getApplication(),
                                "Upload successful", Toast.LENGTH_LONG).show();

                            })
                .addOnFailureListener(e
                        -> Toast.makeText(getApplication(),
                        "Something went wrong", Toast.LENGTH_LONG).show()));
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getApplication().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}