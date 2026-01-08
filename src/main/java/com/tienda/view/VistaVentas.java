package com.tienda.view;

import com.tienda.controller.ProductoController;
import com.tienda.controller.ControladorVentas;
import com.tienda.model.Frescos;
import com.tienda.model.Producto;
import com.tienda.model.TiempoAire;
import com.tienda.model.Venta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.text.DecimalFormat;

/**
 * VISTA: VistaVentas (Unidad V - Interfaz Gráfica)
 * Esta es la parte que el usuario ve para cobrar. Aquí conecto todo lo que
 * el cajero hace con la lógica de mi "Venta Controller". Me aseguré de que
 * se vea bonito porque si no la profe nos baja puntos.
 */
public class VistaVentas extends JPanel {
    // Le puse mucho énfasis a los frescos porque, como son perecederos y necesitan
    // refri, el riesgo de pérdida es mayor si no cuidamos la caducidad.
    private ProductoController productoController;
    private ControladorVentas ventaController;

    private JTable tablaProductos;
    private DefaultTableModel modeloProductos;
    private JTable tablaCarrito;
    private DefaultTableModel modeloCarrito;

    private JLabel lblTotal;
    private JComboBox<String> comboFormaPago;
    private JButton btnFinalizarVenta;
    private JButton btnCancelarVenta;

    private String categoriaActual = "Todos";
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public VistaVentas(ProductoController productoController, ControladorVentas ventaController) {
        this.productoController = productoController;
        this.ventaController = ventaController;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(45, 45, 45));

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel lateral con categorías
        JPanel panelLateral = crearPanelLateral();
        add(panelLateral, BorderLayout.WEST);

        // Panel central con tabla de productos
        JPanel panelCentral = crearPanelCentral();
        add(panelCentral, BorderLayout.CENTER);

        // Panel derecho con carrito
        JPanel panelDerecho = crearPanelDerecho();
        add(panelDerecho, BorderLayout.EAST);

