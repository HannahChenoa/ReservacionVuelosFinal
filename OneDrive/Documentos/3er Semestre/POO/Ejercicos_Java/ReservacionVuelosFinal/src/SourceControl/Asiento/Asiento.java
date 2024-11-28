package SourceControl.Asiento;

public class Asiento {
    private final String numeroAsiento;
    private final String tipo; // "Económica" o "Ejecutiva"
    private boolean disponible;

    public Asiento(String numeroAsiento, String tipo, boolean disponible) {
        this.numeroAsiento = numeroAsiento;
        this.tipo = tipo;
        this.disponible = disponible;
    }

    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Asiento{" +
                "numeroAsiento='" + numeroAsiento + '\'' +
                ", tipo='" + tipo + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}
