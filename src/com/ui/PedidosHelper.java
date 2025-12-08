package com.ui;

import com.servicio.ServicioPedidos;
import com.modelo.Cliente;
import com.excepciones.NotEncotradoException;
import com.excepciones.ProductoNotEncotradoException;
import com.modelo.Pedido;
import com.modelo.Producto;

import java.util.ArrayList;
import java.util.function.Predicate;

import static com.Main.*;
import static com.ui.ClientesHelper.buscarCliente;
import static com.ui.ClientesHelper.crearCliente;
import static com.ui.Utils.*;
import static com.ui.ProductosHelper.seleccionarProductoPorInput;

public class PedidosHelper {

    /** Solicita por input un cliente valido, para crear un pedido nuevo */
    public static  void validarPedidoMenu() throws NotEncotradoException {

        boolean salir = false;
        while (!salir){
            System.out.println("""
                Crear Pedido:
                ----------------
                1) Registrar Cliente
                2) Buscar Cliente
                
                0) Salir
                Elija una opción:
                """);

            String entrada = sc.nextLine().trim();

            switch (entrada){
                case "1": crearCliente(); break;
                case "2": buscarCliente();break;
                case "0": salir=true;
            }
        }

    }


    /** Dado un cliente valido, agrega productos al pedido */
    public static void armarPedidoPorInput(Cliente c) {
        Pedido pedido = new Pedido(c);
        boolean salir= false;
        while (!salir){
           try{
               Producto producto =  seleccionarProductoPorInput();
               int cantidad = inputCantidad();
               boolean agregado = pedido.agregarProducto(producto, cantidad);
               if(agregado) printSuccess("Operacion exitosa...");
           }catch (ProductoNotEncotradoException e){
               printWarning(e.getMessage());
           }
           salir=!confirmarAccion("Agregar otro producto?");;
        }
        pedidos.agregar(pedido);
    }

    public static void listarPedidos(Predicate<Pedido> filtro) throws NotEncotradoException {

        if(ServicioPedidos.cantidadPedidos == 0){
            throw new NotEncotradoException("No se encontraron pedidos...");
        }
        System.out.printf("%-5s %-15s %-15s\n",
                "ID", "CLIENTE", "CANTIDAD PRODUCTO"
        );
        System.out.println("-".repeat(10+15+15));
        for (Pedido p : pedidos.filtrarPedidos(filtro)) {
            if (filtro.test(p)) {
                System.out.printf("%-5d %-15s %-15d\n",
                        p.getID(),
                        p.getCliente().getNombre(),
                        p.cantidadProductos()
                );
            }
        }
        System.out.println();
    }

    // Lista productos de un pedido (pide id del pedido)
    public static void listarProductosEnPedido(int id) throws NotEncotradoException {
        Pedido pedido = pedidos.buscarPorID(id);

        System.out.printf("%-5s %-30s %-15s %-15s %-15s\n",
                "ID", "PRODUCTO", "TIPO", "PRECIO" ,"UNIDADES"
        );
        System.out.println("-".repeat(15*5+10));
        for (Producto p : pedidos.getProductos(id)) {
            System.out.printf("%-5d %-30s %-15s %-15.2f %-10d\n",
                    p.getID(),
                    p.getNombre(),
                    p.getTipo(),
                    p.getPrecio(),
                    pedido.cantidadDeUnProductosID(p.getID())
            );
        }
        System.out.println();
    }




    public static void registrarPedidos(ArrayList<Pedido> ps){
        for(Pedido p : ps ){
            pedidos.agregar(p);
        }
    }





}

