package SourceControl.Asiento;

public enum TipoAsiento {
    EJECUTIVO,
    ECONOMICO;

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
