package Practica_evaluacion.test;

import Practica_evaluacion.Utils.Validaciones;
import Practica_evaluacion.excepcion.*;

import static org.junit.jupiter.api.Assertions.*;

class ValidacionesTest {

    @org.junit.jupiter.api.Test
    void validación_tarjeta() {
        assertTrue(Validaciones.validación_tarjeta("4015394992567893"));
        assertTrue(Validaciones.validación_tarjeta(""));
        assertFalse(Validaciones.validación_tarjeta("213"));
    }

    @org.junit.jupiter.api.Test
    void solo_numero() {
        assertTrue(Validaciones.solo_numero("5"));
        assertFalse(Validaciones.solo_numero("h"));
        assertTrue(Validaciones.solo_numero(""));
        assertFalse(Validaciones.solo_numero("@"));

    }

    @org.junit.jupiter.api.Test
    void primera_letra() {
        assertThrows(StringVacioException.class, () -> {
            Validaciones.primera_letra("Pipo Pope Pipas Parque");
            Validaciones.primera_letra("Pipo Pope PipasParque");
            Validaciones.primera_letra("PipoPopePipasParque");
            Validaciones.primera_letra("Pipo");
            Validaciones.primera_letra("  e  ");
            Validaciones.primera_letra("@");
        });

    }

    @org.junit.jupiter.api.Test
    void fechaCorrecta() {
        assertThrows(FormatoFechaNoValidoException.class,()->{
           Validaciones.fechaCorrecta("19-12-1999");
            Validaciones.fechaCorrecta("19/12/1999");
            Validaciones.fechaCorrecta("00-00-00");
            Validaciones.fechaCorrecta("00/00/00");
            Validaciones.fechaCorrecta("04-10-2015");
            Validaciones.fechaCorrecta("04/10/2015");
            Validaciones.fechaCorrecta("04-10-2033");
            Validaciones.fechaCorrecta("04/10/2033");
            Validaciones.fechaCorrecta("-04-10-2015");
            Validaciones.fechaCorrecta("-04/10/2015");
        });
    }

    @org.junit.jupiter.api.Test
    void comprobar_fecha_entrada_salida() {
        assertTrue(Validaciones.comprobar_fecha_entrada_salida("01-01-2023","02-02-2023"));
        assertFalse(Validaciones.comprobar_fecha_entrada_salida("01-01-2004","02-02-2004"));
        assertFalse(Validaciones.comprobar_fecha_entrada_salida("0","0"));
        assertFalse(Validaciones.comprobar_fecha_entrada_salida("0","0"));

    }

    @org.junit.jupiter.api.Test
    void nombrecorrecto() {
        assertThrows(NombreNoValidoException.class,()->{
            Validaciones.nombrecorrecto("sergio",false);
            Validaciones.nombrecorrecto("6",false);
            Validaciones.nombrecorrecto("a@a",false);
            Validaciones.nombrecorrecto("@",true);

        });
    }
    @org.junit.jupiter.api.Test
    void fecha_correcta(){
        assertThrows(FormatoFechaNoValidoException.class,()->{
            Validaciones.fechaCorrecta("08/01/2004");
            Validaciones.fechaCorrecta("08-01-2004");
            Validaciones.fechaCorrecta("08/01/2''4");
            Validaciones.fechaCorrecta("a");
            Validaciones.fechaCorrecta("08/01-2''4");
        });
    }


    @org.junit.jupiter.api.Test
    void emailcorrecto() {
        assertThrows(EmailInvalidoException.class,()->{
            Validaciones.emailcorrecto("a@a.es");
            Validaciones.emailcorrecto("a@.es");
            Validaciones.emailcorrecto("@a.es");
            Validaciones.emailcorrecto("@.es");
            Validaciones.emailcorrecto("a@a.mes");
            Validaciones.emailcorrecto("a@e@a.es");
            Validaciones.emailcorrecto("a @a.es");
            Validaciones.emailcorrecto("a@");
        });
    }

    @org.junit.jupiter.api.Test
    void numerocorrecto() {
        assertThrows(NumeroInvalidoException.class,()->{
            Validaciones.numerocorrecto("694493951");
            Validaciones.numerocorrecto("a");
            Validaciones.numerocorrecto("1");
            Validaciones.numerocorrecto("@");
            Validaciones.numerocorrecto("");
            Validaciones.numerocorrecto("123456789");
            Validaciones.numerocorrecto("123456 578");
        });

    }

}