package com.tienda.dao;

import com.tienda.model.Proveedor;
import java.util.List;

/**
 * Interfaz DAO para persistencia de Proveedores
 * Unidad V: Mapeo Objeto-Relacional básico
 */
public interface ProveedorDAO {
    /**
     * Guarda un proveedor en la base de datos
     * @param proveedor Proveedor a guardar
     * @return true si se guardó correctamente
     */
    boolean guardar(Proveedor proveedor);

    /**
     * Busca un proveedor por nombre de empresa
     * @param nombreEmpresa Nombre de la empresa
     * @return Proveedor encontrado o null
     */
    Proveedor buscarPorNombreEmpresa(String nombreEmpresa);

    /**
     * Obtiene todos los proveedores
     * @return Lista de todos los proveedores
     */
    List<Proveedor> obtenerTodos();

    /**
     * Actualiza un proveedor existente
     * @param proveedor Proveedor a actualizar
     * @return true si se actualizó correctamente
     */
    boolean actualizar(Proveedor proveedor);

    /**
     * Elimina un proveedor
     * @param nombreEmpresa Nombre de la empresa a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminar(String nombreEmpresa);
}
