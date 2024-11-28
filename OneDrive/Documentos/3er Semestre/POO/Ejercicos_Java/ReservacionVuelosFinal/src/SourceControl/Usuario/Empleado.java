package SourceControl.Usuario;
import SourceControl.Vuelo.*;
import SourceControl.Horario.*;
public abstract class Empleado extends Usuario {

    // Constructor
    public Empleado(String username, String email, String contraseña, String apellido, String nombre) {
        super(username, email, contraseña, apellido, nombre);
    }


    // Implementación del método abstracto `iniciarSesion`
    @Override
    public boolean iniciarSesion(String email, String contraseña) {
        return this.getEmail().equals(email) && this.getContraseña().equals(contraseña);
    }

    public void agregarVuelo(Vuelo vuelo) {
        System.out.println("Agregando vuelo: " + vuelo.getIdVuelo());
        // Agregar el vuelo al historial
        getHistorialVuelos().add(vuelo);
        System.out.println("Vuelo agregado con éxito.");
    }

    public void editarVuelo(Vuelo vueloEditado) {
        boolean vueloEncontrado = false;
        for (Vuelo vuelo : getHistorialVuelos()) {
            if (vuelo.getIdVuelo().equals(vueloEditado.getIdVuelo())) {
                // Actualiza los datos del vuelo
                vuelo.setOrigen(vueloEditado.getOrigen());
                vuelo.setDestino(vueloEditado.getDestino());
                vuelo.setHorario(vueloEditado.getHorario());
                vuelo.setAerolinea(vueloEditado.getAerolinea());
                vuelo.setAsientos(vueloEditado.getAsientos());
                System.out.println("Vuelo editado con éxito: " + vuelo.getIdVuelo());
                vueloEncontrado = true;
                break;
            }
        }
        if (!vueloEncontrado) {
            System.out.println("Error: Vuelo no encontrado para editar.");
        }
    }

    public void eliminarVuelo(String vueloId) {
        boolean eliminado = getHistorialVuelos().removeIf(vuelo -> vuelo.getIdVuelo().equals(vueloId));
        if (eliminado) {
            System.out.println("Vuelo eliminado con éxito. ID: " + vueloId);
        } else {
            System.out.println("Error: Vuelo no encontrado para eliminar. ID: " + vueloId);
        }
    }

    public void gestionarHorario(String vueloId, Horario nuevoHorario) {
        boolean vueloEncontrado = false;
        for (Vuelo vuelo : getHistorialVuelos()) {
            if (vuelo.getIdVuelo().equals(vueloId)) {
                vuelo.setHorario(nuevoHorario);
                System.out.println("Horario del vuelo con ID " + vueloId + " actualizado con éxito.");
                vueloEncontrado = true;
                break;
            }
        }
        if (!vueloEncontrado) {
            System.out.println("Error: Vuelo no encontrado para gestionar el horario. ID: " + vueloId);
        }
    }
}
