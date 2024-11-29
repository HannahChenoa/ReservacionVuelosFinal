package UI.Login;
import SourceControl.Usuario.*;
import UI.Bienvenida.BienvenidaFrame;
import UI.Empleado.EmpleadoFrame;

import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.security.MessageDigest;

public class LoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public LoginFrame() {
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Fondo con degradado
        JPanel backgroundPanel = createBackgroundPanel("Iniciar Sesión");
        add(backgroundPanel);

        // Campos para usuario y contraseña
        JLabel lblUsuario = createLabel("Usuario:");
        lblUsuario.setBounds(50, 80, 100, 30);
        backgroundPanel.add(lblUsuario);

        JTextField txtUsuario = createTextField();
        txtUsuario.setBounds(150, 80, 200, 30);
        backgroundPanel.add(txtUsuario);

        JLabel lblPassword = createLabel("Contraseña:");
        lblPassword.setBounds(50, 130, 100, 30);
        backgroundPanel.add(lblPassword);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 130, 200, 30);
        txtPassword.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        backgroundPanel.add(txtPassword);

        // Botón de Iniciar Sesión
        RoundedButton btnLogin = new RoundedButton("Iniciar Sesión");
        btnLogin.setBounds(100, 200, 150, 50);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText().trim();
                String password = new String(txtPassword.getPassword());

                // Validar las credenciales
                if (validarCredenciales(usuario, password)) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Login exitoso");

                    // Redirigir a la ventana correspondiente
                    // Si el usuario es un cliente, abre BienvenidaFrame
                    // Si el usuario es un empleado, abre EmpleadoFrame
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        backgroundPanel.add(btnLogin);

        // Botón de Regresar
        RoundedButton btnRegresar = new RoundedButton("Regresar");
        btnRegresar.setBounds(260, 200, 150, 50);
        btnRegresar.addActionListener(e -> dispose());
        backgroundPanel.add(btnRegresar);

        // Hacer visible la ventana
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Método para validar las credenciales
    private boolean validarCredenciales(String usuario, String password) {
        boolean esValido = false;
        boolean esEmpleado = false;

        // Intentar conectar a la base de datos
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Brownie5")) {

            // Verificar si el usuario es Empleado
            String queryEmpleado = "SELECT * FROM EMPLEADO WHERE usuario = ? AND contraseña = ?";
            try (PreparedStatement stmtEmpleado = conn.prepareStatement(queryEmpleado)) {
                stmtEmpleado.setString(1, usuario);
                stmtEmpleado.setString(2, password);  // Contraseña para empleados sin hash
                ResultSet rsEmpleado = stmtEmpleado.executeQuery();

                if (rsEmpleado.next()) {
                    esValido = true;  // Credenciales correctas
                    esEmpleado = true;  // Es un empleado

                    // Crear un objeto Empleado con datos de la base de datos
                    String email = rsEmpleado.getString("email");
                    String apellido = rsEmpleado.getString("apellido");
                    String nombre = rsEmpleado.getString("nombre");
                    
                    Empleado empleado = new Empleado(usuario, email, password, apellido, nombre);
                    new EmpleadoFrame(empleado).setVisible(true); // Abre la ventana de empleado
                }
            }

            // Si no es empleado, verificar si es cliente
            if (!esValido) {
                String queryCliente = "SELECT * FROM CLIENTE WHERE usuario = ? AND contraseña = ?";
                try (PreparedStatement stmtCliente = conn.prepareStatement(queryCliente)) {
                    stmtCliente.setString(1, usuario);

                    // Encriptar la contraseña para los clientes
                    String hashedPassword = hashPassword(password);
                    stmtCliente.setString(2, hashedPassword);
                    ResultSet rsCliente = stmtCliente.executeQuery();

                    if (rsCliente.next()) {
                        esValido = true;  // Credenciales correctas

                        // Redirigir a la ventana de cliente
                        new BienvenidaFrame(usuario).setVisible(true);
                    }
                }
            }

            // Si las credenciales son válidas, cerrar la ventana de login
            if (esValido) {
                dispose();  // Cierra la ventana de login
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return esValido;
    }

 // Método para encriptar la contraseña con SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Métodos auxiliares
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

    private JPanel createBackgroundPanel(String titleText) {
        JPanel backgroundPanel = new JPanel(null) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(new java.awt.GradientPaint(0, 0, new Color(74, 0, 224), 0, getHeight(), new Color(142, 45, 226)));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setBounds(0, 0, 400, 300);

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 20, 400, 30);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(title);

        return backgroundPanel;
    }

    // Clase personalizada para crear botones redondeados
    private static class RoundedButton extends JButton {
        private static final long serialVersionUID = 1L;

        public RoundedButton(String text) {
            super(text);
            setFont(new Font("Arial", Font.BOLD, 16));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fondo del botón
            g2.setColor(new Color(142, 45, 226));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            // Hover del botón
            if (getModel().isRollover()) {
                g2.setColor(new Color(74, 0, 224));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }

            // Texto del botón
            super.paintComponent(g2);
        }

        @Override
        public void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
