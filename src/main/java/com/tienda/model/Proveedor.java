package com.tienda.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Clase que representa un proveedor
 */
public class Proveedor {
    private String nombreEmpresa;
    private String telefono;
    private String contacto;
    private List<Producto> productosSolicitados;

    public Proveedor() {
        this.productosSolicitados = new ArrayList<>();
    }

    public Proveedor(String nombreEmpresa, String telefono, String contacto) {
        this();
        this.nombreEmpresa = nombreEmpresa;
        this.telefono = telefono;
        this.contacto = contacto;
    }

    // Getters y Setters
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
        return nombreEmpresa;
    }
}
