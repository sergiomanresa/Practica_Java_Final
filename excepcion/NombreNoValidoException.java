package Practica_evaluacion.excepcion;

public class NombreNoValidoException extends Exception{

    public String mensaje;

    public NombreNoValidoException(String mensaje){
        this.mensaje=mensaje;
    }

    @Override
    public  String getMessage(){return mensaje;}
}

