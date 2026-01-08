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
     * El Vendedor solo puede entrar a la pestaña de Ventas.
     * Aplicación de polimorfismo: implementa el método abstracto de Usuario
     */
    @Override
    public boolean verificarPermisos(String modulo) {
        return "Ventas".equals(modulo);
    }
}
