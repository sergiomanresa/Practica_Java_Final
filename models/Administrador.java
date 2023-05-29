package Practica_evaluacion.models;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Administrador extends Usuario {
    private boolean activo;

    public Administrador(String nombreUsuario, String password, String email, boolean activo) {
        super(nombreUsuario, password, email, true);
        this.activo = activo;
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
}
