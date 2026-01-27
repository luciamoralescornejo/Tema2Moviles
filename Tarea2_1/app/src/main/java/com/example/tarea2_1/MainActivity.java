package com.example.tarea2_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    private TextView txtEleccionBBDD;
    private TextView txtEleccionDificultad;

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

        txtEleccionBBDD = findViewById(R.id.txtEleccionBBDD);
        txtEleccionDificultad = findViewById(R.id.txtEleccionDificultad);

        activarPreferencs();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activarPreferencs();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.setGroupVisible(R.id.op_preferencias, true);
        menu.setGroupVisible(R.id.op_salir, true);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.op_preferencias) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.op_salir) {
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }

    public void activarPreferencs(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String dificultad = sharedPreferences.getString("list_preference_1", "Medio");
        boolean tipoBD = sharedPreferences.getBoolean("bd", false);
        String nombreBD = sharedPreferences.getString("nombre", "MiBD");
        String ipBD = sharedPreferences.getString("ip", "127.0.0.1");
        txtEleccionDificultad.setText(dificultad);
        txtEleccionBBDD.setText(tipoBD?"Externa: "+nombreBD+" "+ipBD:"Interna SQLite");
    }
}