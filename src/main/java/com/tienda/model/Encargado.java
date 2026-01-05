package com.tienda.model;

/**
 * Clase que representa un usuario Encargado
 * Hereda de Usuario aplicando herencia
 * El encargado es un vendedor con funciones adicionales
 */
public class Encargado extends Usuario {

    public Encargado() {
        super();
        this.tipoUsuario = TipoUsuario.ENCARGADO;
    }

    public Encargado(String nombreUsuario, String contrasena, String nombreCompleto) {
        super(nombreUsuario, contrasena, nombreCompleto, TipoUsuario.ENCARGADO);
    }

    /**
     * Permisos del encargado según requerimientos:
     * - Todas las funciones de vendedor
     * - Verificar que las entregas coincidan con los pedidos
     * - Realizar pagos en efectivo
     * - Para pagos interbancarios, debe comunicarse con el administrador
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
                case "verificar_entrega":
                case "pagar_efectivo":
                    return true;
                case "pagar_interbancario":
                    // Debe comunicarse con administrador
                    return false;
                default:
                    return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean puedeVerificarEntrega() {
        return tienePermiso("verificar_entrega");
    }

    public boolean puedePagarEfectivo() {
        return tienePermiso("pagar_efectivo");
    }

    public boolean puedeRealizarVenta() {
        return tienePermiso("realizar_venta");
    }
}
