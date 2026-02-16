package com.example.myapplication.ui.listacompra;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentListaCompraBinding;
import com.example.myapplication.ui.adaptadores.AdaptadorProducto;
import com.example.myapplication.ui.entidades.Producto;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListaCompraFragment extends Fragment {
    private FragmentListaCompraBinding binding;
    private ListaCompraViewModel listaCompraViewModel;
    private AdaptadorProducto adaptador;
    private List<Producto> datos = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listaCompraViewModel = new ViewModelProvider(this).get(ListaCompraViewModel.class);
        binding = FragmentListaCompraBinding.inflate(inflater, container, false);

        setupRecyclerView();

        listaCompraViewModel.getText().observe(getViewLifecycleOwner(), text -> {
            binding.titulo.setText(text);
        });

        // Esto permite que el RecyclerView escuche las pulsaciones largas
        registerForContextMenu(binding.recyclerView);

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        if (datos.isEmpty()) {
            datos.add(new Producto("Leche", 2));
            datos.add(new Producto("Carne", 1));
            datos.add(new Producto("Lechugas", 2));
        }

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptador = new AdaptadorProducto(getContext(), datos);
        binding.recyclerView.setAdapter(adaptador);
    }

    // ESTE MÉTODO ES IMPRESCINDIBLE PARA QUE LOS IDS FUNCIONEN
    @SuppressLint("ResourceType")
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // "nombre_del_menu" debe ser el nombre de tu archivo .xml en res/menu
        getLayoutInflater().inflate(R.menu.mimenu, (ViewGroup) menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int posicion;
        try {
            posicion = adaptador.getPosicion();
        } catch (Exception e) {
            return super.onContextItemSelected(item);
        }

        // Usamos if-else si el switch te da problemas con IDs no constantes
        int id = item.getItemId();

        if (id == R.id.mi1) {
            Snackbar.make(binding.recyclerView, "Editar: " + datos.get(posicion).getProducto(), Snackbar.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.mi2) {
            datos.remove(posicion);
            adaptador.notifyItemRemoved(posicion);
            Snackbar.make(binding.recyclerView, "Elemento borrado", Snackbar.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.mi3) {
            datos.add(new Producto("Producto Nuevo", 1));
            adaptador.notifyItemInserted(datos.size() - 1);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}