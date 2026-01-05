package com.tienda.view;

import com.tienda.controller.PedidoProveedorController;
import com.tienda.controller.ProductoController;
import com.tienda.controller.ProveedorController;
import com.tienda.model.Frescos;
import com.tienda.model.PedidoProveedor;
import com.tienda.model.Producto;
import com.tienda.model.Proveedor;
import com.tienda.model.TiempoAire;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.text.DecimalFormat;

/**
 * Vista del módulo de Pedidos Proveedor
 * Permite seleccionar proveedor, agregar productos y marcar como entregado
 */
public class VistaPedidos extends JPanel {
    private PedidoProveedorController pedidoController;
    private ProveedorController proveedorController;
    private ProductoController productoController;

    private JComboBox<Proveedor> comboProveedores;
    private JTable tablaProductos;
    private DefaultTableModel modeloProductos;
    private JTable tablaPedido;
    private DefaultTableModel modeloPedido;

    private JButton btnAgregarProducto;
    private JButton btnGuardarPedido;
    private JButton btnMarcarEntregado;
    private JButton btnCancelarPedido;

    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public VistaPedidos(PedidoProveedorController pedidoController,
            ProveedorController proveedorController,
            ProductoController productoController) {
        this.pedidoController = pedidoController;
        this.proveedorController = proveedorController;
        this.productoController = productoController;

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(45, 45, 45));

