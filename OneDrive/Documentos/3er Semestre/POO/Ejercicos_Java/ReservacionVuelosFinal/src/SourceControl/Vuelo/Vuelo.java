package SourceControl.Vuelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import SourceControl.Asiento.Asiento;

public class Vuelo {

    private int idVuelo;
    private String origen;
    private String destino;
    private List<Asiento> asientos;

    // Constructor
    public Vuelo(int idVuelo, String origen, String destino) {
        this.idVuelo = idVuelo;
        this.origen = origen;
        this.destino = destino;
        this.asientos = new ArrayList<>();
        cargarAsientos();  // Cargar los asientos desde la base de datos
    }

    // Getter y Setter
    public int getIdVuelo() {
        return idVuelo;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    // Método para cargar asientos desde la base de datos
    private void cargarAsientos() {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Brownie5");
             PreparedStatement stmt = conn.prepareStatement("select * from asiento order by numeroasiento asc")) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String numeroAsiento = rs.getString("numeroAsiento");
                String clase = rs.getString("clase");
                String estadoAsientoQuery  = "Select * from vuelo_asiento_usuario where idasiento = ? and idvuelo = ?";
                PreparedStatement estadoAsientoStmt = conn.prepareStatement(estadoAsientoQuery);
                estadoAsientoStmt.setInt(1, rs.getInt("idasiento"));
                estadoAsientoStmt.setInt(2, idVuelo);
               ResultSet estadoasientoResult= estadoAsientoStmt.executeQuery();
                Asiento asiento = new Asiento(numeroAsiento, clase, !estadoasientoResult.next());
                this.asientos.add(asiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para reservar un asiento
    public void reservarAsiento(String numeroAsiento) {
        // Aquí intentamos reservar el asiento desde la base de datos
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Brownie5");
             PreparedStatement stmt = conn.prepareStatement("Select * from asiento")) {

            stmt.setString(1, numeroAsiento);
            stmt.setInt(2, this.idVuelo);

            // Ejecuta la actualización para cambiar el estado del asiento
            ResultSet rowsAffected = stmt.executeQuery();

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
        return false; // Si no se encuentra el asiento o no está disponible, retornamos falso
    }


}