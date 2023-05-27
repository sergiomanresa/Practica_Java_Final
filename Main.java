package Practica_evaluacion;
import Practica_evaluacion.Controllers.Gestor_Clientes;
import Practica_evaluacion.excepcion.*;
import Practica_evaluacion.models.Cliente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

        public static void mostrarMenu(Scanner scanner) throws StringVacioException, FormatoFechaNoValidoException, Numero_no_valido_Exception, IOException, ArrayHabitacionesVacioException, Campos_no_válidos_Exception, NumeroInvalidoException {

            //Arraylists,Hashmap y scanner
            ArrayList<Cliente> cliente =new ArrayList<>();
            Gestor_Clientes gestor=new Gestor_Clientes(cliente);
            gestor.generarClientesBase();
            Gestor_Clientes gestorClientes=new Gestor_Clientes();
            //variables
            char caso = ' ';
            String opcion="";
            //menu
            System.out.println("************************");
            System.out.println("****  MUÑÓN DREAMS  ****");
            System.out.println("************************");
            System.out.println("1. Registro usuario");
            System.out.println("2. Login usuario");
            System.out.println("0. Salir");
            System.out.println("Opción en número:");

             opcion = scanner.nextLine();
            if (opcion.length() == 1)
                caso = opcion.charAt(0);
            else {
                System.out.println("Opción inválida");
                mostrarMenu(scanner);
                return;
            }

            switch (caso) {
                case '1':
                    gestorClientes.registro_clientes();
                    do {
                        System.out.println("Desea logearse?(S/N)");
                        opcion= scanner.nextLine();
                        opcion=opcion.toUpperCase();
                    }while (!opcion.equals("S")&& !opcion.equals("N"));
                    if(opcion.equals("S")){
                        gestorClientes.login_cliente();
                    }
                    else System.out.println("Saliendo...");

                    break;
                case '2':
                    gestorClientes.login_cliente();
                    break;
                case '0':
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida");
                    mostrarMenu(scanner);
                    return;
            }



            mostrarMenu(scanner); // Volver a mostrar el menú
        }



    public static void main(String[] args) throws StringVacioException, FormatoFechaNoValidoException, Numero_no_valido_Exception, IOException, ArrayHabitacionesVacioException, Campos_no_válidos_Exception, NumeroInvalidoException {

        Scanner scanner = new Scanner(System.in);
        mostrarMenu(scanner);

        }
    }
