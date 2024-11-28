package SourceControl.Reservacion;

import SourceControl.Asiento.Asiento;
import SourceControl.Vuelo.Vuelo;

import java.util.HashMap;
import java.util.Map;

public class ReservacionServiceImpl implements ReservacionService {
    private final Map<String, Reservacion> reservaciones = new HashMap<>();

    @Override
    public void reservarAsiento(Vuelo vuelo, Asiento asiento) {
        if (!asiento.isDisponible()) {
            System.out.println("El asiento ya está reservado.");
            return;
        }

        String reservacionId = "RES-" + asiento.getNumeroAsiento();
        Reservacion reservacion = new Reservacion(reservacionId, vuelo, asiento);
        reservaciones.put(reservacionId, reservacion);
        asiento.setDisponible(false);
        System.out.println("Reservación realizada: " + reservacion);
    }

    @Override
    public void cancelarReservacion(String reservacionId) {
        Reservacion reservacion = reservaciones.remove(reservacionId);
        if (reservacion == null) {
            System.out.println("No se encontró la reservación con ID: " + reservacionId);
            return;
        }

        reservacion.getAsiento().setDisponible(true);
        System.out.println("Reservación cancelada: " + reservacion);
    }

    @Override
    public void confirmarReservacion(String reservacionId) {
        Reservacion reservacion = reservaciones.get(reservacionId);
        if (reservacion == null) {
            System.out.println("No se encontró la reservación con ID: " + reservacionId);
            return;
        }

        System.out.println("Reservación confirmada: " + reservacion);
    }
}
