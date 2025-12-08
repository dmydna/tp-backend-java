package com.ui;

import static com.Main.*;
import static com.ui.ClientesHelper.*;
import static com.ui.PedidosHelper.*;
import static com.ui.ProductosHelper.*;
import static com.ui.Utils.*;

import com.servicio.*;
import com.excepciones.*;
import com.modelo.*;

import java.util.ArrayList;
import java.util.List;


public class MenuPrincipal {

    public  static void menu()  {
        String bloque = """
        
        \u001B[33m1) Agregar Muestra\u001B[0m
        2) Agregar producto
        3) Buscar/Actualizar producto
        4) Eliminar producto
        ------------------------------
        5) Crear un pedido
        6) Ver Pedido
        ------------------------------
        7) Listar productos
        8) Listar clientes
        9) Listar pedidos
        
        0) Salir
        Elija una opción:
        """;


        boolean salir = false;

        while(!salir){
            System.out.println(bloque);
            String entrada = sc.nextLine().trim();

            switch (entrada){
                case "1":  AgregarMuestraMenu();break;   // Agregar Muestra
                case "2":  AgregarProductoMenu();break;  // Agregar producto
                case "3":  BuscarProductoMenu();break;   // Buscar/Actualizar producto
                case "4":  EliminarPorductoMenu();break; // Eliminar producto
               /*---------------------------------------------------------------*/
                case "5":  CrearPedidoMenu();break; // Crear un pedido
                case "6":  VerPedidosMenu();break;  // Ver Pedido
                /*---------------------------------------------------------------*/
                case "7":  ListarProductoMenu();break; // Listar productos
                case "8":  ListarClientesMenu();break; // Lista clientes
                case "9":  ListarPedidosMenu();break;  // Listar pedidos
                /*---------------------------------------------------------------*/
                case "0":  salir = true;break;
            }
        }
    }

    /** Agregar productos */
    public static void AgregarProductoMenu() {

        boolean salir = false;
        while (!salir){
            String bloque = """
                Agregar Producto:
                -----------------
                1) Cafe
                2) Te
                3) Bebidas
                
                0) Salir
                Elija una opción:
                """;
            System.out.println(bloque);
            String entrada = sc.nextLine().trim();
            switch (entrada) {
                case "1": registrarProducto("Cafe"); break;
                case "2": registrarProducto("Te"); break;
                case "3": registrarProducto("Bebida");break;
                case "0": salir = true;
            }
        }

    }

    public static void ListarClientesMenu() {
        try{
            listarClientes(c -> true);
        }catch (NotEncotradoException e){
            printWarning(e.getMessage());
        }finally {
            pausarConsola();
        }
    }

    /** Listar Productos */
    public static void ListarProductoMenu() {
        try{
            listarProductos(p->true);
        }catch (ProductoNotEncotradoException e){
            printWarning(e.getMessage());
        }finally {
            pausarConsola();
        }
    }

    /** Buscar/Actualizar producto */
    public static void BuscarProductoMenu() {
        boolean salir = false;
        while (!salir){
            String bloque = """
                Buscar/Actualizar producto:
                ---------------------------
                1) Buscar
                2) Actualizar
                
                0) Salir
                Elija una opción:
                """;

            System.out.println(bloque);
            String entrada = sc.nextLine().trim();

            switch (entrada) {
                case "1": buscarProducto(); break;
                case "2": actualizarProducto(); break;
                case "0": salir = true;
            }
        }


    }


    /** Eliminar producto*/
    public static void EliminarPorductoMenu() {
        try {
            Producto p = seleccionarProductoPorInput();
            catalogo.quitarProducto(p.getID());
            printSuccess("Se elimino el producto: " + p.getNombre() );
        }catch (ProductoNotEncotradoException e){
            printWarning(e.getMessage());
        }finally {
            pausarConsola();
        }

    }

    /** Crear un pedido */
    public static void CrearPedidoMenu(){
        try{
            validarPedidoMenu();
        }catch (NotEncotradoException e){
            printWarning(e.getMessage());
        }
    }

