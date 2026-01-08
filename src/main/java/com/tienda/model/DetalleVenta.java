package com.tienda.model;

/**
 * CLASE: DetalleVenta (Unidad II - Clases y Objetos)
 * Esta clase representa un renglón en nuestra nota de venta.
 * En lugar de tener una lista simple de productos, usamos esto para
 * poder decir "lleva 3 gansitos" sin repetir el producto 3 veces.
 * ¡Eficiencia ante todo!
 */
public class DetalleVenta implements Cloneable {
    private Producto producto;
    private int cantidad;

    public DetalleVenta(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return producto.getPrecioVenta() * cantidad;
    }

    @Override
    public DetalleVenta clone() {
        try {
            return (DetalleVenta) super.clone();
        } catch (CloneNotSupportedException e) {
            return new DetalleVenta(this.producto, this.cantidad);
        }
    }
}
