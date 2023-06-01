package Practica_evaluacion.models;

import Practica_evaluacion.Main;
import Practica_evaluacion.Utils.Validaciones;
import Practica_evaluacion.excepcion.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Clase para las reservas
 *
 * @author Sergio Manresa
 * @version 1.0
 * @since 11/01/2023
 */
public class Reservas {
    private int cod;
    private String id_cliente;
    private static ArrayList<Integer> id_habitacion;

    private String fecha_entrada;
    private String fecha_salida;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public static ArrayList<Integer> getId_habitacion() {
        return id_habitacion;
    }

    public void setId_habitacion(ArrayList<Integer> id_habitacion) {
        this.id_habitacion = id_habitacion;
    }

    public String getFecha_entrada() {
        return fecha_entrada;
    }

    public void setFecha_entrada(String fecha_entrada) {
        this.fecha_entrada = fecha_entrada;
    }

    public String getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(String fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public Reservas(int cod, String id_cliente, ArrayList<Integer> id_habitacion, String fecha_entrada, String fecha_salida) {
        this.cod = cod;
        this.id_cliente = id_cliente;
        this.id_habitacion = id_habitacion;
        this.fecha_entrada = fecha_entrada;
        this.fecha_salida = fecha_salida;
    }

    public String formatearObjeto(){
        return cod + ";"+ id_cliente +";" + id_habitacion+";" + fecha_entrada + ";"+fecha_salida+";";
    }
    public static Reservas buscarReserva(int codigo) {
        for (Reservas reserva : reservas) {
            if (reserva.getCod() == codigo) {
                return reserva;
            }
        }
        return null;
    }
    public void eliminarReserva() {
        reservas.remove(this);
        System.out.println("La reserva con código " + cod + " ha sido eliminada.");
    }
    public  void actualizarReserva(int nuevoCod, String nuevoIdCliente, ArrayList<Integer> nuevoIdHabitacion, String nuevaFechaEntrada, String nuevaFechaSalida) {
        setCod(nuevoCod);
        setId_cliente(nuevoIdCliente);
        setId_habitacion(nuevoIdHabitacion);
        setFecha_entrada(nuevaFechaEntrada);
        setFecha_salida(nuevaFechaSalida);
        System.out.println("La reserva con código " + cod + " ha sido actualizada.");
    }


    public  void escribirEnArchivo(String datos) {
        try {
            FileWriter writer = new FileWriter("data/reservas",true);
            writer.write(datos);
            writer.close();
            System.out.println("Datos de reserva escritos en el archivo.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
    private static ArrayList<Reservas> reservas = new ArrayList<>();

    // ... (Otros métodos y funcionalidades de gestión de reservas)

    public static void buscarReservaPorCodigo() {
        String nuevoCod="";
        String _codigo="";
        String nuevoIdCliente="";
        String nuevaFechaEntrada="";
        String nuevaFechaSalida="";
        ArrayList<Integer> nuevoIdHabitacion;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Ingrese el código de la reserva:");
            _codigo = scanner.nextLine();
            scanner.nextLine(); // Limpiar el buffer
        } while (!Validaciones.solo_numero(String.valueOf(_codigo)) || !Validaciones.noTieneNada(String.valueOf(_codigo)));
        int codigo = Integer.parseInt(_codigo);

        Reservas reservaEncontrada = buscarReserva(codigo);
        if (reservaEncontrada != null) {
            System.out.println("Reserva encontrada:");
            System.out.println("Código: " + reservaEncontrada.getCod());
            System.out.println("Cliente: " + reservaEncontrada.getId_cliente());
            System.out.println("Habitación: " + reservaEncontrada.getId_habitacion());
            System.out.println("Fecha de entrada: " + reservaEncontrada.getFecha_entrada());
            System.out.println("Fecha de salida: " + reservaEncontrada.getFecha_salida());
            System.out.println("-----------------------------");

            // Menú de opciones
            System.out.println("Seleccione una opción:");
            System.out.println("1. Eliminar reserva");
            System.out.println("2. Actualizar reserva");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    reservaEncontrada.eliminarReserva();
                    guardarReservasEnArchivo();
                    break;
                case 2:
                    do {
                        System.out.println("Ingrese el nuevo código:");
                         nuevoCod = scanner.nextLine();
                        scanner.nextLine(); // Limpiar el buffer
                    }while (!Validaciones.solo_numero(nuevoCod) || !Validaciones.noTieneNada(nuevoCod));
                    int _nuevoCod = Integer.parseInt(nuevoCod);

                    do {
                        System.out.println("Ingrese el nuevo ID del cliente:");
                         nuevoIdCliente = scanner.nextLine();
                    }while (!Validaciones.noTieneNada(nuevoIdCliente) || !Validaciones.solo_numero(nuevoCod));


                    do {
                        System.out.println("Ingrese el nuevo ID de la habitación:");
                        nuevoIdHabitacion = new ArrayList<>();
                    }while (!Validaciones.noTieneNada(nuevoIdCliente) || !Validaciones.solo_numero(nuevoCod));

                    do {
                        System.out.println("¿Cuál es la fecha de entrada?");
                        nuevaFechaEntrada = scanner.nextLine();
                        System.out.println("¿Cuál es la fecha de salida?");
                        nuevaFechaSalida = scanner.nextLine();
                    } while (!Validaciones.fechaentrada_salida(nuevaFechaEntrada) || !Validaciones.fechaentrada_salida(nuevaFechaSalida));


                    reservaEncontrada.actualizarReserva(_nuevoCod, nuevoIdCliente, nuevoIdHabitacion, nuevaFechaEntrada, nuevaFechaSalida);
                    guardarReservasEnArchivo();
                    break;
                default:
                    System.out.println("Opción inválida.");
                    buscarReservaPorCodigo();
                    break;
            }
        } else {
            System.out.println("No se encontró ninguna reserva con ese código.");
        }
    }

    public static void guardarReservasEnArchivo() {
        try {
            FileWriter writer = new FileWriter("data/reservas");
            for (Reservas reserva : reservas) {
                writer.write(reserva.formatearObjeto() + "\n");
            }
            writer.close();
            System.out.println("Reservas actualizadas guardadas en el archivo.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }


}


