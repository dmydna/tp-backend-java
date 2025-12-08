package com.ui;

import com.servicio.ServicioClientes;
import com.modelo.Cliente;
import com.excepciones.NotEncotradoException;

import java.util.ArrayList;
import java.util.function.Predicate;

import static com.Main.*;
import static com.ui.PedidosHelper.armarPedidoPorInput;
import static com.ui.Utils.*;

public class ClientesHelper {

    public static void buscarCliente(){
        try{
            Cliente c = buscarClientePorInput();
            mostrarInformacion(c);
            pausarConsola();
            armarPedidoPorInput(c);
        }catch (NotEncotradoException e){
            printWarning(e.getMessage());
            pausarConsola();
        }finally {
            printInfo("Operacion  finalizada...");
        }
    }

    public static Cliente registrarClientePorInput(){
        String nombre = inputNombre();
        String email = inputEmail();

        Cliente c = new Cliente(nombre, email);
        clientes.agregar(c);
        printSuccess("Cliente registrado con exito...");
        return c;
    }


    /** Muestra una lista de clientes por consola segun filtro */
    public static void listarClientes(Predicate<Cliente> filtro) throws NotEncotradoException {
        if(ServicioClientes.cantidadClientes == 0){
            throw new NotEncotradoException("Clientes no encontrados...");
        }
        System.out.printf("%-5s %-15s %-20s\n",
                "ID", "NOMBRE", "EMAIL");
        System.out.println("-".repeat(15*3));
        for (Cliente c : clientes.filtrarClientes(filtro)) {
            if (filtro.test(c)) {
                System.out.printf("%-5d %-15s %-20s\n",
                        c.getID(),
                        c.getNombre(),
                        c.getEmail()
                );
            }
        }
    }

    public static void crearCliente() {
        try {
            Cliente c = registrarClientePorInput();
            mostrarInformacion(c);
            pausarConsola();
            armarPedidoPorInput(c);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }finally {
            printInfo("Operacion  finalizada...");
        }
    }

    public static void mostrarInformacion(Cliente c){
        System.out.printf("%-15s %-15s %-15s\n",
                "ID", "NOMBRE", "EMAIL");
        System.out.println("-".repeat(15*3));
        System.out.printf("%-15d %-15s %-15s\n",
                c.getId(),
                c.getNombre(),
                c.getEmail()
        );
    }

    public static Cliente buscarClientePorInput() throws NotEncotradoException {
        int Id = inputId();
        return clientes.buscarPorID(Id);
    }

    public static void registrarClientes(ArrayList<Cliente> cs){
        for(Cliente c : cs ){
            clientes.agregar(c);
        }
    }

}
