package com.tienda.controller;

import com.tienda.model.Administrador;
import com.tienda.model.Encargado;
import com.tienda.model.Usuario;
import com.tienda.model.Vendedor;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar usuarios y autenticación
 */
public class UsuarioController {
    private List<Usuario> usuarios;
    private Usuario usuarioActual;

    public UsuarioController() {
        this.usuarios = new ArrayList<>();
        this.usuarioActual = null;
        inicializarUsuariosEjemplo();
    }

    /**
     * Inicializa con usuarios de ejemplo para cada perfil
     */
    private void inicializarUsuariosEjemplo() {
        try {
            // Administrador
            usuarios.add(new Administrador("admin", "admin123", "Administrador Principal"));

            // Vendedor
            usuarios.add(new Vendedor("vendedor1", "vendedor123", "Juan Pérez"));

            // Encargado
            usuarios.add(new Encargado("encargado1", "encargado123", "María González"));
        } catch (Exception e) {
            System.err.println("Error al inicializar usuarios: " + e.getMessage());
        }
    }

    /**
     * Autentica un usuario
     * 
     * @param nombreUsuario Nombre de usuario
     * @param contrasena    Contraseña
     * @return true si las credenciales son correctas, false en caso contrario
     */
    public boolean autenticar(String nombreUsuario, String contrasena) {
        try {
            if (nombreUsuario == null || contrasena == null) {
                return false;
            }

            Usuario usuario = usuarios.stream()
                    .filter(u -> u.verificarCredenciales(nombreUsuario, contrasena))
                    .findFirst()
                    .orElse(null);

            if (usuario != null) {
                this.usuarioActual = usuario;
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error en autenticación: " + e.getMessage());
            return false;
        }
    }

    /**
     * Cierra la sesión del usuario actual
     */
    public void cerrarSesion() {
        try {
            this.usuarioActual = null;
        } catch (Exception e) {
            System.err.println("Error al cerrar sesión: " + e.getMessage());
        }
    }

    /**
     * Obtiene el usuario actualmente autenticado
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Verifica si el usuario actual tiene permiso para acceder a un módulo
     */
    public boolean verificarPermisos(String modulo) {
        try {
            if (usuarioActual == null) {
                return false;
            }
            return usuarioActual.verificarPermisos(modulo);
        } catch (Exception e) {
            System.err.println("Error al verificar permiso: " + e.getMessage());
            return false;
        }
    }

    /**
     * Agrega un nuevo usuario
     */
    public boolean agregarUsuario(Usuario usuario) {
        try {
            if (usuario == null) {
                return false;
            }
            // Verificar que no exista un usuario con el mismo nombre
            boolean existe = usuarios.stream()
                    .anyMatch(u -> u.getNombreUsuario().equals(usuario.getNombreUsuario()));
            if (existe) {
                return false;
            }
            usuarios.add(usuario);
            return true;
        } catch (Exception e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los usuarios
     */
    public List<Usuario> obtenerTodosUsuarios() {
        return new ArrayList<>(usuarios);
    }

    /**
     * Verifica si hay un usuario autenticado
     */
    public boolean hayUsuarioAutenticado() {
        return usuarioActual != null;
    }

    /**
     * Verifica si el usuario actual es administrador
     */
    public boolean esAdministrador() {
        try {
            return usuarioActual instanceof Administrador;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica si el usuario actual es vendedor
     */
    public boolean esVendedor() {
        try {
            return usuarioActual instanceof Vendedor;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica si el usuario actual es encargado
     */
    public boolean esEncargado() {
        try {
            return usuarioActual instanceof Encargado;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Establece un usuario por defecto sin autenticación
     * Útil para iniciar el sistema con un perfil específico
     */
    public void establecerUsuarioPorDefecto(Usuario usuario) {
        try {
            if (usuario != null) {
                this.usuarioActual = usuario;
            }
        } catch (Exception e) {
            System.err.println("Error al establecer usuario por defecto: " + e.getMessage());
        }
    }
}
