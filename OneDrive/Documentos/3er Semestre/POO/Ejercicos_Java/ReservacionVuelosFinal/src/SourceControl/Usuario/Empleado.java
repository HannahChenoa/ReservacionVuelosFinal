package SourceControl.Usuario;

import SourceControl.Horario.*;
import java.util.HashMap;
import java.util.Map;
import SourceControl.Vuelo.*;

public class Empleado extends Usuario {

    // Usamos un Map donde el ID del vuelo es la clave y el vuelo es el valor
    private Map<String, Vuelo> historialVuelos;

    // Constructor
    public Empleado(String username, String email, String contraseña, String apellido, String nombre) {
        super(username, email, contraseña, apellido, nombre);
        this.historialVuelos = new HashMap<>();
    }

    // Método para registrar un nuevo empleado (implementación concreta)
    @Override
    public void registrar() {
        System.out.println("Registrando al empleado: " + this.getNombre() + " " + this.getApellido());
        // Lógica adicional para registrar al empleado en una base de datos, archivo, etc.
    }

    // Método para agregar un vuelo al historial
    public void agregarVuelo(Vuelo vuelo) {
        String idVuelo = String.valueOf(vuelo.getIdVuelo());  // Convertimos el ID del vuelo a String
        if (!historialVuelos.containsKey(idVuelo)) {
            historialVuelos.put(idVuelo, vuelo);  // Usamos el ID como String
            System.out.println("Vuelo agregado con éxito: " + vuelo.getIdVuelo());
        } else {
            System.out.println("Error: El vuelo ya existe.");
        }
    }

    // Método para editar un vuelo
    public void editarVuelo(Vuelo vueloEditado) {
        String idVuelo = String.valueOf(vueloEditado.getIdVuelo());  // Convertimos el ID del vuelo a String
        if (historialVuelos.containsKey(idVuelo)) {
            Vuelo vuelo = historialVuelos.get(idVuelo);  // Usamos el ID como String
            // Actualiza los datos del vuelo
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

    // Método para eliminar un vuelo
    public void eliminarVuelo(String vueloId) {
        if (historialVuelos.remove(vueloId) != null) {
            System.out.println("Vuelo eliminado con éxito. ID: " + vueloId);
        } else {
            System.out.println("Error: Vuelo no encontrado para eliminar. ID: " + vueloId);
        }
    }

    // Método para gestionar el horario de un vuelo
    public void gestionarHorario(String vueloId, Horario nuevoHorario) {
        if (historialVuelos.containsKey(vueloId)) {
            Vuelo vuelo = historialVuelos.get(vueloId);
            vuelo.setHorario(nuevoHorario);  // Actualizar el horario
            System.out.println("Horario del vuelo con ID " + vueloId + " actualizado con éxito.");
        } else {
            System.out.println("Error: Vuelo no encontrado para gestionar el horario. ID: " + vueloId);
        }
    }

    @Override
    public boolean iniciarSesion(String email, String contraseña) {
        // Implementación concreta para iniciar sesión
        return this.getEmail().equals(email) && this.getContraseña().equals(contraseña);
    }
}
