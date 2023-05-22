package Practica_evaluacion.excepcion;

public class EmailInvalidoException extends Exception{

    public String mensaje;

    public EmailInvalidoException(String mensaje){this.mensaje =mensaje;}

    @Override
    public  String getMessage(){return mensaje;}
}
