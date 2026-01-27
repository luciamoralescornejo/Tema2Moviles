package com.example.tarea2_3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ListaCompraDatabaseAdapter {

    private static final String DATABASE_NOMBRE = "dbCompra";
    private static final String DATABASE_TABLA = "productos";
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "ListaCompraDatabaseAdapter";

    public static final String CLAVE_PRODUCTO = "producto";
    public static final String CLAVE_CANTIDAD = "cantidad";
    public static final String CLAVE_ID = "_id";

    private static final String[] COLUMNAS_CONSULTA = {CLAVE_ID, CLAVE_PRODUCTO, CLAVE_CANTIDAD};

    private static final String CREAR_DATABASE = "create table " + DATABASE_TABLA + " (" + CLAVE_ID +
            " integer primary key autoincrement, " + CLAVE_PRODUCTO + " text not null, " +
            CLAVE_CANTIDAD + " integer not null);";

    private DatabaseHelper dbHelper;
    private SQLiteDatabase dbCompra;
    private final Context dbContext;

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREAR_DATABASE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Actualizando BD");
            db.execSQL("DROP TABLE IF EXISTS productos");
            onCreate(db);
        }
    }

    public ListaCompraDatabaseAdapter(Context ctx) {
        this.dbContext = ctx;
    }

    public ListaCompraDatabaseAdapter open() throws SQLException {
        this.dbHelper = new DatabaseHelper(this.dbContext);
        this.dbCompra = this.dbHelper.getWritableDatabase();
        return this;
    }

    //Metodo para cerrar la conexión a la BD
    public void close() {
        dbHelper.close();
    }

    //Metodo para insertar un elemento en la BD
    public long crearElemento(String nombre, int cantidad) {
        ContentValues valoresProducto = new ContentValues();
        valoresProducto.put(CLAVE_PRODUCTO, nombre);
        valoresProducto.put(CLAVE_CANTIDAD, cantidad);
        return dbCompra.insert(DATABASE_TABLA, null, valoresProducto);
    }

    //Metodo para borrar un elemento en la BD
    public boolean borrarElemento(long rowId) {
        return dbCompra.delete(DATABASE_TABLA, CLAVE_ID + "=" + rowId, null) > 0;
    }

    //Metodo para borrar todos los elementos de la tabla
    public boolean limpiarTabla(){
        return dbCompra.delete(DATABASE_TABLA,null, null) > 0;
    }

    //Metodo para obtener todos los elementos de la tabla mediante un cursor
    public Cursor obtenerTodosElementos() {
        return dbCompra.query(DATABASE_TABLA, COLUMNAS_CONSULTA, null, null, null, null, null);
    }

    //Metodo para obtener un elemento a partir de su id
    public Cursor obtenerElemento(long rowId) throws SQLException {
        Cursor mCursor = dbCompra.query(true, DATABASE_TABLA, COLUMNAS_CONSULTA, CLAVE_ID + "=" +
                rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //Metodo para actualizar un elemento de la tabla
    public boolean actualizarElemento(long rowId, String producto, int cantidad) {
        ContentValues args = new ContentValues();
        args.put(CLAVE_PRODUCTO, producto);
        args.put(CLAVE_CANTIDAD, cantidad);
        return this.dbCompra.update(DATABASE_TABLA, args, CLAVE_ID + "=" + rowId, null) > 0;
    }
}
