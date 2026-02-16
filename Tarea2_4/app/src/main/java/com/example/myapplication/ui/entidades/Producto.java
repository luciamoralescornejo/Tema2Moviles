package com.example.myapplication.ui.entidades;


//Anotación para indicar que una clase es una entidad Room
public class Producto {

    public int uid;

    private String producto;

    private double cantidad;

    public Producto(String producto, double cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto() {
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
