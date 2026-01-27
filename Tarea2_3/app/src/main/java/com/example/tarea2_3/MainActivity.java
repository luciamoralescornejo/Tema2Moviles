package com.example.tarea2_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tarea2_3.database.ListaCompraDatabaseAdapter;

public class MainActivity extends AppCompatActivity {

    private ListaCompraDatabaseAdapter dbAdapter;
    private Button btAnadir;
    private Button btLimpiar;
    private Button btSalir;
    private ListView listado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listado = findViewById(R.id.lvListado);
        dbAdapter = new ListaCompraDatabaseAdapter(this);
        dbAdapter.open();
        rellenarLista();
        btAnadir = findViewById(R.id.btAnadir);
        btAnadir.setOnClickListener(v -> añadirProducto());
        btLimpiar = findViewById(R.id.btLimpiar);
        btLimpiar.setOnClickListener(v -> {
            dbAdapter.limpiarTabla();
            rellenarLista();
        });
        btSalir = findViewById(R.id.btSalir);
        btSalir.setOnClickListener(v -> {
            dbAdapter.close();
            finishAffinity();
        });
    }

    private void rellenarLista() {
        @SuppressWarnings("deprecation")
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.producto_row,
                dbAdapter.obtenerTodosElementos(),
                new String[] { ListaCompraDatabaseAdapter.CLAVE_PRODUCTO, ListaCompraDatabaseAdapter.CLAVE_CANTIDAD },
                new int[] { R.id.tvProducto, R.id.tvCantidad },
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listado.setAdapter(adapter);
    }

    protected void añadirProducto(){
        Intent i = new Intent(this, NuevoProductoActivity.class );
        lanzadorActividades.launch(i);
    }

    ActivityResultLauncher<Intent> lanzadorActividades = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    rellenarLista();
                }
            });
}