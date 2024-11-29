package SourceControl.Usuario;

import SourceControl.Vuelo.Vuelo;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
    private String username;
    private String email;
    private String contraseña;
    private String nombre;
    private String apellido;
    private List<Vuelo> historialVuelos;

    public Usuario(String username, String email, String contraseña, String apellido, String nombre) {
        setUsername(username);
        setEmail(email);
        setContraseña(contraseña);
        setApellido(apellido);
        setNombre(nombre);
        this.historialVuelos = new ArrayList<>();
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public List<Vuelo> getHistorialVuelos() {
        return new ArrayList<>(historialVuelos); // Devuelve una copia para evitar modificaciones externas
    }

    // Setters
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío.");
        }
        this.username = username.trim();
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío.");
        }
        this.email = email.trim();
    }

    public void setContraseña(String contraseña) {
        if (contraseña == null || contraseña.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        this.contraseña = contraseña.trim();
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        this.apellido = apellido.trim();
    }

    // Métodos adicionales para el historial de vuelos
    public void agregarVuelo(Vuelo vuelo) {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser nulo.");
        }
        if (!historialVuelos.contains(vuelo)) {
            this.historialVuelos.add(vuelo);
        } else {
            System.out.println("El vuelo ya está registrado en el historial.");
        }
    }

    public void eliminarVuelo(Vuelo vuelo) {
        if (vuelo == null) {
            throw new IllegalArgumentException("El vuelo no puede ser nulo.");
        }
        if (historialVuelos.contains(vuelo)) {
            this.historialVuelos.remove(vuelo);
        } else {
            System.out.println("El vuelo no está en el historial.");
        }
    }

    public String consultarHistorial() {
        if (historialVuelos.isEmpty()) {
            return "No hay vuelos registrados en el historial para " + username + ".";
        }
        StringBuilder historial = new StringBuilder("Historial de vuelos para " + username + ":\n");
        for (Vuelo vuelo : historialVuelos) {
            historial.append(vuelo).append("\n");
        }
        return historial.toString();
    }

    // Métodos abstractos
    public abstract void registrar();

 // Método abstracto que debe ser implementado por las clases hijas
    public abstract boolean iniciarSesion(String email, String contraseña);
}
