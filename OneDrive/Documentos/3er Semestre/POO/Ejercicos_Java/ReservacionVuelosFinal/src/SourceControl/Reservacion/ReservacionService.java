package SourceControl.Reservacion;

import SourceControl.Asiento.Asiento;
import SourceControl.Vuelo.Vuelo;

public interface ReservacionService {
    void reservarAsiento(Vuelo vuelo, Asiento asiento);

    void cancelarReservacion(String reservacionId);

    void confirmarReservacion(String reservacionId);
}
