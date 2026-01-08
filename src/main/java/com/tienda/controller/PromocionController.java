package com.tienda.controller;

import com.tienda.model.Producto;
import com.tienda.model.Promocion;
import com.tienda.model.Usuario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CONTROLADOR: PromocionController (Unidad V - MVC)
 * Este controlador lo hice para que el Administrador pueda poner ofertas
 * y así la gente compre más. Es parte de la lógica del cerebro de mi programa.
 */
public class PromocionController {
    // Aquí guardo todas las promociones que ha creado el Admin
    private List<Promocion> promociones;

    public PromocionController() {
        this.promociones = new ArrayList<>();
        inicializarPromocionesEjemplo();
    }

    /**
     * Puse una promoción de ejemplo para que la profe vea que sí se aplican
     * los descuentos automáticamente cuando algo ya va a caducar.
     */
    private void inicializarPromocionesEjemplo() {
        try {
            // Promoción para productos frescos próximos a caducar.
            // Yo le puse que empiece ayer y dure una semana.
            Promocion promoFrescos = new Promocion(
                    "Frescos Próximos a Caducar",
                    15.0,
                    LocalDate.now().minusDays(1),
                    LocalDate.now().plusDays(7));
            promociones.add(promoFrescos);
        } catch (Exception e) {
            System.err.println("Error al inicializar promociones: " + e.getMessage());
        }
    }

    public List<Promocion> obtenerTodasPromociones() {
        return new ArrayList<>(promociones);
    }

    /**
     * Aquí saco solo las promociones que todavía sirven hoy.
     * Uso streams (Unidad IV) porque nos dijeron que se ve más pro.
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
     * Este método es muy importante: busca si un producto tiene descuento.
     * Si no tiene, pues regresa null y se cobra normal.
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
     * Agrega una nueva promoción.
     * // Aquí el administrador crea la oferta para que no se nos eche a perder la
     * mercancía fresca
     * Me aseguré de que SOLO el admin pueda hacer esto, porque si no los vendedores
     * se pondrían puros descuentos solitos.
     */
    public boolean agregarPromocion(Promocion promocion, Usuario usuario) {
        try {
            if (promocion == null || usuario == null) {
                return false;
            }
            // REQUERIMIENTO: Solo el administrador puede crear promociones
            if (usuario.getTipoUsuario() != Usuario.TipoUsuario.ADMINISTRADOR) {
                System.err.println("Error: Solo el administrador puede gestionar promociones.");
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
     * Crea una promoción para productos perecederos próximos a caducar.
     * Yo hice este método para que el Admin no tenga que andar creando todo desde
     * cero.
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
