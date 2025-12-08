package com.modelo;

import com.excepciones.ProductoNotEncotradoException;
import com.excepciones.StockInsuficienteException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ui.Utils.printWarning;


public class Pedido {

    public enum state {
        PROCESANDO,
        COMPLETO,
    }
    // porductos<ProductoID, DetallesPedido>
    private final Map<Integer, DetallesPedidos> detalles = new HashMap<>();
    public ArrayList<Producto> productos;
    private Cliente cliente;
    private int id = 0;
    public state estado;
    static int cantidadPedidos = 0; //asigna ids

    // Contructor
    public Pedido(Cliente cliente){
        this.id = cantidadPedidos;
        this.cliente = cliente;
        this.estado = state.PROCESANDO;
        this.productos = this.getProductos();
        this.cantidadPedidos ++;
    }


    public void cerrarPedido(){
        this.estado = state.COMPLETO;
    }

    public ArrayList<Producto> getProductos() {
        return this.detalles.values().stream()
                .map(DetallesPedidos->DetallesPedidos.getProducto()) // Mapea cada Detalle a su Producto
                .collect(Collectors.toCollection(ArrayList::new)); // Recolecta en un ArrayList
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public int cantidadDeUnProductosID(int Id){
        DetallesPedidos d = detalles.get(Id);
        return d.getCantidad();
    }


    public int cantidadProductos(){
        return this.detalles.size();
    }


    public boolean agregarProducto(Producto p, int cantidad){
        try{
            int productId = p.getID();
            // valida existencia de producto
            DetallesPedidos detalleExiste = this.detalles.get(productId);
            // valida cantidad pedida y descuenta del stock
            p.descontarStock(cantidad);

            if (detalleExiste != null) {
                detalleExiste.setCantidad(cantidad);
            } else {
                this.detalles.put(productId, new DetallesPedidos(p, cantidad));
            }

            return true;
        }catch (StockInsuficienteException e){
            printWarning(e.getMessage());
            return false;
        }
    }

    public double calcularTotal(){
        double total = 0;
        for (Producto p : this.getProductos()){
            total += p.getPrecio() * p.getCantidadEnStock();
        }
        return total;
    }

    public int getID(){
        return this.id;
    }

}


