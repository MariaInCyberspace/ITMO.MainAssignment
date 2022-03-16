package com.mariaincyberspace.lostandfound_1.presentation.ui.item;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mariaincyberspace.lostandfound_1.R;
import com.mariaincyberspace.lostandfound_1.databinding.AddItemFragmentBinding;

public class AddItemFragment extends Fragment {

    private AddItemViewModel mViewModel;
    private AddItemFragmentBinding binding;

//    public static AddItemFragment newInstance() {
//        return new AddItemFragment();
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AddItemViewModel.class);

        binding = AddItemFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAdd;
        final TextView hintTextView = binding.textViewAddPictureHint;
        final TextView locHintTextView = binding.textViewInputLocationHint;

        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mViewModel.getPicHint().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                hintTextView.setText(s);
            }
        });

        mViewModel.getLocHint().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                locHintTextView.setText(s);
            }
        });

        Fragment fragment = new MapsFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();

        return root;
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