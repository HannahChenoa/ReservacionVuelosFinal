package UI.Reservacion;

import javax.swing.*;
import java.awt.*;
import SourceControl.Pago.Pago;
import SourceControl.Vuelo.*;
import UI.Boleto.BoletoFrame;

public class MetodoPagoFrame extends JFrame {

    private static final long serialVersionUID = 1L;  // Mover esto aquí fuera de la clase anónima

    public MetodoPagoFrame(Vuelo vuelo, String asientoSeleccionado) {
        setTitle("Método de Pago");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fondo con degradado
        JPanel backgroundPanel = createBackgroundPanel();
        add(backgroundPanel, BorderLayout.CENTER);

        // Título
        JLabel title = new JLabel("Método de Pago", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        backgroundPanel.add(title, BorderLayout.NORTH);

        // Panel de detalles
        JPanel detailsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        backgroundPanel.add(detailsPanel, BorderLayout.CENTER);

        JLabel lblNombrePropietario = new JLabel("Nombre del Propietario:");
        lblNombrePropietario.setFont(new Font("Arial", Font.PLAIN, 18));
        lblNombrePropietario.setForeground(Color.WHITE);
        detailsPanel.add(lblNombrePropietario);

        JTextField txtNombrePropietario = new JTextField();
        txtNombrePropietario.setFont(new Font("Arial", Font.PLAIN, 16));
        detailsPanel.add(txtNombrePropietario);

        JLabel lblNumeroTarjeta = new JLabel("Número de Tarjeta:");
        lblNumeroTarjeta.setFont(new Font("Arial", Font.PLAIN, 18));
        lblNumeroTarjeta.setForeground(Color.WHITE);
        detailsPanel.add(lblNumeroTarjeta);

        JTextField txtNumeroTarjeta = new JTextField();
        txtNumeroTarjeta.setFont(new Font("Arial", Font.PLAIN, 16));
        detailsPanel.add(txtNumeroTarjeta);

        JLabel lblFechaExpiracion = new JLabel("Fecha de Expiración (MM/AA):");
        lblFechaExpiracion.setFont(new Font("Arial", Font.PLAIN, 18));
        lblFechaExpiracion.setForeground(Color.WHITE);
        detailsPanel.add(lblFechaExpiracion);

        JTextField txtFechaExpiracion = new JTextField();
        txtFechaExpiracion.setFont(new Font("Arial", Font.PLAIN, 16));
        detailsPanel.add(txtFechaExpiracion);

        JLabel lblCVV = new JLabel("CVV:");
        lblCVV.setFont(new Font("Arial", Font.PLAIN, 18));
        lblCVV.setForeground(Color.WHITE);
        detailsPanel.add(lblCVV);

        JTextField txtCVV = new JTextField();
        txtCVV.setFont(new Font("Arial", Font.PLAIN, 16));
        detailsPanel.add(txtCVV);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton btnPagar = new JButton("Pagar");
        styleButton(btnPagar);
        btnPagar.addActionListener(e -> {
            try {
                // Crear un objeto Pago con los datos ingresados
                Pago pago = new Pago(
                    txtNombrePropietario.getText(),
                    txtNumeroTarjeta.getText(),
                    txtFechaExpiracion.getText(),
                    txtCVV.getText()
                );

                // Reservar el asiento
                vuelo.reservarAsiento(asientoSeleccionado);

                // Detalles adicionales
                String aerolinea=null; // O recupera este dato de una instancia de Aerolinea
                String reserva = "ZYSTRM"; // Generar o recibir desde lógica previa

                // Abrir la ventana del boleto con todos los detalles
                new BoletoFrame(
                    vuelo,                      // Información del vuelo
                    asientoSeleccionado,        // Número de asiento seleccionado
                    pago.getNombrePropietario(),// Nombre del pasajero
                    reserva,                    // Código de reserva
                    aerolinea            // Nombre de la aerolínea
                );

                // Cerrar ventana de método de pago
                dispose();

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnGuardar = new JButton("Guardar Tarjeta");
        styleButton(btnGuardar);
        btnGuardar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tarjeta guardada con éxito."));

        JButton btnCancelar = new JButton("Cancelar");
        styleButton(btnCancelar);
        btnCancelar.addActionListener(e -> {
            // Regresar al frame de reservaciones
            JOptionPane.showMessageDialog(this, "Cancelaste el pago.");
            dispose();
        });

        buttonPanel.add(btnPagar);
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(74, 0, 224));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private JPanel createBackgroundPanel() {
        return new JPanel(new BorderLayout()) {
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new GradientPaint(0, 0, new Color(74, 0, 224), 0, getHeight(), new Color(142, 45, 226)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        }; // El punto y coma se debe colocar aquí, después de la clase anónima
    }
}