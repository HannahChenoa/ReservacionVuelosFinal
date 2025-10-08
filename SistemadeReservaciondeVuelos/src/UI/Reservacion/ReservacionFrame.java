package UI.Reservacion;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import SourceControl.Vuelo.Vuelo;
import UI.Aerolineas.*;

/**
 * Clase ReservacionFrame.
 * Representa la interfaz gráfica para realizar la reservación de asientos de un vuelo.
 * Permite visualizar, seleccionar y reservar asientos según su estado.
 */
public class ReservacionFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private final Vuelo vuelo; // Objeto que representa el vuelo seleccionado
    private final ArrayList<String> asientosSeleccionados = new ArrayList<>(); // Lista de asientos seleccionados
    private int costoTotal = 0; // Costo total de la reservación

    /**
     * Constructor de ReservacionFrame.
     * Inicializa la interfaz de usuario para la reservación de un vuelo.
     *
     * @param vuelo Objeto Vuelo que contiene los detalles del vuelo seleccionado.
     */
    public ReservacionFrame(Vuelo vuelo) {
        this.vuelo = vuelo;

        // Configuración inicial de la ventana
        setTitle("Reservación de Vuelo");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal con fondo degradado
        JPanel backgroundPanel = createBackgroundPanel();
        add(backgroundPanel, BorderLayout.CENTER);

        // Título
        JLabel title = new JLabel("Reservación de Vuelo", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        backgroundPanel.add(title, BorderLayout.NORTH);

        // Panel para los asientos
        JPanel seatsPanel = new JPanel();
        seatsPanel.setOpaque(false);
        seatsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(seatsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior para leyenda y botones
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        // Leyenda
        JPanel legendPanel = createLegendPanel();
        bottomPanel.add(legendPanel, BorderLayout.NORTH);

        // Botones de reservación y regresar
        JButton btnReservar = createReservarButton();
        JButton btnRegresar = createRegresarButton();
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(btnReservar);
        buttonsPanel.add(btnRegresar);
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);

        backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Cargar los asientos del vuelo
        cargarAsientos(seatsPanel);

        // Mostrar la ventana
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
     * Crea un panel con la leyenda de los estados de los asientos.
     *
     * @return Un objeto JPanel configurado.
     */
    private JPanel createLegendPanel() {
        JPanel legendPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        legendPanel.setOpaque(false);
        legendPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        legendPanel.add(createLegendLabel("Económica (Azul)", Color.BLUE));
        legendPanel.add(createLegendLabel("Ejecutiva (Amarillo)", Color.ORANGE));
        legendPanel.add(createLegendLabel("Reservado (Gris)", Color.GRAY));
        legendPanel.add(createLegendLabel("No disponible (Rojo)", Color.RED));

        return legendPanel;
    }

    /**
     * Crea una etiqueta para la leyenda.
     *
     * @param text  Texto de la etiqueta.
     * @param color Color del texto.
     * @return Un objeto JLabel configurado.
     */
    private JLabel createLegendLabel(String text, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(color);
        return label;
    }

    /**
     * Crea el botón para confirmar la reservación.
     *
     * @return Un objeto JButton configurado.
     */
    private JButton createReservarButton() {
        JButton btnReservar = new JButton("Reservar");
        btnReservar.setFont(new Font("Arial", Font.BOLD, 16));
        btnReservar.setBackground(new Color(74, 0, 224));
        btnReservar.setForeground(Color.WHITE);
        btnReservar.setFocusPainted(false);
        btnReservar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnReservar.addActionListener(e -> mostrarResumen());
        return btnReservar;
    }

    /**
     * Crea el botón para regresar a la pantalla anterior.
     *
     * @return Un objeto JButton configurado.
     */
    private JButton createRegresarButton() {
        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegresar.setBackground(Color.GRAY);
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(e -> {
            new AerolineaFrame("Aeroméxico", 1); // Cambiar el idAerolinea según sea necesario
            dispose();
        });
        return btnRegresar;
    }

    /**
     * Carga los asientos del vuelo desde la base de datos y los muestra en el panel.
     *
     * @param seatsPanel Panel donde se mostrarán los asientos.
     */
    private void cargarAsientos(JPanel seatsPanel) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
                "Brownie5");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM asiento ORDER BY numeroasiento ASC")) {

            ResultSet rs = stmt.executeQuery();
            seatsPanel.setLayout(new GridLayout(0, 7, 10, 10)); // Configura un diseño de cuadrícula para los asientos
            int columnCounter = 0;

            while (rs.next()) {
                String numeroAsiento = rs.getString("numeroAsiento");
                String clase = rs.getString("clase");

                // Verificar si el asiento está reservado
                String estadoAsientoQuery = "SELECT * FROM vuelo_asiento_usuario WHERE idasiento = ? AND idvuelo = ?";
                PreparedStatement estadoAsientoStmt = conn.prepareStatement(estadoAsientoQuery);
                estadoAsientoStmt.setInt(1, rs.getInt("idasiento"));
                estadoAsientoStmt.setInt(2, vuelo.getIdVuelo());
                ResultSet estadoasientoResult = estadoAsientoStmt.executeQuery();

                JButton seatButton = createSeatButton(numeroAsiento, clase, !estadoasientoResult.next());
                seatsPanel.add(seatButton);

                if (++columnCounter == 7) columnCounter = 0; // Reinicia el contador de columnas después de 7
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los asientos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Crea un botón de asiento con la configuración correspondiente.
     *
     * @param numeroAsiento Número del asiento.
     * @param clase         Clase del asiento (Económica o Ejecutiva).
     * @param disponible    Indica si el asiento está disponible.
     * @return Un objeto JButton configurado.
     */
    private JButton createSeatButton(String numeroAsiento, String clase, boolean disponible) {
        JButton seatButton = new JButton(numeroAsiento);
        seatButton.setFont(new Font("Arial", Font.BOLD, 12));
        seatButton.setPreferredSize(new Dimension(80, 80));
        seatButton.setFocusPainted(false);

        if (disponible) {
            int costo = "Económica".equalsIgnoreCase(clase) ? 500 : 1000;
            seatButton.setBackground("Económica".equalsIgnoreCase(clase) ? Color.BLUE : Color.ORANGE);
            seatButton.setForeground(Color.WHITE);
            seatButton.addActionListener(e -> manejarSeleccionAsiento(seatButton, numeroAsiento, clase, costo));
        } else {
            seatButton.setBackground(Color.RED);
            seatButton.setForeground(Color.WHITE);
            seatButton.setEnabled(false);
        }
        return seatButton;
    }

    /**
     * Maneja la selección o deselección de un asiento.
     *
     * @param seatButton    Botón del asiento.
     * @param numeroAsiento Número del asiento.
     * @param clase         Clase del asiento.
     * @param costo         Costo del asiento.
     */
    private void manejarSeleccionAsiento(JButton seatButton, String numeroAsiento, String clase, int costo) {
        if (asientosSeleccionados.contains(numeroAsiento)) {
            asientosSeleccionados.remove(numeroAsiento);
            costoTotal -= costo;
            seatButton.setBackground("Económica".equalsIgnoreCase(clase) ? Color.BLUE : Color.ORANGE);
        } else {
            asientosSeleccionados.add(numeroAsiento);
            costoTotal += costo;
            seatButton.setBackground(Color.GRAY);
        }
        JOptionPane.showMessageDialog(this, "Asiento " + numeroAsiento +
                (asientosSeleccionados.contains(numeroAsiento) ? " seleccionado." : " deseleccionado.") +
                " Costo: $" + costo);
    }

    /**
     * Muestra el resumen de la reservación.
     */
    private void mostrarResumen() {
        new ResumenFrame(vuelo, asientosSeleccionados, costoTotal); // Muestra el resumen de la reservación
        dispose();
    }
}