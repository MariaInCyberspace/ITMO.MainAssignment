package com.mariaincyberspace.lostandfound_1.presentation.ui.item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddItemViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;
    private MutableLiveData<String> picHint;
    private MutableLiveData<String> locHint;

    public AddItemViewModel() {
        mText = new MutableLiveData<>();
        picHint = new MutableLiveData<>();
        locHint = new MutableLiveData<>();

        mText.setValue("Add your lost item here");
        picHint.setValue("Add a picture here");
        locHint.setValue("Add location where you lost the item");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getPicHint() {
        return picHint;
    }
    public LiveData<String> getLocHint() { return locHint; }
}