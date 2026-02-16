package com.example.myapplication.ui.adaptadores;


import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.entidades.Producto;

import java.util.List;


public class AdaptadorProducto extends RecyclerView.Adapter {
    private List<Producto> datos;
    private Context contexto;
    private int posicion;

    public AdaptadorProducto(Context contexto, List<Producto> datos) {
        this.datos = datos;
        this.contexto = contexto;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Inflamos el layout del contenido de cada fila de la lista que representamos.
        View layout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.producto_row, parent, false);
        //Creamos el objeto ViewHolder que servirá para mostrar los datos en la fila, y le pasamos
        //el layout que hemos inflado en la anterior línea de código.
        ProductoViewHolder producto = new ProductoViewHolder(layout);

        return producto;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        //Asignamos el dato del array correspondiente a la posición actual al
        //objeto ViewHolder, de forma que se represente en el RecyclerView.
        ((ProductoViewHolder) holder).bindProducto(datos.get(position));
        //Si detectamos un click largo, hacemos que el atributo "posicion" del Adaptador
        //sea igual a la posición del elemento del RecyclerView donde se haga el click largo.
        //Así conseguimos guardar el elemento sobre el que tenemos que actuar.
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosicion(holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        //Devolvemos el tamaño de array de datos de Capitales
        return datos.size();
    }

    public class ProductoViewHolder
            extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        private TextView tvProducto;
        private TextView tvCantidad;

        public ProductoViewHolder(View itemView) {
            super(itemView);
            //Asignamos al Layout de cada fila que obtenga el evento onClick
            itemView.setOnClickListener(this);
            tvProducto = itemView.findViewById(R.id.tvProducto);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            itemView.setOnCreateContextMenuListener(this);
        }

        //Método que nos permitirá dar valores a cada campo del objeto ViewHolder y que
        //el mismo pueda ser mostrado en el RecyclerView
        public void bindProducto(Producto p) {
            tvProducto.setText(p.getProducto());
            tvCantidad.setText(String.valueOf(p.getCantidad()));
        }

        @Override
        public void onClick(View v) {
            //Extraemos los valores de los campos de la fila en la que hemos hecho click
            String resultado = "El producto seleccionado es: " + ((TextView) v
                    .findViewById(R.id.tvProducto)).getText() + " y hay que comprar " + ((TextView) v
                    .findViewById(R.id.tvCantidad)).getText() + " unidades.";
            //Mostramos la información en un diálogo.
            AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

            builder.setTitle("Información");
            builder.setMessage(resultado);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }

        //Método que crea el menú contextual al hacer un click largo.
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater = new MenuInflater(contexto);
            inflater.inflate(R.menu.mimenu, menu);
        }
    }
}