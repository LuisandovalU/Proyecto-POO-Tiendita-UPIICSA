 package com.tienda.model;

import java.util.Objects;

/**
 * CLASE ABSTRACTA: Producto (Unidad III - Herencia y Polimorfismo)
 * Esta clase sirve como base para todos los productos del sistema.
 * Es 'abstract' porque no tiene sentido instanciar un 'Producto' genérico;
 * siempre debe ser de un tipo específico (Abarrotes, TiempoAire, etc.).
 */
public abstract class Producto {
    // APLICACIÓN DE ENCAPSULAMIENTO (Unidad II):
    // Usamos 'protected' para que las clases hijas puedan acceder a estos atributos
    // directamente por herencia, pero manteniéndolos protegidos del exterior.
    protected String codigoBarras; // Identificador único requerido para el inventario
    protected String nombre; // Nombre descriptivo del producto
    protected String marca; // Marca para filtrado y reportes
    protected String tamanoGramaje; // Información de presentación (ej. 500g, 1L)
    protected double precioCompra; // Costo por unidad para calcular margen de utilidad
    protected double precioVenta; // Precio final al cliente
    protected int stockActual; // Cantidad física disponible en tienda
    protected int stockMinimo; // Nivel crítico para alertas de resurtido (Unidad V)

    public Producto() {
    }

    public Producto(String codigoBarras, String nombre, String marca, String tamanoGramaje,
            double precioCompra, double precioVenta, int stockActual, int stockMinimo) {
        this.codigoBarras = codigoBarras;
        this.nombre = nombre;
        this.marca = marca;
        this.tamanoGramaje = tamanoGramaje;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
    }

    /**
     * Verifica si hay stock disponible
     * 
     * @return true si hay stock suficiente, false en caso contrario
     */
    public boolean verificarStock() {
        try {
            return stockActual > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica si el stock está por debajo del mínimo
     * 
     * @return true si el stock actual es menor o igual al stock mínimo
     */
    public boolean necesitaReposicion() {
        try {
            return stockActual <= stockMinimo;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Calcula el precio con descuento aplicado
     * 
     * @param descuento Porcentaje de descuento (0-100)
     * @return Precio con descuento aplicado
     */
    public double calcularPrecioConDescuento(double descuento) {
        try {
            if (descuento < 0 || descuento > 100) {
                return precioVenta;
            }
            return precioVenta * (1 - descuento / 100.0);
        } catch (Exception e) {
            return precioVenta;
        }
    }

    // Getters y Setters
    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTamanoGramaje() {
        return tamanoGramaje;
    }

    public void setTamanoGramaje(String tamanoGramaje) {
        this.tamanoGramaje = tamanoGramaje;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStockActual() {
        return stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Producto producto = (Producto) o;
        return Objects.equals(codigoBarras, producto.codigoBarras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoBarras);
    }

    @Override
    public String toString() {
        return nombre + " - " + marca + " (" + codigoBarras + ")";
    }
}
