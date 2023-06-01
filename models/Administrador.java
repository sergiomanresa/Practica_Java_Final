package Practica_evaluacion.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Administrador extends Usuario {
    private boolean activo;

    public Administrador(String nombreUsuario, String Paasword, String email, boolean activo) {
        super(nombreUsuario, Paasword, email, true);
        this.activo = activo;
    }

    public Administrador() {
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public static final String ARCHIVO_ADMINISTRADORES = "data/Administrador";

    // Agregar nuevoAdministrador al listado de administradores
    public static void agregarAdministrador(Administrador nuevoAdministrador, List<Administrador> listadoAdministradores) {
        listadoAdministradores.add(nuevoAdministrador);
    }

    // Guardar el listado de administradores en un archivo
    public void guardarAdministradoresEnArchivo(List<Administrador> listadoAdministradores) {
        try (FileWriter writer = new FileWriter(ARCHIVO_ADMINISTRADORES,true)) {
            for (Administrador administrador : listadoAdministradores) {
                String linea = administrador.getNombreUsuario() + "," + administrador.getPassword() + "," + administrador.getEmail() + "," + administrador.isActivo();
                writer.write(linea);
                writer.write(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los administradores en el archivo.");
        }
    }

    public static List<Administrador> cargarAdministradoresDesdeArchivo() {
        List<Administrador> administradores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_ADMINISTRADORES))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                String nombre = partes[0];
                String dni = partes[1];
                String email = partes[2];
                boolean activo = Boolean.parseBoolean(partes[3]);

                Administrador administrador = new Administrador(nombre, dni, email, activo);
                administradores.add(administrador);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los administradores desde el archivo.");
        }

        return administradores;
    }
    // Verificar el inicio de sesión de un administrador
    public static boolean verificarInicioSesion(String email, String Paasword) {
        List<Administrador> administradores = cargarAdministradoresDesdeArchivo();

        for (Administrador administrador : administradores) {
            if (administrador.getEmail().equals(email) && administrador.getPassword().equals(Paasword)) {
                return true; // Las credenciales son válidas
            }
        }

        return false; // Las credenciales son inválidas
}
}
