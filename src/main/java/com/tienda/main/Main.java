package com.tienda.main;

import com.tienda.view.MainWindow;
import javax.swing.SwingUtilities;

/**
 * Clase principal para ejecutar la aplicación
 * Inicia directamente con perfil de Vendedor
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Ejecutar en el hilo de eventos de Swing
            SwingUtilities.invokeLater(() -> {
                try {
                    MainWindow mainWindow = new MainWindow();
                    mainWindow.setVisible(true);
                } catch (Exception e) {
                    System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                    e.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(null,
                        "Error al iniciar la aplicación: " + e.getMessage(),
                        "Error Fatal", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            });
        } catch (Exception e) {
            System.err.println("Error crítico al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
