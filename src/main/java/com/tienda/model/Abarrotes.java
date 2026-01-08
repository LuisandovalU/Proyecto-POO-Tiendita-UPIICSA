package com.tienda.model;

import java.time.LocalDate;

/**
 * CLASE HIJA: Abarrotes (Unidad III - Herencia)
 * Mi clase para los productos de abarrotes. Hereda de Producto porque
 * son lo mismo pero con envase y caducidad. Yo hice esto para que no
 * tengamos que escribir "marca" o "precio" otra vez, que flojera.
 */
public class Abarrotes extends Producto {
    // Encapsulamiento (Unidad II): Atributos privados para seguridad.
    // Mis compañeros dicen que para qué, pero la profe dice que es la regla.
    private LocalDate fechaCaducidad;
    private String tipoEnvase;
    private int unidadesPorPaquete; // ej. una caja trae 12 piezas

    public Abarrotes() {
        super();
    }

    public Abarrotes(String codigoBarras, String nombre, String marca, String tamanoGramaje,
            double precioCompra, double precioVenta, int stockActual, int stockMinimo,
            LocalDate fechaCaducidad, String tipoEnvase) {
        // Aprovecho el constructor del papá para que se guarde todo rápido.
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.fechaCaducidad = fechaCaducidad;
        this.tipoEnvase = tipoEnvase;
    }

    @Override
    public void mostrarDetalles() {
        // Polimorfismo: muestro los detalles específicos de abarrotes.
        // Aquí le puse lo del envase porque es lo que importa en esta categoría.
        System.out.println("ABARROTE: " + getNombre());
        System.out.println("Envase: " + tipoEnvase);
        System.out.println("Caduca: " + fechaCaducidad);
    }

    @Override
    public String getDetallesEspecificos() {
        return "Envase: " + tipoEnvase + ", Caducidad: " + fechaCaducidad;
    }

    // Getters y Setters para que nadie le meta mano directo a los datos
    // (Encapsulamiento)
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

    public int getUnidadesPorPaquete() {
        return unidadesPorPaquete;
    }

    public void setUnidadesPorPaquete(int unidadesPorPaquete) {
        this.unidadesPorPaquete = unidadesPorPaquete;
    }
}
