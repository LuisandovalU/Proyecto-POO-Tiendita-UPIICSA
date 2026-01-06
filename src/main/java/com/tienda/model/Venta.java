package com.tienda.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * CLASE: Venta (Unidad II - Clases y Objetos)
 * Representa el ticket o transacción final de la tienda.
 * 
 * COMENTARIO DE INGENIERÍA: Se migró de LocalDate a LocalDateTime para capturar
 * la precisión de horas y minutos en cada transacción, permitiendo una
 * auditoría en tiempo real exacta y evitando conflictos de tipo en el formateo.
 */
public class Venta implements Cloneable {
    // RELACIÓN DE COMPOSICIÓN/AGREGACIÓN (Unidad III):
    // Una Venta 'tiene una' lista de Productos. Sin productos, no hay venta.
    private static int contadorFolios = 1; // Contador estático para folios correlativos
    private int folio; // Identificador único de la venta
    private LocalDateTime fecha; // Fecha de la operación
    private String formaPago; // Efectivo, Tarjeta, etc. (Unidad I)
    private double total; // Monto final a pagar
    private double descuentoAplicado; // Valor ahorrado por el cliente
    private List<Producto> listaProductos; // Colección de objetos Producto (Polimorfismo)
    private Promocion promocionAplicada; // Asociación con una promoción opcional

    public Venta() {
        this.folio = contadorFolios++;
        this.fecha = LocalDateTime.now();
        this.listaProductos = new ArrayList<>();
        this.total = 0.0;
        this.descuentoAplicado = 0.0;
        this.promocionAplicada = null;
    }

    public Venta(String formaPago) {
        this();
        this.formaPago = formaPago;
    }

    public void agregarProducto(Producto producto) {
        try {
            if (producto != null && producto.verificarStock()) {
                // --- UNIDAD III: POLIMORFISMO Y LÓGICA DE NEGOCIO ---
                // Aplicamos Polimorfismo y Lógica de Negocio de la Unidad III para que el
                // sistema decida el precio final dinámicamente.
                // REGLA DE ORO: Si es Frescos y caduca pronto, aplicamos el 'Remate de
                // Frescos'.
                if (producto instanceof Frescos) {
                    Frescos f = (Frescos) producto;
                    if (f.verificarCaducidad() && promocionAplicada == null) {
                        System.out.println("Sugerencia del Sistema: Aplicando 'Remate de Frescos' (20% OFF)");
                        // Creamos una promoción virtual de remate inmediata (Unidad II)
                        Promocion remate = new Promocion("Remate de Frescos", 20.0, java.time.LocalDate.now(),
                                java.time.LocalDate.now().plusDays(1));
                        remate.getListaProductos().add(f);
                        this.promocionAplicada = remate;
                    }
                }

                listaProductos.add(producto);
                calcularTotal();
            }
        } catch (Exception e) {
            System.err.println("Error al agregar producto a la venta: " + e.getMessage());
        }
    }

    /**
     * Calcula el total de la venta aplicando promociones si existen
     */
    private void calcularTotal() {
        try {
            double subtotal = listaProductos.stream()
                    .mapToDouble(Producto::getPrecioVenta)
                    .sum();

            // Aplicar promoción si existe y está activa
            if (promocionAplicada != null && promocionAplicada.estaActiva()) {
                double totalConDescuento = 0.0;
                for (Producto producto : listaProductos) {
                    if (promocionAplicada.aplicaAProducto(producto)) {
                        totalConDescuento += promocionAplicada.calcularPrecioConDescuento(producto);
                    } else {
                        totalConDescuento += producto.getPrecioVenta();
                    }
                }
                this.descuentoAplicado = subtotal - totalConDescuento;
                this.total = totalConDescuento;
            } else {
                this.descuentoAplicado = 0.0;
                this.total = subtotal;
            }
        } catch (Exception e) {
            System.err.println("Error al calcular total: " + e.getMessage());
            this.total = 0.0;
        }
    }

    /**
     * Aplica una promoción a la venta
     * 
     * @param promocion Promoción a aplicar
     */
    public void aplicarPromocion(Promocion promocion) {
        try {
            if (promocion != null && promocion.estaActiva()) {
                this.promocionAplicada = promocion;
                calcularTotal();
            }
        } catch (Exception e) {
            System.err.println("Error al aplicar promoción: " + e.getMessage());
        }
    }

    /**
     * Finaliza la venta y actualiza el stock.
     * --- POLIMORFISMO Y LÓGICA DE UNIDAD IV ---
     * Antes de cerrar, revisamos si existen productos frescos próximos a caducar
     * para aplicar un descuento del 20% automáticamente si no tienen promoción.
     */
    public void finalizarVenta() {
        try {
            double totalDescuentoExtra = 0.0;

            for (Producto producto : listaProductos) {
                // REDUCCIÓN DE STOCK:
                if (producto.getStockActual() > 0) {
                    producto.setStockActual(producto.getStockActual() - 1);
                }

                // POLIMORFISMO (Unidad III):
                // Identificamos dinámicamente si el producto es de tipo Frescos.
                if (producto instanceof Frescos) {
                    Frescos f = (Frescos) producto;
                    // REGLA DE NEGOCIO: Si vence pronto, aplicamos descuento de liquidación.
                    if (f.verificarCaducidad() && promocionAplicada == null) {
                        double descuento = f.getPrecioVenta() * 0.20; // 20% de descuento
                        totalDescuentoExtra += descuento;
                        System.out.println("Liquidación aplicada a: " + f.getNombre());
                    }
                }
            }

            // Ajustamos el total final con los descuentos por caducidad
            this.descuentoAplicado += totalDescuentoExtra;
            this.total -= totalDescuentoExtra;

        } catch (Exception e) {
            System.err.println("Error al finalizar venta: " + e.getMessage());
        }
    }

    /**
     * Implementación de Clonación Profunda (Unidad II/III)
     * Permite guardar el estado de la venta en el historial sin que cambios
     * posteriores afecten el registro.
     */
    @Override
    public Venta clone() {
        try {
            Venta clon = (Venta) super.clone();
            // Clonamos la lista de productos para asegurar independencia (Clonación
            // Profunda)
            clon.listaProductos = new ArrayList<>(this.listaProductos);
            return clon;
        } catch (CloneNotSupportedException e) {
            System.err.println("Error al clonar venta: " + e.getMessage());
            return null;
        }
    }

    // Getters y Setters
    public int getFolio() {
        return folio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
        calcularTotal();
    }

    public double getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(double descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public Promocion getPromocionAplicada() {
        return promocionAplicada;
    }

    public void setPromocionAplicada(Promocion promocionAplicada) {
        this.promocionAplicada = promocionAplicada;
    }
}
