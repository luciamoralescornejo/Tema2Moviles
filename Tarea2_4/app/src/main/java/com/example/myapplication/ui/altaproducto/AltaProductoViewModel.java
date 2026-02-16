package com.example.myapplication.ui.altaproducto;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AltaProductoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AltaProductoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}