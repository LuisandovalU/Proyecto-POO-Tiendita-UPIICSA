package com.tienda.dao;

import com.tienda.model.Producto;
import java.util.List;

/**
 * INTERFAZ DAO: ProductoDAO (Unidad V - Persistencia)
 * Esta es una interfaz para que sepamos qué métodos debe tener cualquier
 * clase que quiera guardar productos, ya sea en una lista o en una base de
 * datos.
 * Como nos dijo la profe, esto es pura Abstracción.
 */
public interface ProductoDAO {
    /**
     * Guarda un producto para que no se pierda.
     */
    boolean guardar(Producto producto);

    /**
     * Busca un producto por su código de barras.
     */
    Producto buscarProducto(String codigoBarras);

    /**
     * Saca la lista de todos los productos que tenemos guardados.
     */
    List<Producto> obtenerTodos();

    /**
     * Actualiza los datos de un producto que ya existía.
     */
    boolean actualizar(Producto producto);

    /**
     * Borra el producto del sistema.
     */
    boolean eliminar(String codigoBarras);
}
