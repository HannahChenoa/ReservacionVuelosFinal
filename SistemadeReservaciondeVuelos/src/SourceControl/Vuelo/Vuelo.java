package SourceControl.Vuelo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import SourceControl.Horario.*;
import SourceControl.Asiento.Asiento;

/**
 * Clase que representa un vuelo. Contiene información sobre su origen, destino,
 * horario, asientos y aerolínea asociada.
 */
public class Vuelo {

    private int idVuelo; // Identificador único del vuelo
    private String origen; // Ciudad de origen del vuelo
    private String destino; // Ciudad de destino del vuelo
    private List<Asiento> asientos; // Lista de asientos disponibles en el vuelo
    private Aerolinea aerolinea; // Aerolínea a la que pertenece el vuelo
    private Horario horario; // Horario del vuelo

    /**
     * Constructor para inicializar un vuelo con un ID, origen y destino.
     *
     * @param idVuelo Identificador único del vuelo.
     * @param origen  Ciudad de origen del vuelo.
     * @param destino Ciudad de destino del vuelo.
     */
    public Vuelo(int idVuelo, String origen, String destino) {
        this.idVuelo = idVuelo;
        this.origen = origen;
        this.destino = destino;
        this.asientos = new ArrayList<>();
        this.horario = null;
    }

    // Getters y Setters

    /**
     * Obtiene el identificador único del vuelo.
     *
     * @return ID del vuelo.
     */
    public int getIdVuelo() {
        return idVuelo;
    }

    /**
     * Obtiene la ciudad de origen del vuelo.
     *
     * @return Origen del vuelo.
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * Establece la ciudad de origen del vuelo.
     *
     * @param origen Nueva ciudad de origen.
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * Obtiene la ciudad de destino del vuelo.
     *
     * @return Destino del vuelo.
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Establece la ciudad de destino del vuelo.
     *
     * @param destino Nueva ciudad de destino.
     */
    public void setDestino(String destino) {
        this.destino = destino;
    }

    /**
     * Obtiene la lista de asientos del vuelo.
     *
     * @return Lista de asientos.
     */
    public List<Asiento> getAsientos() {
        return asientos;
    }

    /**
     * Establece la lista de asientos del vuelo.
     *
     * @param asientos Nueva lista de asientos.
     */
    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    /**
     * Obtiene la aerolínea asociada al vuelo.
     *
     * @return Aerolínea del vuelo.
     */
    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    /**
     * Establece la aerolínea del vuelo.
     *
     * @param aerolinea Nueva aerolínea.
     */
    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }

    /**
     * Obtiene el horario del vuelo.
     *
     * @return Horario del vuelo.
     */
    public Horario getHorario() {
        return horario;
    }

    /**
     * Establece el horario del vuelo.
     *
     * @param horario Nuevo horario.
     */
    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    // Métodos adicionales

    /**
     * Obtiene la hora de salida del vuelo a partir del objeto Horario.
     *
     * @return Hora de salida o {@code null} si no hay horario definido.
     */
    public LocalDateTime getHoraSalida() {
        if (horario != null) {
            return horario.getHoraSalida();
        }
        return null;
    }

    /**
     * Obtiene la hora de llegada del vuelo a partir del objeto Horario.
     *
     * @return Hora de llegada o {@code null} si no hay horario definido.
     */
    public LocalDateTime getHoraLlegada() {
        if (horario != null) {
            return horario.getHoraLlegada();
        }
        return null;
    }

    /**
     * Reserva un asiento en el vuelo.
     *
     * @param numeroAsiento Número del asiento a reservar.
     */
    public void reservarAsiento(String numeroAsiento) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Brownie5");
             PreparedStatement stmt = conn.prepareStatement("Select * from asiento")) {

            stmt.setString(1, numeroAsiento);
            stmt.setInt(2, this.idVuelo);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica si un asiento está disponible.
     *
     * @param numeroAsiento Número del asiento a verificar.
     * @return {@code true} si el asiento está disponible, {@code false} en caso contrario.
     */
    public boolean isAsientoDisponible(String numeroAsiento) {
        for (Asiento asiento : asientos) {
            if (asiento.getNumeroAsiento().equals(numeroAsiento)) {
                return asiento.isDisponible();
            }
        }
        return false;
    }
}