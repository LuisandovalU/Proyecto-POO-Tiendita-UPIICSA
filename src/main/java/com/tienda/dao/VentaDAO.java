package com.tienda.dao;

import com.tienda.model.Venta;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaz DAO para persistencia de Ventas
 * Unidad V: Mapeo Objeto-Relacional básico
 */
public interface VentaDAO {
    /**
     * Guarda una venta en la base de datos
     * @param venta Venta a guardar
     * @return true si se guardó correctamente
     */
    boolean guardar(Venta venta);

    /**
     * Busca una venta por fecha
     * @param fecha Fecha de la venta
     * @return Lista de ventas de esa fecha
     */
    List<Venta> buscarPorFecha(LocalDate fecha);

    /**
     * Obtiene todas las ventas
     * @return Lista de todas las ventas
     */
    List<Venta> obtenerTodas();

    /**
     * Obtiene ventas por rango de fechas
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de ventas en el rango
     */
    List<Venta> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);

    /**
     * Obtiene el total de ventas por forma de pago
     * @param formaPago Forma de pago (Efectivo, Tarjeta, etc.)
     * @return Total de ventas con esa forma de pago
     */
    double obtenerTotalPorFormaPago(String formaPago);
}
