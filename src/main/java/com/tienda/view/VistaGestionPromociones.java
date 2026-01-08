package com.tienda.view;

import com.tienda.controller.ProductoController;
import com.tienda.controller.PromocionController;
import com.tienda.controller.UsuarioController;
import com.tienda.model.Producto;
import com.tienda.model.Promocion;
import com.tienda.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * VISTA: VistaGestionPromociones (Unidad IV - GUI)
 * Permite al Administrador gestionar descuentos manuales.
 */
public class VistaGestionPromociones extends JPanel {
    private ProductoController productoController;
    private PromocionController promocionController;
    private UsuarioController usuarioController;

    private JTable tablaProductos;
    private DefaultTableModel modeloProductos;
    private JTextField txtDescuento;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private JButton btnCrearPromo;

    public VistaGestionPromociones(ProductoController prodCtrl, PromocionController promoCtrl,
            UsuarioController userCtrl) {
        this.productoController = prodCtrl;
        this.promocionController = promoCtrl;
        this.usuarioController = userCtrl;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel Superior: Selección de Producto
        JPanel panelCentral = new JPanel(new BorderLayout(10, 10));
        modeloProductos = new DefaultTableModel(new Object[] { "Código", "Nombre", "Precio" }, 0);
        tablaProductos = new JTable(modeloProductos);
        actualizarTabla();
        panelCentral.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        // Panel Lateral: Formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Nueva Promoción"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelForm.add(new JLabel("Descuento (%):"), gbc);
        txtDescuento = new JTextField(10);
        gbc.gridx = 1;
        panelForm.add(txtDescuento, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelForm.add(new JLabel("Inicio (YYYY-MM-DD):"), gbc);
        txtFechaInicio = new JTextField(LocalDate.now().toString(), 10);
        gbc.gridx = 1;
        panelForm.add(txtFechaInicio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelForm.add(new JLabel("Fin (YYYY-MM-DD):"), gbc);
        txtFechaFin = new JTextField(LocalDate.now().plusDays(7).toString(), 10);
        gbc.gridx = 1;
        panelForm.add(txtFechaFin, gbc);

        btnCrearPromo = new JButton("Crear Promoción");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelForm.add(btnCrearPromo, gbc);

        btnCrearPromo.addActionListener(e -> crearPromocion());

        add(panelCentral, BorderLayout.CENTER);
        add(panelForm, BorderLayout.EAST);
    }

    private void crearPromocion() {
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }

        try {
            String codigo = (String) modeloProductos.getValueAt(fila, 0);
            Producto producto = productoController.buscarProducto(codigo);
            double desc = Double.parseDouble(txtDescuento.getText());
            LocalDate inicio = LocalDate.parse(txtFechaInicio.getText());
            LocalDate fin = LocalDate.parse(txtFechaFin.getText());

            Promocion promo = new Promocion(
                    "Promo " + producto.getNombre(),
                    desc, inicio, fin);
            promo.getListaProductos().add(producto);

            Usuario actual = usuarioController.getUsuarioActual();
            if (promocionController.agregarPromocion(promo, actual)) {
                JOptionPane.showMessageDialog(this, "Promoción creada con éxito por el Administrador.");
            } else {
                JOptionPane.showMessageDialog(this, "Error: Verifique permisos de Administrador.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos: " + e.getMessage());
        }
    }

    private void actualizarTabla() {
        modeloProductos.setRowCount(0);
        List<Producto> lista = productoController.obtenerTodosProductos();
        for (Producto p : lista) {
            modeloProductos.addRow(new Object[] { p.getCodigoBarras(), p.getNombre(), p.getPrecioVenta() });
        }
    }
}
