package Practica_evaluacion.excepcion;

public class Formato_dni_Exception extends Exception{

        public String mensaje;

    public Formato_dni_Exception(String mensaje){this.mensaje =mensaje;}

        @Override
        public  String getMessage(){return mensaje;}

}
