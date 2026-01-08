package com.tienda.view;

import com.tienda.controller.ControladorVentas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Vista del módulo de Historial de Ventas
 * Permite el seguimiento de auditoría requerido por el perfil de Administrador.
 * Unidad V: Persistencia (Memoria RAM en este caso para fines académicos)
 */
public class VistaHistorial extends JPanel {
    private ControladorVentas ventaController;
    private JTable tablaHistorial;
    private DefaultTableModel modeloHistorial;
    private JLabel lblTotalEfectivo;
    private JLabel lblTotalTarjeta;
    private java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00");

    public VistaHistorial(ControladorVentas ventaController) {
        this.ventaController = ventaController;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(45, 45, 45));

        inicializarComponentes();
        actualizarTabla();
    }

    private void inicializarComponentes() {
        // Panel superior con título y actualizar
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(new Color(35, 35, 35));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Historial de Ventas - Auditoría");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24)); // ¡Visible!
        titulo.setForeground(Color.WHITE);
        panelSuperior.add(titulo);

        panelSuperior.add(Box.createHorizontalStrut(20));

        JButton btnActualizar = new JButton("ACTUALIZAR");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnActualizar.setBackground(new Color(0, 120, 215));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setPreferredSize(new Dimension(150, 45));
        btnActualizar.addActionListener(e -> actualizarTabla());
        panelSuperior.add(btnActualizar);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla de historial
        // REQUERIMIENTO: Folio, Fecha, Total y si hubo Promociones aplicadas
        String[] columnas = { "Folio", "Fecha/Hora", "Total Neto", "Pago", "Promociones" };
        modeloHistorial = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaHistorial = new JTable(modeloHistorial);
        tablaHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tablaHistorial.setRowHeight(40);
        tablaHistorial.setBackground(new Color(55, 55, 55));
        tablaHistorial.setForeground(Color.WHITE);
        tablaHistorial.setGridColor(new Color(70, 70, 70));
        tablaHistorial.getTableHeader().setBackground(new Color(35, 35, 35));
        tablaHistorial.getTableHeader().setForeground(Color.WHITE);
        tablaHistorial.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
        scrollPane.getViewport().setBackground(new Color(55, 55, 55));
        add(scrollPane, BorderLayout.CENTER);

        // Panel de Totales por Forma de Pago (Unidad V)
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        panelInferior.setBackground(new Color(35, 35, 35));

        lblTotalEfectivo = new JLabel("Efectivo: $0.00");
        lblTotalEfectivo.setForeground(new Color(0, 200, 100));
        lblTotalEfectivo.setFont(new Font("Segoe UI", Font.BOLD, 18)); // ¡Más grande!

        lblTotalTarjeta = new JLabel("Tarjeta: $0.00");
        lblTotalTarjeta.setForeground(new Color(0, 120, 215));
        lblTotalTarjeta.setFont(new Font("Segoe UI", Font.BOLD, 18));

        panelInferior.add(lblTotalEfectivo);
        panelInferior.add(lblTotalTarjeta);
        add(panelInferior, BorderLayout.SOUTH);
    }

    /**
     * Refresca la tabla recuperando la lista completa desde el controlador.
     * PROPÓSITO ACADÉMICO: Esta sincronización cumple con el requerimiento de
     * 'auditoría en tiempo real' para el perfil de Administrador definido
     * en el ejercicio de 'La Tienda de la Esquina'.
     */
    public void actualizarTabla() {
        try {
            if (ventaController != null) {
                ventaController.actualizarTablaHistorial(modeloHistorial);

                // Actualizar labels de totales (Abstracción de Cremería/Tienda)
                double totalEfectivo = ventaController.obtenerTotalPorFormaPago("Efectivo");
                double totalTarjeta = ventaController.obtenerTotalPorFormaPago("Tarjeta");

                lblTotalEfectivo.setText("Total Efectivo: $" + df.format(totalEfectivo));
                lblTotalTarjeta.setText("Total Tarjeta: $" + df.format(totalTarjeta));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar historial: " + e.getMessage(),
                    "Error de Persistencia", JOptionPane.ERROR_MESSAGE);
        }
    }
}
