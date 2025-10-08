package SourceControl.Reservacion;

import SourceControl.Asiento.Asiento;
import SourceControl.Vuelo.Vuelo;

/**
 * Interfaz que define los métodos para gestionar las operaciones relacionadas con las reservaciones.
 * Estas incluyen reservar un asiento, cancelar una reservación y confirmar una reservación.
 */
public interface ReservacionService {

    /**
     * Reserva un asiento en un vuelo específico.
     *
     * @param vuelo  El vuelo en el que se realizará la reservación.
     * @param asiento El asiento que se desea reservar.
     */
    void reservarAsiento(Vuelo vuelo, Asiento asiento);

    /**
     * Cancela una reservación existente identificada por su ID único.
     *
     * @param reservacionId El identificador único de la reservación a cancelar.
     */
    void cancelarReservacion(String reservacionId);

    /**
     * Confirma una reservación existente identificada por su ID único.
     * Este método puede incluir procesos como enviar notificaciones o actualizar estados.
     *
     * @param reservacionId El identificador único de la reservación a confirmar.
     */
    void confirmarReservacion(String reservacionId);
}