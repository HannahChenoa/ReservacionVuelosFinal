package SourceControl.Reservacion;

import SourceControl.Asiento.Asiento;
import SourceControl.Vuelo.Vuelo;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementación de la interfaz {@link ReservacionService} para gestionar las reservaciones de vuelos.
 * Almacena las reservaciones en un mapa para facilitar la búsqueda, creación, cancelación y confirmación.
 */
public class ReservacionServiceImpl implements ReservacionService {

    private final Map<String, Reservacion> reservaciones = new HashMap<>(); // Almacena las reservaciones por su ID.

    /**
     * Reserva un asiento en un vuelo específico. Verifica si el asiento está disponible antes de proceder.
     * 
     * @param vuelo  El vuelo en el que se desea realizar la reservación.
     * @param asiento El asiento que se desea reservar.
     * 
     * Si el asiento ya está reservado, muestra un mensaje y no realiza ninguna operación.
     */
    @Override
    public void reservarAsiento(Vuelo vuelo, Asiento asiento) {
        if (!asiento.isDisponible()) {
            System.out.println("El asiento ya está reservado.");
            return;
        }

        String reservacionId = "RES-" + asiento.getNumeroAsiento(); // Genera un ID único para la reservación.
        Reservacion reservacion = new Reservacion(reservacionId, vuelo, asiento);
        reservaciones.put(reservacionId, reservacion); // Almacena la reservación.
        asiento.setDisponible(false); // Marca el asiento como no disponible.
        System.out.println("Reservación realizada: " + reservacion);
    }

    /**
     * Cancela una reservación identificada por su ID único.
     * 
     * @param reservacionId El ID de la reservación a cancelar.
     * 
     * Si no se encuentra la reservación, se muestra un mensaje indicando el error.
     */
    @Override
    public void cancelarReservacion(String reservacionId) {
        Reservacion reservacion = reservaciones.remove(reservacionId); // Elimina la reservación del mapa.
        if (reservacion == null) {
            System.out.println("No se encontró la reservación con ID: " + reservacionId);
            return;
        }

        reservacion.getAsiento().setDisponible(true); // Marca el asiento como disponible nuevamente.
        System.out.println("Reservación cancelada: " + reservacion);
    }

    /**
     * Confirma una reservación identificada por su ID único.
     * 
     * @param reservacionId El ID de la reservación a confirmar.
     * 
     * Si no se encuentra la reservación, se muestra un mensaje indicando el error.
     */
    @Override
    public void confirmarReservacion(String reservacionId) {
        Reservacion reservacion = reservaciones.get(reservacionId); // Recupera la reservación del mapa.
        if (reservacion == null) {
            System.out.println("No se encontró la reservación con ID: " + reservacionId);
            return;
        }

        System.out.println("Reservación confirmada: " + reservacion);
    }
}
