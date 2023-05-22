package Practica_evaluacion.excepcion;

public class Formato_no_valido_Exception  extends Exception{


        public String mensaje;

        public Formato_no_valido_Exception(String mensaje){this.mensaje =mensaje;}

        @Override
        public  String getMessage(){return mensaje;}
    }
