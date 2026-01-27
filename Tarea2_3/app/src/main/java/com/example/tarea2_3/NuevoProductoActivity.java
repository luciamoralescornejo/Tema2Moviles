package com.example.tarea2_3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tarea2_3.database.ListaCompraDatabaseAdapter;

public class NuevoProductoActivity extends AppCompatActivity {

    private ListaCompraDatabaseAdapter dbAdapter;
    private EditText etNombre;
    private EditText etCantidad;
    private Button btAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.nuevo_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nuevo_producto_layout), ((v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }));

        dbAdapter = new ListaCompraDatabaseAdapter(this);
        dbAdapter.open();

        etNombre = findViewById(R.id.etProducto);
        etCantidad = findViewById(R.id.etCantidad);
        btAceptar = findViewById(R.id.btAceptar);
        btAceptar.setOnClickListener(v -> {
            if (salvarProducto()) {
                setResult(Activity.RESULT_OK);
            }else{
                setResult(Activity.RESULT_CANCELED);
            }
            finish();
        });
    }

    protected boolean salvarProducto() {
        boolean exito = false;
        try {
            String producto = etNombre.getText().toString();
            int cantidad = Integer.valueOf(etCantidad.getText().toString());
            dbAdapter.crearElemento(producto, cantidad);
            exito = true;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Es obligatorio introducir la cantidad", Toast.LENGTH_LONG);
        }
        return exito;
    }
}