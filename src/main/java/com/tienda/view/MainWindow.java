package com.tienda.view;

import com.tienda.controller.PedidoProveedorController;
import com.tienda.controller.ProductoController;
import com.tienda.controller.ProveedorController;
import com.tienda.controller.UsuarioController;
import com.tienda.controller.ControladorVentas;
import com.tienda.controller.PromocionController;
import com.tienda.model.Vendedor;

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
    private VistaGestionPromociones vistaPromociones; // Manteniendo el tipo original, solo cambiando el comentario si
                                                      // aplica.

    /**
     * Constructor por defecto - Inicia con perfil de Vendedor por si se nos olvida
     * loguear.
     */
    public MainWindow() {
        try {
            // Le puse este diseño (Laf) para que se vea de "Senior" aunque sea de 3er
            // semestre.
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Error al configurar FlatLaf: " + e.getMessage());
        }

        // Crear usuario vendedor por defecto. Me aseguré de que no esté vacío.
        Vendedor vendedorDefault = new Vendedor("vendedor", "vendedor", "Vendedor");
        this.usuarioController = new UsuarioController();
        usuarioController.establecerUsuarioPorDefecto(vendedorDefault);

        // Inicializar controladores (mis cerebros del programa)
        this.productoController = new ProductoController();
        this.promocionController = new PromocionController();
        this.ventaController = new ControladorVentas(promocionController);
        this.proveedorController = new ProveedorController();
        this.pedidoProveedorController = new PedidoProveedorController(productoController);

        inicializarComponentes();
        configurarVentana();
    }

    /**
     * Constructor con UsuarioController - Este lo uso cuando ya sabemos qué usuario
     * entró.
     */
    public MainWindow(UsuarioController usuarioController) {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.err.println("Error al configurar FlatLaf: " + e.getMessage());
        }

        this.usuarioController = usuarioController;

        // Inicializar controladores de nuevo.
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

        // Pestañas solicitadas en la Unidad IV.
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(35, 35, 35));
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Pestaña 1: Ventas (La más importante!)
        vistaVentas = new VistaVentas(productoController, ventaController);
        tabbedPane.addTab("Ventas", vistaVentas);

        // Pestaña 2: Inventario (Para ver qué queda)
        vistaInventario = new VistaInventario(productoController);
        tabbedPane.addTab("Inventario", vistaInventario);

        // Pestaña 3: Proveedores (A quién le compramos)
        vistaProveedores = new VistaProveedores(proveedorController);
        tabbedPane.addTab("Proveedores", vistaProveedores);

        // Pestaña 4: Pedidos (Lo que viene en camino)
        vistaPedidos = new VistaPedidos(pedidoProveedorController, proveedorController, productoController);
        tabbedPane.addTab("Pedidos", vistaPedidos);

        // Pestaña 5: Historial (Para que el Admin audite las ventas)
        vistaHistorial = new VistaHistorial(ventaController);
        tabbedPane.addTab("Historial", vistaHistorial);

        // Pestaña 6: Promociones (Solo para el Administrador)
        vistaPromociones = new VistaGestionPromociones(productoController, promocionController, usuarioController);
        tabbedPane.addTab("Promociones", vistaPromociones);

        add(tabbedPane, BorderLayout.CENTER);

        // Panel de abajo con información del sistema y el usuario que entró.
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

    public VistaHistorial getVistaHistorial() {
        return vistaHistorial;
    }
}
