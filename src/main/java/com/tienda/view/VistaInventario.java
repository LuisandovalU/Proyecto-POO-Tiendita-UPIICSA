package com.tienda.view;

import com.tienda.controller.ProductoController;
import com.tienda.model.Producto;
import com.tienda.model.Frescos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * VISTA: VistaInventario (Unidad V - Interfaz Gráfica)
 * Aquí es donde el Administrador ve qué productos hay y cuáles ya se acabaron.
 * Me aseguré de que se pueda agregar, editar y eliminar, para que el
 * CRUD (Unidad II) esté completo y la profe no nos regañe.
 */
public class VistaInventario extends JPanel {
    private ProductoController productoController;
    private JTable tablaProductos;
    private DefaultTableModel modeloProductos;
    private JTable tablaAlertas;
    private DefaultTableModel modeloAlertas;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public VistaInventario(ProductoController productoController) {
        this.productoController = productoController;

        // También me suscribo aquí por si acaso, para que el inventario siempre esté al
        // día
        // sin tener que picarle al botón de actualizar.
        this.productoController.addChangeListener(() -> {
            actualizarTabla();
        });

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(45, 45, 45));

        inicializarComponentes();
        actualizarTabla();
    }

    private void inicializarComponentes() {
        // Panel de arriba con el título del módulo y los botones de acción.
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.setBackground(new Color(35, 35, 35));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Inventario de Productos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24)); // ¡Más grande!
        titulo.setForeground(Color.WHITE);
        panelSuperior.add(titulo);

        panelSuperior.add(Box.createHorizontalStrut(20));

        // Botón para meter nuevos productos al sistema.
        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAgregar.setBackground(new Color(0, 150, 0));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setPreferredSize(new Dimension(150, 45));
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregar.addActionListener(e -> mostrarDialogoProducto(null));
        panelSuperior.add(btnAgregar);

        // Botón por si nos equivocamos en el precio o el nombre.
        JButton btnEditar = new JButton("EDITAR");
        btnEditar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEditar.setBackground(new Color(200, 150, 0));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setPreferredSize(new Dimension(150, 45));
        btnEditar.addActionListener(e -> editarProductoSeleccionado());
        panelSuperior.add(btnEditar);

        // Botón para quitar productos que ya no vendemos.
        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnEliminar.setBackground(new Color(200, 0, 0));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setPreferredSize(new Dimension(150, 45));
        btnEliminar.addActionListener(e -> eliminarProductoSeleccionado());
        panelSuperior.add(btnEliminar);

        // Botón para refrescar la tabla si algo cambió.
        JButton btnActualizar = new JButton("ACTUALIZAR");
        btnActualizar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnActualizar.setBackground(new Color(0, 120, 215));
        btnActualizar.setForeground(Color.WHITE);
        btnActualizar.setPreferredSize(new Dimension(150, 45));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(e -> actualizarTabla());
        panelSuperior.add(btnActualizar);

        add(panelSuperior, BorderLayout.NORTH);

        // La tabla donde salen todos los datos (Polimorfismo en la Unidad III).
        String[] columnas = { "Código", "Nombre", "Marca", "Tamaño", "Precio Compra",
                "Precio Venta", "Stock Actual", "Stock Mínimo", "Tipo" };
        modeloProductos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Nadie puede editar directo en la celda, tienen que usar el botón.
            }
        };

        tablaProductos = new JTable(modeloProductos);
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Tabla legible
        tablaProductos.setRowHeight(40);
        tablaProductos.setBackground(new Color(55, 55, 55));
        tablaProductos.setForeground(Color.WHITE);
        tablaProductos.setGridColor(new Color(70, 70, 70));
        tablaProductos.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.getTableHeader().setBackground(new Color(35, 35, 35));
        tablaProductos.getTableHeader().setForeground(Color.WHITE);
        tablaProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
        scrollPane.getViewport().setBackground(new Color(55, 55, 55));
        add(scrollPane, BorderLayout.CENTER);

        // Panel de Alertas de Stock Crítico (Integrado aquí en vez de Dashboard)
        JPanel panelAlertas = crearPanelAlertas();
        add(panelAlertas, BorderLayout.SOUTH);
    }

    private JPanel crearPanelAlertas() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(45, 30, 30)); // Fondo rojizo para alertar
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(200, 50, 50)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        panel.setPreferredSize(new Dimension(0, 200));

        JLabel tituloAlertas = new JLabel("⚠️ ALERTAS DE STOCK CRÍTICO (REABASTECIMIENTO)");
        tituloAlertas.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tituloAlertas.setForeground(new Color(255, 100, 100));
        panel.add(tituloAlertas, BorderLayout.NORTH);

        String[] columnasAlertas = { "Producto", "Marca", "Stock Actual", "Stock Mínimo", "Status" };
        modeloAlertas = new DefaultTableModel(columnasAlertas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaAlertas = new JTable(modeloAlertas);
        tablaAlertas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaAlertas.setRowHeight(35);
        tablaAlertas.setBackground(new Color(60, 40, 40));
        tablaAlertas.setForeground(Color.WHITE);
        tablaAlertas.getTableHeader().setBackground(new Color(80, 20, 20));
        tablaAlertas.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollAlertas = new JScrollPane(tablaAlertas);
        scrollAlertas.setBorder(BorderFactory.createLineBorder(new Color(100, 50, 50)));
        scrollAlertas.getViewport().setBackground(new Color(60, 40, 40));
        panel.add(scrollAlertas, BorderLayout.CENTER);

        return panel;
    }

    private void editarProductoSeleccionado() {
        int fila = tablaProductos.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para editar");
            return;
        }
        String codigo = (String) modeloProductos.getValueAt(fila, 0);
        Producto p = productoController.buscarProducto(codigo);
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

    /**
     * Esta ventanita sale para llenar los datos del producto.
     * Batallé un poco con el switch pero ya quedó para que cree el objeto correcto.
     */
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
        JCheckBox chkVentaPeso = new JCheckBox("Venta por Peso (Jamón/Queso)");
        if (p instanceof Frescos) {
            chkVentaPeso.setSelected(((Frescos) p).isEsVentaPorPeso());
        }

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
        panel.add(new JLabel("Ajuste Cremería:"));
        panel.add(chkVentaPeso);

        int result = JOptionPane.showConfirmDialog(this, panel, p == null ? "Agregar Producto" : "Editar Producto",
                JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Producto nuevo;
                String tipo = (String) comboTipo.getSelectedItem();
                // POLIMORFISMO (Unidad III): Creamos el objeto exacto según el tipo.
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

                if (nuevo instanceof Frescos) {
                    ((Frescos) nuevo).setEsVentaPorPeso(chkVentaPeso.isSelected());
                    if (chkVentaPeso.isSelected()) {
                        ((Frescos) nuevo).setUnidadVenta("Gramos");
                    }
                }

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

    /**
     * Refresca los datos en la pantalla.
     */
    private void actualizarTabla() {
        try {
            modeloProductos.setRowCount(0);
            java.util.List<Producto> productos = productoController.obtenerTodosProductos();

            for (Producto producto : productos) {
                // ABSTRACCIÓN: El Tiempo Aire no es físico, así que no lo pongo aquí.
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

            actualizarAlertas(productos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar productos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarAlertas(java.util.List<Producto> productos) {
        modeloAlertas.setRowCount(0);
        for (Producto p : productos) {
            if (p.necesitaReposicion()) {
                Object[] fila = {
                        p.getNombre(),
                        p.getMarca(),
                        p.getStockActual(),
                        p.getStockMinimo(),
                        "🚨 URGENTE"
                };
                modeloAlertas.addRow(fila);
            }
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
