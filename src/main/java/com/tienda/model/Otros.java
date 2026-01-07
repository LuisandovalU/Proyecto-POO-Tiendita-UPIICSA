package com.tienda.model;

/**
 * CLASE HIJA: Otros (Unidad III - Herencia)
 * Esta clase es para los productos que no supe dónde poner, pero
 * igual heredan de Producto para que funcionen en mi sistema.
 */
public class Otros extends Producto {
    // Encapsulamiento
    private String descripcion;

    public Otros() {
        super();
    }

    public Otros(String codigoBarras, String nombre, String marca, String tamanoGramaje,
            double precioCompra, double precioVenta, int stockActual, int stockMinimo,
            String descripcion) {
        // Aprovecho lo que ya hizo el papá
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.descripcion = descripcion;
    }

    @Override
    public void mostrarDetalles() {
        // Polimorfismo: cada hijo muestra lo que ocupa
        System.out.println("OTRO PRODUCTO: " + getNombre());
        System.out.println("Descripción: " + descripcion);
    }

    @Override
    public String getDetallesEspecificos() {
        return "Descripción: " + descripcion;
    }

    // Getters y Setters
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
