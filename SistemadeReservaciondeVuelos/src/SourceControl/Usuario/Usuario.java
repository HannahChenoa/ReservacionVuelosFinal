package SourceControl.Usuario;

import SourceControl.Vuelo.Vuelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que representa a un usuario en el sistema.
 * Proporciona funcionalidades básicas como gestión de información personal,
 * historial de vuelos y métodos abstractos para acciones específicas.
 */
public abstract class Usuario {

    private String username; // Nombre de usuario único.
    private String email; // Dirección de correo electrónico.
    private String contraseña; // Contraseña del usuario.
    private String nombre; // Nombre del usuario.
    private String apellido; // Apellido del usuario.
    private List<Vuelo> historialVuelos; // Historial de vuelos del usuario.

    /**
     * Constructor que inicializa los datos del usuario.
     *
     * @param username   Nombre de usuario único.
     * @param email      Dirección de correo electrónico.
     * @param contraseña Contraseña del usuario.
     * @param apellido   Apellido del usuario.
     * @param nombre     Nombre del usuario.
     * @throws IllegalArgumentException Si alguno de los datos es nulo o vacío.
     */
    public Usuario(String username, String email, String contraseña, String apellido, String nombre) {
        setUsername(username);
        setEmail(email);
        setContraseña(contraseña);
        setApellido(apellido);
        setNombre(nombre);
        this.historialVuelos = new ArrayList<>();
    }

    // Getters

    /**
     * Obtiene el nombre de usuario.
     *
     * @return Nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return Correo electrónico.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return Contraseña.
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el apellido del usuario.
     *
     * @return Apellido del usuario.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Obtiene una copia del historial de vuelos del usuario.
     *
     * @return Lista de vuelos en el historial.
     */
    public List<Vuelo> getHistorialVuelos() {
        return new ArrayList<>(historialVuelos); // Copia para evitar modificaciones externas.
    }

    // Setters

    /**
     * Establece el nombre de usuario.
     *
     * @param username Nombre de usuario único.
     * @throws IllegalArgumentException Si el nombre de usuario es nulo o vacío.
     */
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío.");
        }
        this.username = username.trim();
    }

    /**
     * Establece el correo electrónico.
     *
     * @param email Dirección de correo electrónico.
     * @throws IllegalArgumentException Si el correo es nulo o vacío.
     */
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email no puede estar vacío.");
        }
        this.email = email.trim();
    }

    /**
     * Establece la contraseña.
     *
     * @param contraseña Contraseña del usuario.
     * @throws IllegalArgumentException Si la contraseña es nula o vacía.
     */
    public void setContraseña(String contraseña) {
        if (contraseña == null || contraseña.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        this.contraseña = contraseña.trim();
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param nombre Nombre del usuario.
     * @throws IllegalArgumentException Si el nombre es nulo o vacío.
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    /**
     * Establece el apellido del usuario.
     *
     * @param apellido Apellido del usuario.
     * @throws IllegalArgumentException Si el apellido es nulo o vacío.
     */
    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }
        this.apellido = apellido.trim();
    }

    // Métodos adicionales para el historial de vuelos

    /**
     * Agrega un vuelo al historial del usuario.
     *
     * @param vuelo Vuelo que se desea agregar.
     * @throws IllegalArgumentException Si el vuelo es nulo.
     */
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

    /**
     * Elimina un vuelo del historial del usuario.
     *
     * @param vuelo Vuelo que se desea eliminar.
     * @throws IllegalArgumentException Si el vuelo es nulo.
     */
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

    /**
     * Consulta y devuelve el historial de vuelos en formato legible.
     *
     * @return Cadena representativa del historial de vuelos.
     */
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

    /**
     * Método abstracto para registrar un usuario. Debe ser implementado por las subclases.
     */
    public abstract void registrar();

    /**
     * Método abstracto para iniciar sesión. Debe ser implementado por las subclases.
     *
     * @param email      Correo electrónico del usuario.
     * @param contraseña Contraseña del usuario.
     * @return {@code true} si las credenciales son correctas, {@code false} en caso contrario.
     */
    public abstract boolean iniciarSesion(String email, String contraseña);
}