        actualizarTablaProductos();
    }

    private JPanel crearPanelLateral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(35, 35, 35));
        panel.setPreferredSize(new Dimension(200, 0));

        JLabel titulo = new JLabel("Categorías");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(20));

        String[] categorias = { "Todos", "Frescos", "Abarrotes", "Dulceria", "Limpieza", "Otros",
                "Tiempo Aire" };
        for (String categoria : categorias) {
            JButton btn = crearBotonCategoria(categoria);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 50));
            panel.add(btn);
            panel.add(Box.createVerticalStrut(10));
        }

        return panel;
    }

    private JButton crearBotonCategoria(String categoria) {
        JButton btn = new JButton(categoria);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(new Color(60, 60, 60));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            try {
                if (categoria.equals("Tiempo Aire")) {
                    abrirVentanaTiempoAire();
                    return;
                }

                categoriaActual = categoria;
                actualizarTablaProductos();
                // Actualizar estilo
                Component[] componentes = ((JPanel) btn.getParent()).getComponents();
                for (Component comp : componentes) {
                    if (comp instanceof JButton) {
                        JButton b = (JButton) comp;
                        if (b.getText().equals(categoria)) {
                            b.setBackground(new Color(0, 120, 215));
                        } else {
                            b.setBackground(new Color(60, 60, 60));
                        }
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al cambiar categoría: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return btn;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(45, 45, 45));

        JLabel titulo = new JLabel("Productos Disponibles");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.NORTH);

        String[] columnas = { "Código", "Nombre", "Marca", "Precio", "Stock", "Agregar" };
        modeloProductos = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        tablaProductos = new JTable(modeloProductos);
        tablaProductos.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaProductos.setRowHeight(30);
        tablaProductos.setBackground(new Color(55, 55, 55));
        tablaProductos.setForeground(Color.WHITE);
        tablaProductos.setGridColor(new Color(70, 70, 70));
        tablaProductos.getTableHeader().setBackground(new Color(35, 35, 35));
        tablaProductos.getTableHeader().setForeground(Color.WHITE);
        tablaProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        tablaProductos.getColumn("Agregar").setCellRenderer(new ButtonRenderer());
        tablaProductos.getColumn("Agregar").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
        scrollPane.getViewport().setBackground(new Color(55, 55, 55));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(35, 35, 35));
        panel.setPreferredSize(new Dimension(400, 0));

        JLabel titulo = new JLabel("Carrito de Compra");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.NORTH);

        String[] columnasCarrito = { "Producto", "Precio", "Eliminar" };
        modeloCarrito = new DefaultTableModel(columnasCarrito, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        tablaCarrito = new JTable(modeloCarrito);
        tablaCarrito.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaCarrito.setRowHeight(30);
        tablaCarrito.setBackground(new Color(55, 55, 55));
        tablaCarrito.setForeground(Color.WHITE);
        tablaCarrito.setGridColor(new Color(70, 70, 70));
        tablaCarrito.getTableHeader().setBackground(new Color(35, 35, 35));
        tablaCarrito.getTableHeader().setForeground(Color.WHITE);
        tablaCarrito.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        tablaCarrito.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        tablaCarrito.getColumn("Eliminar").setCellEditor(new ButtonEditorCarrito(new JCheckBox()));

        JScrollPane scrollCarrito = new JScrollPane(tablaCarrito);
        scrollCarrito.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
        scrollCarrito.getViewport().setBackground(new Color(55, 55, 55));
        panel.add(scrollCarrito, BorderLayout.CENTER);

        // Panel de total y pago
        JPanel panelPago = new JPanel();
        panelPago.setLayout(new BoxLayout(panelPago, BoxLayout.Y_AXIS));
        panelPago.setBackground(new Color(35, 35, 35));
        panelPago.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTotal.setForeground(new Color(0, 200, 100));
        lblTotal.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPago.add(lblTotal);
        panelPago.add(Box.createVerticalStrut(10));

        JLabel lblFormaPago = new JLabel("Forma de Pago:");
        lblFormaPago.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFormaPago.setForeground(Color.WHITE);
        lblFormaPago.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPago.add(lblFormaPago);
        panelPago.add(Box.createVerticalStrut(5));

        comboFormaPago = new JComboBox<>(new String[] { "Efectivo", "Tarjeta", "Transferencia" });
        comboFormaPago.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboFormaPago.setMaximumSize(new Dimension(200, 35));
        comboFormaPago.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPago.add(comboFormaPago);
        panelPago.add(Box.createVerticalStrut(15));

        btnFinalizarVenta = new JButton("Finalizar Venta");
        btnFinalizarVenta.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnFinalizarVenta.setBackground(new Color(0, 150, 0));
        btnFinalizarVenta.setForeground(Color.WHITE);
        btnFinalizarVenta.setFocusPainted(false);
        btnFinalizarVenta.setBorderPainted(false);
        btnFinalizarVenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnFinalizarVenta.setMaximumSize(new Dimension(200, 40));
        btnFinalizarVenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnFinalizarVenta.addActionListener(e -> finalizarVenta());
        panelPago.add(btnFinalizarVenta);
        panelPago.add(Box.createVerticalStrut(10));

        btnCancelarVenta = new JButton("Cancelar Venta");
        btnCancelarVenta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCancelarVenta.setBackground(new Color(200, 0, 0));
        btnCancelarVenta.setForeground(Color.WHITE);
        btnCancelarVenta.setFocusPainted(false);
        btnCancelarVenta.setBorderPainted(false);
        btnCancelarVenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelarVenta.setMaximumSize(new Dimension(200, 40));
        btnCancelarVenta.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelarVenta.addActionListener(e -> cancelarVenta());
        panelPago.add(btnCancelarVenta);

        panel.add(panelPago, BorderLayout.SOUTH);

        return panel;
    }

    private void actualizarTablaProductos() {
        try {
            modeloProductos.setRowCount(0);
            java.util.List<Producto> productos;

            if (categoriaActual.equals("Todos")) {
                productos = productoController.obtenerTodosProductos().stream()
                        .filter(p -> !(p instanceof TiempoAire))
                        .collect(java.util.stream.Collectors.toList());
            } else if (categoriaActual.equals("Dulceria")) {
                productos = productoController.obtenerProductosPorCategoria("Dulcería");
            } else {
                productos = productoController.obtenerProductosPorCategoria(categoriaActual);
            }

            for (Producto producto : productos) {
                String nombreMostrado = producto.getNombre();
                if (producto instanceof Frescos && ((Frescos) producto).verificarCaducidad()) {
                    nombreMostrado = "<html><font color='red'>[PRÓXIMO A CADUCAR] " + producto.getNombre()
                            + "</font></html>";
                }

                Object[] fila = {
                        producto.getCodigoBarras(),
                        nombreMostrado,
                        producto.getMarca(),
                        "$" + df.format(producto.getPrecioVenta()),
                        producto.getStockActual(),
                        "Agregar"
                };
                modeloProductos.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar productos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarProductoAlCarrito(int fila) {
        try {
            String codigoBarras = (String) modeloProductos.getValueAt(fila, 0);
            Producto producto = productoController.buscarProducto(codigoBarras);

            if (producto == null) {
                JOptionPane.showMessageDialog(this,
                        "Producto no encontrado",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!producto.verificarStock()) {
                JOptionPane.showMessageDialog(this,
                        "No hay stock disponible para este producto",
                        "Stock Insuficiente", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Lógica de herencia: reconocer tipo de producto
            Producto productoAAgregar = producto;

            if (producto instanceof Frescos) {
                Frescos frescos = (Frescos) producto;

                // --- POLIMORFISMO Y ABSTRACCIÓN (Unidad III) ---
                // Si el producto es por gramos, preguntamos el peso.
                if (frescos.isSeVendePorGramos()) {
                    String gramosStr = JOptionPane.showInputDialog(this,
                            "Ingrese cantidad en gramos",
                            "Venta por Peso",
                            JOptionPane.QUESTION_MESSAGE);

                    if (gramosStr != null && !gramosStr.trim().isEmpty()) {
                        try {
                            double gramos = Double.parseDouble(gramosStr);
                            // Aquí usamos polimorfismo para que si el producto es por gramos,
                            // el precio se calcule solito dividiendo el precio por kilo entre 1000
                            // y multiplicando por lo que pesa.
                            frescos.setCantidadGramos(gramos);
                            productoAAgregar = frescos;
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "Cantidad inválida.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        return; // Cancelar si no ingresa gramos
                    }
                }

                // Pedir fecha de caducidad si es Frescos
                String fechaStr = JOptionPane.showInputDialog(this,
                        "Ingrese la fecha de caducidad (YYYY-MM-DD):",
                        "Fecha de Caducidad",
                        JOptionPane.QUESTION_MESSAGE);

                if (fechaStr != null && !fechaStr.trim().isEmpty()) {
                    try {
                        LocalDate fechaCaducidad = LocalDate.parse(fechaStr);
                        frescos.setFechaCaducidad(fechaCaducidad);
                        productoAAgregar = frescos;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this,
                                "Fecha inválida. Se usará el producto sin fecha específica.",
                                "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }

            if (ventaController.agregarProductoAVenta(productoAAgregar, 1)) {
                actualizarCarrito();
                actualizarTablaProductos();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se pudo agregar el producto al carrito",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al agregar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * --- UNIDAD IV: GUI Y UX (REQUERIMIENTOS ESPECIALES) ---
     * Mostramos etiquetas de 'OFERTA' y precios formateados para feedback visual
     * inmediato.
     */
    private void actualizarCarrito() {
        try {
            modeloCarrito.setRowCount(0);
            Venta venta = ventaController.getVentaActual();

            if (venta != null) {
                for (Producto producto : venta.getListaProductos()) {
                    String nombreMostrado = producto.getNombre();

                    if (producto instanceof Frescos
                            && (((Frescos) producto).isSeVendePorGramos() || ((Frescos) producto).isEsVentaPorPeso())) {
                        Frescos f = (Frescos) producto;
                        nombreMostrado = f.getNombre() + " (" + (int) f.getCantidadGramos() + "g)";
                    }

                    String precioMostrado = "$" + df.format(producto.getPrecioVenta());

                    // Verificamos si tiene descuento aplicado (Polimorfismo Unidad III)
                    // Nota: En un sistema real, compararíamos el precio actual vs original.
                    // Para este prototipo, si hay promoción activa en la venta, resaltamos.
                    if (venta.getPromocionAplicada() != null
                            && venta.getPromocionAplicada().aplicaAProducto(producto)) {
                        double precioConDesc = venta.getPromocionAplicada().calcularPrecioConDescuento(producto);
                        nombreMostrado = "<html><font color='orange'>[OFERTA]</font> " + nombreMostrado
                                + "</html>";
                        precioMostrado = "<html><strike>$" + df.format(producto.getPrecioVenta())
                                + "</strike> <font color='green'>$" + df.format(precioConDesc) + "</font></html>";
                    } else if (producto instanceof Frescos && ((Frescos) producto).verificarCaducidad()) {
                        // Caso de liquidación automática por caducidad
                        double precioLiquidacion = producto.getPrecioVenta() * 0.8;
                        nombreMostrado = "<html><font color='red'>[REMATE DE FRESCOS]</font> " + nombreMostrado
                                + "</html>";
                        precioMostrado = "<html><strike>$" + df.format(producto.getPrecioVenta())
                                + "</strike> <font color='green'>$" + df.format(precioLiquidacion) + "</font></html>";
                    }

                    Object[] fila = {
                            nombreMostrado,
                            precioMostrado,
                            "Eliminar"
                    };
                    modeloCarrito.addRow(fila);
                }
                lblTotal.setText("Total: $" + df.format(venta.getTotal()));
            } else {
                lblTotal.setText("Total: $0.00");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar carrito: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProductoDelCarrito(int fila) {
        try {
            Venta venta = ventaController.getVentaActual();
            if (venta == null || venta.getListaProductos().isEmpty()) {
                return;
            }

            if (fila >= 0 && fila < venta.getListaProductos().size()) {
                Producto producto = venta.getListaProductos().get(fila);
                ventaController.eliminarProductoDeVenta(producto);
                actualizarCarrito();
                actualizarTablaProductos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void finalizarVenta() {
        try {
            Venta venta = ventaController.getVentaActual();
            if (venta == null || venta.getListaProductos().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "El carrito está vacío",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String formaPago = (String) comboFormaPago.getSelectedItem();
            if (ventaController.finalizarVenta(formaPago)) {
                System.out.println("DEBUG: Venta finalizada correctamente en controlador.");
                JOptionPane.showMessageDialog(this, "Venta Exitosa");
                actualizarCarrito();
                actualizarTablaProductos();
            } else {
                System.err.println("DEBUG: El controlador falló al finalizar la venta.");
                JOptionPane.showMessageDialog(this,
                        "Error al finalizar la venta",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            // FORZAR REFRESCO: Buscamos la pestaña de historial independientemente del
            // éxito del controlador
            // para diagnóstico visual.
            try {
                JTabbedPane tabbedPane = (JTabbedPane) SwingUtilities.getAncestorOfClass(JTabbedPane.class, this);
                if (tabbedPane != null) {
                    boolean vistaEncontrada = false;
                    for (Component c : tabbedPane.getComponents()) {
                        if (c instanceof VistaHistorial) {
                            ((VistaHistorial) c).actualizarTabla();
                            vistaEncontrada = true;
                            System.out.println("DEBUG: VistaHistorial notificada para actualizar.");
                        }
                    }
                    if (!vistaEncontrada) {
                        System.err.println("DEBUG: No se encontró la instancia de VistaHistorial en el JTabbedPane.");
                    }
                } else {
                    System.err.println("DEBUG: No se pudo obtener el JTabbedPane ancestro.");
                }
            } catch (Exception ex) {
                System.err.println("DEBUG: Error al intentar sincronizar vistas: " + ex.getMessage());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al finalizar venta: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelarVenta() {
        try {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de cancelar la venta actual?",
                    "Confirmar Cancelación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                ventaController.cancelarVenta();
                actualizarCarrito();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cancelar venta: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Clases internas para botones en tablas
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setBackground(new Color(0, 120, 215));
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.PLAIN, 11));
            setFocusPainted(false);
            setBorderPainted(false);
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            this.row = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            button.setBackground(new Color(0, 150, 0));
            button.setForeground(Color.WHITE);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                agregarProductoAlCarrito(row);
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    class ButtonEditorCarrito extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int row;

        public ButtonEditorCarrito(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            this.row = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            button.setBackground(new Color(200, 0, 0));
            button.setForeground(Color.WHITE);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                eliminarProductoDelCarrito(row);
            }
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    /**
     * --- UNIDAD IV: MANEJO DE DIÁLOGOS Y RESPONSABILIDAD ÚNICA ---
     * Implementamos un JDialog para separar el servicio de Tiempo Aire del
     * inventario físico, aplicando el principio de Responsabilidad Única y
     * Abstracción de la Unidad I.
     */
    private void abrirVentanaTiempoAire() {
        JDialog dialogo = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Tiempo Aire", true);
        dialogo.setLayout(new BorderLayout(15, 15));
        dialogo.setSize(400, 520);
        dialogo.setLocationRelativeTo(this);

        JPanel panelContenido = new JPanel(new BorderLayout(15, 15));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(0, 1, 10, 10));
        JComboBox<String> comboCompanias = new JComboBox<>(TiempoAire.COMPANIAS_PERMITIDAS.toArray(new String[0]));
        JTextField txtNumero = new JTextField(10);
        txtNumero.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtNumero.setHorizontalAlignment(JTextField.CENTER);

        JTextField txtMontoPersonalizado = new JTextField(5);
        txtMontoPersonalizado.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtMontoPersonalizado.setHorizontalAlignment(JTextField.CENTER);

        // KeyListener para monto (solo números y decimales)
        txtMontoPersonalizado.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                }
            }
        });

        // KeyListener (Unidad IV)
        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || txtNumero.getText().length() >= 10) {
                    e.consume();
                }
            }
        });

        panelForm.add(new JLabel("Compañía:"));
        panelForm.add(comboCompanias);
        panelForm.add(new JLabel("Número Celular (10 dígitos):"));
        panelForm.add(txtNumero);
        panelForm.add(new JLabel("Monto Personalizado (Opcional):"));
        panelForm.add(txtMontoPersonalizado);

        // Botones de Monto
        JPanel panelMontos = new JPanel(new GridLayout(2, 3, 10, 10));
        final double[] montoSelected = { 0 };
        for (Integer monto : TiempoAire.MONTOS_PERMITIDOS) {
            JButton btnMonto = new JButton("$" + monto);
            btnMonto.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btnMonto.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnMonto.addActionListener(e -> {
                montoSelected[0] = monto;
                for (Component c : panelMontos.getComponents()) {
                    c.setBackground(new Color(60, 60, 60));
                    c.setForeground(Color.WHITE);
                }
                btnMonto.setBackground(new Color(0, 120, 215));
            });
            panelMontos.add(btnMonto);
        }

        panelContenido.add(panelForm, BorderLayout.NORTH);
        panelContenido.add(new JLabel("Monto de Recarga:"), BorderLayout.CENTER);
        panelContenido.add(panelMontos, BorderLayout.SOUTH);

        // Botón Aceptar
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnAceptar.setBackground(new Color(0, 150, 0));
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAceptar.addActionListener(e -> {
            try {
                if (txtNumero.getText().length() != 10)
                    throw new IllegalArgumentException("El número debe tener 10 dígitos.");

                double montoFinal = 0;
                if (!txtMontoPersonalizado.getText().trim().isEmpty()) {
                    montoFinal = Double.parseDouble(txtMontoPersonalizado.getText().trim());
                } else if (montoSelected[0] > 0) {
                    montoFinal = montoSelected[0];
                }

                if (montoFinal <= 0)
                    throw new IllegalArgumentException("Seleccione un monto o ingrese uno personalizado.");

                TiempoAire ta = new TiempoAire();
                ta.validarMonto(montoFinal); // Validación del modelo

                ta.setCodigoBarras("REC-TA");
                ta.setPrecioCompra(0);
                ta.setCompania((String) comboCompanias.getSelectedItem());
                ta.setNumeroCelular(txtNumero.getText());

                // Aquí le sumamos el peso de comisión que es la ganancia neta de la tienda por
                // el servicio
                ta.setPrecioVenta(montoFinal + 1.0);

                // COMENTARIO (Unidad I): El objeto se inserta directamente en el modelo
                // de la tabla para agilizar la venta, independizándolo del inventario físico.
                ventaController.agregarProductoAVenta(ta, 1);
                actualizarCarrito();
                dialogo.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialogo.add(panelContenido, BorderLayout.CENTER);
        dialogo.add(btnAceptar, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }
}
