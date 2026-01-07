package com.tienda.dao;

import com.tienda.model.Producto;
import java.util.List;

/**
 * Interfaz DAO para persistencia de Productos
 * Unidad V: Mapeo Objeto-Relacional básico
 */
public interface ProductoDAO {
    /**
     * Guarda un producto en la base de datos
     * 
     * @param producto Producto a guardar
     * @return true si se guardó correctamente
     */
    boolean guardar(Producto producto);

    /**
     * Busca un producto por código de barras
     * 
     * @param codigoBarras Código de barras del producto
     * @return Producto encontrado o null
     */
    Producto buscarProducto(String codigoBarras);

    /**
     * Obtiene todos los productos
     * 
     * @return Lista de todos los productos
     */
    List<Producto> obtenerTodos();

    /**
     * Actualiza un producto existente
     * 
     * @param producto Producto a actualizar
     * @return true si se actualizó correctamente
     */
    boolean actualizar(Producto producto);

    /**
     * Elimina un producto
     * 
     * @param codigoBarras Código de barras del producto a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminar(String codigoBarras);
}
