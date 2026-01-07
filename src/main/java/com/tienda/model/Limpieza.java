package com.tienda.model;

/**
 * CLASE HIJA: Limpieza (Unidad III - Herencia)
 * Esta clase es para los jabones, cloros y todo eso. Hereda de Producto
 * porque siguen siendo cosas que vendo en mi tiendita.
 */
public class Limpieza extends Producto {
    // Encapsulamiento: atributo privado
    private String usoEspecifico;

    public Limpieza() {
        super();
    }

    public Limpieza(String codigoBarras, String nombre, String marca, String tamanoGramaje,
            double precioCompra, double precioVenta, int stockActual, int stockMinimo,
            String usoEspecifico) {
        // Constructor del papá para ahorrar código
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.usoEspecifico = usoEspecifico;
    }

    @Override
    public void mostrarDetalles() {
        // Polimorfismo en acción
        System.out.println("PRODUCTO DE LIMPIEZA: " + getNombre());
        System.out.println("Uso: " + usoEspecifico);
    }

    @Override
    public String getDetallesEspecificos() {
        return "Uso: " + usoEspecifico;
    }

    // Getters y Setters
    public String getUsoEspecifico() {
        return usoEspecifico;
    }

    public void setUsoEspecifico(String usoEspecifico) {
        this.usoEspecifico = usoEspecifico;
    }
}
