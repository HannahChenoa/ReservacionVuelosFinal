package UI.Bienvenida;

import SourceControl.Vuelo.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la interfaz gráfica de bienvenida para los usuarios.
 * Permite seleccionar una aerolínea y navegar hacia la vista de sus vuelos disponibles.
 */
public class BienvenidaFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor que inicializa la interfaz de bienvenida.
     *
     * @param usuario Nombre del usuario que ingresa al sistema.
     */
    public BienvenidaFrame(String usuario) {
        setTitle("Bienvenido, " + usuario);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fondo con degradado
        JPanel backgroundPanel = createBackgroundPanel();
        add(backgroundPanel, BorderLayout.CENTER);

        // Título principal
        JLabel title = new JLabel("Seleccione una Aerolínea", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        backgroundPanel.add(title, BorderLayout.NORTH);

        // Panel para mostrar las aerolíneas
        JPanel aerolineasPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        aerolineasPanel.setOpaque(false);
        aerolineasPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JScrollPane scrollPane = new JScrollPane(aerolineasPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Cargar aerolíneas desde la base de datos y mostrarlas
        List<Aerolinea> aerolineas = cargarAerolineas();
        for (Aerolinea aerolinea : aerolineas) {
            JPanel aerolineaCard = createAerolineaCard(aerolinea);
            aerolineasPanel.add(aerolineaCard);
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Crea un panel con un fondo de degradado.
     *
     * @return Panel con fondo degradado.
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
     * Crea una tarjeta gráfica para representar una aerolínea.
     *
     * @param aerolinea Aerolínea cuyos datos se mostrarán en la tarjeta.
     * @return Panel con los datos de la aerolínea.
     */
    private JPanel createAerolineaCard(Aerolinea aerolinea) {
        JPanel card = new JPanel(new BorderLayout()) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 220));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };

        card.setPreferredSize(new Dimension(300, 200));
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setOpaque(false);

        // Logotipo
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try {
            ImageIcon logoIcon = new ImageIcon(aerolinea.getLogoPath());
            Image scaledImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            logoLabel.setText("No Imagen");
        }
        card.add(logoLabel, BorderLayout.CENTER);

        // Nombre de la aerolínea
        JLabel nameLabel = new JLabel(aerolinea.getNombre(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(new Color(74, 0, 224));
        card.add(nameLabel, BorderLayout.SOUTH);

        // Efectos de hover y clic
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(142, 45, 226, 180));
                card.setBorder(BorderFactory.createLineBorder(new Color(74, 0, 224), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(new Color(255, 255, 255, 220));
                card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                new UI.Aerolineas.AerolineaFrame(aerolinea.getNombre(), aerolinea.getId()).setVisible(true);
                dispose();
            }
        });

        return card;
    }

    /**
     * Carga las aerolíneas desde la base de datos.
     *
     * @return Lista de aerolíneas cargadas.
     */
    private List<Aerolinea> cargarAerolineas() {
        List<Aerolinea> aerolineas = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Brownie5");
             PreparedStatement stmt = conn.prepareStatement("SELECT idAerolinea, nombre, logo_path FROM aerolineas")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idAerolinea");
                String nombre = rs.getString("nombre");
                String logoPath = rs.getString("logo_path");
                aerolineas.add(new Aerolinea(id, nombre, logoPath));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las aerolíneas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return aerolineas;
    }

    /**
     * Método principal para probar la interfaz.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        new BienvenidaFrame("Usuario");
    }
}