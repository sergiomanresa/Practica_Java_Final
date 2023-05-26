package Practica_evaluacion.models;
import java.util.ArrayList;

/**
 * Clase para las habitaciones
 *
 * @author Sergio Manresa
 * @version 1.0
 * @since 11/01/2023
 */
public class Habitacion {
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
            // Manejo de caso de cadena sin coma
            // Puedes agregar un valor predeterminado o lanzar una excepción, dependiendo de tu requerimiento
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
}