package Practica_evaluacion.Controllers;

import Practica_evaluacion.excepcion.Archivo_no_encontrado_Exception;
import Practica_evaluacion.excepcion.ArrayHabitacionesVacioException;
import Practica_evaluacion.excepcion.Campos_no_válidos_Exception;
import Practica_evaluacion.models.Habitacion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Gestor_Habitaciones {
    ArrayList<Habitacion> habitaciones = new ArrayList<>();

    public Gestor_Habitaciones(){}

    public Gestor_Habitaciones(ArrayList<Habitacion> habitaciones){
        this.habitaciones = habitaciones;
    }

    /**
     * Constructor para cargar la base de datos en el arraylist de habitaciones que se le pasa como parametro
     * @param habitaciones
     * @param cargarBD
     * @throws IOException
     * @throws Campos_no_válidos_Exception
     */
    public Gestor_Habitaciones(ArrayList<Habitacion> habitaciones, boolean cargarBD) throws IOException, Campos_no_válidos_Exception {
        this.habitaciones = habitaciones;
        subir_archivo();
    }
    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }
    public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    /**
     * Muestra las habitaciones que contiene el atributo habitaciones
     */
    public void mostrarHabitaciones(){
        for(Habitacion habitacion : habitaciones){
            System.out.println(habitacion.toString());
        }
    }

    /**
     * Agrega una habitación a la lista de habitaciones
     * @param habitacion
     */
    public void agregarHabitacion(Habitacion habitacion){
        habitaciones.add(habitacion);
    }

    /**
     * Carga el fichero "habitaciones.dat", donde se encuentran los registros de las habitaciones, a la lista de habitaciones
     * @throws Archivo_no_encontrado_Exception
     * @throws IOException
     * @throws Practica_evaluacion.excepcion.Campos_no_válidos_Exception
     */
    private void subir_archivo() throws IOException, Campos_no_válidos_Exception {
        FileReader fr = new FileReader("Data/habitaciones");
        BufferedReader br = new BufferedReader(fr);

        String linea;
        String[] registro;

        while((linea=br.readLine())!=null){
            registro = linea.split(";");
            int id, num_camas, max_personas;
            String nombre, descripcion, fechasOcupadas = null;
            double precio;
            id = Integer.parseInt(registro[0]);
            nombre = registro[1];
            descripcion = registro[2];
            try{
                num_camas = Integer.parseInt(registro[3]);
                max_personas = Integer.parseInt(registro[4]);
                precio = Double.parseDouble(registro[5]);
            }catch(NumberFormatException e){
                throw new Campos_no_válidos_Exception("Existen campos incorrectos en el registro");
            }
            habitaciones.add(new Habitacion(id, nombre, descripcion, num_camas, max_personas, precio));
            

        }
    }

    /**
     * Almacena el contenido del arraylist de habitaciones en la BD
     * @throws IOException
     * @throws ArrayHabitacionesVacioException
     */
    public void guardarRegistros() throws IOException, ArrayHabitacionesVacioException{
        FileWriter fw = new FileWriter("Data/habitaciones.dat", false);
        if(habitaciones.size() > 0){
            for(Habitacion h : habitaciones){
                fw.write(h.formatearObjeto());
            }
        } else{
            throw new ArrayHabitacionesVacioException("No se puede guardar el listado de habitaciones (no existen habitaciones)");
        }
        fw.close();
    }

}
