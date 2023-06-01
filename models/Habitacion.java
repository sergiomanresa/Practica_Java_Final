package Practica_evaluacion.models;
import Practica_evaluacion.Utils.Validaciones;
import Practica_evaluacion.excepcion.NumeroInvalidoException;
import Practica_evaluacion.interfaces.disponibilidad_de_habitaciones;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Clase para las habitaciones
 *
 * @author Sergio Manresa
 * @version 1.0
 * @since 11/01/2023
 */
public class Habitacion implements disponibilidad_de_habitaciones {
    private int id;
    private String nombre;
    private String descripcion;
    private int num_camas;
    private int max_personas;
    private boolean ocupada;
    private String fechasOcupadas="";
    private double precio;

    public Habitacion() {
    }

    public Habitacion(int id, String nombre, String descripcion, int num_camas, int max_personas, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.num_camas = num_camas;
        this.max_personas = max_personas;
        this.ocupada = ocupada;
        this.fechasOcupadas = fechasOcupadas;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNum_camas() {
        return num_camas;
    }

    public void setNum_camas(int num_camas) {
        this.num_camas = num_camas;
    }

    public int getMax_personas() {
        return max_personas;
    }

    public void setMax_personas(int max_personas) {
        this.max_personas = max_personas;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getFechasOcupadas() {
        return fechasOcupadas;
    }

    public void setFechasOcupadas(String fechasOcupadas) {
        this.fechasOcupadas = fechasOcupadas;
        this.fechasOcupadas+=",";
    }

    /**
     * Devuelve un String que contiene todos los atributos del objeto separados por ;
     * @return String con los atributos separados por ;
     */
    public String formatearObjeto(){
        return ";"+id + ";"+ nombre + ";" + descripcion + ";" + num_camas + ";" + max_personas + ";" + precio+"\n";
    }

    /**
	 * Devuelve un ArrayList con los ID's que contiene la opción que ha elegido el usuario
            * @param ids String con los ids de las habitaciones usando como separador el ;
	 * @return ArrayList con los ID's
            */
    public static ArrayList<Integer> getIdsListado(String ids) {
        ArrayList<Integer> aIds = new ArrayList<>();

        if (ids.contains(",")) {
            int coma = ids.indexOf(',');
            aIds.add(Integer.parseInt(ids.substring(0, coma)));
            aIds.add(Integer.parseInt(ids.substring(coma + 1, ids.length())));
        } else {

        }

        return aIds;
    }
    /**
     * Genera 5 habitaciones por defecto y te las devuelve en un ArrayList
     * @return habitaciones base
     */
    public static ArrayList<Habitacion> generarHabitacionesBase(){
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        habitaciones.add(new Habitacion((int)(Math.random()*1000+1), "A1", "habitacion con vistas a la ciudad", 4, 8, 80.50));
        habitaciones.add(new Habitacion((int)(Math.random()*1000+1), "A2", "habitacion con vistas a la piscina", 2, 3, 40.75));
        habitaciones.add(new Habitacion((int)(Math.random()*1000+1), "A3", "habitacion con vistas al campo", 1, 1, 70.00));
        habitaciones.add(new Habitacion((int)(Math.random()*1000+1), "A4", "habitacion con vistas a un descampado", 2, 4, 75.00));
        habitaciones.add(new Habitacion((int)(Math.random()*1000+1), "A5", "habitacion con vistas a la montaña", 3, 5, 60.30));
        return habitaciones;
    }
    /**
     * Comprueba la disponibilidad de la habitación
     * @param rangoFecha
     * @return habitacion disponible para esa fecha
     */
    public boolean comprobarDisponibilidadHabitacion(String rangoFecha){
        if(fechasOcupadas.length()==0) return true;
        else{
            //Contador para finalizar el bucle una vez se hayan recorrido todas las fechas
            int contadorFinal=0;
            int contador=0;
            while(fechasOcupadas.length()>contadorFinal){
                //Comprobamos primero que los rangos no son iguales
                String fechaInicio="", fechaFin="", rangoFechas="";
                String fechaInicioReserva="", fechaFinReserva="";
                contador=contadorFinal;
                for(int i=contadorFinal; fechasOcupadas.charAt(i)!=':'; i++){
                    fechaInicio+=fechasOcupadas.charAt(i);
                    contador++;
                }
                for(int i=contador+1; fechasOcupadas.charAt(i)!=',';i++){
                    fechaFin+=fechasOcupadas.charAt(i);
                }
                rangoFechas+=fechaInicio+':'+fechaFin;
                if(rangoFechas.equals(rangoFecha)) return false;

                //Separo los datos en variables para validar
                int diasFechaRangoInicio=Integer.parseInt(fechaInicio.substring(0, 2));
                int diasFechaRangoFin=Integer.parseInt(fechaFin.substring(0, 2));
                int mesFechaRangoInicio=Integer.parseInt(fechaInicio.substring(3, 5));
                int mesFechaRangoFin=Integer.parseInt(fechaFin.substring(3, 5));
                int anyoFechaRangoInicio=Integer.parseInt(fechaInicio.substring(6, 10));
                int anyoFechaRangoFin=Integer.parseInt(fechaFin.substring(6, 10));

                //Separo los datos del rango de fechas de la reserva del parámetro
                for (int i = 0; rangoFecha.charAt(i)!=':'; i++) {
                    fechaInicioReserva+=rangoFecha.charAt(i);
                    contador=i;
                }
                for(int i=contador+2; i<rangoFecha.length();i++){
                    fechaFinReserva+=rangoFecha.charAt(i);
                }

                //Fecha de entrada de la reserva
                int diasFechaInicioReserva=Integer.parseInt(fechaInicioReserva.substring(0, 2));
                int mesFechaInicioReserva=Integer.parseInt(fechaInicioReserva.substring(3,5));
                int anyoFechaInicioReserva=Integer.parseInt(fechaInicioReserva.substring(6,10));

                //Fecha de Salida de la reserva
                int diasFechaFinReserva=Integer.parseInt(fechaFinReserva.substring(0, 2));
                int mesFechaFinReserva=Integer.parseInt(fechaFinReserva.substring(3,5));
                int anyoFechaFinReserva=Integer.parseInt(fechaFinReserva.substring(6,10));

                //Realizamos las comprobaciones pertinentes
                //"20/10/2023:25/10/2023" rangoFechaReserva
                //"24/09/2023:30/09/2023,10/10/2023:16/10/2023" fechasOcupadas
                if((diasFechaRangoInicio<=diasFechaInicioReserva && diasFechaFinReserva <=diasFechaRangoFin && mesFechaRangoInicio==mesFechaInicioReserva && mesFechaFinReserva==mesFechaRangoFin && anyoFechaInicioReserva==anyoFechaRangoInicio))return false;
                else if(diasFechaInicioReserva<diasFechaRangoInicio&&diasFechaFinReserva>=diasFechaRangoInicio && mesFechaRangoInicio==mesFechaInicioReserva && mesFechaFinReserva==mesFechaRangoFin && anyoFechaInicioReserva==anyoFechaRangoInicio) return false;
                    //Si el dia primer dia de la reserva coincide con el primer dia de una reserva, se devuelve mal
                else if(diasFechaInicioReserva==diasFechaRangoInicio && mesFechaInicioReserva==mesFechaRangoInicio && anyoFechaRangoInicio==anyoFechaInicioReserva) return false;

                else if(diasFechaInicioReserva>diasFechaRangoInicio && diasFechaRangoFin > diasFechaInicioReserva) return false;

                else if(mesFechaFinReserva==mesFechaRangoFin && diasFechaFinReserva >= diasFechaRangoInicio && diasFechaRangoInicio>=diasFechaInicioReserva) return false;
                contadorFinal+=22;
            }

        }
        return true;
    }

    public static Habitacion buscarHabitacion(int id, ArrayList<Habitacion> habitaciones) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getId() == id) {
                return habitacion;
            }
        }
        return null;
    }

    public static void eliminarHabitacion(Habitacion habitacion, ArrayList<Habitacion> habitaciones) {
        habitaciones.remove(habitacion);
        System.out.println("Habitación eliminada correctamente.");
    }

    public static void guardarHabitacionesEnArchivo(ArrayList<Habitacion> habitaciones) {
        try {
            FileWriter writer = new FileWriter("data/habitaciones");
            for (Habitacion habitacion : habitaciones) {
                writer.write(habitacion.formatearObjeto());
            }
            writer.close();
            System.out.println("Habitaciones actualizadas guardadas en el archivo.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public static void menu_habitacion(ArrayList<Habitacion> habitaciones) throws NumeroInvalidoException {
        String nuevoNombre;
        String nuevaDescripcion;
        String nuevoNumCamas;
        String nuevoMaxPersonas;
        String nuevoPrecio;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el ID de la habitación:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        Habitacion habitacionEncontrada = buscarHabitacion(id, habitaciones);
        if (habitacionEncontrada != null) {
            System.out.println("Habitación encontrada:");
            System.out.println("ID: " + habitacionEncontrada.getId());
            System.out.println("Nombre: " + habitacionEncontrada.getNombre());
            System.out.println("Descripción: " + habitacionEncontrada.getDescripcion());
            System.out.println("Número de camas: " + habitacionEncontrada.getNum_camas());
            System.out.println("Máximo de personas: " + habitacionEncontrada.getMax_personas());
            System.out.println("Precio: " + habitacionEncontrada.getPrecio());
            System.out.println("-----------------------------");

            // Menú de opciones
            System.out.println("Seleccione una opción:");
            System.out.println("1. Eliminar habitación");
            System.out.println("2. Actualizar habitación");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    eliminarHabitacion(habitacionEncontrada, habitaciones);
                    guardarHabitacionesEnArchivo(habitaciones);
                    break;
                case 2:
                    do {
                        System.out.println("Ingrese el nuevo nombre:");
                        nuevoNombre = scanner.nextLine();
                    } while (Validaciones.numerocorrecto(nuevoNombre));

                    do {
                        System.out.println("Ingrese la nueva descripción:");
                        nuevaDescripcion = scanner.nextLine();
                    } while (!Validaciones.noTieneNada(nuevaDescripcion));

                    do {
                        System.out.println("Ingrese el nuevo número de camas:");
                        nuevoNumCamas = scanner.nextLine();
                    } while (!Validaciones.solo_numero(nuevoNumCamas) || !Validaciones.noTieneNada(nuevoNumCamas));
                        int numCamas = Integer.parseInt(nuevoNumCamas);
                    do {
                        System.out.println("Ingrese el nuevo número máximo de personas:");
                        nuevoMaxPersonas = scanner.nextLine();
                    } while (!Validaciones.noTieneNada(nuevoMaxPersonas) || !Validaciones.solo_numero(nuevoMaxPersonas));
                    int maxPersonas = Integer.parseInt(nuevoMaxPersonas);
                    do {
                        System.out.println("Ingrese el nuevo precio:");
                        nuevoPrecio = scanner.nextLine();
                    } while (!Validaciones.noTieneNada(nuevoPrecio) || !Validaciones.solo_numero(nuevoPrecio));
                    int precio = Integer.parseInt(nuevoPrecio);

                    habitacionEncontrada.setNombre(nuevoNombre);
                    habitacionEncontrada.setDescripcion(nuevaDescripcion);
                    habitacionEncontrada.setNum_camas(numCamas);
                    habitacionEncontrada.setMax_personas(maxPersonas);
                    habitacionEncontrada.setPrecio(precio);

                    guardarHabitacionesEnArchivo(habitaciones);
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
        } else {
            System.out.println("No se encontró ninguna habitación con ese ID.");
        }

    }


}