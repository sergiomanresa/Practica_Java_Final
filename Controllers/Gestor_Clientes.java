package Practica_evaluacion.Controllers;
/**
 * Clase para los la gestion de los clientes
 *
 * @author Sergio Manresa
 * @version 1.0
 * @since 11/01/2023
 */

import Practica_evaluacion.Utils.Validaciones;
import Practica_evaluacion.excepcion.*;
import Practica_evaluacion.models.Administrador;
import Practica_evaluacion.models.Cliente;
import Practica_evaluacion.models.Habitacion;
import Practica_evaluacion.models.Reservas;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static Practica_evaluacion.Main.mostrarMenu;
import static Practica_evaluacion.models.Administrador.agregarAdministrador;
import static Practica_evaluacion.models.Habitacion.menu_habitacion;
import static Practica_evaluacion.models.Reservas.buscarReservaPorCodigo;
import static java.lang.System.exit;

public class Gestor_Clientes{

    private ArrayList<Cliente> listado_de_clientes = new ArrayList<>();
    ArrayList<Habitacion> habitaciones = new ArrayList<>();

    public Gestor_Clientes() {

    }

    public Gestor_Clientes(ArrayList<Cliente> listado_de_clientes){
        this.listado_de_clientes = listado_de_clientes;
    }

    public void setListado_de_clientes(ArrayList<Cliente> listado_de_clientes) {
        this.listado_de_clientes = listado_de_clientes;
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
    public boolean buscarCliente(Cliente clienteBuscar) throws IOException, Campos_no_válidos_Exception {
        cargar_archivo();
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

    public void guardarClientesEnArchivo( ArrayList<Cliente> listado_de_clientes) {
        try (FileWriter writer = new FileWriter("data/Cliente")) {
            for (Cliente cliente : listado_de_clientes) {
                String linea = cliente.getNombre() + "," + cliente.getApellidos() + "," + cliente.getEmail() + "," + cliente.getTelefono() + ","+ cliente.getDni()+","+cliente.getFechaNacimiento()+","+ cliente.getCodigoAcceso();
                writer.write(linea);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los administradores en el archivo.");
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
                cliente.formatearObjeto();
                if (!buscarCliente(cliente)) {
                    listado_de_clientes.add(cliente);
                    guardarClientesEnArchivo(listado_de_clientes);
                    System.out.println("Para logearte necesitaras el email: " + email + "\n" + "y el codigo: " + Validaciones.primera_letra(control));
                }
                else{
                    System.out.println("Usuario ya existente ,quieres volver a intentarlo?: ");
                    do {
                        System.out.println("Desea registrarte?(S/N)");
                        opcion= scanner.nextLine();
                        opcion=opcion.toUpperCase();
                    }while (!opcion.equals("S")&& !opcion.equals("N"));
                    if(opcion.equals("S")){
                        registro_clientes();
                    }
                }
                break;
        }while(true);
    }
    public void login_cliente() throws NumeroInvalidoException, IOException, Campos_no_válidos_Exception {
        String opcion = "";
        Cliente cliente = new Cliente();
        char caso = ' ';
        boolean usuarioLogueado = false;
        Scanner sc = new Scanner(System.in);
        System.out.println("------- Login clientes -------");
        System.out.println("Dime tu email de usuario:");
        String email_usuario = sc.nextLine();
        System.out.println("Dime tu código:");
        String codigo_usuario = sc.nextLine();
        cargar_archivo();

        for (Cliente cliente_ : listado_de_clientes) {
            if (email_usuario.equals(cliente_.getEmail()) && codigo_usuario.equals(cliente_.getCodigoAcceso())) {
                usuarioLogueado = true;
                break;
            }
        }

        if (usuarioLogueado) {
            System.out.println("Bienvenido");
        } else {
            System.out.println("No se ha encontrado ningún usuario que coincida.");
            System.out.println("¿Desea seguir intentándolo? (S/N):");
            opcion = sc.nextLine();
            if (opcion.equalsIgnoreCase("S")) {
                login_cliente();
            } else {
                System.out.println("Saliendo...");
                return;
            }
        }
        if(usuarioLogueado) {
        String personasReserva = "", fechaEntrada = "", fechaSalida = "";
        String opcionHabitacion = ""; caso = ' ';
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

				 do {
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

    private void cargar_archivo() throws IOException, Campos_no_válidos_Exception {
        FileReader fr = new FileReader("data/Cliente");
        BufferedReader br = new BufferedReader(fr);

        String linea;
        String[] registro;

        while((linea=br.readLine())!=null){
            registro = linea.split(",");
            if (registro.length==7){
                String nombre=registro[0];
                String apellidos=registro[1];
                String email = registro[2];
                String telefono=registro[3];
                String dni=registro[4];
                String fechanacimiento=registro[5];
                String control = registro[6];
                listado_de_clientes.add(new Cliente(nombre,apellidos,email,telefono,dni,fechanacimiento,control));
            }


        }
    }

    /**
     * Almacena el contenido del arraylist de habitaciones en la BD
     * @throws IOException
     * @throws ArrayHabitacionesVacioException
     */
    public void guardarRegistros() throws IOException, ArrayHabitacionesVacioException{
        FileWriter fw = new FileWriter("data/Cliente", false);
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
        String opcion="";
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
        System.out.println("Quieres acceder a la gestion de los clientes(S/N)");
        opcion=scanner.nextLine();
        opcion=opcion.toUpperCase();
        if (opcion.equals("S")){
            Crud_administrador();
        }
    }
    public void menu_Administrador() throws StringVacioException, FormatoFechaNoValidoException, IOException, ArrayHabitacionesVacioException, Campos_no_válidos_Exception {
        Scanner sc = new Scanner(System.in);
        String opcion="";
        char caso = ' ';
        System.out.println("------ Menu de gestión de clientes ------");
        System.out.println("1. Agregar cliente");
        System.out.println("2. Consultar cliente");
        System.out.println("3. Actualizar cliente");
        System.out.println("4. Eliminar cliente");
        System.out.println("5. Regresar al menú anterior");
        System.out.println("Elige una option:");
        opcion = sc.nextLine();
        if (opcion.length() == 1)
            caso = opcion.charAt(0);
        else {
            System.out.println("Opción inválida");
        }
        switch (caso){
            case '1':
                registro_clientes();
                menu_Administrador();
                break;
            case '2':
                buscar_cliente(false);
                menu_Administrador();
                break;
            case '3':
                buscar_cliente(true);
                menu_Administrador();
                break;
            case '4':
                eliminar_cliente();
                menu_Administrador();
                break;
            case '5':
                Crud_administrador();
                break;
            default:
                menu_Administrador();
        }

    }

    /**
     * Metodo para imprimir los datos de un cliente
     * El boolean sirve para permitir actualizar un cliente (true) o solo visualizarlo (false)
     * @param editar
     */
    public void buscar_cliente(boolean editar) throws IOException, Campos_no_válidos_Exception {
        File file = new File("data/Cliente");
        String email="";
        String codigo="";
        Scanner scanner=new Scanner(System.in);
        System.out.println("------ Menu de búsqueda de clientes ------");
        System.out.println("Dime el email:");
        do {
            System.out.println("email:");
            email=scanner.nextLine();

            try{
                Validaciones.emailcorrecto(email);
            }catch (EmailInvalidoException | StringVacioException e){
                System.out.printf(e.getMessage());
                continue;
            }
            break;
        }while (true);

        System.out.println("Dime el codigo de acceso:");
        codigo=scanner.nextLine();
        cargar_archivo();

        for (int i = 0; i < listado_de_clientes.size(); i++) {
            Cliente c =listado_de_clientes.get(i);
            if(c.getEmail().equals(email) && c.getCodigoAcceso().equals(codigo)){
                if (!editar) {
                    System.out.println("------ Datos de "+c.getNombre()+ "------");
                    System.out.println("Usuario:"+c.getNombre());
                    System.out.println("apellidos:"+c.getApellidos());
                    System.out.println("email:"+c.getEmail());
                    System.out.println("telefono:"+c.getTelefono());
                    System.out.println("dni:"+c.getDni());
                    System.out.println("fecha de nacimiento:"+c.getFechaNacimiento());
                    System.out.println("codigo de acceso:"+c.getCodigoAcceso());
                }else{
                    //variables
                    String dni="";
                    String control = "";
                    String nombre="";
                    String apellidos="";
                    String telefono="";
                    String fechanacimiento="";

                    System.out.println("------ Menu de edición de clientes ------");
                    do {
                        System.out.println("Nombre:");
                        nombre=scanner.nextLine();
                        nombre=nombre.toUpperCase();

                        try {
                            Validaciones.nombrecorrecto(nombre,false);
                            c.setNombre(nombre);
                        }catch (StringVacioException| NombreNoValidoException e){
                            System.out.println(e.getMessage());
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
                            c.setApellidos(apellidos);
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
                            c.setEmail(email);
                        } catch (StringVacioException | EmailInvalidoException e){
                            System.out.println(e.getMessage());
                            continue;
                        }
                        break;
                    }while (true);
                    do {
                        System.out.println("Teléfono:");
                        telefono= scanner.nextLine();
                        try{
                            Validaciones.numerocorrecto(telefono);
                            c.setTelefono(telefono);
                        } catch (NumeroInvalidoException e){
                            System.out.println(e.getMessage());
                            continue;
                        }
                        break;
                    }while (true);
                    do {
                        System.out.println("Frase de control (4 palabras separadas por 1 espacio cada palabra):");
                        control = scanner.nextLine();
                        try {
                            codigo=Validaciones.primera_letra(control);
                            c.setCodigoAcceso(codigo);
                        } catch (StringVacioException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        break;
                    }while (true);
                    guardarClientesEnArchivo(listado_de_clientes);
                }
                try {
                    menu_Administrador();
                } catch (StringVacioException e) {
                    throw new RuntimeException(e);
                } catch (FormatoFechaNoValidoException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ArrayHabitacionesVacioException e) {
                    throw new RuntimeException(e);
                } catch (Campos_no_válidos_Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public void eliminar_cliente() throws IOException, Campos_no_válidos_Exception {
        cargar_archivo();
        Scanner scanner = new Scanner(System.in);
        File file = new File("data/Cliente");

        String email = "";
        String codigo = "";
        String opcion = "";
        boolean usuario_existente = false;

        System.out.print("Ingrese el email: ");
        do {
            email = scanner.nextLine();

            try {
                Validaciones.emailcorrecto(email);
            } catch (EmailInvalidoException | StringVacioException e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        } while (true);

        System.out.print("Ingrese el código de acceso: ");
        codigo = scanner.nextLine();

        ArrayList<Cliente> clientes = getListado_de_clientes();
        Iterator<Cliente> iterator = clientes.iterator();
        while (iterator.hasNext()) {
            Cliente c = iterator.next();
            if (c.getEmail().equals(email) && c.getCodigoAcceso().equals(codigo)) {
                System.out.println("Adiós, " + c.getNombre());
                iterator.remove(); // Eliminar el cliente de la lista
                usuario_existente = true;
                break;
            }
        }
        if (!usuario_existente) {
            System.out.print("No se ha encontrado ningún usuario que coincida, ¿desea seguir intentándolo? (S/N): ");
            opcion = scanner.nextLine();
            if (opcion.equalsIgnoreCase("S")) {
                eliminar_cliente();
            } else {
                System.out.println("Saliendo...");
                return;
            }
        }

        // Guardar la lista actualizada de clientes en el archivo
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(clientes);
            System.out.println("Cambios guardados en el archivo.");
        } catch (IOException e) {
            System.out.println("Error al guardar los cambios en el archivo: " + e.getMessage());
        }
    }


    public void login_administrador() throws StringVacioException, FormatoFechaNoValidoException, IOException, ArrayHabitacionesVacioException, Campos_no_válidos_Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-------Login administrador------- ");

        System.out.print("Ingrese su email: ");
        String email = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        String contraseña = scanner.nextLine();

        boolean inicioSesionExitoso = Administrador.verificarInicioSesion(email, contraseña);

        if (inicioSesionExitoso) {
            System.out.println("Inicio de sesión exitoso. ¡Bienvenido!");
            Crud_administrador();
        } else {
            System.out.println("Credenciales inválidas. Inicio de sesión fallido.");
            login_administrador();
        }
    }
    public void Crud_administrador(){
        char caso = ' ';
        String opcion="";
        Scanner sc =new Scanner(System.in);
        System.out.println("-------Menu crud administrador-------");
        System.out.println("1. Gestion clientes");
        System.out.println("2. Gestionar reservas");
        System.out.println("3. Gestion habitaciones");
        System.out.println("4. Volver al menu principal");
        System.out.println("0. Salir de la aplicación");
        opcion = sc.nextLine();
        if (opcion.length() == 1)
            caso = opcion.charAt(0);
        else {
            System.out.println("Opción inválida");
            Crud_administrador();
            return;
        }
        switch (caso){
            case '1':
                try {
                    menu_Administrador();
                } catch (StringVacioException e) {
                    throw new RuntimeException(e);
                } catch (FormatoFechaNoValidoException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ArrayHabitacionesVacioException e) {
                    throw new RuntimeException(e);
                } catch (Campos_no_válidos_Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case '2':
                buscarReservaPorCodigo();
                break;

            case '3':
                try {
                    menu_habitacion(habitaciones);
                } catch (NumeroInvalidoException e) {
                    throw new RuntimeException(e);
                }
                break;
            case '4':
                mostrarMenu(sc);
                break;
            case '5':
                System.out.println("Saliendo...");
                exit(0);
                break;
            default:
                System.out.println("Opción inválida");
                Crud_administrador();
                return;
        }
    }
    }