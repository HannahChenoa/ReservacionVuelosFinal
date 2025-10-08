package UI.Main;

import javax.swing.SwingUtilities;

/**
 * Clase principal que inicia la aplicación gráfica.
 * Utiliza Swing para crear y mostrar la interfaz principal.
 */
public class MainApp {

    /**
     * Método principal que sirve como punto de entrada para la aplicación.
     * Ejecuta la creación de la interfaz principal en el hilo de eventos de Swing.
     *
     * @param args Argumentos de la línea de comandos (no utilizados en esta implementación).
     */
    public static void main(String[] args) {
        // Ejecuta la creación y visualización de la interfaz principal en el hilo de eventos de Swing.
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(); // Instancia la interfaz principal.
            mainFrame.setVisible(true); // Hace visible la ventana principal.
        });
    }
}