    /** Listar pedidos */
    public static void ListarPedidosMenu(){
        try{
            listarPedidos(p->true);
        }catch (NotEncotradoException e){
            printWarning(e.getMessage());
        }finally {
            pausarConsola();
        }
    }

    /** Ver Pedido */
    public  static void VerPedidosMenu() {
       try{
           System.out.println("""
                   Selecciona un pedido:
                   """
           );
           listarPedidos(p->true);
           int iD = inputId();
           listarProductosEnPedido(iD);
       }catch (NotEncotradoException e){
           printWarning(e.getMessage());
        }finally{
           pausarConsola();
       }
    }

    /** Agrega un conjunto de productos, pedidos y clientes */
    public static void AgregarMuestraMenu(){

        // Crea productos
        Producto cafe_1 = new Cafe("Expresso Fuerte", 22.99, 15, 250);
        Producto cafe_2 = new Cafe("Late Suave", 18.50, 80, 750);
        Producto te_1 = new Te("Te Chai Especiado", 10.75, 45, 500);
        Producto te_2 = new Te("Te Blanco Premium", 35.00, 10, 500);
        Producto bebida_1 = new Bebida("Agua Mineral", 3.50, 120, 1000);
        Producto bebida_2 = new Bebida("Jugo Naranja 2L", 8.90, 25, 2000);
        // agrega productos al catalogo
        ArrayList<Producto> list_productos = new ArrayList<>(List.of(cafe_1, cafe_2, te_1, te_2, bebida_1, bebida_2));
        registrarProductos(list_productos);

        // Crea clientes
        Cliente adrian  = new Cliente("Adrian", "adrian@mail.com");
        Cliente juan    = new Cliente("Juan", "juan@mail.com");
        Cliente maria   = new Cliente("Maria", "maria.perez@dominio.com");
        Cliente lucas   = new Cliente("Lucas", "lucas.g@empresa.net");
        Cliente sofia   = new Cliente("Sofia", "sofia88@correo.es");
        Cliente pedro   = new Cliente("Pedro", "pedro.alvarez@info.org");
        // Agrega cliente a la base de clientes
        ArrayList<Cliente> list_clientes = new ArrayList<>(List.of(adrian, juan, maria, lucas, sofia, pedro));
        registrarClientes(list_clientes);

        // Crea pedidos
        Pedido pedido1_adrian = new Pedido(adrian);
        Pedido pedido1_juan = new Pedido(juan);
        Pedido pedido2_juan = new Pedido(juan);
        Pedido pedido1_sofi = new Pedido(sofia);
        Pedido pedido1_pedro = new Pedido(pedro);

        // Agrega productos al pedido
        pedido1_adrian.agregarProducto(cafe_2, 30);
        pedido1_adrian.agregarProducto(bebida_2, 5);

        pedido1_juan.agregarProducto(te_1, 2);
        pedido1_juan.agregarProducto(bebida_1, 10);

        pedido2_juan.agregarProducto(cafe_1, 12);

        pedido1_sofi.agregarProducto(te_2, 1);
        pedido1_sofi.agregarProducto(cafe_2, 5);

        pedido1_pedro.agregarProducto(bebida_1, 25);
        pedido1_pedro.agregarProducto(te_1, 15);

        // agrega pedidos a la base de pedidos
        ArrayList<Pedido> list_pedidos = new ArrayList<>(List.of(pedido1_adrian, pedido1_juan, pedido2_juan, pedido1_sofi, pedido1_pedro));
        registrarPedidos(list_pedidos);

        printSuccess("Muestra agregada con existo....");
        System.out.printf("productos agregados: %d\nclientes agregados: %d\npedidos agregados: %d\n",
                ServicioProductos.cantidadProductos,
                ServicioClientes.cantidadClientes,
                ServicioPedidos.cantidadPedidos
        );
        pausarConsola();
    }
}
