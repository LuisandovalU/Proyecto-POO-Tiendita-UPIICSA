package com.tienda.controller;

import com.tienda.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para gestionar productos
 */
public class ProductoController {
    private List<Producto> productos;

    public ProductoController() {
        this.productos = new ArrayList<>();
        inicializarDatosEjemplo();
    }

    /**
     * Inicializa con datos de ejemplo para demostración
     */
    private void inicializarDatosEjemplo() {
        try {
            // Productos Frescos
            productos.add(new Frescos("001", "Leche Entera", "Lala", "1L", 15.0, 20.0, 50, 10,
                    LocalDate.now().plusDays(7), true, "Litro"));
            productos.add(new Frescos("002", "Yogurt Natural", "Danone", "1Kg", 18.0, 25.0, 30, 5,
                    LocalDate.now().plusDays(5), true, "Kilogramo"));

            // Productos Abarrotes
            productos.add(new Abarrotes("003", "Arroz", "Morelos", "1Kg", 25.0, 35.0, 100, 20,
                    LocalDate.now().plusMonths(6), "Bolsa"));
            productos.add(new Abarrotes("004", "Frijoles", "La Costeña", "500g", 20.0, 28.0, 80, 15,
                    LocalDate.now().plusMonths(12), "Lata"));

            // Productos Dulcería
            productos.add(new Dulceria("005", "Chocolate", "Hershey's", "100g", 12.0, 18.0, 60, 10,
                    "Chocolate"));
            productos.add(new Dulceria("006", "Gomitas", "Trident", "200g", 15.0, 22.0, 45, 8,
                    "Gomitas"));
        } catch (Exception e) {
            System.err.println("Error al inicializar datos de ejemplo: " + e.getMessage());
        }
    }

    /**
     * Obtiene todos los productos
     */
    public List<Producto> obtenerTodosProductos() {
        return new ArrayList<>(productos);
    }

    /**
     * Obtiene productos por categoría
     */
    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        try {
            return productos.stream()
                    .filter(p -> {
                        if (categoria.equals("Frescos")) return p instanceof Frescos;
                        if (categoria.equals("Abarrotes")) return p instanceof Abarrotes;
                        if (categoria.equals("Dulcería")) return p instanceof Dulceria;
                        if (categoria.equals("Limpieza")) return p instanceof Limpieza;
                        if (categoria.equals("Otros")) return p instanceof Otros;
                        if (categoria.equals("TiempoAire")) return p instanceof TiempoAire;
                        return false;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error al filtrar productos por categoría: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Busca un producto por código de barras
     */
    public Producto buscarPorCodigoBarras(String codigoBarras) {
        try {
            return productos.stream()
                    .filter(p -> p.getCodigoBarras().equals(codigoBarras))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            System.err.println("Error al buscar producto: " + e.getMessage());
            return null;
        }
    }

    /**
     * Agrega un nuevo producto
     */
    public boolean agregarProducto(Producto producto) {
        try {
            if (producto == null) {
                return false;
            }
            // Verificar que no exista un producto con el mismo código
            if (buscarPorCodigoBarras(producto.getCodigoBarras()) != null) {
                return false;
            }
            productos.add(producto);
            return true;
        } catch (Exception e) {
            System.err.println("Error al agregar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza un producto existente
     */
    public boolean actualizarProducto(Producto producto) {
        try {
            if (producto == null) {
                return false;
            }
            int index = productos.indexOf(producto);
            if (index >= 0) {
                productos.set(index, producto);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un producto
     */
    public boolean eliminarProducto(String codigoBarras) {
        try {
            Producto producto = buscarPorCodigoBarras(codigoBarras);
            if (producto != null) {
                productos.remove(producto);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
}
