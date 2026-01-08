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
     * El administrador tiene acceso total al sistema (incluyendo Pedidos y
     * Promociones)
     * Aplicación de polimorfismo: implementa el método abstracto de Usuario
     */
    @Override
    public boolean verificarPermisos(String modulo) {
        // El administrador tiene todos los permisos en todos los módulos
        return true;
    }
}
