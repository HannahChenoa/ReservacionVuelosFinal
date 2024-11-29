package SourceControl.Horario;

import java.time.LocalDateTime;

public class Horario {

    private LocalDateTime horaSalida;
    private LocalDateTime horaLlegada;

    // Constructor
    public Horario(LocalDateTime horaSalida, LocalDateTime horaLlegada) {
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
    }

    // Getters y Setters
    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public LocalDateTime getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(LocalDateTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    // Método toString para representar el horario de manera legible
    @Override
    public String toString() {
        return "Horario [horaSalida=" + horaSalida + ", horaLlegada=" + horaLlegada + "]";
    }
}