        inicializarComponentes();
        actualizarTablaProductos();
        actualizarComboProveedores();
    }

    private void inicializarComponentes() {
        // Panel superior
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);

        // Panel central dividido
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(500);
        splitPane.setLeftComponent(crearPanelProductos());
        splitPane.setRightComponent(crearPanelPedido());
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(35, 35, 35));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblProveedor = new JLabel("Proveedor:");
        lblProveedor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblProveedor.setForeground(Color.WHITE);
        panel.add(lblProveedor);

        comboProveedores = new JComboBox<>();
        comboProveedores.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboProveedores.setPreferredSize(new Dimension(250, 35));
        panel.add(comboProveedores);

        panel.add(Box.createHorizontalStrut(20));

        btnAgregarProducto = new JButton("Agregar Producto");
        btnAgregarProducto.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAgregarProducto.setBackground(new Color(0, 150, 0));
        btnAgregarProducto.setForeground(Color.WHITE);
        btnAgregarProducto.setFocusPainted(false);
        btnAgregarProducto.setBorderPainted(false);
        btnAgregarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregarProducto.addActionListener(e -> agregarProductoAlPedido());
        panel.add(btnAgregarProducto);

        btnGuardarPedido = new JButton("Guardar Pedido");
        btnGuardarPedido.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnGuardarPedido.setBackground(new Color(0, 120, 215));
        btnGuardarPedido.setForeground(Color.WHITE);
        btnGuardarPedido.setFocusPainted(false);
        btnGuardarPedido.setBorderPainted(false);
        btnGuardarPedido.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardarPedido.addActionListener(e -> guardarPedido());
        panel.add(btnGuardarPedido);

        btnMarcarEntregado = new JButton("Marcar como Entregado");
        btnMarcarEntregado.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnMarcarEntregado.setBackground(new Color(200, 150, 0));
        btnMarcarEntregado.setForeground(Color.WHITE);
        btnMarcarEntregado.setFocusPainted(false);
        btnMarcarEntregado.setBorderPainted(false);
        btnMarcarEntregado.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMarcarEntregado.addActionListener(e -> marcarComoEntregado());
        panel.add(btnMarcarEntregado);

        btnCancelarPedido = new JButton("Cancelar Pedido");
        btnCancelarPedido.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnCancelarPedido.setBackground(new Color(200, 0, 0));
        btnCancelarPedido.setForeground(Color.WHITE);
        btnCancelarPedido.setFocusPainted(false);
        btnCancelarPedido.setBorderPainted(false);
        btnCancelarPedido.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelarPedido.addActionListener(e -> cancelarPedido());
        panel.add(btnCancelarPedido);

        return panel;
    }

    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(45, 45, 45));

        JLabel titulo = new JLabel("Productos Disponibles");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.NORTH);

        String[] columnas = { "Código", "Nombre", "Marca", "Precio", "Tipo" };
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
        tablaProductos.getTableHeader().setBackground(new Color(35, 35, 35));
        tablaProductos.getTableHeader().setForeground(Color.WHITE);
        tablaProductos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
        scrollPane.getViewport().setBackground(new Color(55, 55, 55));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelPedido() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(35, 35, 35));

        JLabel titulo = new JLabel("Productos del Pedido");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        panel.add(titulo, BorderLayout.NORTH);

        String[] columnas = { "Producto", "Cantidad", "Eliminar" };
        modeloPedido = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };

        tablaPedido = new JTable(modeloPedido);
        tablaPedido.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaPedido.setRowHeight(30);
        tablaPedido.setBackground(new Color(55, 55, 55));
        tablaPedido.setForeground(Color.WHITE);
        tablaPedido.setGridColor(new Color(70, 70, 70));
        tablaPedido.getTableHeader().setBackground(new Color(35, 35, 35));
        tablaPedido.getTableHeader().setForeground(Color.WHITE);
        tablaPedido.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        tablaPedido.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        tablaPedido.getColumn("Eliminar").setCellEditor(new ButtonEditorEliminar(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(tablaPedido);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
        scrollPane.getViewport().setBackground(new Color(55, 55, 55));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void actualizarComboProveedores() {
        try {
            comboProveedores.removeAllItems();
            java.util.List<Proveedor> proveedores = proveedorController.obtenerTodosProveedores();
            for (Proveedor proveedor : proveedores) {
                comboProveedores.addItem(proveedor);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar proveedores: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTablaProductos() {
        try {
            modeloProductos.setRowCount(0);
            java.util.List<Producto> productos = productoController.obtenerTodosProductos();

            for (Producto producto : productos) {
                // --- ABSTRACCIÓN DE COSTOS (Unidad I) ---
                // Los servicios digitales de Tiempo Aire no se "resurten" físicamente
                // a través de pedidos a proveedores tradicionales.
                if (producto instanceof TiempoAire)
                    continue;

                String tipo = obtenerTipoProducto(producto);
                Object[] fila = {
                        producto.getCodigoBarras(),
                        producto.getNombre(),
                        producto.getMarca(),
                        "$" + df.format(producto.getPrecioCompra()),
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

    private void actualizarTablaPedido() {
        try {
            modeloPedido.setRowCount(0);
            PedidoProveedor pedido = pedidoController.getPedidoActual();

            if (pedido != null) {
                java.util.Map<String, Integer> cantidadPorProducto = new java.util.HashMap<>();
                for (Producto producto : pedido.getListaProductos()) {
                    cantidadPorProducto.put(producto.getCodigoBarras(),
                            cantidadPorProducto.getOrDefault(producto.getCodigoBarras(), 0) + 1);
                }

                for (java.util.Map.Entry<String, Integer> entry : cantidadPorProducto.entrySet()) {
                    Producto producto = productoController.buscarPorCodigoBarras(entry.getKey());
                    if (producto != null) {
                        Object[] fila = {
                                producto.getNombre(),
                                entry.getValue(),
                                "Eliminar"
                        };
                        modeloPedido.addRow(fila);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar tabla de pedido: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerTipoProducto(Producto producto) {
        if (producto instanceof Frescos)
            return "Frescos";
        if (producto instanceof com.tienda.model.Abarrotes)
            return "Abarrotes";
        if (producto instanceof com.tienda.model.Dulceria)
            return "Dulcería";
        if (producto instanceof com.tienda.model.Limpieza)
            return "Limpieza";
        if (producto instanceof TiempoAire)
            return "TiempoAire";
        if (producto instanceof com.tienda.model.Otros)
            return "Otros";
        return "Desconocido";
    }

    private void agregarProductoAlPedido() {
        try {
            int filaSeleccionada = tablaProductos.getSelectedRow();
            if (filaSeleccionada < 0) {
                JOptionPane.showMessageDialog(this,
                        "Seleccione un producto de la tabla",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String codigoBarras = (String) modeloProductos.getValueAt(filaSeleccionada, 0);
            Producto producto = productoController.buscarPorCodigoBarras(codigoBarras);

            if (producto == null) {
                JOptionPane.showMessageDialog(this,
                        "Producto no encontrado",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Lógica de herencia: reconocer tipo de producto
            Producto productoAAgregar = producto;

            if (producto instanceof Frescos) {
                // Pedir fecha de caducidad si es Frescos
                String fechaStr = JOptionPane.showInputDialog(this,
                        "Ingrese la fecha de caducidad (YYYY-MM-DD):",
                        "Fecha de Caducidad",
                        JOptionPane.QUESTION_MESSAGE);

                if (fechaStr != null && !fechaStr.trim().isEmpty()) {
                    try {
                        LocalDate fechaCaducidad = LocalDate.parse(fechaStr);
                        Frescos frescos = new Frescos(
                                producto.getCodigoBarras(),
                                producto.getNombre(),
                                producto.getMarca(),
                                producto.getTamanoGramaje(),
                                producto.getPrecioCompra(),
                                producto.getPrecioVenta(),
                                producto.getStockActual(),
                                producto.getStockMinimo(),
                                fechaCaducidad,
                                ((Frescos) producto).isRequiereRefrigeracion(),
                                ((Frescos) producto).getUnidadMedida());
                        productoAAgregar = frescos;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this,
                                "Fecha inválida. Se usará el producto sin fecha específica.",
                                "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }

            // Verificación redundante por seguridad lógica
            if (producto instanceof TiempoAire) {
                JOptionPane.showMessageDialog(this,
                        "El Tiempo Aire no puede ser solicitado mediante pedidos de resurtido.",
                        "Restricción de Servicio", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String cantidadStr = JOptionPane.showInputDialog(this,
                    "Ingrese la cantidad:",
                    "Cantidad",
                    JOptionPane.QUESTION_MESSAGE);

            if (cantidadStr != null && !cantidadStr.trim().isEmpty()) {
                try {
                    int cantidad = Integer.parseInt(cantidadStr);
                    if (cantidad > 0) {
                        if (pedidoController.agregarProductoAPedido(productoAAgregar, cantidad)) {
                            actualizarTablaPedido();
                            JOptionPane.showMessageDialog(this,
                                    "Producto agregado al pedido",
                                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this,
                            "Cantidad inválida",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al agregar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarPedido() {
        try {
            if (comboProveedores.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this,
                        "Seleccione un proveedor",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            PedidoProveedor pedido = pedidoController.getPedidoActual();
            if (pedido == null || pedido.getListaProductos().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "El pedido está vacío",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (pedidoController.guardarPedido()) {
                JOptionPane.showMessageDialog(this,
                        "Pedido guardado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTablaPedido();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar pedido: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void marcarComoEntregado() {
        try {
            // Mostrar lista de pedidos pendientes
            java.util.List<PedidoProveedor> pedidos = pedidoController.obtenerTodosPedidos();
            java.util.List<PedidoProveedor> pedidosPendientes = new java.util.ArrayList<>();

            for (PedidoProveedor pedido : pedidos) {
                if (!"Entregado".equals(pedido.getEstatus())) {
                    pedidosPendientes.add(pedido);
                }
            }

            if (pedidosPendientes.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay pedidos pendientes",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String[] opciones = new String[pedidosPendientes.size()];
            for (int i = 0; i < pedidosPendientes.size(); i++) {
                PedidoProveedor p = pedidosPendientes.get(i);
                opciones[i] = "Pedido del " + p.getFechaSolicitud() +
                        " (" + p.getListaProductos().size() + " productos)";
            }

            String seleccion = (String) JOptionPane.showInputDialog(this,
                    "Seleccione el pedido a marcar como entregado:",
                    "Marcar como Entregado",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);

            if (seleccion != null) {
                int index = java.util.Arrays.asList(opciones).indexOf(seleccion);
                PedidoProveedor pedido = pedidosPendientes.get(index);

                if (pedidoController.marcarComoEntregado(pedido)) {
                    // marcarComoEntregado() ya suma el stock automáticamente
                    JOptionPane.showMessageDialog(this,
                            "Pedido marcado como entregado.\nStock actualizado en inventario.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al marcar como entregado: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelarPedido() {
        try {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de cancelar el pedido actual?",
                    "Confirmar Cancelación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                pedidoController.cancelarPedido();
                actualizarTablaPedido();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cancelar pedido: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProductoDelPedido(int fila) {
        try {
            String nombreProducto = (String) modeloPedido.getValueAt(fila, 0);
            PedidoProveedor pedido = pedidoController.getPedidoActual();

            if (pedido != null) {
                Producto productoAEliminar = null;
                for (Producto producto : pedido.getListaProductos()) {
                    if (producto.getNombre().equals(nombreProducto)) {
                        productoAEliminar = producto;
                        break;
                    }
                }

                if (productoAEliminar != null) {
                    pedidoController.eliminarProductoDePedido(productoAEliminar);
                    actualizarTablaPedido();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Clases internas para botones
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            setBackground(new Color(200, 0, 0));
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.PLAIN, 11));
            setFocusPainted(false);
            setBorderPainted(false);
            return this;
        }
    }

    class ButtonEditorEliminar extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int row;

        public ButtonEditorEliminar(JCheckBox checkBox) {
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
                eliminarProductoDelPedido(row);
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
}
