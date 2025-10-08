package UI.Boleto;

import javax.swing.*;
import java.awt.*;
import SourceControl.Vuelo.*;

/**
 * Clase que representa la interfaz gráfica para mostrar un boleto de avión.
 * Incluye detalles del vuelo, pasajero, asiento y aerolínea.
 */
public class BoletoFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor para inicializar el marco del boleto.
     *
     * @param vuelo             Objeto {@link Vuelo} que contiene los detalles del vuelo.
     * @param asientoSeleccionado Número del asiento seleccionado por el pasajero.
     * @param nombrePasajero    Nombre del pasajero.
     * @param reserva           Número de reserva asociado al boleto.
     * @param aerolinea         Nombre de la aerolínea.
     */
    public BoletoFrame(Vuelo vuelo, String asientoSeleccionado, String nombrePasajero, String reserva, String aerolinea) {
        setTitle("Boleto de Avión");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fondo con degradado
        JPanel backgroundPanel = createBackgroundPanel();
        add(backgroundPanel, BorderLayout.CENTER);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);

        // Encabezado
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setOpaque(false);
        JLabel lblLogo = new JLabel(aerolinea, SwingConstants.CENTER);
        lblLogo.setFont(new Font("Arial", Font.BOLD, 24));
        lblLogo.setForeground(Color.WHITE);
        headerPanel.add(lblLogo);

        JLabel lblVuelo = new JLabel("Vuelo ID: " + vuelo.getIdVuelo(), SwingConstants.CENTER);
        lblVuelo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblVuelo.setForeground(Color.WHITE);
        headerPanel.add(lblVuelo);

        mainPanel.add(headerPanel);

        // Línea divisoria
        mainPanel.add(createSeparator());

        // Detalles del pasajero y vuelo
        JPanel passengerDetails = new JPanel(new GridLayout(3, 1));
        passengerDetails.setOpaque(false);
        passengerDetails.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        passengerDetails.add(createLabel("Pasajero: " + nombrePasajero));
        passengerDetails.add(createLabel("Origen: " + vuelo.getOrigen() + "  →  Destino: " + vuelo.getDestino()));
        passengerDetails.add(createLabel("Asiento: " + asientoSeleccionado));

        mainPanel.add(passengerDetails);

        mainPanel.add(createSeparator());

        // Detalles adicionales
        JPanel additionalDetails = new JPanel(new GridLayout(1, 2));
        additionalDetails.setOpaque(false);
        additionalDetails.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        additionalDetails.add(createLabel("Reserva: " + reserva));
        additionalDetails.add(createLabel("Aerolínea: " + aerolinea));

        mainPanel.add(additionalDetails);

        mainPanel.add(createSeparator());

        // Mostrar el boleto
        JPanel ticketPanel = new JPanel();
        ticketPanel.setOpaque(false);
        JLabel lblTicket = new JLabel("Gracias por elegirnos. ¡Buen viaje!");
        lblTicket.setFont(new Font("Arial", Font.BOLD, 16));
        lblTicket.setForeground(Color.WHITE);
        lblTicket.setHorizontalAlignment(SwingConstants.CENTER);
        ticketPanel.add(lblTicket);

        mainPanel.add(ticketPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Crea una etiqueta estilizada para mostrar información del boleto.
     *
     * @param text Texto a mostrar en la etiqueta.
     * @return Etiqueta configurada con estilo.
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);
        return label;
    }

    /**
     * Crea un separador visual para dividir secciones del boleto.
     *
     * @return Panel que actúa como separador.
     */
    private JPanel createSeparator() {
        JPanel separator = new JPanel();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setBackground(Color.WHITE);
        return separator;
    }

    /**
     * Crea un panel con un fondo de degradado.
     *
     * @return Panel configurado con fondo degradado.
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
}