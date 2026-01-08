package com.tienda.view;

import com.tienda.controller.PedidoProveedorController;
import com.tienda.controller.ProductoController;
import com.tienda.controller.ProveedorController;
import com.tienda.controller.UsuarioController;
import com.tienda.controller.ControladorVentas;
import com.tienda.controller.PromocionController;
import javax.swing.*;
import java.awt.*;

/**
 * CLASE PRINCIPAL DE LA VISTA: MainWindow (Unidad IV - GUI)
 * Esta ventana es el contenedor principal de toda nuestra aplicación.
 * Yo usé un JTabbedPane para organizar los diferentes módulos (Ventas,
 * Inventario, etc.) porque así se ve más ordenado y no se amontonan las cosas.
 */
public class MainWindow extends JFrame {
    // CONTROLADORES (MVC):
    // La vista le pide cosas a los controladores para que hagan la magia.
    // Esto es lo que la profe llama "Separación de responsabilidades".
    private ProductoController productoController;
    private ControladorVentas ventaController;
    private ProveedorController proveedorController;
    private PedidoProveedorController pedidoProveedorController;
    private UsuarioController usuarioController;
    private PromocionController promocionController;

    // COMPONENTES DE LA INTERFAZ (Swing):
    private JTabbedPane tabbedPane; // Pestañas para moverte entre las secciones de la tienda.

    // VISTAS HIJAS (MVC):
    // Mi ventana principal se armó de piezas más chiquitas para que sea más fácil
    // de programar.
    private VistaVentas vistaVentas;
    private VistaInventario vistaInventario;
    private VistaProveedores vistaProveedores;
    private VistaPedidos vistaPedidos;
    private VistaHistorial vistaHistorial;
    private VistaGestionPromociones vistaPromociones;

    /**
     * Constructor con UsuarioController - Forzamos que pasen el controlador
     * de usuario para que no entren si no están logueados.
     */
    public MainWindow(UsuarioController usuarioController) {
        try {
            // Diseño moderno y oscuro (FlatLaf)
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Error al configurar FlatLaf: " + e.getMessage());
        }

        this.usuarioController = usuarioController;

        // Inicializar controladores (cerebros del programa)
        this.productoController = new ProductoController();
        this.promocionController = new PromocionController();
        this.ventaController = new ControladorVentas(promocionController);
        this.proveedorController = new ProveedorController();
        this.pedidoProveedorController = new PedidoProveedorController(productoController);

        inicializarComponentes();
        configurarVentana();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 45));

        // Pestañas dinámicas según el rol (Unidad IV - GUI)
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(35, 35, 35));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 18)); // ¡Mucha más visibilidad!

        // Verificamos permisos para cada módulo y agregamos solo lo permitido
        // Esto cumple con el requerimiento de "vista simplificada"
        // Se eliminaron funciones no solicitadas para priorizar la usabilidad de los
        // perfiles requeridos por la tienda

        // Pestaña: Ventas (La más importante!)
        vistaVentas = new VistaVentas(productoController, ventaController);
        tabbedPane.addTab("Ventas", vistaVentas);

        // Pestaña: Inventario (Encargado, Admin)
        if (usuarioController.verificarPermisos("Inventarios")) {
            vistaInventario = new VistaInventario(productoController);
            tabbedPane.addTab("Inventario", vistaInventario);
        }

        // Pestaña: Proveedores (Admin)
        if (usuarioController.verificarPermisos("Proveedores")) {
            vistaProveedores = new VistaProveedores(proveedorController);
            tabbedPane.addTab("Proveedores", vistaProveedores);
        }

        // Pestaña: Pedidos (Admin)
        if (usuarioController.verificarPermisos("Pedidos")) {
            vistaPedidos = new VistaPedidos(pedidoProveedorController, proveedorController, productoController);
            tabbedPane.addTab("Pedidos", vistaPedidos);
        }

        // Pestaña: Historial (Admin)
        if (usuarioController.verificarPermisos("Historial")) {
            vistaHistorial = new VistaHistorial(ventaController);
            tabbedPane.addTab("Historial", vistaHistorial);
        }

        // Pestaña: Promociones (Admin)
        if (usuarioController.verificarPermisos("Promociones")) {
            vistaPromociones = new VistaGestionPromociones(productoController, promocionController, usuarioController);
            tabbedPane.addTab("Promociones", vistaPromociones);
        }

        add(tabbedPane, BorderLayout.CENTER);

        // Panel de abajo con información del sistema y el usuario que entró.
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.setBackground(new Color(35, 35, 35));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel info = new JLabel("Sistema de Punto de Venta - Tienda de la Esquina");
        info.setFont(new Font("Segoe UI", Font.BOLD, 14));
        info.setForeground(new Color(180, 180, 180));
        panelInferior.add(info);

        if (usuarioController != null && usuarioController.getUsuarioActual() != null) {
            JLabel lblUsuario = new JLabel(" | Usuario: " +
                    usuarioController.getUsuarioActual().getNombreCompleto() +
                    " (" + usuarioController.getUsuarioActual().getTipoUsuario() + ")");
            lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblUsuario.setForeground(new Color(220, 220, 220));
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

    public VistaHistorial getVistaHistorial() {
        return vistaHistorial;
    }
}
