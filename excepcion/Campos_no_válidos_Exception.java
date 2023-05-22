package Practica_evaluacion.excepcion;

public class Campos_no_válidos_Exception extends Exception {

    public String mensaje;

    public Campos_no_válidos_Exception(String mensaje){this.mensaje =mensaje;}

    @Override
    public  String getMessage(){return mensaje;}
}
