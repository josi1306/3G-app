package com.example.a3gracing.ui.registro;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class registroViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public registroViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("registro");
    }

    public LiveData<String> getText() {
        return mText;
    }
}