package com.tienda.model;

import java.util.Objects;

/**
 * Clase base para usuarios del sistema
 * Implementa polimorfismo para diferentes tipos de usuarios
 */
public abstract class Usuario {
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
     * Método abstracto para verificar permisos específicos de cada tipo de usuario
     * Aplicación de polimorfismo
     */
    public abstract boolean tienePermiso(String accion);

    /**
     * Verifica las credenciales del usuario
     */
    public boolean verificarCredenciales(String nombreUsuario, String contrasena) {
        try {
            return this.nombreUsuario.equals(nombreUsuario) && 
                   this.contrasena.equals(contrasena);
        } catch (Exception e) {
            return false;
        }
    }

    // Getters y Setters
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
