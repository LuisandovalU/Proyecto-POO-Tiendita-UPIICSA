package com.tienda.view;

import com.tienda.controller.ProductoController;
import com.tienda.model.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Vista del módulo de Inventario/Productos
 * Permite gestionar el inventario de productos
 */
public class VistaInventario extends JPanel {
    private ProductoController productoController;
    private JTable tablaProductos;
    private DefaultTableModel modeloProductos;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public VistaInventario(ProductoController productoController) {
        this.productoController = productoController;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(45, 45, 45));

        inicializarComponentes();
        actualizarTabla();
    }

    private void inicializarComponentes() {
        // Panel superior con título y botones
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(new Color(35, 35, 35));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Inventario de Productos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        panelSuperior.add(titulo);

        panelSuperior.add(Box.createHorizontalStrut(20));

        JButton btnAgregar = new JButton("Agregar Producto");
        btnAgregar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAgregar.setBackground(new Color(0, 150, 0));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.addActionListener(e -> mostrarDialogoProducto(null));
        panelSuperior.add(btnAgregar);

        JButton btnEditar = new JButton("Editar Producto");
        btnEditar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnEditar.setBackground(new Color(200, 150, 0));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.addActionListener(e -> editarProductoSeleccionado());
        panelSuperior.add(btnEditar);

        JButton btnEliminar = new JButton("Eliminar Producto");
        btnEliminar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnEliminar.setBackground(new Color(200, 0, 0));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.addActionListener(e -> eliminarProductoSeleccionado());
        panelSuperior.add(btnEliminar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnActualizar.setBackground(new Color(0, 120, 215));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(e -> actualizarTabla());
        panelSuperior.add(btnActualizar);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla de productos
        String[] columnas = { "Código", "Nombre", "Marca", "Tamaño", "Precio Compra",
                "Precio Venta", "Stock Actual", "Stock Mínimo", "Tipo" };
        modeloProductos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaProductos = new JTable(modeloProductos);
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaProductos.setRowHeight(30);
        tablaProductos.setBackground(new Color(55, 55, 55));
        tablaProductos.setForeground(Color.WHITE);
        tablaProductos.setGridColor(new Color(70, 70, 70));
        tablaProductos.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.getTableHeader().setBackground(new Color(35, 35, 35));
        tablaProductos.getTableHeader().setForeground(Color.WHITE);
        tablaProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
        scrollPane.getViewport().setBackground(new Color(55, 55, 55));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void editarProductoSeleccionado() {
        int fila = tablaProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para editar");
            return;
        }
        String codigo = (String) modeloProductos.getValueAt(fila, 0);
        Producto p = productoController.buscarPorCodigoBarras(codigo);
        mostrarDialogoProducto(p);
    }

    private void eliminarProductoSeleccionado() {
        int fila = tablaProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar");
            return;
        }
        String codigo = (String) modeloProductos.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el producto " + codigo + "?");
        if (confirm == JOptionPane.YES_OPTION) {
            if (productoController.eliminarProducto(codigo)) {
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Producto eliminado");
            }
        }
    }

    private void mostrarDialogoProducto(Producto p) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField txtCodigo = new JTextField(p != null ? p.getCodigoBarras() : "");
        JTextField txtNombre = new JTextField(p != null ? p.getNombre() : "");
        JTextField txtMarca = new JTextField(p != null ? p.getMarca() : "");
        JTextField txtTamano = new JTextField(p != null ? p.getTamanoGramaje() : "");
        JTextField txtPrecioC = new JTextField(p != null ? String.valueOf(p.getPrecioCompra()) : "0.0");
        JTextField txtPrecioV = new JTextField(p != null ? String.valueOf(p.getPrecioVenta()) : "0.0");
        JTextField txtStockA = new JTextField(p != null ? String.valueOf(p.getStockActual()) : "0");
        JTextField txtStockM = new JTextField(p != null ? String.valueOf(p.getStockMinimo()) : "0");
        JComboBox<String> comboTipo = new JComboBox<>(
                new String[] { "Abarrotes", "Frescos", "Dulcería", "Limpieza", "Otros" });

        if (p != null) {
            txtCodigo.setEnabled(false);
            comboTipo.setSelectedItem(obtenerTipoProducto(p));
            comboTipo.setEnabled(false);
        }

        panel.add(new JLabel("Código:"));
        panel.add(txtCodigo);
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Marca:"));
        panel.add(txtMarca);
        panel.add(new JLabel("Tamaño:"));
        panel.add(txtTamano);
        panel.add(new JLabel("Precio Compra:"));
        panel.add(txtPrecioC);
        panel.add(new JLabel("Precio Venta:"));
        panel.add(txtPrecioV);
        panel.add(new JLabel("Stock Actual:"));
        panel.add(txtStockA);
        panel.add(new JLabel("Stock Mínimo:"));
        panel.add(txtStockM);
        panel.add(new JLabel("Tipo:"));
        panel.add(comboTipo);

        int result = JOptionPane.showConfirmDialog(this, panel, p == null ? "Agregar Producto" : "Editar Producto",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Producto nuevo;
                String tipo = (String) comboTipo.getSelectedItem();
                // Creamos instancia según tipo básico para simplificar manual CRUD
                switch (tipo) {
                    case "Frescos":
                        nuevo = new com.tienda.model.Frescos();
                        break;
                    case "Abarrotes":
                        nuevo = new com.tienda.model.Abarrotes();
                        break;
                    case "Dulcería":
                        nuevo = new com.tienda.model.Dulceria();
                        break;
                    case "Limpieza":
                        nuevo = new com.tienda.model.Limpieza();
                        break;
                    default:
                        nuevo = new com.tienda.model.Otros();
                        break;
                }

                nuevo.setCodigoBarras(txtCodigo.getText());
                nuevo.setNombre(txtNombre.getText());
                nuevo.setMarca(txtMarca.getText());
                nuevo.setTamanoGramaje(txtTamano.getText());
                nuevo.setPrecioCompra(Double.parseDouble(txtPrecioC.getText()));
                nuevo.setPrecioVenta(Double.parseDouble(txtPrecioV.getText()));
                nuevo.setStockActual(Integer.parseInt(txtStockA.getText()));
                nuevo.setStockMinimo(Integer.parseInt(txtStockM.getText()));

                if (p == null) {
                    productoController.agregarProducto(nuevo);
                } else {
                    productoController.actualizarProducto(nuevo);
                }
                actualizarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en los datos: " + ex.getMessage());
            }
        }
    }

    private void actualizarTabla() {
        try {
            modeloProductos.setRowCount(0);
            java.util.List<Producto> productos = productoController.obtenerTodosProductos();

            for (Producto producto : productos) {
                // --- FILTRO DE INVENTARIO (Unidad I: Concepto de Objeto) ---
                // El Tiempo Aire es un servicio, no un producto físico con stock controlable.
                // Por lo tanto, se excluye de este módulo de gestión de bienes tangibles.
                if (producto instanceof com.tienda.model.TiempoAire)
                    continue;

                String tipo = obtenerTipoProducto(producto);
                Object[] fila = {
                        producto.getCodigoBarras(),
                        producto.getNombre(),
                        producto.getMarca(),
                        producto.getTamanoGramaje(),
                        "$" + df.format(producto.getPrecioCompra()),
                        "$" + df.format(producto.getPrecioVenta()),
                        producto.getStockActual(),
                        producto.getStockMinimo(),
                        tipo
                };
                modeloProductos.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar productos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerTipoProducto(Producto producto) {
        if (producto instanceof com.tienda.model.Frescos)
            return "Frescos";
        if (producto instanceof com.tienda.model.Abarrotes)
            return "Abarrotes";
        if (producto instanceof com.tienda.model.Dulceria)
            return "Dulcería";
        if (producto instanceof com.tienda.model.Limpieza)
            return "Limpieza";
        if (producto instanceof com.tienda.model.Otros)
            return "Otros";
        return "Desconocido";
    }
}
