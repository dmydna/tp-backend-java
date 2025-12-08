package com.servicio;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.excepciones.ProductoNotEncotradoException;
import com.modelo.Producto;

import static com.ui.ProductosHelper.mostrarInformacion;

public class ServicioProductos {

    private final ArrayList<Producto> productos = new ArrayList<>();
    public static int cantidadProductos = 0;

    public void agregarProducto(Producto p){
        this.productos.add(p);
        this.cantidadProductos ++;
    }



    public void quitarProducto(int id) throws ProductoNotEncotradoException {
        boolean productoRemovido = this.productos
                .removeIf(p -> p.getID() == id);
        if (productoRemovido) {
            cantidadProductos--;
        } else {
            throw new ProductoNotEncotradoException("Producto con ID " + id + " no encontrado.");
        }
    }

    public Producto buscarPorID(int Id) throws ProductoNotEncotradoException {
        for(Producto p : this.productos){
            if(Id == p.getID()) {
                return p;
            }
        }
        throw new ProductoNotEncotradoException("Producto con Id " + Id + " no encontrado.");
    }

    private void actualizarPorID(int Id, Consumer<Producto> accion){
        try {
            Producto p = this.buscarPorID(Id);
            System.out.println("Producto encontrado: " + p.getNombre() + "\n");
            accion.accept(p);
            mostrarInformacion(p);
        } catch (ProductoNotEncotradoException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Operación de búsqueda finalizada");
        }
    }


    public ArrayList<Producto> filtrarProductos(Predicate<Producto> filtro){
        return new ArrayList<>(
                this.productos.stream().filter(filtro).toList()
        ) ;
    }




}
