package com.tienda.model;

import java.util.Objects;

/**
 * Clase base para usuarios del sistema. Yo la hice para que no tengamos
 * que repetir el nombre y la contraseña en cada tipo de usuario.
 * Implementa polimorfismo (Unidad III) para que cada quien tenga sus permisos.
 */
public abstract class Usuario {
    // Usamos protected para que mis clases hijas (Admin, Vendedor) puedan
    // usar las variables sin problemas, pero que los de fuera no las vean.
    protected String nombreUsuario;
    protected String contrasena;
    protected String nombreCompleto;
    protected TipoUsuario tipoUsuario;

    public enum TipoUsuario {
        ADMINISTRADOR,
        VENDEDOR,
        ENCARGADO
    }

    public Usuario() {
    }

    public Usuario(String nombreUsuario, String contrasena, String nombreCompleto, TipoUsuario tipoUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.tipoUsuario = tipoUsuario;
    }

    /**
     * MÉTODO ABSTRACTO (Unidad III):
     * Este lo hice para ver si el usuario puede entrar a un módulo específico.
     * Es puro polimorfismo para que cada rol tenga su propio control de acceso.
     */
    public abstract boolean verificarPermisos(String modulo);

    /**
     * Verifica que la contraseña sea la correcta.
     * Yo le puse un try-catch por si se me pasaba algún nulo.
     */
    public boolean verificarCredenciales(String nombreUsuario, String contrasena) {
        try {
            return this.nombreUsuario.equals(nombreUsuario) &&
                    this.contrasena.equals(contrasena);
        } catch (Exception e) {
            return false;
        }
    }

    // Getters y Setters (Encapsulamiento de la Unidad II)
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombreUsuario, usuario.nombreUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreUsuario);
    }

    @Override
    public String toString() {
        return nombreCompleto + " (" + tipoUsuario + ")";
    }
}
