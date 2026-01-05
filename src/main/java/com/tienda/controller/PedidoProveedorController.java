package com.tienda.controller;

import com.tienda.model.PedidoProveedor;
import com.tienda.model.Producto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * CONTROLADOR: PedidoProveedorController (Unidad V - MVC)
 * Actúa como el puente entre la Vista (VistaPedidos) y los Modelos.
 * Aquí se concentra la 'Lógica de Negocio' para el resurtido de stock.
 */
public class PedidoProveedorController {
    // Almacenamos los datos en listas (Persistencia volátil por ahora)
    private List<PedidoProveedor> pedidos;
    private PedidoProveedor pedidoActual;
    private ProductoController productoController; // Inyección de dependencia para actualizar stock

    public PedidoProveedorController(ProductoController productoController) {
        this.pedidos = new ArrayList<>();
        this.productoController = productoController;
    }

    /**
     * Inicia un nuevo pedido
     */
    public void iniciarPedido() {
        try {
            pedidoActual = new PedidoProveedor();
            pedidoActual.generarPedido();
        } catch (Exception e) {
            System.err.println("Error al iniciar pedido: " + e.getMessage());
            pedidoActual = new PedidoProveedor();
        }
    }

    /**
     * Obtiene el pedido actual
     */
    public PedidoProveedor getPedidoActual() {
        if (pedidoActual == null) {
            iniciarPedido();
        }
        return pedidoActual;
    }

    /**
     * Agrega un producto al pedido actual
     */
    public boolean agregarProductoAPedido(Producto producto, int cantidad) {
        try {
            if (pedidoActual == null) {
                iniciarPedido();
            }
            if (producto == null || cantidad <= 0) {
                return false;
            }
            for (int i = 0; i < cantidad; i++) {
                pedidoActual.getListaProductos().add(producto);
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error al agregar producto a pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Guarda el pedido actual
     */
    public boolean guardarPedido() {
        try {
            if (pedidoActual == null || pedidoActual.getListaProductos().isEmpty()) {
                return false;
            }
            pedidos.add(pedidoActual);
            pedidoActual = null;
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar pedido: " + e.getMessage());
            return false;
        }
    }

    /**
     * Marca un pedido como entregado y suma el stock al inventario
     */
    public boolean marcarComoEntregado(PedidoProveedor pedido) {
        try {
            if (pedido == null) {
                return false;
            }

            pedido.setEstatus("Entregado");
            pedido.setFechaEntrega(LocalDate.now());

            // Sumar stock al inventario para cada producto en el pedido
            if (productoController != null) {
                List<Producto> productosPedido = pedido.getListaProductos();
                System.out.println("Procesando entrega: " + productosPedido.size() + " productos.");

                for (Producto productoPedido : productosPedido) {
                    Producto productoInventario = productoController.buscarPorCodigoBarras(
                            productoPedido.getCodigoBarras());

                    if (productoInventario != null) {
                        // Sumar 1 al stock actual por cada ocurrencia en la lista del pedido
                        productoInventario.setStockActual(
                                productoInventario.getStockActual() + 1);
                    } else {
                        // Si el producto no existe en el catálogo, lo agregamos con stock inicial 1
                        productoPedido.setStockActual(1);
                        productoController.agregarProducto(productoPedido);
                    }
                }
            }

            return true;
        } catch (Exception e) {
            System.err.println("Error al marcar pedido como entregado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los pedidos
     */
    public List<PedidoProveedor> obtenerTodosPedidos() {
        return new ArrayList<>(pedidos);
    }

    /**
     * Cancela el pedido actual
     */
    public void cancelarPedido() {
        try {
            pedidoActual = null;
        } catch (Exception e) {
            System.err.println("Error al cancelar pedido: " + e.getMessage());
        }
    }

    /**
     * Elimina un producto del pedido actual
     */
    public boolean eliminarProductoDePedido(Producto producto) {
        try {
            if (pedidoActual == null) {
                return false;
            }
            return pedidoActual.getListaProductos().remove(producto);
        } catch (Exception e) {
            System.err.println("Error al eliminar producto de pedido: " + e.getMessage());
            return false;
        }
    }
}
