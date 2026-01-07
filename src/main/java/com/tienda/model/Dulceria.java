package com.tienda.model;

/**
 * CLASE HIJA: Dulceria (Unidad III - Herencia)
 * Esta clase es para los dulces. Como son productos, heredan de la clase
 * Producto
 * y así ya no tengo que escribir todo otra vez.
 */
public class Dulceria extends Producto {
    // Encapsulamiento: atributo privado para que solo se mueva desde aquí
    private String categoria;

    public Dulceria() {
        super();
    }

    public Dulceria(String codigoBarras, String nombre, String marca, String tamanoGramaje,
            double precioCompra, double precioVenta, int stockActual, int stockMinimo,
            String categoria) {
        // Constructor del papá
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.categoria = categoria;
    }

    @Override
    public void mostrarDetalles() {
        // Polimorfismo: muestro qué tipo de dulce es
        System.out.println("DULCE: " + getNombre());
        System.out.println("Categoría: " + categoria);
    }

    @Override
    public String getDetallesEspecificos() {
        return "Categoría Dulce: " + categoria;
    }

    // Getters y Setters
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
