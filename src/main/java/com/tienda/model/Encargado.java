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
     * El Encargado puede entrar a Ventas e Inventarios.
     * Aplicación de polimorfismo: implementa el método abstracto de Usuario
     */
    @Override
    public boolean verificarPermisos(String modulo) {
        if (modulo == null)
            return false;

        switch (modulo) {
            case "Ventas":
            case "Inventarios":
                return true;
            default:
                return false;
        }
    }
}
