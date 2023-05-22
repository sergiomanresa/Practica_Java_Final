package Practica_evaluacion.excepcion;

public class ArrayHabitacionesVacioException extends  Exception {
    public String mensaje;

    public ArrayHabitacionesVacioException(String mensaje){this.mensaje =mensaje;}

    @Override
    public  String getMessage(){return mensaje;}
}


