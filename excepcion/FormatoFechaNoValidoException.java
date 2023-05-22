package Practica_evaluacion.excepcion;

public class FormatoFechaNoValidoException extends Exception{
    public String mensaje;

    public FormatoFechaNoValidoException(String mensaje){this.mensaje =mensaje;}

    @Override
    public  String getMessage(){return mensaje;}
}
