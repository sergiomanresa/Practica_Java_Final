package Practica_evaluacion.Controllers;
/**
 * Clase para los la gestion de los clientes
 *
 * @author Sergio Manresa
 * @version 1.0
 * @since 11/01/2023
 */
import Practica_evaluacion.Main;
import Practica_evaluacion.Utils.Validaciones;
import Practica_evaluacion.excepcion.*;
import Practica_evaluacion.models.Administrador;
import Practica_evaluacion.models.Cliente;
import Practica_evaluacion.models.Habitacion;
import Practica_evaluacion.models.Reservas;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static Practica_evaluacion.models.Administrador.agregarAdministrador;

public class Gestor_Clientes {
    private ArrayList<Cliente> listado_de_clientes = new ArrayList<>();

    public Gestor_Clientes() {
            cargar_fichero();
    }

    public Gestor_Clientes(ArrayList<Cliente> listado_de_clientes){
        listado_de_clientes = listado_de_clientes;
    }

    public void setListado_de_clientes(ArrayList<Cliente> listado_de_clientes) {
        listado_de_clientes = listado_de_clientes;
    }

    public ArrayList<Cliente> getListado_de_clientes() {
        return listado_de_clientes;
    }

    /**
     * Muestra los datos básicos de los clientes que se encuentran en el listado
     */
    public void mostrarCliente(){
        for (Cliente cliente :listado_de_clientes){
            System.out.println(cliente.getNombre());
            System.out.println(cliente.getApellidos());
            System.out.println(cliente.getDni());
        }
    }
    /**
     * Busca un cliente en el listado de clientes
     *
     * @param clienteBuscar
     * @return cliente encontrado
     */
    public boolean buscarCliente(Cliente clienteBuscar){
        for(Cliente cliente : listado_de_clientes){
            if(cliente.getEmail().equals(clienteBuscar.getEmail())){
                System.out.println("Cliente encontrado");
                return true;
            }
        }
        return false;
    }



    /**
     * Agrega un cliente al final del listado de clientes
     *
     * @param cliente
     */
    public void agregarCliente(Cliente cliente){
        listado_de_clientes.add(cliente);
    }

    /**
     * Genera 7 clientes de prueba
     */
    public void generarClientesBase(){
        listado_de_clientes.add( new Cliente("Antonio", "Box Sanchez","antoniob@gmail.com","636439552","89704325H","11/11/2004","PPPP34"));
        listado_de_clientes.add( new Cliente("Jorge", "Carmona Girona", "JorgeJ@gmail.com","626437351","32480956F","10/11/2004","PPPP32"));
        listado_de_clientes.add( new Cliente("Sergio", "Manresa Bernabeu", "serj@gmail.com","97683242","90832424K","12/11/2004","PPPP33"));
        listado_de_clientes.add(new Cliente("Alexis", "Escolano Mora", "AleA@gmail.com","807932488","34267823O","13/11/2004","PPPP35"));
        listado_de_clientes.add(new Cliente("Manuel", "Garcia Santamaria","ManuR@gmail.com","210987123","78634289L","14/11/2004","PPPP38"));
        listado_de_clientes.add(new Cliente("Fulgencio", "Ortuño", "Fg@gmail.com","756123890","08912345U","15/11/2004","PPPP36"));
        listado_de_clientes.add(new Cliente("Jorge", "Pelegrin", "Descapotao@gmail.com","321487945","68712323P","16/11/2004","PPPP00"));

    }

