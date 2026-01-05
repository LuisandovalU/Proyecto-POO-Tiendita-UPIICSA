package com.tienda.model;

/**
 * Clase para productos de dulcería
 */
public class Dulceria extends Producto {
    private String categoria;

    public Dulceria() {
        super();
    }

    public Dulceria(String codigoBarras, String nombre, String marca, String tamanoGramaje,
                   double precioCompra, double precioVenta, int stockActual, int stockMinimo,
                   String categoria) {
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.categoria = categoria;
    }

    // Getters y Setters
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
