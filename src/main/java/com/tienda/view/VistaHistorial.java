package com.tienda.view;

import com.tienda.controller.VentaController;
import com.tienda.model.Venta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Vista del módulo de Historial de Ventas
 * Permite el seguimiento de auditoría requerido por el perfil de Administrador.
 * Unidad V: Persistencia (Memoria RAM en este caso para fines académicos)
 */
public class VistaHistorial extends JPanel {
    private VentaController ventaController;
    private JTable tablaHistorial;
    private DefaultTableModel modeloHistorial;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public VistaHistorial(VentaController ventaController) {
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
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        panelSuperior.add(titulo);

        panelSuperior.add(Box.createHorizontalStrut(20));

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBackground(new Color(0, 120, 215));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.addActionListener(e -> actualizarTabla());
        panelSuperior.add(btnActualizar);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla de historial
        String[] columnas = { "Fecha/Hora", "Forma de Pago", "Cant. Productos", "Promoción", "Total Neto" };
        modeloHistorial = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaHistorial = new JTable(modeloHistorial);
        tablaHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaHistorial.setRowHeight(30);
        tablaHistorial.setBackground(new Color(55, 55, 55));
        tablaHistorial.setForeground(Color.WHITE);
        tablaHistorial.setGridColor(new Color(70, 70, 70));
        tablaHistorial.getTableHeader().setBackground(new Color(35, 35, 35));
        tablaHistorial.getTableHeader().setForeground(Color.WHITE);
        tablaHistorial.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaHistorial);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
        scrollPane.getViewport().setBackground(new Color(55, 55, 55));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void actualizarTabla() {
        try {
            modeloHistorial.setRowCount(0);
            List<Venta> ventas = ventaController.obtenerTodasVentas();

            for (Venta venta : ventas) {
                String promoNombre = (venta.getPromocionAplicada() != null)
                        ? venta.getPromocionAplicada().getNombrePromo()
                        : "Ninguna";

                Object[] fila = {
                        venta.getFecha().format(dtf),
                        venta.getFormaPago(),
                        venta.getListaProductos().size(),
                        promoNombre,
                        "$" + df.format(venta.getTotal())
                };
                modeloHistorial.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar historial: " + e.getMessage(),
                    "Error de Persistencia", JOptionPane.ERROR_MESSAGE);
        }
    }
}
