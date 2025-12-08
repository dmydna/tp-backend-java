package com.modelo;

import com.excepciones.StockInsuficienteException;

public class DetallesPedidos {

    private int   cantidad;       // cantidad del producto pedido
    private double precioTotal;   // precio total por producto
    private Producto producto;

    public DetallesPedidos(Producto p, int cantidad){
        this.producto = p;
        this.cantidad = cantidad;
        this.precioTotal += producto.getPrecio() * cantidad;;
    }

    public Producto getProducto(){
        return this.producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.precioTotal = (producto.getPrecio() * cantidad);
    }

    public int getCantidad(){
        return this.cantidad;
    }

    public int getProductoId(){
        return this.producto.getID();
    }

    public String getProductoNombre(){
        return this.producto.getNombre();
    }

    public double getProductoPrecio(){
        return this.producto.getPrecio();
    }

}
