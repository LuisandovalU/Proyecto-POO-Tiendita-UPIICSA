package com.tienda.main;

import com.tienda.view.MainWindow;
import javax.swing.SwingUtilities;

/**
 * CLASE PRINCIPAL: Main (Donde empieza todo)
 * Este es el punto de arranque de mi programa. Yo lo configuré para que
 * abra la MainWindow y de ahí ya el usuario pueda navegar por toda la tienda.
 * Inicia directamente como Vendedor por si no queremos pasar por el login.
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