    /**
     * Carga el fichero del cliente en el gestor
     */
    private void cargar_fichero() {
        FileReader fr;
        try {
            fr = new FileReader("C:\\Users\\zancr\\IdeaProjects\\proyecto\\src\\Practica_evaluacion\\data\\clientes");
        } catch (FileNotFoundException f) {
            f.printStackTrace();
            return;
        }
        BufferedReader br = new BufferedReader(fr);
        String linea = "";
        String[] dato_cliente;
        try {
            while ((linea = br.readLine()) != null) {
                dato_cliente = linea.split(";");
                listado_de_clientes.add(new Cliente(dato_cliente[0], dato_cliente[1], dato_cliente[2], dato_cliente[3], dato_cliente[4], dato_cliente[5], dato_cliente[6]));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void registro_clientes() throws FormatoFechaNoValidoException, StringVacioException, IOException, ArrayHabitacionesVacioException, Campos_no_válidos_Exception {
        Scanner scanner=new Scanner(System.in);


        //variables
        String dni="";
        String email = "";
        String control = "";
        String nombre="";
        String apellidos="";
        String telefono="";
        String fechanacimiento="";
        String opcion="";

        System.out.println("**************");
        System.out.println("** Registro **");
        System.out.println("**************");
        System.out.println("Introduce los siguientes datos:");
        System.out.println("_______________________________");

        do {
            System.out.println("DNI:");
            dni=scanner.nextLine();
            dni=dni.toUpperCase();

            try{
               Validaciones.dni(dni);
            }catch (Formato_dni_Exception e){
                System.out.printf(e.getMessage());
                continue;
            }
            break;
        }while (true);


        do {
            System.out.println("Nombre:");
            nombre=scanner.nextLine();
            nombre=nombre.toUpperCase();

            try {
                Validaciones.nombrecorrecto(nombre,false);
            }catch (StringVacioException| NombreNoValidoException e){
                System.out.printf(e.getMessage());
                continue;
            }
            break;

        }while (true);

        do {
            System.out.println("Apellidos:");
            apellidos= scanner.nextLine();
            apellidos=apellidos.toUpperCase();

            try {
                Validaciones.nombrecorrecto(apellidos,true);
            } catch (StringVacioException | NombreNoValidoException e) {
                System.out.printf(e.getMessage());
                continue;
            }
            break;
        }while (true);

        do {
            System.out.println("Email:");
            email= scanner.nextLine();

            try {
                Validaciones.emailcorrecto(email);
            } catch (StringVacioException | EmailInvalidoException e){
                System.out.printf(e.getMessage());
                continue;
            }
            break;
        }while (true);
        do {
            System.out.println("Teléfono:");
            telefono= scanner.nextLine();
            try{
                Validaciones.numerocorrecto(telefono);
            } catch (NumeroInvalidoException e){
                System.out.printf(e.getMessage());
                continue;
            }
            break;
        }while (true);
        do {
            System.out.println("Fecha de nacimiento (dd/mm/aaaa) o (dd-mm-aaaa):");
            fechanacimiento= scanner.nextLine();
            try{
                Validaciones.fechaCorrecta(fechanacimiento);
            }catch (FormatoFechaNoValidoException e){
                System.out.println(e.getMessage());
                continue;
            }
            break;

        }while (true);
        do {
            System.out.println("Frase de control (4 palabras separadas por 1 espacio cada palabra):");
            control = scanner.nextLine();
            control = control.toUpperCase();
            String codigo="";
            try {
                 codigo=Validaciones.primera_letra(control);
            } catch (StringVacioException e) {

                System.out.printf(e.getMessage());
                continue;

            }
            Cliente cliente = new Cliente(nombre, apellidos, email, telefono, dni,fechanacimiento,codigo);
            subir_archivo();
            listado_de_clientes.add(cliente);
            guardarRegistros();



            System.out.println("Para logearte necesitaras el email: "+email+"\n"+"y el codigo: "+Validaciones.primera_letra(control));
            break;
        }while(true);
    }

    public void login_cliente() throws  NumeroInvalidoException {
        String opcion="";

        Cliente cliente = new Cliente();
        char caso = ' ';
        boolean usuarioLogueado=false;
        Scanner sc = new Scanner(System.in);
        Gestor_Clientes gc = new Gestor_Clientes();
        gc.generarClientesBase();


        System.out.println("Dime tu email de usuario:");
        String email_usuario = sc.nextLine();
        System.out.println("Dime tu código:");
        String codigo_usuario= sc.nextLine();

        ArrayList<Cliente> clientes = getListado_de_clientes();

        for(Cliente cliente_ : clientes){
            if(cliente_.getEmail().equals(email_usuario) && cliente_.getCodigoAcceso().equals(codigo_usuario)){
                cliente = cliente_;
            }
        }
        for(Cliente c : clientes){
            if(c.getEmail().equals(email_usuario) && c.getCodigoAcceso().equals(codigo_usuario)){
                System.out.println("Bienvenido, "+c.getNombre());
                usuarioLogueado=true;
                opcion="";
            }
        }
        if(!usuarioLogueado){
            System.out.println("No se ha encontrado ningún usuario que coincida, ¿desea seguir intentándolo?(S/N): ");
            opcion = sc.nextLine();
            if(opcion.equals("S") || opcion.equals("s")){
                login_cliente();
            }
            else{
                opcion="";
                System.out.println("Saliendo...");
            }
        }
		if(usuarioLogueado) {
        String personasReserva = "", fechaEntrada = "", fechaSalida = "";
        String opcionHabitacion = "";
        do {
            System.out.printf("*****Bienvenido a muñon dreams*******\n");
            System.out.printf("\n1. Reserva de habitación");
            System.out.printf("\n2. Atención al cliente");
            System.out.printf("\n3. Salir\n");
            System.out.println("\n Opción en numero: ");
            opcion = sc.nextLine();
            if (opcion.length()==1){
                caso=opcion.charAt(0);
            } else{
                System.out.println("opción invalida");
            }
        }while (caso!= '1' && caso!= '2' && caso!= '3');
            if (caso == '1') {
                System.out.println("***********  RESERVAR HABITACIÓN  ***********");
                boolean todoCorrecto = true;
                do {
                    todoCorrecto = true;
                    System.out.println("¿Para cuántas personas se hace la reserva?");
                    personasReserva = sc.nextLine();
                    if(!Validaciones.solo_numero(personasReserva)){
                        System.out.println("Introduce un número, por favor");
                        todoCorrecto = false;
                    }
                    else if(personasReserva.length() > 2){
                        System.out.println("No se permite ese número de personas, máximo 99 por reserva");
                        todoCorrecto = false;
                    }
                    else if(Integer.parseInt(personasReserva)<=0){
                        System.out.println("La reserva debe de hacerse para un número de personas mayor");
                        todoCorrecto=false;
                    }
                } while (!todoCorrecto);

				 {
					System.out.println("¿Cuál es la fecha de entrada?");
					fechaEntrada = sc.nextLine();
					System.out.println("¿Cuál es la fecha de salida?");
					fechaSalida = sc.nextLine();
				} while (!Validaciones.fechaentrada_salida(fechaEntrada) || !Validaciones.fechaentrada_salida(fechaSalida));
				int personasTotalReserva = Integer.parseInt(personasReserva);
                int opcionHabitaciones = 0;
                ArrayList<Habitacion> habitaciones = new ArrayList<>();
                habitaciones.addAll(Habitacion.generarHabitacionesBase());
                ArrayList<Habitacion> habitacionesRepetidas = new ArrayList<>();
                HashMap<Integer, String> opcionesHabitacion = new HashMap<>();
                //nOpciones determina el número de habitaciones que se muestran,
                //para saber si el usuario puede seleccionar alguna
                boolean seguirBuscando = true;
                int nOpciones = 0;
                do{
                    nOpciones=0;
                    for (Habitacion h : habitaciones) {
                        if (h.getMax_personas() == personasTotalReserva && h.comprobarDisponibilidadHabitacion(fechaEntrada + ":" + fechaSalida)) {
                            String precios = "";
                            System.out.println("* Opcion " + (++opcionHabitaciones));
                            System.out.println("Habitación " + h.getNombre() + " para " + h.getMax_personas());
                            nOpciones++;
                            precios += h.getPrecio();
                            System.out.println("Precio final: " + precios);
                            opcionesHabitacion.put(opcionHabitaciones, "" + h.getId());
                        }
                    }
                    for (Habitacion h : habitaciones) {
                        String precios = "";
                        habitacionesRepetidas.add(h);
                        for (Habitacion h_ : habitaciones) {
                            boolean habitacionRepetida = false;
                            for (Habitacion hr : habitacionesRepetidas) {
                                if (h_.equals(hr)) habitacionRepetida = true;
                            }
                            if (!habitacionRepetida) {
                                if (h.getMax_personas() + h_.getMax_personas() == personasTotalReserva) {
                                    System.out.println("* Opcion " + (++opcionHabitaciones));
                                    System.out.println("Habitación " + h.getNombre() + " para " + h.getMax_personas());
                                    System.out.println("Habitación " + h_.getNombre() + " para " + h_.getMax_personas());
                                    nOpciones++;
                                    precios += h.getPrecio()+ h_.getPrecio();
                                    System.out.println("Precio final: " + precios);
                                    opcionesHabitacion.put(opcionHabitaciones, "" + h.getId() + "," + h_.getId());
                                }
                            }
                        }
                    }
                    if(nOpciones==0){
                        System.out.println("No se han encontrado habitaciones disponibles para esos criterios");
                        System.out.println("¿Desea seguir intentando?");

                        seguirBuscando = sc.nextLine().equals("S") || sc.nextLine().equals("s");

                    }
                }while(nOpciones==0 && seguirBuscando);
                boolean habitacionCorrecta = false;
                if(nOpciones>0){
                    do {
                        System.out.println("¿Qué opción desea?");
                        opcionHabitacion = sc.nextLine();
                        if(Validaciones.solo_numero(opcionHabitacion)){
                            for(int opcion_ : opcionesHabitacion.keySet()){
                                if(opcion_==Integer.parseInt(opcionHabitacion)){
                                    System.out.println("Habitaciones a la espera del pago...");
                                    habitacionCorrecta = true;
                                }
                                else{
                                    habitacionCorrecta = false;
                                }
                            }
                            opcionHabitacion = habitacionCorrecta ? opcionHabitacion : "ERROR";
                        }
                        else{
                            opcionHabitacion="ERROR";
                        }
                    }while(opcionHabitacion.equals("ERROR"));
                }
                boolean pagoRealizado = false;
                if(habitacionCorrecta){
                    String metodoPago = "";
                    System.out.println("Elige un método de pago\n" +
                            "1.Tarjeta de crédito\n" +
                            "2.Bizum");
                    metodoPago = sc.nextLine();
                    if(metodoPago.equals("1")){
                        String tarjetaCredito = "";
                        do{
                            System.out.println("Introduce el número de tarjeta de crédito");
                            tarjetaCredito = sc.nextLine();
                        }while(!Validaciones.validación_tarjeta(tarjetaCredito));
                        String visa = "415006";
                        String americanExpress = "375699";
                        String masterCard = " 515878";
                        if(tarjetaCredito.substring(0, 6).equals(visa)){
                            System.out.println("Tienes una VISA");
                            pagoRealizado=true;
                        }
                        else if(tarjetaCredito.substring(0, 6).equals(americanExpress)){
                            System.out.println("Tienes una American Express");
                            pagoRealizado=true;
                        }
                        else if(tarjetaCredito.substring(0, 6).equals(masterCard)){
                            System.out.println("Tienes una MasterCard");
                            pagoRealizado=true;
                        }
                        else{
                            System.out.println("No se ha detectado el tipo de tu tarjeta de crédito");
                        }
                    }
                    else if(metodoPago.equals("2")){
                        String bizum = "";
                        System.out.println("Bizum");
                        System.out.println("Introduce tu número de teléfono");
                        bizum = sc.nextLine();
						if(Validaciones.numerocorrecto(bizum)){
							System.out.println("Pago realizado");
							pagoRealizado=true;
						}else{
							System.out.println("Número inválida");
						}
                    }
                }
                if(pagoRealizado){
                    Reservas reserva = new Reservas((int)(Math.random()*1000+1), cliente.getDni(), Habitacion.getIdsListado(opcionesHabitacion.get(Integer.parseInt(opcionHabitacion))), fechaEntrada, fechaSalida);
                    reserva.escribirEnArchivo(reserva.formatearObjeto());


                    cliente.infoBasica();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDateTime now = LocalDateTime.now();
                    System.out.println("Fecha de facturación: "+dtf.format(now));
                    double precioTotalReserva = 0;
                    for(int x : Reservas.getId_habitacion()){
                        for(Habitacion h : habitaciones){
                            if(h.getId()==x){
                                System.out.println("Habitacion "+h.getNombre()+" para "+h.getMax_personas());
                                h.setFechasOcupadas(fechaEntrada+":"+fechaSalida);
                                precioTotalReserva+=h.getPrecio();
                            }
                        }
                    }
                    System.out.println("Precio total: "+(precioTotalReserva+precioTotalReserva*0.21));
                }
                System.out.println("quieres hacer alguna pregunta?:(S/N)");
                opcion = sc.nextLine();
                if (opcion.equals("S")){
                    Preguntas();

                }
                else;

            }
    }}



    public void Preguntas(){

        Scanner scanner=new Scanner(System.in);
        String control = "";
        String opcion="";
        String pregunta="";

        System.out.println("***** PREGUNTAS FRECUENTES *****");
        ArrayList<String> preguntas = new ArrayList<String>();
        ArrayList<String>respuestas = new ArrayList<String>();
        preguntas.add(" Salir");
        preguntas.add(" Agregar preguntas");
        preguntas.add(" ¿Cómo puedo llamar fuera del hotel con el teléfono de la habitación?");
        preguntas.add(" ¿Es gratis la comida de la nevera de la habitation?");
        respuestas.add("pulsando el * puedes llamar");
        respuestas.add("No no es gratis");
        for (int i=0; i<preguntas.size(); i++) {
            System.out.println(i + ". "+preguntas.get(i));
        }
        do {
            System.out.println("Opción en número: ");
            opcion=scanner.nextLine();
        }while (!opcion.equals("0") && !opcion.equals("1") && !opcion.equals("2")&& !opcion.equals("3"));

        if (!Validaciones.solo_numero(opcion)|| Validaciones.noTieneNada(opcion)){
            System.out.println("Solo números");
        }
        else{
            if (opcion.equals("1")){
                System.out.println("Dime la pregunta: ");
                pregunta=scanner.nextLine();
                if (!Validaciones.noTieneNada(pregunta)){
                    preguntas.add(pregunta);
                    System.out.println("El administrador a recibido la pregunta espere la posible respuesta");
                }else{
                    System.out.println("no dejes en blanco la pregunta");
                }
            }
            else if(Integer.parseInt(opcion)!=0) {
                System.out.println(respuestas.get(Integer.parseInt(opcion)-2));
            }
        }
    }

    private void subir_archivo() throws IOException, Campos_no_válidos_Exception {
        //todo cambiar la ruta
        FileReader fr = new FileReader("C:\\Users\\zancr\\IdeaProjects\\proyecto\\src\\Practica_evaluacion\\data\\clientes");
        BufferedReader br = new BufferedReader(fr);

        String linea;
        String[] registro;

        while((linea=br.readLine())!=null){
            registro = linea.split(";");
            String dni="";
            String email = "";
            String control = "";
            String nombre="";
            String apellidos="";
            String telefono="";
            String fechanacimiento="";
            listado_de_clientes.add(new Cliente(nombre,apellidos,email,telefono,dni,fechanacimiento,control));


        }
    }

    /**
     * Almacena el contenido del arraylist de habitaciones en la BD
     * @throws IOException
     * @throws ArrayHabitacionesVacioException
     */
    public void guardarRegistros() throws IOException, ArrayHabitacionesVacioException{
        //todo cambiar la ruta
        FileWriter fw = new FileWriter("C:\\Users\\zancr\\IdeaProjects\\proyecto\\src\\Practica_evaluacion\\data\\clientes", false);
        if(listado_de_clientes.size() > 0){
            for(Cliente c : listado_de_clientes){
                fw.write(c.formatearObjeto());
            }
        } else{
            throw new ArrayHabitacionesVacioException("No se puede guardar el listado de habitaciones (no existen habitaciones)");
        }
        fw.close();
    }
    public void registrarAdministrador(List<Administrador> listadoAdministradores) throws EmailInvalidoException, StringVacioException {
        String password="";
        String email="";
        String nombreUsuario="";
        Scanner scanner = new Scanner(System.in);
        System.out.println("------ REGISTRAR ADMINISTRADOR ------");
        do {

            System.out.print("Nombre de usuario: ");
            nombreUsuario = scanner.nextLine();
            try {
                Validaciones.nombrecorrecto(nombreUsuario,false);
            }catch (StringVacioException| NombreNoValidoException e){
                System.out.printf(e.getMessage());
                continue;
            }
            break;
        }while (true);

        do {
            System.out.print("La contraseña debe contener al menos una letra mayúscula, una letra minúscula y un dígito (logitud (8-20)): ");
             password = scanner.nextLine();
        } while (!Validaciones.validarContrasena(password));

        do {
            System.out.print("Correo electrónico: ");
             email = scanner.nextLine();
             try{
                 Validaciones.emailcorrecto(email);
             }catch (EmailInvalidoException e){
                 System.out.println(e.getMessage());
                 continue;
             }
                break;
        }while (true);




        Administrador nuevoAdministrador = new Administrador(nombreUsuario, password, email, true);
        agregarAdministrador(nuevoAdministrador, listadoAdministradores);
        nuevoAdministrador.guardarAdministradoresEnArchivo(listadoAdministradores);

        System.out.println("Administrador registrado con éxito.");
    }







}



