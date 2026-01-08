package com.tienda.dao;

import com.tienda.model.Venta;
import java.time.LocalDate;
import java.util.List;

/**
 * INTERFAZ DAO: VentaDAO (Unidad V - Persistencia)
 * Aquí es donde defino qué debe poder hacer mi sistema con las ventas
 * que guardamos. Es como un contrato que dice: "si quieres guardar ventas,
 * tienes que poder hacer todo esto". Aplicación de Abstracción pura.
 */
public interface VentaDAO {
    /**
     * Guarda una venta para que no se nos olvide cuánto dinero entró.
     */
    boolean guardar(Venta venta);

    /**
     * Busca qué vendimos en un día específico.
     */
    List<Venta> buscarPorFecha(LocalDate fecha);

    /**
     * Trae todas las ventas que se han hecho en la historia de la tienda.
     */
    List<Venta> obtenerTodas();

    /**
     * Saca las ventas de un periodo de tiempo (por ejemplo, de lunes a viernes).
     */
    List<Venta> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin);

    /**
     * Calcula cuánto dinero entró por Efectivo o por Tarjeta.
     * Muy útil para el corte de caja.
     */
    double obtenerTotalPorFormaPago(String formaPago);
}
