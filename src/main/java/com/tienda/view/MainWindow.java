package com.tienda.view;

import com.tienda.controller.PedidoProveedorController;
import com.tienda.controller.ProductoController;
import com.tienda.controller.ProveedorController;
import com.tienda.controller.UsuarioController;
import com.tienda.controller.VentaController;
import com.tienda.model.Vendedor;

import javax.swing.*;
import java.awt.*;

/**
 * CLASE PRINCIPAL DE LA VISTA: MainWindow (Unidad IV - GUI)
 * Esta ventana es el contenedor principal de toda nuestra aplicación.
 * Utiliza un JTabbedPane para organizar los diferentes módulos (Ventas,
 * Inventario, etc.).
 */
public class MainWindow extends JFrame {
    // CONTROLADORES (MVC):
    // La vista interactúa con los controladores para manejar la lógica de negocio.
    private ProductoController productoController;
    private VentaController ventaController;
    private ProveedorController proveedorController;
    private PedidoProveedorController pedidoProveedorController;
    private UsuarioController usuarioController;

    // COMPONENTES DE LA INTERFAZ (Swing):
    private JTabbedPane tabbedPane; // Permite la navegación por pestañas (Punto 3 de la solicitud)

    // VISTAS HIJAS (MVC):
    // La vista principal se 'compone' de otras vistas más pequeñas para mayor
    // orden.
    private VistaVentas vistaVentas;
    private VistaInventario vistaInventario;
    private VistaProveedores vistaProveedores;
    private VistaPedidos vistaPedidos;
    private VistaHistorial vistaHistorial;

    /**
     * Constructor por defecto - Inicia con perfil de Vendedor
     */
    public MainWindow() {
        try {
            // Configurar FlatLaf Dark Mode como estándar Senior
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Error al configurar FlatLaf: " + e.getMessage());
        }

        // Crear usuario vendedor por defecto
        Vendedor vendedorDefault = new Vendedor("vendedor", "vendedor", "Vendedor");
        this.usuarioController = new UsuarioController();
        usuarioController.establecerUsuarioPorDefecto(vendedorDefault);

        // Inicializar controladores
        this.productoController = new ProductoController();
        this.ventaController = new VentaController();
        this.proveedorController = new ProveedorController();
        this.pedidoProveedorController = new PedidoProveedorController(productoController);

        inicializarComponentes();
        configurarVentana();
    }

    /**
     * Constructor con UsuarioController (para compatibilidad)
     */
    public MainWindow(UsuarioController usuarioController) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Error al configurar FlatLaf: " + e.getMessage());
        }

        this.usuarioController = usuarioController;

        // Inicializar controladores
        this.productoController = new ProductoController();
        this.ventaController = new VentaController();
        this.proveedorController = new ProveedorController();
        this.pedidoProveedorController = new PedidoProveedorController(productoController);

        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 45));

        // Crear JTabbedPane con las 4 pestañas solicitadas
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(35, 35, 35));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Pestaña 1: Ventas
        vistaVentas = new VistaVentas(productoController, ventaController);
        tabbedPane.addTab("Ventas", vistaVentas);

        // Pestaña 2: Inventario
        vistaInventario = new VistaInventario(productoController);
        tabbedPane.addTab("Inventario", vistaInventario);

        // Pestaña 3: Proveedores
        vistaProveedores = new VistaProveedores(proveedorController);
        tabbedPane.addTab("Proveedores", vistaProveedores);

        // Pestaña 4: Pedidos
        vistaPedidos = new VistaPedidos(pedidoProveedorController, proveedorController, productoController);
        tabbedPane.addTab("Pedidos", vistaPedidos);

        // Pestaña 5: Historial (Auditoría Unidad V)
        vistaHistorial = new VistaHistorial(ventaController);
        tabbedPane.addTab("Historial", vistaHistorial);

        add(tabbedPane, BorderLayout.CENTER);

        // Panel inferior con información
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBackground(new Color(35, 35, 35));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel info = new JLabel("Sistema de Punto de Venta - Tienda de la Esquina");
        info.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        info.setForeground(new Color(150, 150, 150));
        panelInferior.add(info);

        if (usuarioController != null && usuarioController.getUsuarioActual() != null) {
            JLabel lblUsuario = new JLabel(" | Usuario: " +
                    usuarioController.getUsuarioActual().getNombreCompleto() +
                    " (" + usuarioController.getUsuarioActual().getTipoUsuario() + ")");
            lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblUsuario.setForeground(new Color(200, 200, 200));
            panelInferior.add(lblUsuario);
        }

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void configurarVentana() {
        setTitle("Sistema de Punto de Venta - Tienda de la Esquina");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setResizable(true);
    }
}
