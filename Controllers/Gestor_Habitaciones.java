package Practica_evaluacion.Controllers;

import Practica_evaluacion.Utils.Validaciones;
import Practica_evaluacion.excepcion.*;
import Practica_evaluacion.models.Habitacion;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Gestor_Habitaciones {
    private static ArrayList<Habitacion> habitaciones = new ArrayList<>();

    public Gestor_Habitaciones() {}

    public Gestor_Habitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    /**
     * Constructor para cargar la base de datos en el arraylist de habitaciones que se le pasa como parametro
     * @param habitaciones
     * @param cargarBD
     * @throws IOException
     * @throws Campos_no_válidos_Exception
     */
    public Gestor_Habitaciones(ArrayList<Habitacion> habitaciones, boolean cargarBD) throws IOException, Campos_no_válidos_Exception {
        this.habitaciones = habitaciones;
        subir_archivo();
    }
    public ArrayList<Habitacion> getHabitaciones() {
        return habitaciones;
    }
    public void setHabitaciones(ArrayList<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    /**
     * Muestra las habitaciones que contiene el atributo habitaciones
     */
    public void mostrarHabitaciones(){
        for(Habitacion habitacion : habitaciones){
            System.out.println(habitacion.toString());
        }
    }

    /**
     * Agrega una habitación a la lista de habitaciones
     * @param habitacion
     */
    public static void agregarHabitacion(Habitacion habitacion) {
        habitaciones.add(habitacion);
    }

    /**
     * Carga el fichero "habitaciones.dat", donde se encuentran los registros de las habitaciones, a la lista de habitaciones
     * @throws Archivo_no_encontrado_Exception
     * @throws IOException
     * @throws Practica_evaluacion.excepcion.Campos_no_válidos_Exception
     */
    private void subir_archivo() throws IOException, Campos_no_válidos_Exception {
        FileReader fr = new FileReader("data/habitaciones");
        BufferedReader br = new BufferedReader(fr);

        String linea;
        String[] registro;

        // Reiniciar la lista de habitaciones antes de cargar los datos
        Gestor_Habitaciones.habitaciones = new ArrayList<>();

        while((linea = br.readLine()) != null) {
            registro = linea.split(",");
            int id, num_camas, max_personas;
            String nombre, descripcion;
            double precio;
            id = Integer.parseInt(registro[0]);
            nombre = registro[1];
            descripcion = registro[2];
            try {
                num_camas = Integer.parseInt(registro[3]);
                max_personas = Integer.parseInt(registro[4]);
                precio = Double.parseDouble(registro[5]);
            } catch(NumberFormatException e) {
                throw new Campos_no_válidos_Exception("Existen campos incorrectos en el registro");
            }
            boolean disponible = Boolean.parseBoolean(registro[6]);

            Habitacion habitacion = new Habitacion(id, nombre, descripcion, num_camas, max_personas, precio, disponible);
            Gestor_Habitaciones.agregarHabitacion(habitacion);
        }

        br.close();
        fr.close();
    }

    /**
     * Almacena el contenido del arraylist de habitaciones en la BD
     * @throws IOException
     * @throws ArrayHabitacionesVacioException
     */
    public static void guardarHabitacionesEnArchivo() throws IOException, ArrayHabitacionesVacioException {
        FileWriter fw = new FileWriter("data/habitaciones");

        if (habitaciones.size() > 0) {
            for (Habitacion h : habitaciones) {
                fw.write(h.getId() + "," +
                        h.getNombre() + "," +
                        h.getDescripcion() + "," +
                        h.getNum_camas() + "," +
                        h.getMax_personas() + "," +
                        h.getPrecio() + "," +
                        h.isOcupada() + "\n");
            }
        } else {
            throw new ArrayHabitacionesVacioException("No se puede guardar el listado de habitaciones (no existen habitaciones)");
        }

        fw.close();
    }

    public static void eliminarHabitacion(String nombreHabitacion) {
        Gestor_Habitaciones gh = new Gestor_Habitaciones();

        for(int i=0; i<gh.getHabitaciones().size(); i++){
            if (gh.getHabitaciones().get(i).getNombre().equals(nombreHabitacion)) {
                gh.getHabitaciones().remove(i);
            }
        }
        cargarHabitacionesDesdeArchivo();
    }

    public static Habitacion crearHabitacion() {
        int id = 0;
        String nombre = "";
        String descripcion = "";
        int numCamas = 0;
        int maxPersonas = 0;
        double precio = 0.0;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Ingrese el ID de la habitación:");
            id = scanner.nextInt();
        } while (id <= 0);


        do {
            System.out.println("Ingrese el nombre de la habitación:");
            nombre = scanner.nextLine();
        } while (nombre.isEmpty());

        do {
            System.out.println("Ingrese la descripción de la habitación:");
            descripcion = scanner.nextLine();
        } while (descripcion.isEmpty());

        do {
            System.out.println("Ingrese el número de camas de la habitación:");
            numCamas = scanner.nextInt();
        } while (numCamas <= 0);

        do {
            System.out.println("Ingrese el máximo de personas para la habitación:");
            maxPersonas = scanner.nextInt();
        } while (maxPersonas <= 0);

        do {
            System.out.println("Ingrese el precio de la habitación:");
            precio = scanner.nextDouble();
        } while (precio <= 0.0);

        Habitacion nuevaHabitacion = new Habitacion(id, nombre, descripcion, numCamas, maxPersonas, precio);

        // Agregar la nueva habitación a la lista de habitaciones
        agregarHabitacion(nuevaHabitacion);

        try {
            guardarHabitacionesEnArchivo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayHabitacionesVacioException e) {
            throw new RuntimeException(e);
        }

        return nuevaHabitacion;
    }

    public static void menu_habitacion() throws NumeroInvalidoException {
        cargarHabitacionesDesdeArchivo();
        String nombre;
        String nuevoNombre;
        String nuevaDescripcion;
        String nuevoNumCamas;
        String nuevoMaxPersonas;
        String nuevoPrecio;
        String opcion="";
        char caso = ' ';
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre de la habitación:");
        nombre = scanner.nextLine();


        Habitacion habitacionEncontrada = buscarHabitacionPorNombre(nombre);
        if (habitacionEncontrada != null) {
            System.out.println("Habitación encontrada:");
            System.out.println("ID: " + habitacionEncontrada.getId());
            System.out.println("Nombre: " + habitacionEncontrada.getNombre());
            System.out.println("Descripción: " + habitacionEncontrada.getDescripcion());
            System.out.println("Número de camas: " + habitacionEncontrada.getNum_camas());
            System.out.println("Máximo de personas: " + habitacionEncontrada.getMax_personas());
            System.out.println("Precio: " + habitacionEncontrada.getPrecio());
            System.out.println("-----------------------------");

            // Menú de opciones
            System.out.println("Seleccione una opción:");
            System.out.println("1. Eliminar habitación");
            System.out.println("2. Actualizar habitación");
            System.out.println("3. Agregar habitacion");
            opcion = scanner.nextLine();
            if (opcion.length() == 1)
                caso = opcion.charAt(0);
            else {
                System.out.println("Opción inválida");
                menu_habitacion();
                return;
            }


            switch (caso) {
                case '1':
                    eliminarHabitacion(habitacionEncontrada.getNombre());
                    menu_habitacion();

                    try {
                        Gestor_Clientes.menu_Administrador();
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
                    scanner.nextLine(); // Consumir el salto de línea pendiente después de nextInt()

                    do {
                        System.out.println("Ingrese el nuevo nombre:");
                        nuevoNombre = scanner.nextLine();
                    } while (!Validaciones.noTieneNada(nuevoNombre));

                    do {
                        System.out.println("Ingrese la nueva descripción:");
                        nuevaDescripcion = scanner.nextLine();
                    } while (!Validaciones.noTieneNada(nuevaDescripcion));

                    do {
                        System.out.println("Ingrese el nuevo número de camas:");
                        nuevoNumCamas = scanner.nextLine();
                    } while (!Validaciones.solo_numero(nuevoNumCamas) || !Validaciones.noTieneNada(nuevoNumCamas));
                    int numCamas = Integer.parseInt(nuevoNumCamas);

                    do {
                        System.out.println("Ingrese el nuevo número máximo de personas:");
                        nuevoMaxPersonas = scanner.nextLine();
                    } while (!Validaciones.solo_numero(nuevoMaxPersonas) || !Validaciones.noTieneNada(nuevoMaxPersonas));
                    int maxPersonas = Integer.parseInt(nuevoMaxPersonas);

                    do {
                        System.out.println("Ingrese el nuevo precio:");
                        nuevoPrecio = scanner.nextLine();
                    } while (!Validaciones.solo_numero(nuevoPrecio) || !Validaciones.noTieneNada(nuevoPrecio));
                    int precio = Integer.parseInt(nuevoPrecio);

                    habitacionEncontrada.setNombre(nuevoNombre);
                    habitacionEncontrada.setDescripcion(nuevaDescripcion);
                    habitacionEncontrada.setNum_camas(numCamas);
                    habitacionEncontrada.setMax_personas(maxPersonas);
                    habitacionEncontrada.setPrecio(precio);

                    try {
                        guardarHabitacionesEnArchivo();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ArrayHabitacionesVacioException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;

                case '3':
                    crearHabitacion();
                    break;

            }
        } else {
            System.out.println("No se encontró ninguna habitación con ese nombre.");
            menu_habitacion();
        }
    }
    public static Habitacion buscarHabitacionPorNombre(String nombre) {
        Gestor_Habitaciones gh = new Gestor_Habitaciones();
        cargarHabitacionesDesdeArchivo();

        for (Habitacion habitacion : gh.getHabitaciones()) {
            String habitacionNombre = habitacion.getNombre();
            if (habitacionNombre != null && habitacionNombre.equalsIgnoreCase(nombre)) {
                return habitacion;
            }
        }
        return null; // Si no se encuentra la habitación
    }

    public static void cargarHabitacionesDesdeArchivo() {
        try {
            FileReader fr = new FileReader("data/habitaciones");
            BufferedReader br = new BufferedReader(fr);

            String linea;
            String[] registro;

            // Reiniciar la lista de habitaciones antes de cargar los datos
            habitaciones = new ArrayList<>();

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue; // Saltar líneas vacías
                }

                registro = linea.split(",");
                int id, num_camas, max_personas;
                String nombre, descripcion;
                double precio;
                id = Integer.parseInt(registro[0]);
                nombre = registro[1];
                descripcion = registro[2];

                if (!registro[3].isEmpty())
                    num_camas = Integer.parseInt(registro[3]);
                else
                    num_camas = 0;

                if (!registro[4].isEmpty())
                    max_personas = Integer.parseInt(registro[4]);
                else
                    max_personas = 0;

                precio = Double.parseDouble(registro[5]);
                boolean disponible = Boolean.parseBoolean(registro[6]);

                Habitacion habitacion = new Habitacion(id, nombre, descripcion, num_camas, max_personas, precio, disponible);
                habitaciones.add(habitacion);
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println("No se pudo encontrar el archivo de habitaciones.");
        }
    }


}
