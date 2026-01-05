package com.tienda.model;

import java.time.LocalDate;

/**
 * Clase para productos frescos que requieren refrigeración
 */
public class Frescos extends Producto {
    private LocalDate fechaCaducidad;
    private boolean requiereRefrigeracion;
    private String unidadMedida;

    public Frescos() {
        super();
    }

    public Frescos(String codigoBarras, String nombre, String marca, String tamanoGramaje,
            double precioCompra, double precioVenta, int stockActual, int stockMinimo,
            LocalDate fechaCaducidad, boolean requiereRefrigeracion, String unidadMedida) {
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.fechaCaducidad = fechaCaducidad;
        this.requiereRefrigeracion = requiereRefrigeracion;
        this.unidadMedida = unidadMedida;
    }

    /**
     * ESPECIALIZACIÓN (Unidad III):
     * Verifica si el producto está próximo a caducar (menos de 3 días).
     * 
     * @return true si vence en 3 días o menos, false en caso contrario.
     */
    public boolean verificarCaducidad() {
        if (fechaCaducidad == null) {
            return false;
        }
        // COMENTARIO: Aplicamos lógica de negocio para alertar al sistema
        // sobre productos perecederos (Unidad I: Reglas del Objeto).
        return LocalDate.now().plusDays(3).isAfter(fechaCaducidad);
    }

    // Getters y Setters
    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public boolean isRequiereRefrigeracion() {
        return requiereRefrigeracion;
    }

    public void setRequiereRefrigeracion(boolean requiereRefrigeracion) {
        this.requiereRefrigeracion = requiereRefrigeracion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
}
