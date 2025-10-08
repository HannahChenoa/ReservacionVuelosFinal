package SourceControl.Usuario;

import SourceControl.Pago.Pago;

/**
 * Clase que representa a un cliente en el sistema.
 * Extiende la clase abstracta {@link Usuario} e implementa funcionalidades específicas
 * para la gestión de tarjetas guardadas y pagos.
 */
public class Cliente extends Usuario {

    private Pago tarjetaGuardada; // Tarjeta guardada asociada al cliente.

    /**
     * Constructor que inicializa los datos de un cliente.
     *
     * @param username   Nombre de usuario único.
     * @param email      Dirección de correo electrónico.
     * @param contraseña Contraseña del cliente.
     * @param nombre     Nombre del cliente.
     * @param apellido   Apellido del cliente.
     */
    public Cliente(String username, String email, String contraseña, String nombre, String apellido) {
        super(username, email, contraseña, apellido, nombre);
        this.tarjetaGuardada = null; // Inicialmente no hay tarjeta guardada.
    }

    // Métodos para la tarjeta guardada

    /**
     * Obtiene la tarjeta guardada del cliente.
     *
     * @return Objeto {@link Pago} que representa la tarjeta guardada, o {@code null} si no hay tarjeta registrada.
     */
    public Pago getTarjetaGuardada() {
        return tarjetaGuardada;
    }

    /**
     * Guarda una tarjeta en la cuenta del cliente.
     *
     * @param tarjeta Objeto {@link Pago} que representa la tarjeta a guardar.
     * @throws IllegalArgumentException Si la tarjeta proporcionada es nula.
     */
    public void guardarTarjeta(Pago tarjeta) {
        if (tarjeta == null) {
            throw new IllegalArgumentException("La tarjeta no puede ser nula.");
        }
        this.tarjetaGuardada = tarjeta;
        System.out.println("Tarjeta guardada correctamente para el cliente: " + getUsername());
    }

    /**
     * Verifica si el cliente tiene una tarjeta guardada.
     *
     * @return {@code true} si el cliente tiene una tarjeta guardada, {@code false} en caso contrario.
     */
    public boolean tieneTarjetaGuardada() {
        return tarjetaGuardada != null;
    }

    // Métodos abstractos implementados

    /**
     * Registra al cliente en el sistema.
     * Imprime los detalles básicos del cliente.
     */
    @Override
    public void registrar() {
        System.out.println("Registrando cliente...");
        System.out.println("Nombre: " + getNombre() + " " + getApellido());
        System.out.println("Email: " + getEmail());
    }

    /**
     * Inicia sesión para el cliente.
     *
     * @param email      Dirección de correo electrónico del cliente.
     * @param contraseña Contraseña del cliente.
     * @return {@code true} si las credenciales son correctas, {@code false} en caso contrario.
     */
    @Override
    public boolean iniciarSesion(String email, String contraseña) {
        if (this.getEmail().equals(email) && this.getContraseña().equals(contraseña)) {
            System.out.println("Inicio de sesión exitoso para el cliente: " + getUsername());
            return true;
        } else {
            System.out.println("Error: Correo o contraseña incorrectos para el cliente: " + getUsername());
            return false;
        }
    }

    // Métodos adicionales

    /**
     * Registra un pago para el cliente. Si no se proporciona un método de pago, utiliza la tarjeta guardada.
     *
     * @param metodo Objeto {@link Pago} que representa el método de pago a utilizar, o {@code null} para usar la tarjeta guardada.
     * @throws IllegalArgumentException Si no se proporciona un método de pago y no hay tarjeta guardada.
     */
    public void registrarPago(Pago metodo) {
        if (metodo == null) {
            if (tieneTarjetaGuardada()) {
                System.out.println("Usando la tarjeta guardada para el pago...");
                tarjetaGuardada.procesarPago();
            } else {
                throw new IllegalArgumentException("No hay tarjeta registrada ni se proporcionó un método de pago.");
            }
        } else {
            System.out.println("Registrando pago para el cliente: " + getUsername());
            metodo.procesarPago();
        }
    }

    /**
     * Devuelve una representación en forma de cadena del cliente.
     *
     * @return Cadena representativa del cliente, incluyendo su nombre, apellido, email y username.
     */
    @Override
    public String toString() {
        return "Cliente [Username: " + getUsername() + ", Nombre: " + getNombre() + " " + getApellido() +
               ", Email: " + getEmail() + "]";
    }
}