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
 * CONTROLADOR: ControladorVentas (Unidad V - MVC)
 * Este es el controlador para las ventas. Aquí manejo cuando alguien compra
 * algo, se guarda en el historial y se bajan las existencias.
 */
public class ControladorVentas {
    private List<Venta> ventas;
    private ArrayList<Venta> historialGlobal; // Para que no se borren mis ventas
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
     * Cuando llega un cliente, inicio una nueva venta.
     */
    public void iniciarVenta() {
        try {
            ventaActual = new Venta();
        } catch (Exception e) {
            ventaActual = new Venta();
        }
    }

    /**
     * Meto productos a la cuenta del cliente.
     */
    public boolean agregarProductoAVenta(Producto producto, int cantidad) {
        try {
            if (ventaActual == null) {
                iniciarVenta();
            }
            if (producto == null || cantidad <= 0) {
                return false;
            }
            // Checo si tengo suficiente en la bodega (Unidad I - Reglas)
            if (!producto.verificarStock() || producto.getStockActual() < cantidad) {
                return false;
            }
            for (int i = 0; i < cantidad; i++) {
                ventaActual.agregarProducto(producto);
            }

            // Aplicamos polimorfismo para ver si hay promos para este producto
            if (promocionController != null) {
                Promocion promocion = promocionController.buscarPromocionParaProducto(producto);
                if (promocion != null && promocion.isVigente()) {
                    ventaActual.aplicarPromocion(promocion);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Venta getVentaActual() {
        if (ventaActual == null) {
            iniciarVenta();
        }
        return ventaActual;
    }

    /**
     * SOBRECARGA DE MÉTODOS (Unidad II):
     * Este finaliza la venta diciéndole cómo pagó.
     */
    public boolean finalizarVenta(String formaPago) {
        try {
            if (ventaActual == null || ventaActual.getListaProductos().isEmpty()) {
                return false;
            }

            // Le pongo la fecha de hoy con hora exacta (Unidad III)
            ventaActual.setFormaPago(formaPago);
            ventaActual.setFecha(java.time.LocalDateTime.now());

            // Llamo al método de la clase Venta
            ventaActual.finalizarVenta();

            // CLONACIÓN (Unidad II): Guardo una copia en el historial
            Venta ventaParaHistorial = ventaActual.clone();
            if (ventaParaHistorial != null) {
                historialGlobal.add(ventaParaHistorial);
                ventas.add(ventaParaHistorial);
            }

            // Limpio para el que sigue
            ventaActual = null;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * SOBRECARGA DE MÉTODOS (Unidad II):
     * Si no me dicen cómo pagó, asumo que fue en efectivo.
     */
    public boolean finalizarVenta() {
        return finalizarVenta("Efectivo");
    }

    /**
     * Con esto lleno la tabla que ve el Administrador (Unidad IV - GUI).
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
            System.err.println("Error al llenar la tabla: " + e.getMessage());
        }
    }

    public void cancelarVenta() {
        ventaActual = null;
    }

    public List<Venta> obtenerTodasVentas() {
        return new ArrayList<>(historialGlobal);
    }

    /**
     * Si el cliente se arrepiente, quito el producto de la cuenta.
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
            return false;
        }
    }
}
