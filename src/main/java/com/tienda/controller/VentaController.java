package com.tienda.controller;

import com.tienda.model.Producto;
import com.tienda.model.Promocion;
import com.tienda.model.Venta;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar ventas
 * Integra la aplicación de promociones
 */
public class VentaController {
    private List<Venta> ventas;
    private Venta ventaActual;
    private PromocionController promocionController;

    public VentaController() {
        this.ventas = new ArrayList<>();
        this.ventaActual = null;
        this.promocionController = new PromocionController();
    }

    public VentaController(PromocionController promocionController) {
        this.ventas = new ArrayList<>();
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
     * Finaliza la venta actual
     */
    public boolean finalizarVenta(String formaPago) {
        try {
            if (ventaActual == null || ventaActual.getListaProductos().isEmpty()) {
                return false;
            }
            ventaActual.setFormaPago(formaPago);
            ventaActual.finalizarVenta();
            ventas.add(ventaActual);
            ventaActual = null;
            return true;
        } catch (Exception e) {
            System.err.println("Error al finalizar venta: " + e.getMessage());
            return false;
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
     */
    public List<Venta> obtenerTodasVentas() {
        return new ArrayList<>(ventas);
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
