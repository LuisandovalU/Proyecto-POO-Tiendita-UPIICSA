package com.tienda.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * CLASE: Promoción (Unidad II - Relaciones entre Clases)
 * Gestiona colecciones de objetos para aplicar descuentos masivos.
 */
public class Promocion {
    private String nombrePromo;
    private double descuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    // ASOCIACIÓN (Unidad II): Una promoción gestiona múltiples productos.
    private List<Producto> listaProductos;

    public Promocion() {
        this.listaProductos = new ArrayList<>();
    }

    public Promocion(String nombrePromo, double descuento, LocalDate fechaInicio, LocalDate fechaFin) {
        this();
        this.nombrePromo = nombrePromo;
        this.descuento = descuento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    /**
     * --- UNIDAD II: MANEJO DE MÉTODOS ---
     * Implementamos isVigente() para comparar fechas y determinar la validez
     * temporal del objeto de negocio.
     */
    public boolean isVigente() {
        return estaActiva();
    }

    /**
     * Verifica si la promoción está activa según la fecha
     * 
     * @return true si está activa, false en caso contrario
     */
    public boolean estaActiva() {
        try {
            if (fechaInicio == null || fechaFin == null) {
                return false;
            }
            LocalDate hoy = LocalDate.now();
            return !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaFin);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica si un producto está incluido en esta promoción
     * 
     * @param producto Producto a verificar
     * @return true si el producto está en la lista de productos promocionados
     */
    public boolean aplicaAProducto(Producto producto) {
        try {
            if (producto == null || listaProductos == null) {
                return false;
            }
            return listaProductos.contains(producto);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Calcula el precio con descuento para un producto
     * 
     * @param producto Producto al que aplicar el descuento
     * @return Precio con descuento aplicado, o precio original si no aplica
     */
    public double calcularPrecioConDescuento(Producto producto) {
        try {
            if (!estaActiva() || !aplicaAProducto(producto)) {
                return producto.getPrecioVenta();
            }
            // Si el descuento viene como decimal (ej. 0.15), lo multiplicamos por 100
            // para compatibilidad con Producto.calcularPrecioConDescuento
            double descPorcentaje = (descuento <= 1.0) ? descuento * 100 : descuento;
            return producto.calcularPrecioConDescuento(descPorcentaje);
        } catch (Exception e) {
            return producto != null ? producto.getPrecioVenta() : 0.0;
        }
    }

    // Getters y Setters
    public String getNombrePromo() {
        return nombrePromo;
    }

    public void setNombrePromo(String nombrePromo) {
        this.nombrePromo = nombrePromo;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }
}
