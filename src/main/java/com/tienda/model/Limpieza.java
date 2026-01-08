package com.tienda.model;

/**
 * CLASE HIJA: Limpieza (Unidad III - Herencia)
 * Esta clase es para el cloro, jabón y esas cosas. Heredan de Producto
 * para que no me canse escribiendo lo mismo 20 veces.
 */
public class Limpieza extends Producto {
    // Encapsulamiento: atributo privado. No queremos que nadie lave mal los datos.
    private String instruccionesUso;

    public Limpieza() {
        super();
    }

    public Limpieza(String codigoBarras, String nombre, String marca, String tamanoGramaje,
            double precioCompra, double precioVenta, int stockActual, int stockMinimo,
            String instruccionesUso) {
        // Usamos super() como nos enseñaron en clase para llamar al papá.
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.instruccionesUso = instruccionesUso;
    }

    @Override
    public void mostrarDetalles() {
        // Polimorfismo: muestro lo que es especial de limpieza.
        System.out.println("PRODUCTO DE LIMPIEZA: " + getNombre());
        System.out.println("Instrucciones: " + instruccionesUso);
    }

    @Override
    public String getDetallesEspecificos() {
        return "Instrucciones: " + instruccionesUso;
    }

    // Getters y Setters (Unidad II - Encapsulamiento)
    public String getInstruccionesUso() {
        return instruccionesUso;
    }

    public void setInstruccionesUso(String instruccionesUso) {
        this.instruccionesUso = instruccionesUso;
    }
}
