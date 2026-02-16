package com.example.myapplication.ui.listacompra;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import com.example.myapplication.ui.entidades.Producto;

public class ListaCompraViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ListaCompraViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Mi Lista de la Compra");
    }

    public LiveData<String> getText() {
        return mText;
    }
}