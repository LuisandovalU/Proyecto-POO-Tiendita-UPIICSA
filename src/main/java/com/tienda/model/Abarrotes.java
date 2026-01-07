package com.tienda.model;

import java.time.LocalDate;

/**
 * CLASE HIJA: Abarrotes (Unidad III - Herencia)
 * Mi clase para los productos de abarrotes. Hereda de Producto porque
 * son lo mismo pero con envase y caducidad.
 */
public class Abarrotes extends Producto {
    // Encapsulamiento (Unidad II): Atributos privados para seguridad
    private LocalDate fechaCaducidad;
    private String tipoEnvase;

    public Abarrotes() {
        super();
    }

    public Abarrotes(String codigoBarras, String nombre, String marca, String tamanoGramaje,
            double precioCompra, double precioVenta, int stockActual, int stockMinimo,
            LocalDate fechaCaducidad, String tipoEnvase) {
        // Aprovecho el constructor del papá
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.fechaCaducidad = fechaCaducidad;
        this.tipoEnvase = tipoEnvase;
    }

    @Override
    public void mostrarDetalles() {
        // Polimorfismo: muestro los detalles específicos de abarrotes
        System.out.println("ABARROTE: " + getNombre());
        System.out.println("Envase: " + tipoEnvase);
        System.out.println("Caduca: " + fechaCaducidad);
    }

    @Override
    public String getDetallesEspecificos() {
        return "Envase: " + tipoEnvase + ", Caducidad: " + fechaCaducidad;
    }

    // Getters y Setters para que nadie le meta mano directo a los datos
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
