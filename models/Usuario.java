package Practica_evaluacion.models;

public class Usuario {

    public class usuario {
        private String nombreUsuario;
        private String password;
        private String email;
        private boolean rol;


        usuario(){}

        public usuario(String nombreUsuario, String password, String email, boolean rol) {
            this.nombreUsuario = nombreUsuario;
            this.password = password;
            this.email = email;
            this.rol = rol;
        }

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public void setNombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public boolean isRol() {
            return rol;
        }

        public void setRol(boolean rol) {
            this.rol = rol;
        }
    }
}
