package UI.Empleado;

import javax.swing.*;
import java.awt.*;
import SourceControl.Vuelo.*;

public class EmpleadoFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private Empleado empleado;  // El empleado logueado

    public EmpleadoFrame(Empleado empleado) {
        this.empleado = empleado;

        setTitle("Panel de Empleado");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Fondo con degradado
        JPanel backgroundPanel = createBackgroundPanel();
        add(backgroundPanel, BorderLayout.CENTER);

        // Título
        JLabel title = new JLabel("Bienvenido, " + empleado.getNombre(), SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        backgroundPanel.add(title, BorderLayout.NORTH);

        // Panel de opciones
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.setOpaque(false);
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botón de agregar vuelo
        JButton btnAgregarVuelo = new JButton("Agregar Vuelo");
        btnAgregarVuelo.setFont(new Font("Arial", Font.BOLD, 18));
        btnAgregarVuelo.addActionListener(e -> agregarVuelo());

        // Botón de editar vuelo
        JButton btnEditarVuelo = new JButton("Editar Vuelo");
        btnEditarVuelo.setFont(new Font("Arial", Font.BOLD, 18));
        btnEditarVuelo.addActionListener(e -> editarVuelo());

        // Botón de eliminar vuelo
        JButton btnEliminarVuelo = new JButton("Eliminar Vuelo");
        btnEliminarVuelo.setFont(new Font("Arial", Font.BOLD, 18));
        btnEliminarVuelo.addActionListener(e -> eliminarVuelo());

        // Botón de gestionar horario
        JButton btnGestionarHorario = new JButton("Gestionar Horario");
        btnGestionarHorario.setFont(new Font("Arial", Font.BOLD, 18));
        btnGestionarHorario.addActionListener(e -> gestionarHorario());

        optionsPanel.add(btnAgregarVuelo);
        optionsPanel.add(btnEditarVuelo);
        optionsPanel.add(btnEliminarVuelo);
        optionsPanel.add(btnGestionarHorario);

        backgroundPanel.add(optionsPanel, BorderLayout.CENTER);

        // Botón de cerrar sesión
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 18));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        buttonPanel.add(btnCerrarSesion);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void agregarVuelo() {
        // Lógica para agregar un vuelo (pide los datos y luego lo agrega)
        String idVuelo = JOptionPane.showInputDialog(this, "Ingrese ID del Vuelo:");
        String origen = JOptionPane.showInputDialog(this, "Ingrese el origen:");
        String destino = JOptionPane.showInputDialog(this, "Ingrese el destino:");
        // Suponiendo que el vuelo es válido, lo agregamos al historial
        Vuelo nuevoVuelo = new Vuelo(idVuelo, origen, destino, null); // Necesitarías un constructor adecuado
        empleado.agregarVuelo(nuevoVuelo);
    }

    private void editarVuelo() {
        // Lógica para editar un vuelo (elige un vuelo y luego edítalo)
        String idVuelo = JOptionPane.showInputDialog(this, "Ingrese ID del Vuelo a editar:");
        String nuevoOrigen = JOptionPane.showInputDialog(this, "Nuevo origen:");
        String nuevoDestino = JOptionPane.showInputDialog(this, "Nuevo destino:");
        // Aquí deberíamos buscar el vuelo y actualizarlo
        Vuelo vueloEditado = new Vuelo(idVuelo, nuevoOrigen, nuevoDestino, null); // Construir un nuevo vuelo con los cambios
        empleado.editarVuelo(vueloEditado);
    }

    private void eliminarVuelo() {
        // Lógica para eliminar un vuelo
        String idVuelo = JOptionPane.showInputDialog(this, "Ingrese ID del Vuelo a eliminar:");
        empleado.eliminarVuelo(idVuelo);
    }

    private void gestionarHorario() {
        // Lógica para gestionar el horario de un vuelo
        String idVuelo = JOptionPane.showInputDialog(this, "Ingrese ID del Vuelo:");
        // Aquí deberías pedir el nuevo horario
        String nuevoHorarioStr = JOptionPane.showInputDialog(this, "Ingrese el nuevo horario:");
        // Suponiendo que el formato del horario es correcto, lo gestionamos
        Horario nuevoHorario = new Horario(nuevoHorarioStr); // Crear el objeto Horario
        empleado.gestionarHorario(idVuelo, nuevoHorario);
    }

    private void cerrarSesion() {
        // Aquí puedes cerrar sesión o volver a la pantalla de inicio
        JOptionPane.showMessageDialog(this, "Has cerrado sesión.");
        dispose(); // Cerrar esta ventana
        // Llamar a la ventana de login (suponiendo que existe)
        new LoginFrame();  // Asegúrate de tener la ventana de Login configurada
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
        };
    }

    public static void main(String[] args) {
        // Solo para prueba
        Empleado empleado = new Empleado("empleado1", "empleado1@empresa.com", "1234", "Gonzalez", "Juan") {
            @Override
            public boolean iniciarSesion(String email, String contraseña) {
                return super.iniciarSesion(email, contraseña);
            }
        };
        new EmpleadoFrame(empleado);
    }
}
