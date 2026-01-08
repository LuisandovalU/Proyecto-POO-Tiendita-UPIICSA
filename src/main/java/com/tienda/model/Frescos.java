package com.tienda.model;

import java.time.LocalDate;

/**
 * CLASE HIJA: Frescos (Unidad III - Herencia)
 * Esta clase hereda de Producto porque los frescos son productos,
 * pero además tienen caducidad y algunos necesitan refri. Yo le puse
 * estos datos extra porque si no no sabríamos cuándo se echan a perder.
 */
public class Frescos extends Producto {
    // Estos datos son solo de los frescos (Encapsulamiento)
    private LocalDate fechaCaducidad;
    private boolean requiereRefrigeracion;
    private String unidadMedida; // ej. Kg, Litro
    private String unidadVenta; // ej. Pieza, Gramos
    private double cantidadGramos; // Para calcular precio si es por peso
    private boolean seVendePorGramos; // Aquí separé los frescos que se venden por pieza de los que pesamos, como el
                                      // jamón, para que el inventario por gramos sea exacto y no se pierda dinero
    private double pesoPorPiezaGramos; // ej. una barra de jamón pesa 4000g
    private boolean esVentaPorPeso; // Si es true, se vende por gramos aunque se compre por barra/pieza

    public Frescos() {
        super();
    }

    public Frescos(String codigoBarras, String nombre, String marca, String tamanoGramaje,
            double precioCompra, double precioVenta, int stockActual, int stockMinimo,
            LocalDate fechaCaducidad, boolean requiereRefrigeracion, String unidadMedida) {
        // Llamo al constructor del papá (Producto) para no repetir código.
        // La profe dijo que usáramos super() para eso.
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.fechaCaducidad = fechaCaducidad;
        this.requiereRefrigeracion = requiereRefrigeracion;
        this.unidadMedida = unidadMedida;
    }

    @Override
    public void mostrarDetalles() {
        // Aquí uso polimorfismo para mostrar lo especial de los frescos.
        // Me aseguré de que salgan los datos que no tienen los otros productos.
        System.out.println("PRODUCTO FRESCO: " + getNombre());
        System.out.println("Caduca el: " + fechaCaducidad);
        System.out.println("¿Ocupa refri?: " + (requiereRefrigeracion ? "Sí" : "No"));
        System.out.println("Venta por: " + unidadVenta);
    }

    @Override
    public double getPrecioVenta() {
        // POLIMORFISMO (Unidad III):
        // Si el producto se vende por gramos, calculamos el proporcional.
        // Aquí le puse esta cuenta porque los jamones son un relajo si no los pesas.
        if (seVendePorGramos || esVentaPorPeso) {
            return (super.getPrecioVenta() / 1000.0) * cantidadGramos;
        }
        return super.getPrecioVenta();
    }

    @Override
    public String getDetallesEspecificos() {
        return "Caducidad: " + fechaCaducidad + ", Refri: " + (requiereRefrigeracion ? "Sí" : "No") +
                ", Venta: " + (esVentaPorPeso ? "Por Peso" : "Por Pieza");
    }

    /**
     * ESPECIALIZACIÓN (Unidad III):
     * Aquí hice una lógica para ver si ya casi caduca el producto y ponerlo en
     * oferta. Yo me fijé que si faltan 3 días ya urge venderlo.
     */
    public boolean verificarCaducidad() {
        if (fechaCaducidad == null) {
            return false;
        }
        return LocalDate.now().plusDays(3).isAfter(fechaCaducidad);
    }

    // Getters y Setters para mis datos privados (Encapsulamiento)
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

    public String getUnidadVenta() {
        return unidadVenta;
    }

    public void setUnidadVenta(String unidadVenta) {
        this.unidadVenta = unidadVenta;
    }

    public double getCantidadGramos() {
        return cantidadGramos;
    }

    public void setCantidadGramos(double cantidadGramos) {
        this.cantidadGramos = cantidadGramos;
    }

    public boolean isSeVendePorGramos() {
        return seVendePorGramos;
    }

    public void setSeVendePorGramos(boolean seVendePorGramos) {
        this.seVendePorGramos = seVendePorGramos;
    }

    public double getPesoPorPiezaGramos() {
        return pesoPorPiezaGramos;
    }

    public void setPesoPorPiezaGramos(double pesoPorPiezaGramos) {
        this.pesoPorPiezaGramos = pesoPorPiezaGramos;
    }

    public boolean isEsVentaPorPeso() {
        return esVentaPorPeso;
    }

    public void setEsVentaPorPeso(boolean esVentaPorPeso) {
        this.esVentaPorPeso = esVentaPorPeso;
    }

    @Override
    public void setTamanoGramaje(String tamanoGramaje) {
        // Me aseguré de que no dejen esto vacío porque si no no sabemos de qué tamaño
        // es.
        if (tamanoGramaje == null || tamanoGramaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El tamaño/gramaje es obligatorio para productos frescos.");
        }
        super.setTamanoGramaje(tamanoGramaje);
    }
}
