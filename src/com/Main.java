package com;
import com.servicio.ServicioProductos;
import com.servicio.ServicioClientes;
import com.servicio.ServicioPedidos;

import java.util.Scanner;

import static com.ui.MenuPrincipal.menu;


public class Main {

    public static final ServicioProductos catalogo = new ServicioProductos();
    public static final ServicioClientes clientes = new ServicioClientes();
    public static final ServicioPedidos pedidos = new ServicioPedidos();
    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
        sc.close();
    }

    }






