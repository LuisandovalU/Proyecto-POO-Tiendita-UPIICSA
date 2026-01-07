package com.tienda.controller;

import com.tienda.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CONTROLADOR: ProductoController (Unidad V - MVC)
 * Aquí es donde controlo todo lo que pasa con los productos de mi tienda.
 * Es como el cerebro que sabe qué productos tenemos y cuáles no.
 */
public class ProductoController {
    private List<Producto> productos;

    public ProductoController() {
        this.productos = new ArrayList<>();
        inicializarDatosEjemplo();
    }

    /**
     * Lleno la lista con unos productos para que la profe vea que sí funciona.
     */
    private void inicializarDatosEjemplo() {
        try {
            // Meto unos frescos
            productos.add(new Frescos("001", "Leche Entera", "Lala", "1L", 15.0, 20.0, 50, 10,
                    LocalDate.now().plusDays(7), true, "Litro"));
            productos.add(new Frescos("002", "Yogurt Natural", "Danone", "1Kg", 18.0, 25.0, 30, 5,
                    LocalDate.now().plusDays(5), true, "Kilogramo"));

            // Unos de abarrotes
            productos.add(new Abarrotes("003", "Arroz", "Morelos", "1Kg", 25.0, 35.0, 100, 20,
                    LocalDate.now().plusMonths(6), "Bolsa"));
            productos.add(new Abarrotes("004", "Frijoles", "La Costeña", "500g", 20.0, 28.0, 80, 15,
                    LocalDate.now().plusMonths(12), "Lata"));

            // Y de dulcería porque siempre se antojan
            productos.add(new Dulceria("005", "Chocolate", "Hershey's", "100g", 12.0, 18.0, 60, 10,
                    "Chocolate"));
        } catch (Exception e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
        }
    }

    public List<Producto> obtenerTodosProductos() {
        return new ArrayList<>(productos);
    }

    public List<Producto> obtenerProductosPorCategoria(String categoria) {
        try {
            return productos.stream()
                    .filter(p -> {
                        if (categoria.equals("Frescos"))
                            return p instanceof Frescos;
                        if (categoria.equals("Abarrotes"))
                            return p instanceof Abarrotes;
                        if (categoria.equals("Dulcería"))
                            return p instanceof Dulceria;
                        if (categoria.equals("Limpieza"))
                            return p instanceof Limpieza;
                        if (categoria.equals("Otros"))
                            return p instanceof Otros;
                        if (categoria.equals("TiempoAire"))
                            return p instanceof TiempoAire;
                        return false;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * SOBRECARGA DE MÉTODOS (Unidad II):
     * Aquí busco por el código de barras que es lo más común.
     */
    public Producto buscarProducto(String codigoBarras) {
        try {
            for (Producto p : productos) {
                if (p.getCodigoBarras().equals(codigoBarras)) {
                    return p;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * SOBRECARGA DE MÉTODOS (Unidad II):
     * También puedo buscar por el número de lista o ID (índice).
     */
    public Producto buscarProducto(int id) {
        try {
            if (id >= 0 && id < productos.size()) {
                return productos.get(id);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * SOBRECARGA DE MÉTODOS (Unidad II):
     * O si no me sé el código, lo busco por su nombre.
     */
    public Producto buscarProductoPorNombre(String nombre) {
        try {
            for (Producto p : productos) {
                if (p.getNombre().equalsIgnoreCase(nombre)) {
                    return p;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean agregarProducto(Producto producto) {
        try {
            if (producto == null)
                return false;
            if (buscarProducto(producto.getCodigoBarras()) != null)
                return false;
            productos.add(producto);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean actualizarProducto(Producto producto) {
        try {
            if (producto == null)
                return false;
            int index = productos.indexOf(producto);
            if (index >= 0) {
                productos.set(index, producto);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminarProducto(String codigoBarras) {
        try {
            Producto producto = buscarProducto(codigoBarras);
            if (producto != null) {
                productos.remove(producto);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
