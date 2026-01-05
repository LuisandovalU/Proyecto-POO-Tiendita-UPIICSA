package com.tienda.controller;

import com.tienda.model.Producto;
import com.tienda.model.Promocion;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar promociones
 */
public class PromocionController {
    private List<Promocion> promociones;

    public PromocionController() {
        this.promociones = new ArrayList<>();
        inicializarPromocionesEjemplo();
    }

    /**
     * Inicializa con promociones de ejemplo
     */
    private void inicializarPromocionesEjemplo() {
        try {
            // Promoción para productos frescos próximos a caducar
            Promocion promoFrescos = new Promocion(
                "Frescos Próximos a Caducar",
                15.0,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(7)
            );
            promociones.add(promoFrescos);
        } catch (Exception e) {
            System.err.println("Error al inicializar promociones: " + e.getMessage());
        }
    }

    /**
     * Obtiene todas las promociones
     */
    public List<Promocion> obtenerTodasPromociones() {
        return new ArrayList<>(promociones);
    }

    /**
     * Obtiene solo las promociones activas
     */
    public List<Promocion> obtenerPromocionesActivas() {
        try {
            return promociones.stream()
                    .filter(Promocion::estaActiva)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error al obtener promociones activas: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Busca una promoción que aplique a un producto específico
     * @param producto Producto a verificar
     * @return Promoción activa que aplica al producto, o null si no hay ninguna
     */
    public Promocion buscarPromocionParaProducto(Producto producto) {
        try {
            if (producto == null) {
                return null;
            }
            return promociones.stream()
                    .filter(p -> p.estaActiva() && p.aplicaAProducto(producto))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            System.err.println("Error al buscar promoción para producto: " + e.getMessage());
            return null;
        }
    }

    /**
     * Agrega una nueva promoción
     */
    public boolean agregarPromocion(Promocion promocion) {
        try {
            if (promocion == null) {
                return false;
            }
            promociones.add(promocion);
            return true;
        } catch (Exception e) {
            System.err.println("Error al agregar promoción: " + e.getMessage());
            return false;
        }
    }

    /**
     * Crea una promoción para productos perecederos próximos a caducar
     * Según requerimientos: para gestionar productos perecederos próximos a caducar
     */
    public Promocion crearPromocionPerecederos(String nombre, double descuento, 
                                             LocalDate fechaInicio, LocalDate fechaFin,
                                             List<Producto> productos) {
        try {
            Promocion promocion = new Promocion(nombre, descuento, fechaInicio, fechaFin);
            if (productos != null) {
                promocion.setListaProductos(productos);
            }
            return promocion;
        } catch (Exception e) {
            System.err.println("Error al crear promoción de perecederos: " + e.getMessage());
            return null;
        }
    }

    /**
     * Elimina una promoción
     */
    public boolean eliminarPromocion(Promocion promocion) {
        try {
            if (promocion == null) {
                return false;
            }
            return promociones.remove(promocion);
        } catch (Exception e) {
            System.err.println("Error al eliminar promoción: " + e.getMessage());
            return false;
        }
    }
}
