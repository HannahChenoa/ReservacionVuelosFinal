package SourceControl.Pago;

import java.util.regex.Pattern;
import java.time.YearMonth;

/**
 * Clase que representa un pago con tarjeta de crédito o débito.
 * Realiza validaciones estrictas para los datos del propietario, número de tarjeta, 
 * fecha de expiración y código CVV. Además, maneja el enmascaramiento del número de tarjeta 
 * para proteger la información sensible.
 */
public class Pago {

    // Expresiones regulares para validar los datos de entrada
    private static final String NUMERO_TARJETA_REGEX = "\\d{16}";
    private static final String FECHA_EXPIRACION_REGEX = "\\d{2}/\\d{2}";
    private static final String CVV_REGEX = "\\d{3}";

    // Mensajes de error para validaciones
    private static final String ERROR_NUMERO_TARJETA = "El número de tarjeta debe contener exactamente 16 dígitos.";
    private static final String ERROR_FECHA_EXPIRACION = "La fecha de expiración debe estar en el formato MM/AA.";
    private static final String ERROR_CVV = "El CVV debe contener exactamente 3 dígitos.";
    private static final String ERROR_NOMBRE = "El nombre del propietario no puede estar vacío.";

    private String nombrePropietario; // Nombre del propietario de la tarjeta.
    private String numeroTarjetaMask; // Número de tarjeta enmascarado para seguridad.
    private String fechaExpiracion; // Fecha de expiración en formato MM/AA.
    private String cvv; // Código de seguridad (CVV) de 3 dígitos.

    /**
     * Constructor que inicializa los datos del pago.
     *
     * @param nombrePropietario Nombre del propietario de la tarjeta.
     * @param numeroTarjeta     Número de tarjeta (16 dígitos).
     * @param fechaExpiracion   Fecha de expiración en formato MM/AA.
     * @param cvv               Código de seguridad (CVV) de 3 dígitos.
     * @throws IllegalArgumentException Si alguno de los datos no es válido.
     */
    public Pago(String nombrePropietario, String numeroTarjeta, String fechaExpiracion, String cvv) {
        setNombrePropietario(nombrePropietario);
        setNumeroTarjeta(numeroTarjeta);
        setFechaExpiracion(fechaExpiracion);
        setCvv(cvv);
    }

    /**
     * Obtiene el nombre del propietario de la tarjeta.
     *
     * @return Nombre del propietario.
     */
    public String getNombrePropietario() {
        return nombrePropietario;
    }

    /**
     * Establece el nombre del propietario de la tarjeta.
     *
     * @param nombrePropietario Nombre del propietario.
     * @throws IllegalArgumentException Si el nombre es nulo o está vacío.
     */
    public void setNombrePropietario(String nombrePropietario) {
        if (nombrePropietario == null || nombrePropietario.trim().isEmpty()) {
            throw new IllegalArgumentException(ERROR_NOMBRE);
        }
        this.nombrePropietario = nombrePropietario.trim();
    }

    /**
     * Obtiene el número de tarjeta enmascarado.
     *
     * @return Número de tarjeta enmascarado.
     */
    public String getNumeroTarjetaMask() {
        return numeroTarjetaMask;
    }

    /**
     * Establece el número de tarjeta, verificando que contenga exactamente 16 dígitos.
     * Genera automáticamente el número enmascarado.
     *
     * @param numeroTarjeta Número de tarjeta (16 dígitos).
     * @throws IllegalArgumentException Si el número no es válido.
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        if (!Pattern.matches(NUMERO_TARJETA_REGEX, numeroTarjeta)) {
            throw new IllegalArgumentException(ERROR_NUMERO_TARJETA);
        }
        this.numeroTarjetaMask = "**** **** **** " + numeroTarjeta.substring(12);
    }

    /**
     * Obtiene la fecha de expiración de la tarjeta.
     *
     * @return Fecha de expiración en formato MM/AA.
     */
    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    /**
     * Establece la fecha de expiración de la tarjeta, verificando que esté en formato MM/AA
     * y que no esté expirada.
     *
     * @param fechaExpiracion Fecha de expiración en formato MM/AA.
     * @throws IllegalArgumentException Si la fecha no es válida o la tarjeta está expirada.
     */
    public void setFechaExpiracion(String fechaExpiracion) {
        if (!Pattern.matches(FECHA_EXPIRACION_REGEX, fechaExpiracion)) {
            throw new IllegalArgumentException(ERROR_FECHA_EXPIRACION);
        }
        YearMonth fecha = YearMonth.parse("20" + fechaExpiracion.substring(3) + "-" + fechaExpiracion.substring(0, 2));
        if (fecha.isBefore(YearMonth.now())) {
            throw new IllegalArgumentException("La tarjeta está expirada.");
        }
        this.fechaExpiracion = fechaExpiracion;
    }

    /**
     * Obtiene el código de seguridad (CVV) de la tarjeta.
     *
     * @return CVV de la tarjeta.
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Establece el código de seguridad (CVV) de la tarjeta, verificando que contenga
     * exactamente 3 dígitos.
     *
     * @param cvv CVV de 3 dígitos.
     * @throws IllegalArgumentException Si el CVV no es válido.
     */
    public void setCvv(String cvv) {
        if (!Pattern.matches(CVV_REGEX, cvv)) {
            throw new IllegalArgumentException(ERROR_CVV);
        }
        this.cvv = cvv;
    }

    /**
     * Simula el procesamiento del pago.
     * Este método imprime mensajes indicando el inicio y éxito del procesamiento.
     */
    public void procesarPago() {
        System.out.println("Procesando el pago...");
        System.out.println("Pago realizado con éxito.");
    }

    /**
     * Devuelve una representación en forma de cadena del pago.
     * El número de tarjeta se muestra parcialmente enmascarado.
     *
     * @return Cadena representativa del pago.
     */
    @Override
    public String toString() {
        return "Pago [Propietario: " + nombrePropietario + ", Número Tarjeta: " + numeroTarjetaMask 
               + ", Fecha Expiración: " + fechaExpiracion + "]";
    }
}