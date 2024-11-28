package SourceControl.Usuario;

import SourceControl.Pago.Pago;

public class Cliente extends Usuario {
    private Pago tarjetaGuardada;

    public Cliente(String username, String email, String contraseña, String nombre, String apellido) {
        super(username, email, contraseña, apellido, nombre);
        this.tarjetaGuardada = null; // No hay tarjeta guardada inicialmente
    }

    // Métodos para la tarjeta guardada
    public Pago getTarjetaGuardada() {
        return tarjetaGuardada;
    }

    public void guardarTarjeta(Pago tarjeta) {
        if (tarjeta == null) {
            throw new IllegalArgumentException("La tarjeta no puede ser nula.");
        }
        this.tarjetaGuardada = tarjeta;
        System.out.println("Tarjeta guardada correctamente para el cliente: " + getUsername());
    }

    public boolean tieneTarjetaGuardada() {
        return tarjetaGuardada != null;
    }

    // Implementación del método abstracto 'registrar'
    @Override
    public void registrar() {
        System.out.println("Registrando cliente...");
        System.out.println("Nombre: " + getNombre() + " " + getApellido());
        System.out.println("Email: " + getEmail());
    }

    // Implementación del método abstracto 'iniciarSesion'
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

    // Método para registrar un pago
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

    // Representación textual de Cliente
    @Override
    public String toString() {
        return "Cliente [Username: " + getUsername() + ", Nombre: " + getNombre() + " " + getApellido() +
               ", Email: " + getEmail() + "]";
    }
}
