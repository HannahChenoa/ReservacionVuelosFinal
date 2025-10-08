package UI.Reservacion;

import javax.swing.*;
import java.awt.*;
import SourceControl.Pago.Pago;
import SourceControl.Vuelo.*;
import UI.Boleto.BoletoFrame;

/**
 * Clase MetodoPagoFrame.
 * Representa la interfaz gráfica para ingresar detalles de pago al reservar un asiento en un vuelo.
 * Permite realizar el pago, guardar la tarjeta o cancelar la operación.
 */
public class MetodoPagoFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor de MetodoPagoFrame.
     * Inicializa la interfaz para ingresar los detalles de pago.
     *
     * @param vuelo              Objeto Vuelo que contiene la información del vuelo.
     * @param asientoSeleccionado Número del asiento seleccionado para reservar.
     */
    public MetodoPagoFrame(Vuelo vuelo, String asientoSeleccionado) {
        // Configuración inicial de la ventana
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

        // Panel de detalles de pago
        JPanel detailsPanel = createDetailsPanel();
        backgroundPanel.add(detailsPanel, BorderLayout.CENTER);

        // Campos de texto para detalles de pago
        JTextField txtNombrePropietario = new JTextField();
        JTextField txtNumeroTarjeta = new JTextField();
        JTextField txtFechaExpiracion = new JTextField();
        JTextField txtCVV = new JTextField();

        // Agregar etiquetas y campos al panel
        addDetailField(detailsPanel, "Nombre del Propietario:", txtNombrePropietario);
        addDetailField(detailsPanel, "Número de Tarjeta:", txtNumeroTarjeta);
        addDetailField(detailsPanel, "Fecha de Expiración (MM/AA):", txtFechaExpiracion);
        addDetailField(detailsPanel, "CVV:", txtCVV);

        // Panel de botones
        JPanel buttonPanel = createButtonPanel(vuelo, asientoSeleccionado, txtNombrePropietario, txtNumeroTarjeta, txtFechaExpiracion, txtCVV);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Configurar ventana
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Crea el panel con un fondo degradado.
     *
     * @return Un objeto JPanel configurado.
     */
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
        };
    }

    /**
     * Crea un panel para los detalles de pago.
     *
     * @return Un objeto JPanel configurado.
     */
    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return detailsPanel;
    }

    /**
     * Agrega un campo de detalle al panel de detalles.
     *
     * @param panel Panel donde se agregará el campo.
     * @param label Etiqueta del campo.
     * @param field Campo de texto asociado.
     */
    private void addDetailField(JPanel panel, String label, JTextField field) {
        JLabel lblField = new JLabel(label);
        lblField.setFont(new Font("Arial", Font.PLAIN, 18));
        lblField.setForeground(Color.WHITE);
        panel.add(lblField);

        field.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(field);
    }

    /**
     * Crea un panel con los botones para realizar acciones.
     *
     * @param vuelo              Objeto Vuelo con información del vuelo.
     * @param asientoSeleccionado Número del asiento seleccionado.
     * @param txtNombrePropietario Campo de texto para el nombre del propietario.
     * @param txtNumeroTarjeta    Campo de texto para el número de tarjeta.
     * @param txtFechaExpiracion  Campo de texto para la fecha de expiración.
     * @param txtCVV              Campo de texto para el CVV.
     * @return Un objeto JPanel configurado.
     */
    private JPanel createButtonPanel(Vuelo vuelo, String asientoSeleccionado, JTextField txtNombrePropietario, JTextField txtNumeroTarjeta, JTextField txtFechaExpiracion, JTextField txtCVV) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        // Botón Pagar
        JButton btnPagar = new JButton("Pagar");
        styleButton(btnPagar);
        btnPagar.addActionListener(e -> handlePayment(vuelo, asientoSeleccionado, txtNombrePropietario, txtNumeroTarjeta, txtFechaExpiracion, txtCVV));
        buttonPanel.add(btnPagar);

        // Botón Guardar Tarjeta
        JButton btnGuardar = new JButton("Guardar Tarjeta");
        styleButton(btnGuardar);
        btnGuardar.addActionListener(e -> JOptionPane.showMessageDialog(this, "Tarjeta guardada con éxito."));
        buttonPanel.add(btnGuardar);

        // Botón Cancelar
        JButton btnCancelar = new JButton("Cancelar");
        styleButton(btnCancelar);
        btnCancelar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Cancelaste el pago.");
            dispose();
        });
        buttonPanel.add(btnCancelar);

        return buttonPanel;
    }

    /**
     * Maneja el evento de pago, crea el objeto Pago y muestra el boleto.
     *
     * @param vuelo              Objeto Vuelo con información del vuelo.
     * @param asientoSeleccionado Número del asiento seleccionado.
     * @param txtNombrePropietario Campo de texto con el nombre del propietario.
     * @param txtNumeroTarjeta    Campo de texto con el número de tarjeta.
     * @param txtFechaExpiracion  Campo de texto con la fecha de expiración.
     * @param txtCVV              Campo de texto con el CVV.
     */
    private void handlePayment(Vuelo vuelo, String asientoSeleccionado, JTextField txtNombrePropietario, JTextField txtNumeroTarjeta, JTextField txtFechaExpiracion, JTextField txtCVV) {
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
            String aerolinea = null; // Recuperar este dato de la lógica de la aerolínea
            String reserva = "ZYSTRM"; // Generar o recibir desde la lógica previa

            // Mostrar el boleto
            new BoletoFrame(
                vuelo,
                asientoSeleccionado,
                pago.getNombrePropietario(),
                reserva,
                aerolinea
            );

            dispose(); // Cerrar la ventana de método de pago

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Aplica estilo a un botón.
     *
     * @param button Botón al que se aplicará el estilo.
     */
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(74, 0, 224));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
}