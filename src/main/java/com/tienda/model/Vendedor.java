package com.tienda.model;

/**
 * Clase que representa un usuario Vendedor
 * Hereda de Usuario aplicando herencia
 */
public class Vendedor extends Usuario {

    public Vendedor() {
        super();
        this.tipoUsuario = TipoUsuario.VENDEDOR;
    }

    public Vendedor(String nombreUsuario, String contrasena, String nombreCompleto) {
        super(nombreUsuario, contrasena, nombreCompleto, TipoUsuario.VENDEDOR);
    }

    /**
     * Permisos del vendedor según requerimientos:
     * - Administrar ventas de productos
     * - Cobrar y dar cambio
     * - Procesar devoluciones (solo si el producto está en mal estado)
     * Aplicación de polimorfismo: implementa el método abstracto de Usuario
     */
    @Override
    public boolean tienePermiso(String accion) {
        try {
            switch (accion) {
                case "realizar_venta":
                case "cobrar":
                case "dar_cambio":
                case "procesar_devolucion":
                    return true;
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean puedeRealizarVenta() {
        return tienePermiso("realizar_venta");
    }

    public boolean puedeProcesarDevolucion() {
        return tienePermiso("procesar_devolucion");
    }
}
