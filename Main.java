package Practica_evaluacion;
import Practica_evaluacion.Controllers.Gestor_Clientes;
import Practica_evaluacion.excepcion.*;
import Practica_evaluacion.models.Administrador;
import Practica_evaluacion.models.Cliente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
        public static void mostrarMenu(Scanner scanner){

            //Arraylists,Hashmap y scanner
            ArrayList<Cliente> cliente =new ArrayList<>();
            Gestor_Clientes gestor=new Gestor_Clientes(cliente);
            gestor.generarClientesBase();
            Gestor_Clientes gestorClientes=new Gestor_Clientes();
            List<Administrador> administrador=new ArrayList<>();
            //variables
            char caso = ' ';
            String opcion="";
            //menu
            System.out.println("************************");
            System.out.println("****  MUÑÓN DREAMS  ****");
            System.out.println("************************");
            System.out.println("1. Registro usuario");
            System.out.println("2. Login usuario");
            System.out.println("3. Registro administrador");
            System.out.println("4. menu gestion");
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
                    try {
                        gestorClientes.registro_clientes();
                    } catch (FormatoFechaNoValidoException e) {
                        throw new RuntimeException(e);
                    } catch (StringVacioException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ArrayHabitacionesVacioException e) {
                        throw new RuntimeException(e);
                    } catch (Campos_no_válidos_Exception e) {
                        throw new RuntimeException(e);
                    }
                    do {
                        System.out.println("Desea logearse?(S/N)");
                        opcion= scanner.nextLine();
                        opcion=opcion.toUpperCase();
                    }while (!opcion.equals("S")&& !opcion.equals("N"));
                    if(opcion.equals("S")){
                        try {
                            gestorClientes.login_cliente();
                        } catch (NumeroInvalidoException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (Campos_no_válidos_Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else System.out.println("Saliendo...");

                    break;
                case '2':
                    try {
                        try {
                            gestorClientes.login_cliente();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (NumeroInvalidoException e) {
                        throw new RuntimeException(e);
                    } catch (Campos_no_válidos_Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case '3':
                    try {
                        gestorClientes.registrarAdministrador(administrador);
                    } catch (EmailInvalidoException e) {
                        throw new RuntimeException(e);
                    } catch (StringVacioException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case '4':
                    try {
                        gestorClientes.menu_Administrador();
                    } catch (StringVacioException e) {
                        throw new RuntimeException(e);
                    } catch (FormatoFechaNoValidoException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ArrayHabitacionesVacioException e) {
                        throw new RuntimeException(e);
                    } catch (Campos_no_válidos_Exception e) {
                        throw new RuntimeException(e);
                    }
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



    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        mostrarMenu(scanner);
        }
    }