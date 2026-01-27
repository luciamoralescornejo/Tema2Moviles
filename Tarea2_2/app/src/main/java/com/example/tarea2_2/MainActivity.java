package com.example.tarea2_2;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    //Declaro las variables
    private EditText eNombreFichero, etNota;
    private Button btnGuardar, btnLeer, btnGuardarSD, btnLeerSD,btnBorrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enlazo los textos y los botones con el .xml
        eNombreFichero = findViewById(R.id.eNombreFichero);
        etNota = findViewById(R.id.etNota);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnLeer = findViewById(R.id.btnLeer);
        btnGuardarSD = findViewById(R.id.btnGuardarSD);
        btnLeerSD = findViewById(R.id.btnLeerSD);
        btnBorrar = findViewById(R.id.btnBorrar);

        // Listener memorio interna
        btnGuardar.setOnClickListener(v -> escribirInterno());
        btnLeer.setOnClickListener(v -> leerInterno());

        // Listener memorio externa
        btnGuardarSD.setOnClickListener(v -> {
            String nombre = eNombreFichero.getText().toString();
            String texto = etNota.getText().toString();
            //Compruebo que el nombre del fichero existe
            if (!nombre.isEmpty()) {
                escribirSD(nombre, texto);
            } else {
                Toast.makeText(this, "Escribe un nombre de fichero", Toast.LENGTH_SHORT).show();
            }
        });

        btnLeerSD.setOnClickListener(v -> {
            String nombre = eNombreFichero.getText().toString();
            //Compruebo que el nombre del fichero existe
            if (!nombre.isEmpty()) {
                String contenido = leerSD(nombre);
                etNota.setText(contenido);
            } else {
                Toast.makeText(this, "Escribe un nombre de fichero", Toast.LENGTH_SHORT).show();
            }
        });

        // Configuro que cuando se ha clicl en el boton Borrar el texto nota se limpie
        btnBorrar.setOnClickListener(v -> etNota.setText(""));
    }


    // MÉTODOS MEMORIA INTERNA
    protected void escribirInterno() {
        String nombreArchivo = eNombreFichero.getText().toString();
        String contenido = etNota.getText().toString();

        if(nombreArchivo.isEmpty()) {
            Toast.makeText(this, "Escribe un nombre de fichero", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            OutputStreamWriter escritor = new OutputStreamWriter(openFileOutput(nombreArchivo, Context.MODE_PRIVATE)); //Abro el archivo
            escritor.write(contenido); //escribo el contenido en el archivo
            escritor.close(); //y lo cierrro
            Toast.makeText(this, R.string.msg_save_ok, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, R.string.msg_readwrite_error, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    protected void leerInterno() {
        String nombreArchivo = eNombreFichero.getText().toString();

        if(nombreArchivo.isEmpty()) {
            Toast.makeText(this, "Escribe un nombre de fichero", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            //Abro archivo línea por línea
            InputStreamReader lector = new InputStreamReader(openFileInput(nombreArchivo));
            BufferedReader buff = new BufferedReader(lector);


            StringBuilder strBuff = new StringBuilder();
            String linea;

            //Leo todas las líneas del archivo
            while ((linea = buff.readLine()) != null) {
                strBuff.append(linea).append("\n");
            }

            lector.close();
            etNota.setText(strBuff.toString()); //Muestro el contenido en la pantalla
            Toast.makeText(this, R.string.msg_load_ok, Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, R.string.msg_file_not_found, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, R.string.msg_readwrite_error, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    // MÉTODOS MEMORIA EXTERNA
    public void escribirSD(String fichero, String texto) {
        String estado = Environment.getExternalStorageState(); //Compruebo el estado de la SD
        if (!Environment.MEDIA_MOUNTED.equals(estado)) {
            Toast.makeText(this, "Almacenamiento externo no disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = new File(this.getExternalFilesDir(null), fichero); //ruta externa de la app

        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file)); //abro fichero
            osw.write(texto); //escribo
            osw.flush(); //guardo
            osw.close(); //cierro
            Toast.makeText(this, R.string.msg_save_ok, Toast.LENGTH_SHORT).show();
        } catch (IOException | NullPointerException e) {
            Toast.makeText(this, R.string.msg_readwrite_error, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public String leerSD(String fichero) {
        String texto = "";

        String estado = Environment.getExternalStorageState();
        if (!(Environment.MEDIA_MOUNTED.equals(estado) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(estado))) {
            Toast.makeText(this, "No se puede leer almacenamiento externo", Toast.LENGTH_SHORT).show();
            return "";
        }

        File file = new File(this.getExternalFilesDir(null), fichero); //apunto el archivo de la SD

        try {
            FileInputStream fIn = new FileInputStream(file); //
            InputStreamReader archivo = new InputStreamReader(fIn);
            BufferedReader buff = new BufferedReader(archivo);
            String strTmp;
            StringBuilder strBuilder = new StringBuilder();


            while ((strTmp = buff.readLine()) != null) { //Leo todo el archivo
                strBuilder.append(strTmp).append("\n");
            }
            buff.close();
            archivo.close();
            texto = strBuilder.toString();
        } catch (FileNotFoundException f) {
            Toast.makeText(this, R.string.msg_file_not_found, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, R.string.msg_readwrite_error, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return texto;
    }
}
