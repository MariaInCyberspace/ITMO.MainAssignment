package com.mariaincyberspace.lostandfound_1.presentation.ui.item;

import static android.app.Activity.RESULT_OK;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.databinding.AddItemFragmentBinding;
import com.mariaincyberspace.lostandfound_1.domain.model.Location;


public class AddItemFragment extends Fragment {

    private AddItemViewModel mViewModel;
    private AddItemFragmentBinding binding;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Uri imageURI;
    private Location mLocation;
    MapsFragment mFragment;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AddItemViewModel.class);

        binding = AddItemFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAdd;
        final Button uploadButton = binding.buttonUploadLostItem;
        final ImageView imageView = binding.imageViewInputItemPicture;

        mFragment = new MapsFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout_Map, mFragment).commit();


        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        assert intent != null;
                        imageURI = intent.getData();
                        imageView.setImageURI(imageURI);
                        // todo: upload to storage
                    }
                }
        );

        mViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        imageView.setOnClickListener(v -> {
            openActivityForResult();
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageURI != null) {
                    mLocation = mFragment.getLocation();
                    String itemName = binding.editTextInputItemName.getText().toString();
                    mViewModel.uploadPicture(imageURI, itemName, mLocation);
                } else {
                    Toast.makeText(requireActivity().getApplication(), "Please select image", Toast.LENGTH_LONG).show();
                }
            }
        });


        return root;
    }



    public void openActivityForResult() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        activityResultLauncher.launch(galleryIntent);
    }
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(AddItemViewModel.class);
//        // TODO: Use the ViewModel
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}