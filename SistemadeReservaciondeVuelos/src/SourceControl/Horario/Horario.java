package SourceControl.Horario;

import java.time.LocalDateTime;

/**
 * Clase que representa el horario de un vuelo, incluyendo la hora de salida y llegada.
 */
public class Horario {

    private LocalDateTime horaSalida; // Hora de salida del vuelo.
    private LocalDateTime horaLlegada; // Hora de llegada del vuelo.

    /**
     * Constructor para inicializar un horario con hora de salida y llegada.
     *
     * @param horaSalida Hora de salida del vuelo.
     * @param horaLlegada Hora de llegada del vuelo.
     */
    public Horario(LocalDateTime horaSalida, LocalDateTime horaLlegada) {
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
    }

    // Getters y Setters

    /**
     * Obtiene la hora de salida del vuelo.
     *
     * @return Hora de salida del vuelo.
     */
    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    /**
     * Establece la hora de salida del vuelo.
     *
     * @param horaSalida Nueva hora de salida del vuelo.
     */
    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    /**
     * Obtiene la hora de llegada del vuelo.
     *
     * @return Hora de llegada del vuelo.
     */
    public LocalDateTime getHoraLlegada() {
        return horaLlegada;
    }

    /**
     * Establece la hora de llegada del vuelo.
     *
     * @param horaLlegada Nueva hora de llegada del vuelo.
     */
    public void setHoraLlegada(LocalDateTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    /**
     * Devuelve una representación en forma de cadena del horario.
     *
     * @return Cadena que contiene la hora de salida y llegada.
     */
    @Override
    public String toString() {
        return "Horario [horaSalida=" + horaSalida + ", horaLlegada=" + horaLlegada + "]";
    }
}