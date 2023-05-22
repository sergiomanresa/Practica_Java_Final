package Practica_evaluacion.excepcion;

public class StringVacioException extends  Exception {

    public String mensaje;

    public StringVacioException(String mensaje){this.mensaje =mensaje;}

    @Override
    public  String getMessage(){return mensaje;}




}
