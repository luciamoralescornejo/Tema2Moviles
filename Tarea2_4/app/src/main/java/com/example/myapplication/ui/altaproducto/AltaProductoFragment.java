package com.example.myapplication.ui.altaproducto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.google.android.material.snackbar.Snackbar;


import com.example.myapplication.databinding.FragmentAltaProductoBinding;

public class AltaProductoFragment extends Fragment {

    private EditText etProducto, etCantidad;
    private Button btAceptar;
    private FragmentAltaProductoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAltaProductoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        etProducto = binding.etProducto;
        etCantidad = binding.etCantidad;


        btAceptar = binding.btAceptar;
        btAceptar.setOnClickListener(v -> {
            etProducto.setText("");
            etCantidad.setText("");
            Snackbar.make(etCantidad, "Se ha insertado un nuevo registro.", Snackbar.LENGTH_LONG).show();
        });

        return root;
    }

}