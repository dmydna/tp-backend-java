package com.servicio;
import com.modelo.Cliente;
import com.excepciones.NotEncotradoException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class ServicioClientes {

    private final ArrayList<Cliente> clientes = new ArrayList<>();
    public static int cantidadClientes = 0;

    public void agregar(Cliente cliente){
        this.clientes.add(cliente);
        cantidadClientes++;
    }


    public Cliente buscarPorID(int Id) throws NotEncotradoException {
        for(Cliente c : this.clientes){
            if(Id == c.getID())  return c;
        }
        throw new NotEncotradoException("Cliente con Id: " + Id + " no encontrado.");
    }

    public void quitarCliente(int id) throws NotEncotradoException {
        boolean clienteRemovido =
                this.clientes.removeIf(c -> c.getID() == id);
        if (clienteRemovido) {
            cantidadClientes--;
        } else {
            throw new NotEncotradoException("Producto con ID " + id + " no encontrado.");
        }
    }

    public ArrayList<Cliente> filtrarClientes(Predicate<Cliente> filtro){
        return new ArrayList<>(
                this.clientes.stream().filter(filtro).toList()
        ) ;
    }


}
