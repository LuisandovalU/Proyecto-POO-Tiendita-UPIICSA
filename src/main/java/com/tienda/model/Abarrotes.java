package com.tienda.model;

import java.time.LocalDate;

/**
 * Clase para productos de abarrotes
 */
public class Abarrotes extends Producto {
    private LocalDate fechaCaducidad;
    private String tipoEnvase;

    public Abarrotes() {
        super();
    }

    public Abarrotes(String codigoBarras, String nombre, String marca, String tamanoGramaje,
                    double precioCompra, double precioVenta, int stockActual, int stockMinimo,
                    LocalDate fechaCaducidad, String tipoEnvase) {
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.fechaCaducidad = fechaCaducidad;
        this.tipoEnvase = tipoEnvase;
    }

    // Getters y Setters
    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getTipoEnvase() {
        return tipoEnvase;
    }

    public void setTipoEnvase(String tipoEnvase) {
        this.tipoEnvase = tipoEnvase;
    }
}
