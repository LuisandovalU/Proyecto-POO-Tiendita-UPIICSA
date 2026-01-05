package com.tienda.model;

/**
 * Clase para otros productos que no encajan en las categorías principales
 */
public class Otros extends Producto {
    private String descripcion;

    public Otros() {
        super();
    }

    public Otros(String codigoBarras, String nombre, String marca, String tamanoGramaje,
                double precioCompra, double precioVenta, int stockActual, int stockMinimo,
                String descripcion) {
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
