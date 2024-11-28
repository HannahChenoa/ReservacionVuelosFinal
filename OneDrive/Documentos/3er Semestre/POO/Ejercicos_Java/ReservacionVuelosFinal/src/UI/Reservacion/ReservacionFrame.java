package UI.Reservacion;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import SourceControl.Vuelo.Vuelo;
import UI.Aerolineas.*;

public class ReservacionFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private final Vuelo vuelo; // Ahora se pasa el objeto Vuelo
	private final ArrayList<String> asientosSeleccionados = new ArrayList<>();
	private int costoTotal = 0;

	public ReservacionFrame(Vuelo vuelo) {
		this.vuelo = vuelo; // Inicializa el objeto Vuelo

		setTitle("Reservación de Vuelo");
		setSize(900, 800);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		// Panel principal con fondo
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

		// Panel inferior que contiene la leyenda, el botón de reservación y el botón de
		// regresar
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setOpaque(false);

		// Leyenda
		JPanel legendPanel = new JPanel(new GridLayout(1, 4, 20, 0));
		legendPanel.setOpaque(false);
		legendPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		JLabel economyLegend = new JLabel("Económica (Azul)", SwingConstants.CENTER);
		economyLegend.setFont(new Font("Arial", Font.PLAIN, 16));
		economyLegend.setForeground(Color.BLUE);

		JLabel executiveLegend = new JLabel("Ejecutiva (Amarillo)", SwingConstants.CENTER);
		executiveLegend.setFont(new Font("Arial", Font.PLAIN, 16));
		executiveLegend.setForeground(Color.ORANGE);

		JLabel reservedLegend = new JLabel("Reservado (Gris)", SwingConstants.CENTER);
		reservedLegend.setFont(new Font("Arial", Font.PLAIN, 16));
		reservedLegend.setForeground(Color.GRAY);

		JLabel unavailableLegend = new JLabel("No disponible (Rojo)", SwingConstants.CENTER);
		unavailableLegend.setFont(new Font("Arial", Font.PLAIN, 16));
		unavailableLegend.setForeground(Color.RED);

		legendPanel.add(economyLegend);
		legendPanel.add(executiveLegend);
		legendPanel.add(reservedLegend);
		legendPanel.add(unavailableLegend);

		bottomPanel.add(legendPanel, BorderLayout.NORTH);

		// Botón de reservación
		JButton btnReservar = new JButton("Reservar");
		btnReservar.setFont(new Font("Arial", Font.BOLD, 16));
		btnReservar.setBackground(new Color(74, 0, 224));
		btnReservar.setForeground(Color.WHITE);
		btnReservar.setFocusPainted(false);
		btnReservar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReservar.addActionListener(e -> mostrarResumen());
		bottomPanel.add(btnReservar, BorderLayout.SOUTH);

		// Botón de regresar
		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.setFont(new Font("Arial", Font.BOLD, 16));
		btnRegresar.setBackground(Color.GRAY);
		btnRegresar.setForeground(Color.WHITE);
		btnRegresar.setFocusPainted(false);
		btnRegresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRegresar.addActionListener(e -> {
			new AerolineaFrame("Aeroméxico", 1); // O pasar el idAerolinea correcto
			dispose(); // Cierra la ventana actual y regresa a AerolineaFrame
		});
		bottomPanel.add(btnRegresar, BorderLayout.NORTH);

		backgroundPanel.add(bottomPanel, BorderLayout.SOUTH);

		// Cargar los asientos del vuelo
		cargarAsientos(seatsPanel);

		setLocationRelativeTo(null);
		setVisible(true);
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

	private void cargarAsientos(JPanel seatsPanel) {
		try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
				"Brownie5");
				PreparedStatement stmt = conn.prepareStatement(
						"select * from asiento order by numeroasiento asc")) {
			ResultSet rs = stmt.executeQuery();

			// Panel principal con GridLayout para separar las columnas
			seatsPanel.setLayout(new GridLayout(0, 7, 10, 10)); // 7 columnas totales (3, espacio, 3)

			int columnCounter = 0;

			while (rs.next()) {
				String numeroAsiento = rs.getString("numeroAsiento");
				String clase = rs.getString("clase");
		

				 String estadoAsientoQuery  = "Select * from vuelo_asiento_usuario where idasiento = ? and idvuelo = ?";
	                PreparedStatement estadoAsientoStmt = conn.prepareStatement(estadoAsientoQuery);
	                estadoAsientoStmt.setInt(1, rs.getInt("idasiento"));
	                estadoAsientoStmt.setInt(2, vuelo.getIdVuelo());
	               ResultSet estadoasientoResult= estadoAsientoStmt.executeQuery();
	               
	               
				// Crear un botón para cada asiento
				JButton seatButton = new JButton(numeroAsiento);
				seatButton.setFont(new Font("Arial", Font.BOLD, 12));
				seatButton.setPreferredSize(new Dimension(80, 80));
				seatButton.setFocusPainted(false);

				// Configurar el color y la habilitación del botón según el estado
				if (!estadoasientoResult.next()) {
					seatButton.setBackground("Económica".equalsIgnoreCase(clase) ? Color.BLUE : Color.ORANGE);
					seatButton.setForeground(Color.WHITE);
					int costo = "Económica".equalsIgnoreCase(clase) ? 500 : 1000; // Costo por clase
					seatButton.addActionListener(e -> {
						// Si el asiento ya fue seleccionado, deseleccionarlo
						if (asientosSeleccionados.contains(numeroAsiento)) {
							// Restaurar el color original del asiento
							seatButton.setBackground("Económica".equalsIgnoreCase(clase) ? Color.BLUE : Color.ORANGE);
							asientosSeleccionados.remove(numeroAsiento);
							costoTotal -= costo;
							JOptionPane.showMessageDialog(this,
									"Asiento " + numeroAsiento + " deseleccionado. Costo: -$" + costo);
						} else {
							// Cambiar el color a gris cuando se seleccione
							seatButton.setBackground(Color.GRAY);
							asientosSeleccionados.add(numeroAsiento);
							costoTotal += costo;
							JOptionPane.showMessageDialog(this,
									"Asiento " + numeroAsiento + " seleccionado. Costo: $" + costo);
						}
					});
				} else {
					// Si el asiento está reservado, lo mostramos en rojo y lo deshabilitamos
					seatButton.setBackground(Color.RED);
					seatButton.setForeground(Color.WHITE);
					seatButton.setEnabled(false); // Deshabilita la selección
				}

				// Agregar asiento al panel
				if (columnCounter == 3) {
					seatsPanel.add(Box.createGlue()); // Espacio vacío en la columna 4
					columnCounter++;
				}
				seatsPanel.add(seatButton);
				columnCounter++;

				// Reiniciar contador después de llenar 7 columnas
				if (columnCounter == 7) {
					columnCounter = 0;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error al cargar los asientos: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void mostrarResumen() {
		new ResumenFrame(vuelo, asientosSeleccionados, costoTotal); // Pasa el objeto Vuelo completo
		dispose(); // Cerrar la ventana actual
	}

}
