package SourceControl.Reservacion;

import SourceControl.Asiento.Asiento;
import SourceControl.Vuelo.Vuelo;

public class Reservacion {
    private final String reservacionId;
    private final Vuelo vuelo;
    private final Asiento asiento;

    public Reservacion(String reservacionId, Vuelo vuelo, Asiento asiento) {
        this.reservacionId = reservacionId;
        this.vuelo = vuelo;
        this.asiento = asiento;
    }

    public String getReservacionId() {
        return reservacionId;
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    @Override
    public String toString() {
        return "Reservacion{" +
                "reservacionId='" + reservacionId + '\'' +
                ", vuelo=" + vuelo +
                ", asiento=" + asiento +
                '}';
    }
}
