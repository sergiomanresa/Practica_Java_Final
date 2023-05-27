package Practica_evaluacion.models;

import Practica_evaluacion.excepcion.ArrayHabitacionesVacioException;
import Practica_evaluacion.excepcion.Campos_no_válidos_Exception;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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

    public  void escribirEnArchivo(String datos) {
        try {
            FileWriter writer = new FileWriter("C:\\Users\\zancr\\IdeaProjects\\proyecto\\src\\Practica_evaluacion\\data\\reservas",true);
            writer.write(datos);
            writer.close();
            System.out.println("Datos de reserva escritos en el archivo.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }



}
