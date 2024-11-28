package UI.NuevaCuenta;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrearCuentaFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public CrearCuentaFrame() {
        setTitle("Crear Cuenta");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Fondo con degradado
        JPanel backgroundPanel = createBackgroundPanel("Crear Cuenta");
        add(backgroundPanel);

        // Campos de texto y etiquetas
        JLabel lblNombre = createLabel("Nombre:");
        lblNombre.setBounds(100, 100, 150, 30);
        backgroundPanel.add(lblNombre);

        JTextField txtNombre = createTextField();
        txtNombre.setBounds(250, 100, 200, 30);
        backgroundPanel.add(txtNombre);
        
        JLabel lblApellido = createLabel("Apellido:");
        lblApellido.setBounds(100, 150, 150, 30);
        backgroundPanel.add(lblApellido);

        JTextField txtApellido = createTextField();
        txtApellido.setBounds(250, 150, 200, 30);
        backgroundPanel.add(txtApellido);
        
        JLabel lblUsuario = createLabel("Usuario:");
        lblUsuario.setBounds(100, 200, 150, 30);
        backgroundPanel.add(lblUsuario);

        JTextField txtUsuario = createTextField();
        txtUsuario.setBounds(250, 200, 200, 30);
        backgroundPanel.add(txtUsuario);

        JLabel lblCorreo = createLabel("Correo:");
        lblCorreo.setBounds(100, 250, 150, 30);
        backgroundPanel.add(lblCorreo);

        JTextField txtCorreo = createTextField();
        txtCorreo.setBounds(250, 250, 200, 30);
        backgroundPanel.add(txtCorreo);

        JLabel lblPassword = createLabel("Contraseña:");
        lblPassword.setBounds(100, 300, 150, 30);
        backgroundPanel.add(lblPassword);

        JPasswordField txtPassword = new JPasswordField();
        txtPassword.setBounds(250, 300, 200, 30);
        txtPassword.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        backgroundPanel.add(txtPassword);

        JLabel lblConfirmPassword = createLabel("Confirmar Contraseña:");
        lblConfirmPassword.setBounds(100, 350, 150, 30);
        backgroundPanel.add(lblConfirmPassword);

        JPasswordField txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setBounds(250, 350, 200, 30);
        txtConfirmPassword.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        backgroundPanel.add(txtConfirmPassword);

        // Botón Crear Cuenta
        RoundedButton btnCrearCuenta = new RoundedButton("Crear Cuenta");
        btnCrearCuenta.setBounds(150, 400, 200, 50);
        btnCrearCuenta.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String usuario = txtUsuario.getText().trim();
            String correo = txtCorreo.getText().trim();
            String apellido = txtApellido.getText().trim();
            String password = new String(txtPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());

            // Validar campos vacíos
            if (nombre.isEmpty() || usuario.isEmpty() || correo.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()|| apellido.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar formato de correo
            if (!isValidEmail(correo)) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un correo válido.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar contraseñas coincidentes
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Encriptar contraseña usando SHA-256
            String hashedPassword = hashPassword(password);

            if (hashedPassword == null) {
                JOptionPane.showMessageDialog(this, "Error al encriptar la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Conexión a la base de datos e inserción de datos
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "Brownie5");
                final String SQL_INSERT = "INSERT INTO CLIENTE (usuario, nombre, email, contraseña,apellido) VALUES (?, ?, ?, ?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
                preparedStatement.setString(1, usuario);
                preparedStatement.setString(2, nombre);
                preparedStatement.setString(3, correo);
                preparedStatement.setString(4, hashedPassword); // Almacenar contraseña encriptada
                preparedStatement.setString(5, apellido);
                int row = preparedStatement.executeUpdate();

                if (row > 0) {
                    JOptionPane.showMessageDialog(this, "Cuenta creada exitosamente.");
                    dispose();
                }

                connection.close();
            } catch (SQLException ex) {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }
                }
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        backgroundPanel.add(btnCrearCuenta);

        // Botón de Regresar
        RoundedButton btnRegresar = new RoundedButton("Regresar");
        btnRegresar.setBounds(150, 420, 200, 50);
        btnRegresar.addActionListener(e -> dispose());
        backgroundPanel.add(btnRegresar);

        // Hacer visible la ventana
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Método para encriptar la contraseña usando SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para validar correos
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    // Métodos auxiliares
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 16));
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
        backgroundPanel.setBounds(0, 0, 500, 500);

        JLabel title = new JLabel(titleText);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 20, 500, 30);
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

            g2.setColor(new Color(142, 45, 226));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            if (getModel().isRollover()) {
                g2.setColor(new Color(74, 0, 224));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }

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
        new CrearCuentaFrame();
    }
}
