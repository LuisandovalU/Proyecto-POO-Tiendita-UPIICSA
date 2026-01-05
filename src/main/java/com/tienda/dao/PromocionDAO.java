package com.tienda.dao;

import com.tienda.model.Promocion;
import java.util.List;

/**
 * Interfaz DAO para persistencia de Promociones
 * Unidad V: Mapeo Objeto-Relacional básico
 */
public interface PromocionDAO {
    /**
     * Guarda una promoción en la base de datos
     * @param promocion Promoción a guardar
     * @return true si se guardó correctamente
     */
    boolean guardar(Promocion promocion);

    /**
     * Obtiene todas las promociones
     * @return Lista de todas las promociones
     */
    List<Promocion> obtenerTodas();

    /**
     * Busca una promoción por nombre
     * @param nombrePromo Nombre de la promoción
     * @return Promoción encontrada o null
     */
    Promocion buscarPorNombre(String nombrePromo);

    /**
     * Actualiza una promoción existente
     * @param promocion Promoción a actualizar
     * @return true si se actualizó correctamente
     */
    boolean actualizar(Promocion promocion);

    /**
     * Elimina una promoción
     * @param nombrePromo Nombre de la promoción a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminar(String nombrePromo);
}
