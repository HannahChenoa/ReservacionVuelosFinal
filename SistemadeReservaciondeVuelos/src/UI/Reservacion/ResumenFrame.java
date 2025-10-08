package UI.Reservacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import SourceControl.Vuelo.Vuelo;

/**
 * Clase ResumenFrame.
 * Representa la interfaz gráfica para mostrar un resumen de la reservación de un vuelo.
 * Incluye detalles como el vuelo, los asientos seleccionados y el costo total.
 */
public class ResumenFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor de ResumenFrame.
     * Inicializa la interfaz para mostrar el resumen de la reservación.
     *
     * @param vuelo                Objeto Vuelo con la información del vuelo reservado.
     * @param asientosSeleccionados Lista de asientos seleccionados por el usuario.
     * @param costoTotal            Costo total de la reservación.
     */
    public ResumenFrame(Vuelo vuelo, List<String> asientosSeleccionados, int costoTotal) {
        // Configuración inicial de la ventana
        setTitle("Resumen de Reservación");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal con fondo
        JPanel backgroundPanel = createBackgroundPanel();
        add(backgroundPanel, BorderLayout.CENTER);

        // Título
        JLabel title = new JLabel("Resumen de Reservación", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        backgroundPanel.add(title, BorderLayout.NORTH);

        // Panel de detalles
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        detailsPanel.setOpaque(false);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Detalles del vuelo
        JLabel lblVueloId = new JLabel("Vuelo ID: " + vuelo.getIdVuelo(), SwingConstants.CENTER);
        lblVueloId.setFont(new Font("Arial", Font.PLAIN, 18));
        lblVueloId.setForeground(Color.WHITE);

        JLabel lblAsientos = new JLabel("Asientos seleccionados: " + String.join(", ", asientosSeleccionados), SwingConstants.CENTER);
        lblAsientos.setFont(new Font("Arial", Font.PLAIN, 18));
        lblAsientos.setForeground(Color.WHITE);

        JLabel lblCosto = new JLabel("Costo Total: $" + costoTotal, SwingConstants.CENTER);
        lblCosto.setFont(new Font("Arial", Font.PLAIN, 18));
        lblCosto.setForeground(Color.WHITE);

        detailsPanel.add(lblVueloId);
        detailsPanel.add(lblAsientos);
        detailsPanel.add(lblCosto);

        backgroundPanel.add(detailsPanel, BorderLayout.CENTER);

        // Botones de acción
        JButton btnRegresar = createRegresarButton(vuelo);
        JButton btnMetodoPago = createMetodoPagoButton(vuelo, asientosSeleccionados);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnRegresar);
        buttonPanel.add(btnMetodoPago);

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Configurar ventana
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Crea un panel de fondo con un degradado.
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
     * Crea el botón para regresar a la ventana de Reservación.
     *
     * @param vuelo Objeto Vuelo con la información del vuelo.
     * @return Un objeto JButton configurado.
     */
    private JButton createRegresarButton(Vuelo vuelo) {
        JButton btnRegresar = new JButton("Regresar a Reservación");
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 18));
        btnRegresar.setBackground(Color.GRAY);
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFocusPainted(false);
        btnRegresar.addActionListener(e -> {
            // Cierra la ventana actual y regresa a ReservacionFrame
            new ReservacionFrame(vuelo);
            dispose();
        });
        return btnRegresar;
    }

    /**
     * Crea el botón para abrir la ventana de método de pago.
     *
     * @param vuelo                Objeto Vuelo con la información del vuelo reservado.
     * @param asientosSeleccionados Lista de asientos seleccionados por el usuario.
     * @return Un objeto JButton configurado.
     */
    private JButton createMetodoPagoButton(Vuelo vuelo, List<String> asientosSeleccionados) {
        JButton btnMetodoPago = new JButton("Método de Pago");
        btnMetodoPago.setFont(new Font("Arial", Font.BOLD, 18));
        btnMetodoPago.setBackground(new Color(74, 0, 224));
        btnMetodoPago.setForeground(Color.WHITE);
        btnMetodoPago.setFocusPainted(false);
        btnMetodoPago.addActionListener(e -> {
            // Abre la ventana de método de pago para el primer asiento seleccionado
            if (!asientosSeleccionados.isEmpty()) {
                String asientoSeleccionado = asientosSeleccionados.get(0); // Usa el primer asiento como ejemplo
                new MetodoPagoFrame(vuelo, asientoSeleccionado);
            } else {
                JOptionPane.showMessageDialog(this, "No se seleccionó ningún asiento.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return btnMetodoPago;
    }
}