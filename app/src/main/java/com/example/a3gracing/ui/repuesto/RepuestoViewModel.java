package com.example.a3gracing.ui.repuesto;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RepuestoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RepuestoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Repuestos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}