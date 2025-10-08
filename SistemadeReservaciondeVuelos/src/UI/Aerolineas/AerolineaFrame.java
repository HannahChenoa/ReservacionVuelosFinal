package UI.Aerolineas;

import UI.Bienvenida.BienvenidaFrame;
import UI.Reservacion.ReservacionFrame;
import SourceControl.Vuelo.Vuelo;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * Clase que representa la interfaz gráfica para mostrar los vuelos disponibles de una aerolínea específica.
 * Permite cargar los vuelos desde la base de datos y gestionar reservas.
 */
public class AerolineaFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor que inicializa el marco de la interfaz para los vuelos de una aerolínea.
     *
     * @param aerolinea   Nombre de la aerolínea.
     * @param idAerolinea Identificador único de la aerolínea.
     */
    public AerolineaFrame(String aerolinea, int idAerolinea) {
        setTitle("Vuelos de " + aerolinea);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fondo con degradado
        JPanel backgroundPanel = createBackgroundPanel();
        add(backgroundPanel, BorderLayout.CENTER);

        // Título de la aerolínea
        JLabel title = new JLabel("Vuelos disponibles de " + aerolinea, SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        backgroundPanel.add(title, BorderLayout.NORTH);

        // Panel para mostrar los vuelos
        JPanel flightsPanel = new JPanel(new GridLayout(0, 1, 20, 20));
        flightsPanel.setOpaque(false);
        flightsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(flightsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Cargar vuelos desde la base de datos
        cargarVuelos(idAerolinea, flightsPanel);

        // Botón para regresar al marco de bienvenida
        JButton btnRegresar = new JButton("Regresar a Bienvenida");
        btnRegresar.setFont(new Font("Arial", Font.BOLD, 16));
        btnRegresar.setBackground(Color.GRAY);
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(e -> {
            new BienvenidaFrame(aerolinea); // Abre el marco de bienvenida
            dispose(); // Cierra el marco actual
        });

        backgroundPanel.add(btnRegresar, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Crea un panel con un fondo de degradado.
     *
     * @return Un panel con un fondo de degradado personalizado.
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
     * Carga los vuelos de la aerolínea desde la base de datos y los muestra en el panel.
     *
     * @param idAerolinea Identificador único de la aerolínea.
     * @param flightsPanel Panel donde se mostrarán los vuelos.
     */
    private void cargarVuelos(int idAerolinea, JPanel flightsPanel) {
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Brownie5");
             PreparedStatement stmt = conn.prepareStatement("SELECT idVuelo, origen, destino, fechaHoraSalida, fechaHoraLlegada FROM vuelos WHERE idAerolinea = ?")) {

            stmt.setInt(1, idAerolinea);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idVuelo = rs.getInt("idVuelo");
                String origen = rs.getString("origen");
                String destino = rs.getString("destino");
                String fechaSalida = rs.getString("fechaHoraSalida");
                String fechaLlegada = rs.getString("fechaHoraLlegada");

                // Crear una tarjeta para cada vuelo
                JPanel flightCard = createFlightCard(idVuelo, origen, destino, fechaSalida, fechaLlegada);
                flightsPanel.add(flightCard);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los vuelos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Crea una tarjeta para mostrar los detalles de un vuelo.
     *
     * @param idVuelo     ID del vuelo.
     * @param origen      Ciudad de origen.
     * @param destino     Ciudad de destino.
     * @param fechaSalida Fecha y hora de salida.
     * @param fechaLlegada Fecha y hora de llegada.
     * @return Un panel con la información del vuelo.
     */
    private JPanel createFlightCard(int idVuelo, String origen, String destino, String fechaSalida, String fechaLlegada) {
        JPanel card = new JPanel(new GridLayout(4, 1)) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };

        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setOpaque(false);

        // Información del vuelo
        JLabel lblVuelo = new JLabel("Vuelo: " + idVuelo);
        lblVuelo.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblRuta = new JLabel("Ruta: " + origen + " → " + destino);
        lblRuta.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel lblHorario = new JLabel("Salida: " + fechaSalida + " | Llegada: " + fechaLlegada);
        lblHorario.setFont(new Font("Arial", Font.PLAIN, 14));

        // Botón para reservar
        JButton btnReservar = new JButton("Reservar");
        btnReservar.setFont(new Font("Arial", Font.BOLD, 14));
        btnReservar.setBackground(new Color(74, 0, 224));
        btnReservar.setForeground(Color.WHITE);
        btnReservar.setFocusPainted(false);
        btnReservar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Acción para reservar
        btnReservar.addActionListener(e -> {
            Vuelo vuelo = new Vuelo(idVuelo, origen, destino);
            new ReservacionFrame(vuelo).setVisible(true);
            dispose();
        });

        card.add(lblVuelo);
        card.add(lblRuta);
        card.add(lblHorario);
        card.add(btnReservar);

        return card;
    }

    /**
     * Método principal para probar la interfaz.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        new AerolineaFrame("Aeroméxico", 1);
    }
}