package com.tienda.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * CLASE: Venta (Unidad II - Clases y Objetos)
 * Esta es la clase más importante porque es el ticket de la venta.
 * Aquí guardo todo lo que el cliente se va a llevar. Yo la hice pensando
 * en que sea como un carrito de súper pero en código.
 */
public class Venta implements Cloneable {
    // Estas son mis variables compartidas por toda la clase
    private static int contadorFolios = 1;

    // ENCAPSULAMIENTO (Unidad II):
    // Todos mis atributos son private para que nadie les mueva nada
    // por fuera del sistema (protección de datos). Si alguien quiere
    // cambiar el total, tiene que pasar por mis métodos.
    private int folio;
    private LocalDateTime fecha; // Registra la hora exacta (Unidad III)
    private String formaPago; // Si pagó con lana o tarjeta
    private double total;
    private double descuentoAplicado;
    private List<Producto> listaProductos;
    private Promocion promocionAplicada;

    /**
     * SOBRECARGA DE CONSTRUCTORES (Unidad II):
     * Aquí hice el constructor vacío por si ocupo crear la venta antes
     * de saber cómo va a pagar el cliente. Me sirve mucho para no trabarme.
     */
    public Venta() {
        this.folio = contadorFolios++;
        this.fecha = LocalDateTime.now(); // Puse LocalDateTime para que no falle la fecha
        this.listaProductos = new ArrayList<>();
        this.total = 0.0;
        this.descuentoAplicado = 0.0;
        this.formaPago = "Efectivo"; // Por default
    }

    /**
     * SOBRECARGA DE CONSTRUCTORES (Unidad II):
     * Este otro constructor me sirve si ya desde el inicio sé cómo van a pagar.
     * Es herencia de los conceptos que vimos en la primer semana.
     */
    public Venta(String formaPago) {
        this();
        this.formaPago = formaPago;
    }

    /**
     * Este método lo hice para ir metiendo los productos al carrito.
     * Uso polimorfismo porque puedo meter CUALQUIER producto al mismo tiempo:
     * ya sea un refresco o un kilo de jamón.
     */
    public void agregarProducto(Producto producto) {
        try {
            if (producto != null && producto.verificarStock()) {
                // Aquí checo si es un producto fresco para ver si le bajo el precio.
                // Este es un ejemplo de cómo uso el polimorfismo (instanceof).
                if (producto instanceof Frescos) {
                    Frescos f = (Frescos) producto;
                    if (f.verificarCaducidad() && promocionAplicada == null) {
                        System.out.println("Liquidación: Este fresco está por vencer, 20% menos!");
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
            System.err.println("Error al agregar al carrito: " + e.getMessage());
        }
    }

    /**
     * Con este método saco la cuenta de cuánto va a ser el total.
     * Batallé un poco con los descuentos, pero ya quedó.
     */
    private void calcularTotal() {
        try {
            double subtotal = 0;
            for (Producto p : listaProductos) {
                subtotal += p.getPrecioVenta();
            }

            // Si hay promo, la aplico aquí
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
            this.total = 0.0;
        }
    }

    public void aplicarPromocion(Promocion promocion) {
        try {
            if (promocion != null && promocion.estaActiva()) {
                this.promocionAplicada = promocion;
                calcularTotal();
            }
        } catch (Exception e) {
            System.err.println("Error en la promo: " + e.getMessage());
        }
    }

    /**
     * Este método cierra la venta y baja el stock de la tienda.
     */
    public void finalizarVenta() {
        try {
            double totalDescuentoExtra = 0.0;
            for (Producto producto : listaProductos) {
                // Bajo el stock de mi inventario físico
                if (producto.getStockActual() > 0) {
                    if (producto instanceof Frescos && ((Frescos) producto).isSeVendePorGramos()) {
                        Frescos f = (Frescos) producto;
                        // Aquí va lo de los gramos porque los jamones son un relajo para el inventario.
                        producto.setStockActual((int) (producto.getStockActual() - f.getCantidadGramos()));
                    } else {
                        producto.setStockActual(producto.getStockActual() - 1);
                    }
                }

                // Aquí aplico la Unidad III (Polimorfismo) para ver tipos de productos
                if (producto instanceof Frescos) {
                    Frescos f = (Frescos) producto;
                    if (f.verificarCaducidad() && promocionAplicada == null) {
                        double descuento = f.getPrecioVenta() * 0.20;
                        totalDescuentoExtra += descuento;
                    }
                }
            }
            this.descuentoAplicado += totalDescuentoExtra;
            this.total -= totalDescuentoExtra;
        } catch (Exception e) {
            System.err.println("Error al terminar la venta: " + e.getMessage());
        }
    }

    @Override
    public Venta clone() {
        try {
            Venta clon = (Venta) super.clone();
            clon.listaProductos = new ArrayList<>(this.listaProductos);
            return clon;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    // Mis Getters y Setters para entrar a los datos privados (Encapsulamiento de la
    // Unidad II)
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
        if (formaPago == null || formaPago.trim().isEmpty()) {
            throw new IllegalArgumentException("La forma de pago es obligatoria.");
        }
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
