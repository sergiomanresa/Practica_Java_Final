package Practica_evaluacion.excepcion;

public class NumeroInvalidoException extends Exception {

    public String mensaje;

    public NumeroInvalidoException(String mensaje){
        this.mensaje=mensaje;
    }

    @Override
    public  String getMessage(){return mensaje;}
}
