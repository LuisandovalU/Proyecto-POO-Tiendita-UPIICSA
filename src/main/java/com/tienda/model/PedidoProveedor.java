package com.tienda.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * CLASE: PedidoProveedor (Unidad III - Relación entre clases)
 * Representa la solicitud de mercancía a un proveedor externo.
 * Cumple con la relación 'se_pide_a' del diagrama de clases.
 */
public class PedidoProveedor {
    // ATRIBUTOS DE ESTADO (Unidad II):
    // Gestionan el ciclo de vida del pedido desde la solicitud hasta la entrega.
    private LocalDate fechaSolicitud;
    private LocalDate fechaEntrega;
    private String estatus; // Pendiente, Generado, Entregado
    private List<Producto> listaProductos; // Los productos que esperamos recibir
    private Proveedor proveedor; // El Proveedor al que se le realiza el pedido

    public PedidoProveedor() {
        this.fechaSolicitud = LocalDate.now();
        this.listaProductos = new ArrayList<>();
        this.estatus = "Pendiente";
    }

    public PedidoProveedor(Proveedor proveedor) {
        this();
        this.proveedor = proveedor;
    }

    /**
     * Genera un nuevo pedido inicializando la fecha y el estatus.
     * Según el flujo del negocio UML.
     */
    public void generarPedido() {
        this.fechaSolicitud = LocalDate.now();
        this.estatus = "Generado";
    }

    // Getters y Setters
    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
