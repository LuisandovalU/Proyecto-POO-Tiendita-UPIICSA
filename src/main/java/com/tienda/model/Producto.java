package com.tienda.model;

import java.util.Objects;

/**
 * CLASE ABSTRACTA: Producto (Unidad III - Herencia y Polimorfismo)
 * Esta es la clase base de todo mi sistema. La puse abstracta porque no tiene
 * sentido crear un "Producto" a secas, siempre tiene que ser algo como leche o
 * frituras. La profe dijo que esto es lo mejor para no andar repitiendo código.
 */
public abstract class Producto {
    // ENCAPSULAMIENTO (Unidad II):
    // Usé private aquí para que nadie de fuera pueda moverle a los datos sin
    // permiso,
    // así protejo la integridad de la información de mi programa. Mis compañeros
    // luego quieren cambiar los precios directo y así no se puede.
    private String codigoBarras;
    private String nombre;
    private String marca;
    private String tamanoGramaje;
    private double precioCompra;
    private double precioVenta;
    private int stockActual;
    private int stockMinimo;

    public Producto() {
    }

    public Producto(String codigoBarras, String nombre, String marca, String tamanoGramaje,
            double precioCompra, double precioVenta, int stockActual, int stockMinimo) {
        // Aquí permitimos variedad para que el precio cambie según la marca o el
        // tamaño,
        // como en una tienda real. Me aseguré de que todo se guarde bien desde el
        // inicio.
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
     * UNIDAD III - MÉTODO ABSTRACTO:
     * Este método lo tienen que implementar a fuerzas todos los hijos para
     * mostrar sus propios datos, aplicando el polimorfismo. Es como una receta
     * que cada quien sigue a su modo.
     */
    public abstract void mostrarDetalles();

    /**
     * UNIDAD III - MÉTODO ABSTRACTO:
     * Cada tipo de producto debe devolver su información única. Yo lo hice así
     * para que si es un Refresco diga una cosa y si es Jamón diga otra.
     */
    public abstract String getDetallesEspecificos();

    /**
     * Aquí checo si todavía tengo mercancía en la tienda.
     * Si regresa false, ya valió y hay que pedir más.
     */
    public boolean verificarStock() {
        try {
            return stockActual > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Este método me avisa si ya me queda poquito para ir a pedirle al proveedor.
     * Es para que no nos quedemos sin nada que vender.
     */
    public boolean necesitaReposicion() {
        try {
            return stockActual <= stockMinimo;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Con esto calculo cuánto va a costar con el descuento que me diga la profe
     * o lo que el Administrador ponga en su panel.
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

    // Getters y Setters para poder entrar a los datos privados (Encapsulamiento)
    // Los hice todos para que la Unidad II quede completa.
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
        // // Aquí el sistema avisa solo cuando ya estamos en el límite, para que el
        // Administrador sepa qué pedirle al proveedor antes de que se acabe por
        // completo
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
