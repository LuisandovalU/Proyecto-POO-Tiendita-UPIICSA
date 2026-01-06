package com.tienda.controller;

import com.tienda.model.Producto;
import com.tienda.model.Promocion;
import com.tienda.model.Venta;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar ventas (ControladorVentas)
 * Integra la aplicación de promociones y la persistencia en memoria del
 * historial.
 * 
 * COMENTARIO DE INGENIERÍA: Se implementó un patrón de mediación simple para
 * sincronizar
 * las vistas del Vendedor y el Administrador, garantizando la integridad del
 * historial
 * de auditoría.
 */
public class ControladorVentas {
    private List<Venta> ventas; // Lista de ventas de la sesión (legacy)
    private ArrayList<Venta> historialGlobal; // Requerimiento: Persistencia del historial
    private Venta ventaActual;
    private PromocionController promocionController;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ControladorVentas() {
        this.ventas = new ArrayList<>();
        this.historialGlobal = new ArrayList<>();
        this.ventaActual = null;
        this.promocionController = new PromocionController();
    }

    public ControladorVentas(PromocionController promocionController) {
        this.ventas = new ArrayList<>();
        this.historialGlobal = new ArrayList<>();
        this.ventaActual = null;
        this.promocionController = promocionController;
    }

    /**
     * Inicia una nueva venta
     */
    public void iniciarVenta() {
        try {
            ventaActual = new Venta();
        } catch (Exception e) {
            System.err.println("Error al iniciar venta: " + e.getMessage());
            ventaActual = new Venta();
        }
    }

    /**
     * Agrega un producto a la venta actual
     * Aplica promociones automáticamente si existen
     */
    public boolean agregarProductoAVenta(Producto producto, int cantidad) {
        try {
            if (ventaActual == null) {
                iniciarVenta();
            }
            if (producto == null || cantidad <= 0) {
                return false;
            }
            if (!producto.verificarStock() || producto.getStockActual() < cantidad) {
                return false;
            }
            for (int i = 0; i < cantidad; i++) {
                ventaActual.agregarProducto(producto);
            }

            // Aplicar promoción si existe para este producto (Unidad III: Polimorfismo)
            if (promocionController != null) {
                Promocion promocion = promocionController.buscarPromocionParaProducto(producto);
                if (promocion != null && promocion.isVigente()) {
                    ventaActual.aplicarPromocion(promocion);
                }
            }

            return true;
        } catch (Exception e) {
            System.err.println("Error al agregar producto a venta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene la venta actual
     */
    public Venta getVentaActual() {
        if (ventaActual == null) {
            iniciarVenta();
        }
        return ventaActual;
    }

    /**
     * Finaliza la venta actual y realiza la persistencia en el historial.
     */
    public boolean finalizarVenta(String formaPago) {
        try {
            if (ventaActual == null || ventaActual.getListaProductos().isEmpty()) {
                return false;
            }

            // 1. Asignar fecha actual y forma de pago
            ventaActual.setFormaPago(formaPago);
            // La fecha ya se asigna en el constructor de Venta (LocalDate.now())

            // 2. Finalizar la venta (calcula totales y resta stock)
            ventaActual.finalizarVenta();

            // 3. Clonar el objeto Venta actual y añadirlo al historialGlobal (Persistencia)
            Venta ventaParaHistorial = ventaActual.clone();
            if (ventaParaHistorial != null) {
                historialGlobal.add(ventaParaHistorial);
                ventas.add(ventaParaHistorial); // Mantener compatibilidad
            }

            // 4. Limpiar el carrito para la siguiente venta
            ventaActual = null;

            return true;
        } catch (Exception e) {
            System.err.println("Error al finalizar venta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualización de la Vista (Unidad IV): Refresca la tabla del historial.
     */
    public void actualizarTablaHistorial(DefaultTableModel modeloHistorial) {
        try {
            modeloHistorial.setRowCount(0);
            for (Venta venta : historialGlobal) {
                String promoNombre = (venta.getPromocionAplicada() != null)
                        ? venta.getPromocionAplicada().getNombrePromo()
                        : "Ninguna";

                Object[] fila = {
                        venta.getFolio(),
                        venta.getFecha().format(dtf),
                        "$" + df.format(venta.getTotal()),
                        promoNombre
                };
                modeloHistorial.addRow(fila);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar tabla de historial: " + e.getMessage());
        }
    }

    /**
     * Cancela la venta actual
     */
    public void cancelarVenta() {
        try {
            ventaActual = null;
        } catch (Exception e) {
            System.err.println("Error al cancelar venta: " + e.getMessage());
        }
    }

    /**
     * Obtiene todas las ventas realizadas
     * DEBUG: Imprime el tamaño de la lista para verificar persistencia.
     */
    public List<Venta> obtenerTodasVentas() {
        System.out.println("DEBUG: Ventas en historial (ventas): " + ventas.size());
        System.out.println("DEBUG: Ventas en historial (historialGlobal): " + historialGlobal.size());
        return new ArrayList<>(historialGlobal);
    }

    /**
     * Elimina un producto de la venta actual
     */
    public boolean eliminarProductoDeVenta(Producto producto) {
        try {
            if (ventaActual == null) {
                return false;
            }
            List<Producto> productos = ventaActual.getListaProductos();
            if (productos.remove(producto)) {
                ventaActual.setListaProductos(productos);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al eliminar producto de venta: " + e.getMessage());
            return false;
        }
    }
}
