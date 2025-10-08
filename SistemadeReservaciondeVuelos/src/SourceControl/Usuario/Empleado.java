package SourceControl.Usuario;

import SourceControl.Horario.*;
import java.util.HashMap;
import java.util.Map;
import SourceControl.Vuelo.*;

/**
 * Clase que representa a un empleado en el sistema.
 * Extiende la clase abstracta {@link Usuario} e implementa funcionalidades específicas
 * para la gestión de vuelos, incluyendo la capacidad de agregar, editar, eliminar y gestionar horarios.
 */
public class Empleado extends Usuario {

    private Map<String, Vuelo> historialVuelos; // Historial de vuelos gestionados por el empleado.

    /**
     * Constructor que inicializa los datos de un empleado.
     *
     * @param username   Nombre de usuario único.
     * @param email      Dirección de correo electrónico.
     * @param contraseña Contraseña del empleado.
     * @param apellido   Apellido del empleado.
     * @param nombre     Nombre del empleado.
     */
    public Empleado(String username, String email, String contraseña, String apellido, String nombre) {
        super(username, email, contraseña, apellido, nombre);
        this.historialVuelos = new HashMap<>();
    }

    /**
     * Registra al empleado en el sistema.
     * Imprime los detalles básicos del empleado.
     */
    @Override
    public void registrar() {
        System.out.println("Registrando al empleado: " + this.getNombre() + " " + this.getApellido());
        // Lógica adicional para registrar al empleado en una base de datos o archivo.
    }

    /**
     * Agrega un vuelo al historial de vuelos gestionados por el empleado.
     *
     * @param vuelo El vuelo que se desea agregar.
     */
    public void agregarVuelo(Vuelo vuelo) {
        String idVuelo = String.valueOf(vuelo.getIdVuelo());
        if (!historialVuelos.containsKey(idVuelo)) {
            historialVuelos.put(idVuelo, vuelo);
            System.out.println("Vuelo agregado con éxito: " + vuelo.getIdVuelo());
        } else {
            System.out.println("Error: El vuelo ya existe.");
        }
    }

    /**
     * Edita un vuelo existente en el historial del empleado.
     *
     * @param vueloEditado El vuelo con los datos actualizados.
     */
    public void editarVuelo(Vuelo vueloEditado) {
        String idVuelo = String.valueOf(vueloEditado.getIdVuelo());
        if (historialVuelos.containsKey(idVuelo)) {
            Vuelo vuelo = historialVuelos.get(idVuelo);
            vuelo.setOrigen(vueloEditado.getOrigen());
            vuelo.setDestino(vueloEditado.getDestino());
            vuelo.setHorario(vueloEditado.getHorario());
            vuelo.setAerolinea(vueloEditado.getAerolinea());
            vuelo.setAsientos(vueloEditado.getAsientos());
            System.out.println("Vuelo editado con éxito: " + vuelo.getIdVuelo());
        } else {
            System.out.println("Error: Vuelo no encontrado para editar.");
        }
    }

    /**
     * Elimina un vuelo del historial gestionado por el empleado.
     *
     * @param vueloId El ID del vuelo que se desea eliminar.
     */
    public void eliminarVuelo(String vueloId) {
        if (historialVuelos.remove(vueloId) != null) {
            System.out.println("Vuelo eliminado con éxito. ID: " + vueloId);
        } else {
            System.out.println("Error: Vuelo no encontrado para eliminar. ID: " + vueloId);
        }
    }

    /**
     * Gestiona el horario de un vuelo, actualizándolo con un nuevo horario.
     *
     * @param vueloId      El ID del vuelo cuyo horario se desea gestionar.
     * @param nuevoHorario El nuevo horario que se desea asignar al vuelo.
     */
    public void gestionarHorario(String vueloId, Horario nuevoHorario) {
        if (historialVuelos.containsKey(vueloId)) {
            Vuelo vuelo = historialVuelos.get(vueloId);
            vuelo.setHorario(nuevoHorario);
            System.out.println("Horario del vuelo con ID " + vueloId + " actualizado con éxito.");
        } else {
            System.out.println("Error: Vuelo no encontrado para gestionar el horario. ID: " + vueloId);
        }
    }

    /**
     * Inicia sesión para el empleado.
     *
     * @param email      Dirección de correo electrónico del empleado.
     * @param contraseña Contraseña del empleado.
     * @return {@code true} si las credenciales son correctas, {@code false} en caso contrario.
     */
    @Override
    public boolean iniciarSesion(String email, String contraseña) {
        return this.getEmail().equals(email) && this.getContraseña().equals(contraseña);
    }
}