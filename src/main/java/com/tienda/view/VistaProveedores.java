package com.tienda.view;

import com.tienda.controller.ProveedorController;
import com.tienda.model.Proveedor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Vista del módulo de Proveedores
 * Permite gestionar la lista de Proveedores (nombreEmpresa, telefono, contacto)
 */
public class VistaProveedores extends JPanel {
    private ProveedorController proveedorController;
    private JTable tablaProveedores;
    private DefaultTableModel modeloProveedores;

    public VistaProveedores(ProveedorController proveedorController) {
        this.proveedorController = proveedorController;
        
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

        JLabel titulo = new JLabel("Gestión de Proveedores");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Color.WHITE);
        panelSuperior.add(titulo);
        
        panelSuperior.add(Box.createHorizontalStrut(20));
        
        JButton btnAgregar = new JButton("Agregar Proveedor");
        btnAgregar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnAgregar.setBackground(new Color(0, 150, 0));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAgregar.addActionListener(e -> agregarProveedor());
        panelSuperior.add(btnAgregar);
        
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

        // Tabla de proveedores
        String[] columnas = {"Nombre Empresa", "Teléfono", "Contacto", "Eliminar"};
        modeloProveedores = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        tablaProveedores = new JTable(modeloProveedores);
        tablaProveedores.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaProveedores.setRowHeight(30);
        tablaProveedores.setBackground(new Color(55, 55, 55));
        tablaProveedores.setForeground(Color.WHITE);
        tablaProveedores.setGridColor(new Color(70, 70, 70));
        tablaProveedores.getTableHeader().setBackground(new Color(35, 35, 35));
        tablaProveedores.getTableHeader().setForeground(Color.WHITE);
        tablaProveedores.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        tablaProveedores.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        tablaProveedores.getColumn("Eliminar").setCellEditor(new ButtonEditorEliminar(new JCheckBox()));

        JScrollPane scrollPane = new JScrollPane(tablaProveedores);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 70)));
        scrollPane.getViewport().setBackground(new Color(55, 55, 55));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void actualizarTabla() {
        try {
            modeloProveedores.setRowCount(0);
            java.util.List<Proveedor> proveedores = proveedorController.obtenerTodosProveedores();

            for (Proveedor proveedor : proveedores) {
                Object[] fila = {
                    proveedor.getNombreEmpresa(),
                    proveedor.getTelefono(),
                    proveedor.getContacto(),
                    "Eliminar"
                };
                modeloProveedores.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar proveedores: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarProveedor() {
        try {
            JTextField txtNombre = new JTextField(20);
            JTextField txtTelefono = new JTextField(20);
            JTextField txtContacto = new JTextField(20);

            JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
            panel.add(new JLabel("Nombre Empresa:"));
            panel.add(txtNombre);
            panel.add(new JLabel("Teléfono:"));
            panel.add(txtTelefono);
            panel.add(new JLabel("Contacto:"));
            panel.add(txtContacto);

            int resultado = JOptionPane.showConfirmDialog(this, panel,
                "Agregar Proveedor", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

            if (resultado == JOptionPane.OK_OPTION) {
                String nombre = txtNombre.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String contacto = txtContacto.getText().trim();

                if (nombre.isEmpty() || telefono.isEmpty() || contacto.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Proveedor proveedor = new Proveedor(nombre, telefono, contacto);
                if (proveedorController.agregarProveedor(proveedor)) {
                    JOptionPane.showMessageDialog(this,
                        "Proveedor agregado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Ya existe un proveedor con ese nombre",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al agregar proveedor: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProveedor(int fila) {
        try {
            String nombreEmpresa = (String) modeloProveedores.getValueAt(fila, 0);
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el proveedor: " + nombreEmpresa + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                if (proveedorController.eliminarProveedor(nombreEmpresa)) {
                    JOptionPane.showMessageDialog(this,
                        "Proveedor eliminado exitosamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Error al eliminar el proveedor",
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error al eliminar proveedor: " + e.getMessage(),
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
                eliminarProveedor(row);
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
