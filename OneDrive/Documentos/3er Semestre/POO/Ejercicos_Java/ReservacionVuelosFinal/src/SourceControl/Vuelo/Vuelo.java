package SourceControl.Vuelo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import SourceControl.Horario.*;
import SourceControl.Asiento.Asiento;



public class Vuelo {

	private int idVuelo;
    private String origen;
    private String destino;
    private List<Asiento> asientos;
    private Aerolinea aerolinea;
    private Horario horario;  
    // Constructor
    public Vuelo(int idVuelo, String origen, String destino) {
        this.idVuelo = idVuelo;
        this.origen = origen;
        this.destino = destino;
        this.asientos = new ArrayList<>();
        this.horario = null;  
    }

    // Getters y Setters
    public int getIdVuelo() {
        return idVuelo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }

    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    // Métodos para obtener hora de salida y llegada desde el objeto horario
    public LocalDateTime getHoraSalida() {
        if (horario != null) {
            return horario.getHoraSalida();  // Devuelve la hora de salida desde Horario
        }
        return null;
    }

    public LocalDateTime getHoraLlegada() {
        if (horario != null) {
            return horario.getHoraLlegada();  // Devuelve la hora de llegada desde Horario
        }
        return null;
    }
	   

    
    // Método para reservar un asiento
    public void reservarAsiento(String numeroAsiento) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Brownie5");
             PreparedStatement stmt = conn.prepareStatement("Select * from asiento")) {

            stmt.setString(1, numeroAsiento);
            stmt.setInt(2, this.idVuelo);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para verificar si un asiento está disponible
    public boolean isAsientoDisponible(String numeroAsiento) {
        for (Asiento asiento : asientos) {
            if (asiento.getNumeroAsiento().equals(numeroAsiento)) {
                return asiento.isDisponible();
            }
        }
        return false;
    }
}
