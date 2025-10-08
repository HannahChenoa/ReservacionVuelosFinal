package SourceControl.Reservacion;

import SourceControl.Asiento.Asiento;
import SourceControl.Vuelo.Vuelo;

/**
 * Clase que representa una reservación en un vuelo.
 * Incluye información sobre el identificador de la reservación, el vuelo asociado
 * y el asiento reservado.
 */
public class Reservacion {
    private final String reservacionId; // Identificador único de la reservación.
    private final Vuelo vuelo; // Vuelo asociado a la reservación.
    private final Asiento asiento; // Asiento reservado en el vuelo.

    /**
     * Constructor para inicializar una reservación.
     *
     * @param reservacionId Identificador único de la reservación.
     * @param vuelo         Vuelo asociado a la reservación.
     * @param asiento       Asiento reservado en el vuelo.
     */
    public Reservacion(String reservacionId, Vuelo vuelo, Asiento asiento) {
        this.reservacionId = reservacionId;
        this.vuelo = vuelo;
        this.asiento = asiento;
    }

    /**
     * Obtiene el identificador único de la reservación.
     *
     * @return Identificador de la reservación.
     */
    public String getReservacionId() {
        return reservacionId;
    }

    /**
     * Obtiene el vuelo asociado a la reservación.
     *
     * @return Vuelo asociado.
     */
    public Vuelo getVuelo() {
        return vuelo;
    }

    /**
     * Obtiene el asiento reservado.
     *
     * @return Asiento reservado.
     */
    public Asiento getAsiento() {
        return asiento;
    }

    /**
     * Devuelve una representación en forma de cadena de la reservación.
     * Incluye el ID de la reservación, el vuelo y el asiento reservado.
     *
     * @return Cadena representativa de la reservación.
     */
    @Override
    public String toString() {
        return "Reservacion{" +
                "reservacionId='" + reservacionId + '\'' +
                ", vuelo=" + vuelo +
                ", asiento=" + asiento +
                '}';
    }
}