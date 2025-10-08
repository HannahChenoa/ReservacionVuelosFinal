package SourceControl.Vuelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una aerolínea en el sistema.
 * Contiene información básica de la aerolínea, como su ID, nombre, ruta del logo y
 * una lista de vuelos asociados.
 */
public class Aerolinea {

    private int id; // Identificador único de la aerolínea.
    private String nombre; // Nombre de la aerolínea.
    private String logoPath; // Ruta del archivo del logo de la aerolínea.
    private List<Vuelo> vuelos; // Lista de vuelos asociados a la aerolínea.

    /**
     * Constructor para inicializar una aerolínea con sus datos básicos.
     *
     * @param id       Identificador único de la aerolínea.
     * @param nombre   Nombre de la aerolínea.
     * @param logoPath Ruta del archivo del logo de la aerolínea.
     */
    public Aerolinea(int id, String nombre, String logoPath) {
        this.id = id;
        this.nombre = nombre;
        this.logoPath = logoPath;
        this.vuelos = new ArrayList<>(); // Inicializa la lista de vuelos.
    }

    // Getters y Setters

    /**
     * Obtiene el identificador único de la aerolínea.
     *
     * @return ID de la aerolínea.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre de la aerolínea.
     *
     * @return Nombre de la aerolínea.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la ruta del logo de la aerolínea.
     *
     * @return Ruta del archivo del logo.
     */
    public String getLogoPath() {
        return logoPath;
    }

    /**
     * Obtiene la lista de vuelos asociados a la aerolínea.
     *
     * @return Lista de vuelos.
     */
    public List<Vuelo> getVuelos() {
        return vuelos;
    }

    /**
     * Establece la lista de vuelos asociados a la aerolínea.
     *
     * @param vuelos Nueva lista de vuelos.
     */
    public void setVuelos(List<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }

    // Métodos adicionales

    /**
     * Agrega un vuelo a la lista de vuelos de la aerolínea.
     * Si el vuelo ya está registrado, no se agrega y se imprime un mensaje de error.
     *
     * @param vuelo Vuelo que se desea agregar.
     */
    public void agregarVuelo(Vuelo vuelo) {
        if (!vuelos.contains(vuelo)) { // Verifica si el vuelo ya existe en la lista.
            this.vuelos.add(vuelo);
            vuelo.setAerolinea(this); // Asocia esta aerolínea con el vuelo.
        } else {
            System.out.println("Error: El vuelo ya está registrado en esta aerolínea.");
        }
    }

    /**
     * Devuelve una representación en forma de cadena de la aerolínea.
     * Incluye el ID, nombre y ruta del logo.
     *
     * @return Cadena representativa de la aerolínea.
     */
    @Override
    public String toString() {
        return "Aerolinea [id=" + id + ", nombre=" + nombre + ", logoPath=" + logoPath + "]";
    }
}