package com.tienda.model;

import java.time.LocalDate;

/**
 * CLASE HIJA: Frescos (Unidad III - Herencia)
 * Esta clase hereda de Producto porque los frescos son productos,
 * pero además tienen caducidad y algunos necesitan refri.
 */
public class Frescos extends Producto {
    // Estos datos son solo de los frescos (Encapsulamiento)
    private LocalDate fechaCaducidad;
    private boolean requiereRefrigeracion;
    private String unidadMedida; // ej. Kg, Litro

    public Frescos() {
        super();
    }

    public Frescos(String codigoBarras, String nombre, String marca, String tamanoGramaje,
            double precioCompra, double precioVenta, int stockActual, int stockMinimo,
            LocalDate fechaCaducidad, boolean requiereRefrigeracion, String unidadMedida) {
        // Llamo al constructor del papá (Producto) para no repetir código
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.fechaCaducidad = fechaCaducidad;
        this.requiereRefrigeracion = requiereRefrigeracion;
        this.unidadMedida = unidadMedida;
    }

    @Override
    public void mostrarDetalles() {
        // Aquí uso polimorfismo para mostrar lo especial de los frescos
        System.out.println("PRODUCTO FRESCO: " + getNombre());
        System.out.println("Caduca el: " + fechaCaducidad);
        System.out.println("¿Ocupa refri?: " + (requiereRefrigeracion ? "Sí" : "No"));
    }

    @Override
    public String getDetallesEspecificos() {
        return "Caducidad: " + fechaCaducidad + ", Refri: " + (requiereRefrigeracion ? "Sí" : "No");
    }

    /**
     * ESPECIALIZACIÓN (Unidad III):
     * Aquí hice una lógica para ver si ya casi caduca el producto y ponerlo en
     * oferta.
     */
    public boolean verificarCaducidad() {
        if (fechaCaducidad == null) {
            return false;
        }
        return LocalDate.now().plusDays(3).isAfter(fechaCaducidad);
    }

    // Getters y Setters para mis datos privados
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
