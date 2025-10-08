package SourceControl.Asiento;

/**
 * Clase que representa un asiento en un vuelo. Incluye información sobre el número
 * de asiento, el tipo (económica o ejecutiva) y su disponibilidad.
 */
public class Asiento {
    private final String numeroAsiento; // Número único que identifica al asiento.
    private final String tipo; // Tipo de asiento: "Económica" o "Ejecutiva".
    private boolean disponible; // Indica si el asiento está disponible.

    /**
     * Constructor para inicializar un asiento con su número, tipo y estado de disponibilidad.
     *
     * @param numeroAsiento Número único del asiento.
     * @param tipo          Tipo de asiento: "Económica" o "Ejecutiva".
     * @param disponible    Indica si el asiento está disponible.
     */
    public Asiento(String numeroAsiento, String tipo, boolean disponible) {
        this.numeroAsiento = numeroAsiento;
        this.tipo = tipo;
        this.disponible = disponible;
    }

    /**
     * Obtiene el número único del asiento.
     *
     * @return Número del asiento.
     */
    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    /**
     * Obtiene el tipo del asiento.
     *
     * @return Tipo del asiento ("Económica" o "Ejecutiva").
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Verifica si el asiento está disponible.
     *
     * @return {@code true} si el asiento está disponible, {@code false} en caso contrario.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Establece el estado de disponibilidad del asiento.
     *
     * @param disponible Nuevo estado de disponibilidad.
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Devuelve una representación en forma de cadena del asiento.
     *
     * @return Cadena que contiene los datos del asiento.
     */
    @Override
    public String toString() {
        return "Asiento{" +
                "numeroAsiento='" + numeroAsiento + '\'' +
                ", tipo='" + tipo + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}
