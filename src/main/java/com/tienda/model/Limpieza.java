package com.tienda.model;

/**
 * Clase para productos de limpieza
 */
public class Limpieza extends Producto {
    private String usoEspecifico;

    public Limpieza() {
        super();
    }

    public Limpieza(String codigoBarras, String nombre, String marca, String tamanoGramaje,
                   double precioCompra, double precioVenta, int stockActual, int stockMinimo,
                   String usoEspecifico) {
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.usoEspecifico = usoEspecifico;
    }

    // Getters y Setters
    public String getUsoEspecifico() {
        return usoEspecifico;
    }

    public void setUsoEspecifico(String usoEspecifico) {
        this.usoEspecifico = usoEspecifico;
    }
}
