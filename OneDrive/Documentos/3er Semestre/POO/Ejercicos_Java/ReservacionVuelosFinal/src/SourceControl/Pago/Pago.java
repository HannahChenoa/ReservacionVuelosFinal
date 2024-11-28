package SourceControl.Pago;

import java.util.regex.Pattern;

public class Pago {

    private String nombrePropietario;
    private String numeroTarjeta;
    private String fechaExpiracion;
    private String cvv;

    public Pago(String nombrePropietario, String numeroTarjeta, String fechaExpiracion, String cvv) {
        setNombrePropietario(nombrePropietario);
        setNumeroTarjeta(numeroTarjeta);
        setFechaExpiracion(fechaExpiracion);
        setCvv(cvv);
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        if (nombrePropietario == null || nombrePropietario.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del propietario no puede estar vacío.");
        }
        this.nombrePropietario = nombrePropietario.trim();
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        if (!Pattern.matches("\\d{16}", numeroTarjeta)) {
            throw new IllegalArgumentException("El número de tarjeta debe contener exactamente 16 dígitos.");
        }
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        if (!Pattern.matches("\\d{2}/\\d{2}", fechaExpiracion)) {
            throw new IllegalArgumentException("La fecha de expiración debe estar en el formato MM/AA.");
        }
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        if (!Pattern.matches("\\d{3}", cvv)) {
            throw new IllegalArgumentException("El CVV debe contener exactamente 3 dígitos.");
        }
        this.cvv = cvv;
    }

    public void procesarPago() {
        System.out.println("Procesando el pago...");
        System.out.println("Pago realizado con éxito.");
    }

    @Override
    public String toString() {
        return "Pago [Propietario: " + nombrePropietario + ", Número Tarjeta: **** **** **** " 
               + numeroTarjeta.substring(12) + ", Fecha Expiración: " + fechaExpiracion + "]";
    }
}
