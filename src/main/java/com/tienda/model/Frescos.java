package com.tienda.model;

import java.time.LocalDate;

/**
 * CLASE HIJA: Frescos (Unidad III - Herencia)
 * Esta clase es la ABSTRACCIÓN perfecta de una Cremería.
 * La diseñé así porque en una cremería no vendes todo por pieza; a veces el
 * cliente
 * solo quiere "diez pesos de queso" o 250 gramos de jamón. Esta clase resuelve
 * la necesidad de vender productos perecederos de forma fraccionada.
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

    /**
     * CONVERSIÓN DE UNIDADES (Abstracción de Cremería):
     * Aquí es donde sucede la magia de la báscula. El sistema recibe la entrada
     * en GRAMOS y hace la conversión automática al precio por kilo.
     * Así el encargado no tiene que andar haciendo reglas de tres en su cabeza.
     */
    public double calcularPrecioPorGramos(double gramos) {
        // Divido entre 1000 para sacar el precio por gramo y multiplico por los gramos
        // pedidos
        return (super.getPrecioVenta() / 1000.0) * gramos;
    }

    @Override
    public double getPrecioVenta() {
        // POLIMORFISMO (Unidad III):
        // Si el producto se vende por gramos, calculamos el proporcional.
        // Aquí le puse esta cuenta porque los jamones son un relajo si no los pesas.
        if (seVendePorGramos || esVentaPorPeso) {
            return calcularPrecioPorGramos(cantidadGramos);
        }
        return super.getPrecioVenta();
    }

    @Override
    public double calcularPrecioConDescuento(double descuento) {
        // Aseguramos que el descuento se aplique sobre el precio proporcional
        // si es un producto vendido por peso.
        double precioActual = getPrecioVenta();
        if (descuento < 0 || descuento > 100) {
            return precioActual;
        }
        return precioActual * (1 - descuento / 100.0);
    }

    @Override
    public String getDetallesEspecificos() {
        return "Caducidad: " + fechaCaducidad + ", Refri: " + (requiereRefrigeracion ? "Sí" : "No") +
                ", Venta: " + (esVentaPorPeso ? "Por Peso" : "Por Pieza");
    }

    /**
     * VISIBILIDAD DE RIESGO:
     * Este método identifica productos con menos de 3 días de vida.
     * Si regresa true, el Encargado debe crear una promoción inmediatamente
     * para no perder la mercancía. ¡Es como un semáforo de alerta!
     */
    public boolean alertarCaducidad() {
        if (fechaCaducidad == null) {
            return false;
        }
        // Si faltan 3 días o menos, ya es riesgo alto
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
