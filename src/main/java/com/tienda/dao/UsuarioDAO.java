package com.tienda.dao;

import com.tienda.model.Usuario;
import java.util.List;

/**
 * Interfaz DAO para persistencia de Usuarios
 * Unidad V: Mapeo Objeto-Relacional básico
 */
public interface UsuarioDAO {
    /**
     * Guarda un usuario en la base de datos
     * @param usuario Usuario a guardar
     * @return true si se guardó correctamente
     */
    boolean guardar(Usuario usuario);

    /**
     * Busca un usuario por nombre de usuario
     * @param nombreUsuario Nombre de usuario
     * @return Usuario encontrado o null
     */
    Usuario buscarPorNombreUsuario(String nombreUsuario);

    /**
     * Obtiene todos los usuarios
     * @return Lista de todos los usuarios
     */
    List<Usuario> obtenerTodos();

    /**
     * Actualiza un usuario existente
     * @param usuario Usuario a actualizar
     * @return true si se actualizó correctamente
     */
    boolean actualizar(Usuario usuario);

    /**
     * Elimina un usuario
     * @param nombreUsuario Nombre de usuario a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminar(String nombreUsuario);
}
