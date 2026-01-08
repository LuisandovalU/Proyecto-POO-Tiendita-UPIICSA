package com.tienda.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * CLASE: Proveedor (Unidad II - Clases y Objetos)
 * Aquí guardo quién nos trae la mercancía. Yo le puse esta clase
 * para tener a la mano el teléfono y el contacto por si algo falla.
 */
public class Proveedor {
    // Encapsulamiento (Unidad II): Todo privado para que no se nos pierda el
    // contacto.
    private String nombreEmpresa;
    private String telefono;
    private String contacto;
    private List<Producto> productosSolicitados;
    private boolean entregaEnTienda; // Aquí anoto si el proveedor viene o si yo tengo que ir por las cosas

    public Proveedor() {
        this.productosSolicitados = new ArrayList<>();
    }

    public Proveedor(String nombreEmpresa, String telefono, String contacto) {
        this();
        this.nombreEmpresa = nombreEmpresa;
        this.telefono = telefono;
        this.contacto = contacto;
    }

    // Getters y Setters (Para entrar a los datos privados como pide la Unidad II)
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public List<Producto> getProductosSolicitados() {
        return productosSolicitados;
    }

    public void setProductosSolicitados(List<Producto> productosSolicitados) {
        this.productosSolicitados = productosSolicitados;
    }

    public boolean isEntregaEnTienda() {
        return entregaEnTienda;
    }

    public void setEntregaEnTienda(boolean entregaEnTienda) {
        this.entregaEnTienda = entregaEnTienda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Proveedor proveedor = (Proveedor) o;
        return Objects.equals(nombreEmpresa, proveedor.nombreEmpresa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreEmpresa);
    }

    @Override
    public String toString() {
        // Aquí le puse un extra para saber si nosotros tenemos que ir por la carga.
        String logistica = entregaEnTienda ? "" : " (Recolección Propia)";
        return nombreEmpresa + logistica;
    }
}
