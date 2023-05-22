package Practica_evaluacion.excepcion;

public class Archivo_no_encontrado_Exception extends Exception{
    public String mensaje;

    public Archivo_no_encontrado_Exception(String mensaje){this.mensaje =mensaje;}

    @Override
    public  String getMessage(){return mensaje;}
}
