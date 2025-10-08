package SourceControl.Asiento;

/**
 * Enumeración que representa los tipos de asiento disponibles en un vuelo.
 * Los tipos son:
 *    EJECUTIVO: Representa la clase ejecutiva.
 *    ECONOMICO: Representa la clase económica.
 */
public enum TipoAsiento {
    EJECUTIVO, // Clase Ejecutiva
    ECONOMICO; // Clase Económica

    /**
     * Devuelve una representación en forma de cadena del tipo de asiento.
     * Los valores posibles son:
     *    "Clase Ejecutiva" para EJECUTIVO.
     *     "Clase Económica" para ECONOMICO.
     * Si el tipo no es reconocido, devuelve "Tipo desconocido".
     *
     * @return Una cadena representativa del tipo de asiento.
     */
    @Override
    public String toString() {
        switch (this) {
            case EJECUTIVO:
                return "Clase Ejecutiva";
            case ECONOMICO:
                return "Clase Económica";
            default:
                return "Tipo desconocido";
        }
    }
}