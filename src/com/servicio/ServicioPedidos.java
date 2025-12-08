package com.servicio;
import com.excepciones.NotEncotradoException;
import com.modelo.Pedido;
import com.modelo.Producto;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class ServicioPedidos {

    private final ArrayList<Pedido> pedidos = new ArrayList<>();
    public static int cantidadPedidos = 0;

    /** Cuando se pide un ID corresponde al del Pedido */

    public void  agregar(Pedido p){
        this.pedidos.add(p);
        cantidadPedidos++;
    }

    public void quitarPorId(int id) throws NotEncotradoException {
        boolean pedidoRemovido = this.pedidos
                .removeIf(p -> p.getID() == id);
        if (pedidoRemovido) {
            cantidadPedidos--;
        } else {
            throw new NotEncotradoException("Producto con ID " + id + " no encontrado.");
        }
    }


    public ArrayList<Pedido> filtrarPedidos(Predicate<Pedido> filtro){
        return new ArrayList<>(
                this.pedidos.stream().filter(filtro).toList()
        ) ;
    }



    public Pedido buscarPorID(int Id) throws NotEncotradoException {
        for(Pedido p : this.pedidos){
            if(Id == p.getID()) {
                return p;
            }
        }
        throw new NotEncotradoException("Pedido con Id: " + Id + " no encontrado.");
    }


    public ArrayList<Producto> getProductos(int id) throws NotEncotradoException {
        Optional<Pedido> pedido = this.pedidos.stream()
                .filter(p -> p.getID() == id)
                .findFirst();
        if(pedido.isEmpty()){
            throw new NotEncotradoException("Producto no encontrados....");
        }
        return pedido.get().getProductos();
    }

}
