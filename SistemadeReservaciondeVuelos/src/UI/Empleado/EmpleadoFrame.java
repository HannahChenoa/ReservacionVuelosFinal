package UI.Empleado;

import SourceControl.Vuelo.*;
import SourceControl.Horario.*;
import SourceControl.Usuario.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase EmpleadoFrame que representa la interfaz gráfica para gestionar vuelos.
 * Permite agregar, editar, eliminar y gestionar horarios de vuelos.
 */
public class EmpleadoFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private Empleado empleado; // Objeto empleado que gestiona los vuelos.
    private JTable vuelosTable; // Tabla para mostrar los vuelos.
    private DefaultTableModel vuelosTableModel; // Modelo de datos para la tabla de vuelos.
    private JTextField idVueloField, origenField, destinoField, horaSalidaField, horaLlegadaField; // Campos de entrada.

    /**
     * Constructor que inicializa el marco para la gestión de vuelos.
     * 
     * @param empleado Objeto empleado que interactuará con el marco.
     */
    public EmpleadoFrame(Empleado empleado) {
        this.empleado = empleado;
        setTitle("Empleado - Gestión de Vuelos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Fondo con degradado
        JPanel backgroundPanel = createBackgroundPanel();
        add(backgroundPanel);

        // Crear componentes de la interfaz
        createComponents(backgroundPanel);

        // Mostrar la ventana
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Crea y configura los componentes de la interfaz.
     * 
     * @param backgroundPanel Panel de fondo donde se añadirán los componentes.
     */
    private void createComponents(JPanel backgroundPanel) {
        // Título
        JLabel titleLabel = new JLabel("Gestión de Vuelos", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 20, 800, 40);
        backgroundPanel.add(titleLabel);

        // Formulario de entrada
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setBounds(50, 80, 400, 300);
        backgroundPanel.add(formPanel);

        formPanel.add(createLabel("ID Vuelo:"));
        idVueloField = createTextField();
        formPanel.add(idVueloField);

        formPanel.add(createLabel("Origen:"));
        origenField = createTextField();
        formPanel.add(origenField);

        formPanel.add(createLabel("Destino:"));
        destinoField = createTextField();
        formPanel.add(destinoField);

        formPanel.add(createLabel("Hora de Salida (yyyy-MM-dd HH:mm):"));
        horaSalidaField = createTextField();
        formPanel.add(horaSalidaField);

        formPanel.add(createLabel("Hora de Llegada (yyyy-MM-dd HH:mm):"));
        horaLlegadaField = createTextField();
        formPanel.add(horaLlegadaField);

        // Botones de acción
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(50, 400, 700, 50);
        backgroundPanel.add(buttonPanel);

        buttonPanel.add(createRoundedButton("Agregar Vuelo", e -> agregarVuelo()));
        buttonPanel.add(createRoundedButton("Editar Vuelo", e -> editarVuelo()));
        buttonPanel.add(createRoundedButton("Eliminar Vuelo", e -> eliminarVuelo()));
        buttonPanel.add(createRoundedButton("Gestionar Horario", e -> gestionarHorario()));

        // Tabla de vuelos
        vuelosTableModel = new DefaultTableModel();
        vuelosTableModel.addColumn("ID Vuelo");
        vuelosTableModel.addColumn("Origen");
        vuelosTableModel.addColumn("Destino");
        vuelosTableModel.addColumn("Hora Salida");
        vuelosTableModel.addColumn("Hora Llegada");

        vuelosTable = new JTable(vuelosTableModel);
        JScrollPane scrollPane = new JScrollPane(vuelosTable);
        scrollPane.setBounds(500, 80, 250, 300);
        backgroundPanel.add(scrollPane);

        // Cargar vuelos en la tabla
        cargarVuelos();
    }

    /**
     * Carga los vuelos desde la base de datos y los muestra en la tabla.
     */
    private void cargarVuelos() {
        vuelosTableModel.setRowCount(0); // Limpiar la tabla

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "Brownie5";
        String query = "SELECT idvuelo, origen, destino, fechahorasalida, fechahorallegada FROM vuelos";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                vuelosTableModel.addRow(new Object[]{
                    rs.getInt("idvuelo"),
                    rs.getString("origen"),
                    rs.getString("destino"),
                    rs.getTimestamp("fechahorasalida").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    rs.getTimestamp("fechahorallegada").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los vuelos: " + e.getMessage());
        }
    }

    /**
     * Agrega un nuevo vuelo a la base de datos.
     */
    private void agregarVuelo() {
        try {
            int idVuelo = Integer.parseInt(idVueloField.getText());
            String origen = origenField.getText();
            String destino = destinoField.getText();
            LocalDateTime fechaHoraSalida = LocalDateTime.parse(horaSalidaField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime fechaHoraLlegada = LocalDateTime.parse(horaLlegadaField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "Brownie5";

            String query = "INSERT INTO vuelos (idvuelo, origen, destino, fechahorasalida, fechahorallegada) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setInt(1, idVuelo);
                stmt.setString(2, origen);
                stmt.setString(3, destino);
                stmt.setTimestamp(4, Timestamp.valueOf(fechaHoraSalida));
                stmt.setTimestamp(5, Timestamp.valueOf(fechaHoraLlegada));
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Vuelo agregado exitosamente.");
                cargarVuelos();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al agregar el vuelo: " + e.getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en los datos: " + e.getMessage());
        }
    }

    /**
     * Edita un vuelo existente en la base de datos.
     */
    private void editarVuelo() { 
    	try {
        int idVuelo = Integer.parseInt(idVueloField.getText());
        String origen = origenField.getText();
        String destino = destinoField.getText();
        LocalDateTime horaSalida = LocalDateTime.parse(horaSalidaField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime horaLlegada = LocalDateTime.parse(horaLlegadaField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Horario horario = new Horario(horaSalida, horaLlegada);

        Vuelo vueloEditado = new Vuelo(idVuelo, origen, destino);
        vueloEditado.setHorario(horario);

        empleado.editarVuelo(vueloEditado);
        cargarVuelos();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al editar vuelo: " + e.getMessage());
    }
    }

    /**
     * Elimina un vuelo de la base de datos.
     */
    private void eliminarVuelo() { 
    	try {
            int idVuelo = Integer.parseInt(idVueloField.getText()); // Obtener ID del vuelo a eliminar

            // Conexión a la base de datos
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String user = "postgres";
            String password = "Brownie5";

            // Consulta SQL para eliminar un vuelo
            String query = "DELETE FROM vuelos WHERE idvuelo = ?";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                // Configurar los parámetros del PreparedStatement
                stmt.setInt(1, idVuelo);

                // Ejecutar la consulta
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Vuelo eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró el vuelo con ese ID.");
                }

                // Recargar la tabla de vuelos
                cargarVuelos();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al eliminar el vuelo: " + e.getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar vuelo: " + e.getMessage());
        }
    }

    /**
     * Gestiona el horario de un vuelo existente.
     */
    private void gestionarHorario() { 
    	 try {
             int idVuelo = Integer.parseInt(idVueloField.getText());
             LocalDateTime nuevaHoraSalida = LocalDateTime.parse(horaSalidaField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
             LocalDateTime nuevaHoraLlegada = LocalDateTime.parse(horaLlegadaField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
             Horario nuevoHorario = new Horario(nuevaHoraSalida, nuevaHoraLlegada);

             empleado.gestionarHorario(String.valueOf(idVuelo), nuevoHorario);
             cargarVuelos();
         } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "Error al gestionar horario: " + e.getMessage());
         }
    }

    private JPanel createBackgroundPanel() {
        return new JPanel(null) {
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

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return textField;
    }

    private JButton createRoundedButton(String text, ActionListener action) {
        JButton button = new JButton(text) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(142, 45, 226));
                g2d.fillRoundRect(0, 

0, getWidth(), getHeight(), 30, 30);

                if (getModel().isRollover()) {
                    g2d.setColor(new Color(74, 0, 224));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                }
                super.paintComponent(g);
            }
        };
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(action);
        return button;
    }
}