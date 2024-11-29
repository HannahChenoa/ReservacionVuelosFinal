package SourceControl.Vuelo;

import java.util.ArrayList;
import java.util.List;

public class Aerolinea {
    private int id;
    private String nombre;
    private String logoPath;
    private List<Vuelo> vuelos; // Lista de vuelos asociados

    public Aerolinea(int id, String nombre, String logoPath) {
        this.id = id;
        this.nombre = nombre;
        this.logoPath = logoPath;
        this.vuelos = new ArrayList<>(); // Inicializa la lista de vuelos
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public List<Vuelo> getVuelos() {
        return vuelos;
    }

    public void setVuelos(List<Vuelo> vuelos) {
        this.vuelos = vuelos;
    }

    // Método para agregar un vuelo individual
    public void agregarVuelo(Vuelo vuelo) {
        if (!vuelos.contains(vuelo)) {  // Evita agregar vuelos duplicados
            this.vuelos.add(vuelo);
            vuelo.setAerolinea(this);  // Establece la aerolínea para el vuelo
        } else {
            System.out.println("Error: El vuelo ya está registrado en esta aerolínea.");
        }
    }

    // Método toString para una representación más legible
    @Override
    public String toString() {
        return "Aerolinea [id=" + id + ", nombre=" + nombre + ", logoPath=" + logoPath + "]";
    }
}
