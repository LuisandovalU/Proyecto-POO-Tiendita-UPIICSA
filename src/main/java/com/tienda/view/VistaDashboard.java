package com.tienda.view;

import com.tienda.controller.ControladorVentas;
import com.tienda.controller.ProductoController;
import com.tienda.model.DetalleVenta;
import com.tienda.model.Frescos;
import com.tienda.model.Producto;
import com.tienda.model.Venta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * VISTA: VistaDashboard (Unidad IV - GUI)
 * Esta es la pantalla principal para el Administrador.
 */
public class VistaDashboard extends JPanel {
    // // Aquí hice un resumen ejecutivo para el Admin. Así, apenas entra, ve qué se
    // está vendiendo más y qué le urge liquidar porque ya va a caducar. ¡Data
    // Science nivel Dios!
    private ProductoController productoController;
    private ControladorVentas ventaController;

    private JTable tablaTopVentas;
    private DefaultTableModel modeloTopVentas;
    private JTable tablaCaducidades;
    private DefaultTableModel modeloCaducidades;

    public VistaDashboard(ProductoController prodCtrl, ControladorVentas ventaCtrl) {
        this.productoController = prodCtrl;
        this.ventaController = ventaCtrl;

        // Me suscribo a cambios en el catálogo para actualizar caducidades
        this.productoController.addChangeListener(this::actualizarDatos);

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(45, 45, 45));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inicializarComponentes();
        actualizarDatos();
    }

    private void inicializarComponentes() {
        JLabel titulo = new JLabel("Resumen de Operaciones (Dashboard)");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28)); // ¡Bien grande!
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);

        JPanel panelTablas = new JPanel(new GridLayout(1, 2, 20, 20));
        panelTablas.setBackground(new Color(45, 45, 45));

        // Tabla 1: Top Ventas
        panelTablas.add(crearSeccionTabla("Top 5 Productos Más Vendidos",
                new String[] { "Producto", "Ventas" },
                modeloTopVentas = new DefaultTableModel(new String[] { "Producto", "Ventas" }, 0),
                tablaTopVentas = new JTable()));

        // Tabla 2: Caducidades
        panelTablas.add(crearSeccionTabla("Productos Próximos a Caducar (< 3 días)",
                new String[] { "Producto", "Fecha", "Status" },
                modeloCaducidades = new DefaultTableModel(new String[] { "Producto", "Fecha", "Status" }, 0),
                tablaCaducidades = new JTable()));

        add(panelTablas, BorderLayout.CENTER);

        JButton btnActualizar = new JButton("ACTUALIZAR DATOS");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnActualizar.setBackground(new Color(0, 120, 215));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setPreferredSize(new Dimension(300, 60)); // Botón imponente
        btnActualizar.addActionListener(e -> actualizarDatos());
        add(btnActualizar, BorderLayout.SOUTH);
    }

    private JPanel crearSeccionTabla(String titulo, String[] columnas, DefaultTableModel modelo, JTable tabla) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(35, 35, 35));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 70, 70)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22)); // Títulos de sección claros
        lblTitulo.setForeground(new Color(0, 200, 100));
        panel.add(lblTitulo, BorderLayout.NORTH);

        tabla.setModel(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // Filas legibles
        tabla.setRowHeight(40);
        tabla.setBackground(new Color(55, 55, 55));
        tabla.setForeground(Color.WHITE);
        tabla.setGridColor(new Color(70, 70, 70));
        tabla.getTableHeader().setBackground(new Color(25, 25, 25));
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(new Color(55, 55, 55));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    public void actualizarDatos() {
        actualizarTopVentas();
        actualizarCaducidades();
    }

    private void actualizarTopVentas() {
        modeloTopVentas.setRowCount(0);
        Map<String, Integer> conteoProductos = new HashMap<>();

        List<Venta> todasVentas = ventaController.obtenerTodasVentas();
        for (Venta v : todasVentas) {
            for (DetalleVenta d : v.getDetalles()) {
                String nombre = d.getProducto().getNombre();
                conteoProductos.put(nombre, conteoProductos.getOrDefault(nombre, 0) + d.getCantidad());
            }
        }

        conteoProductos.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> modeloTopVentas.addRow(new Object[] { e.getKey(), e.getValue() }));
    }

    private void actualizarCaducidades() {
        modeloCaducidades.setRowCount(0);
        List<Producto> productos = productoController.obtenerTodosProductos();

        for (Producto p : productos) {
            if (p instanceof Frescos) {
                Frescos f = (Frescos) p;
                if (f.alertarCaducidad()) {
                    modeloCaducidades.addRow(new Object[] {
                            f.getNombre(),
                            f.getFechaCaducidad(),
                            "<html><font color='red'>CRÍTICO</font></html>"
                    });
                }
            }
        }
    }
}
