package com.tienda.model;

/**
 * Clase que representa un usuario Administrador
 * Hereda de Usuario aplicando herencia
 */
public class Administrador extends Usuario {

    public Administrador() {
        super();
        this.tipoUsuario = TipoUsuario.ADMINISTRADOR;
    }

    public Administrador(String nombreUsuario, String contrasena, String nombreCompleto) {
        super(nombreUsuario, contrasena, nombreCompleto, TipoUsuario.ADMINISTRADOR);
    }

    /**
     * El administrador tiene acceso completo al sistema
     * Aplicación de polimorfismo: implementa el método abstracto de Usuario
     */
    @Override
    public boolean tienePermiso(String accion) {
        try {
            // El administrador tiene todos los permisos
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Permisos específicos del administrador según requerimientos:
     * - Ver desglose completo de ventas por producto y departamento
     * - Generar listas de pedidos cuando el stock llega al mínimo
     * - Ver totales de ventas de tiempo aire por plataforma
     */
    public boolean puedeVerReportes() {
        return true;
    }

    public boolean puedeGenerarPedidos() {
        return true;
    }

    public boolean puedeVerVentasTiempoAire() {
        return true;
    }
}
