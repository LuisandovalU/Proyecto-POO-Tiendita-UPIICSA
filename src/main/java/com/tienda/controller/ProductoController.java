package com.tienda.controller;

import com.tienda.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CONTROLADOR: ProductoController (Unidad V - MVC)
 * Aquí es donde controlo todo lo que pasa con los productos de mi tienda.
 * Es como el cerebro que sabe qué productos tenemos y cuáles no. Yo hice
 * esta clase para separar la lógica de lo que el usuario ve en la pantalla,
 * como nos explicó la profe con el MVC.
 */
public class ProductoController {
    // Mi lista de productos (Persistencia volátil por ahora, no me maten si se
    // borra)
    private List<Producto> productos;
    private List<ProductoChangeListener> listeners;

    /**
     * Interfaz para que las vistas se enteren cuando algo cambia en los productos.
     * Como el chisme en la escuela, si alguien sabe algo, todos se enteran.
     */
    public interface ProductoChangeListener {
        void onProductoChanged();
    }

    public ProductoController() {
        this.productos = new ArrayList<>();
        this.listeners = new ArrayList<>();
        cargarDatosIniciales();
    }

    public void addChangeListener(ProductoChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    private void notifyListeners() {
        for (ProductoChangeListener listener : listeners) {
            listener.onProductoChanged();
        }
    }

    /**
     * Aquí genero el código manual que pide el punto 1 del requerimiento para uso
     * interno de la tienda.
     * Si no tienen código de barras, les invento uno prolijo.
     */
    private String generarCodigoInterno() {
        int max = 0;
        for (Producto p : productos) {
            if (p.getCodigoBarras() != null && p.getCodigoBarras().startsWith("INT-")) {
                try {
                    int num = Integer.parseInt(p.getCodigoBarras().substring(4));
                    if (num > max) {
                        max = num;
                    }
                } catch (NumberFormatException e) {
                    // Ignoramos si el formato no es exacto
                }
            }
        }
        return String.format("INT-%04d", max + 1);
    }

    /**
     * Lleno la lista con 30 productos para que la profe vea que sí funciona y no
     * salga la tabla vacía. Me aseguré de meter de todas las categorías.
     */
    private void cargarDatosIniciales() {
        // Separé bien las clases para que se vea la herencia. No es lo mismo un jabón
        // de limpieza que un dulce, aunque ambos se vendan por pieza
        try {
            // 6 DULCERÍA (Compra por caja, venta por pieza - DUL-XXX)
            productos.add(new Dulceria("DUL-001", "Chicles Trident", "Trident", "12pz", 100.0, 1.5, 500, 100,
                    "Goma de Mascar"));
            productos.add(
                    new Dulceria("DUL-002", "Chocolate Carlos V", "Nestlé", "1pz", 80.0, 10.0, 200, 50, "Chocolate"));
            productos.add(
                    new Dulceria("DUL-003", "Gomitas Panditas", "Ricolino", "65g", 120.0, 15.0, 150, 40, "Gomitas"));
            productos
                    .add(new Dulceria("DUL-004", "Paletas Payaso", "Ricolino", "1pz", 150.0, 25.0, 100, 20, "Paletas"));
            productos.add(new Dulceria("DUL-005", "Bubbaloo Fresa", "Adams", "1pz", 45.0, 1.0, 600, 100, "Chicle"));
            productos.add(
                    new Dulceria("DUL-006", "Mazapán Gigante", "De la Rosa", "1pz", 90.0, 8.0, 300, 60, "Mazapán"));

            // 6 LIMPIEZA (Compra por caja, venta por unidad - LIM-XXX)
            productos.add(new Limpieza("LIM-001", "Jabón Axion", "Colgate", "400g", 240.0, 28.0, 100, 25,
                    "Lavar trastes directamente sobre esponja"));
            productos.add(new Limpieza("LIM-002", "Suavizante Downy", "P&G", "800ml", 360.0, 45.0, 60, 15,
                    "Mezclar con agua en el último ciclo"));
            productos.add(new Limpieza("LIM-003", "Jerga Gris", "Generica", "1pz", 100.0, 22.0, 80, 20,
                    "Lavar con cloro después de usar"));
            productos.add(new Limpieza("LIM-004", "Estropajo Fibra", "Scotch-Brite", "1pz", 180.0, 18.0, 120, 30,
                    "No usar en superficies de teflón"));
            productos.add(new Limpieza("LIM-005", "Cloro Líquido", "Los Arcos", "1L", 150.0, 18.0, 150, 40,
                    "No mezclar con amoníaco"));
            productos.add(new Limpieza("LIM-006", "Desengrasante", "Easy-Off", "500ml", 420.0, 65.0, 30, 8,
                    "Rociar y dejar actuar 5 minutos"));

            // 2 OTROS (OTR-XXX)
            productos.add(new Otros("OTR-001", "Velas Decorativas", "Velas AR", "3pz", 40.0, 15.0, 40, 10,
                    "Velas de cera para decoración"));
            productos.add(new Otros("OTR-002", "Encendedor Bic", "Bic", "1pz", 12.0, 18.0, 100, 25,
                    "Encendedor de gas desechable"));

            // 6 ABARROTES (Compra por paquete, venta por pieza - ABR-XXX)
            productos.add(new Abarrotes("ABR-001", "Atún en Agua", "Dolores", "140g", 180.0, 22.0, 200, 40,
                    LocalDate.now().plusMonths(24), "Lata"));
            productos.add(new Abarrotes("ABR-002", "Sopa de Fideo", "La Moderna", "200g", 85.0, 9.5, 300, 60,
                    LocalDate.now().plusMonths(12), "Bolsa"));
            productos.add(new Abarrotes("ABR-003", "Aceite Vegetal", "1-2-3", "1L", 420.0, 48.0, 100, 20,
                    LocalDate.now().plusMonths(12), "Botella"));
            productos.add(new Abarrotes("ABR-004", "Frijol Negro", "Isadora", "430g", 160.0, 20.0, 150, 30,
                    LocalDate.now().plusMonths(10), "Sobre"));
            productos.add(new Abarrotes("ABR-005", "Arroz Blanco", "Schettino", "900g", 240.0, 32.0, 120, 25,
                    LocalDate.now().plusMonths(8), "Bolsa"));
            productos.add(new Abarrotes("ABR-006", "Mayonesa", "McCormick", "390g", 480.0, 55.0, 80, 15,
                    LocalDate.now().plusMonths(18), "Frasco"));

            // 10 FRESCOS (5 por peso, 5 por pieza - FRE-XXX)
            // Por peso
            String[] nombresPeso = { "Jamón de Pierna", "Queso Oaxaca", "Salchicha Pavo", "Pechuga Pavo",
                    "Queso Panela" };
            for (int i = 0; i < 5; i++) {
                Frescos f = new Frescos("FRE-000" + (i + 1), nombresPeso[i], "Varios", "Kg", 100.0 + (i * 20),
                        160.0 + (i * 30), 5000, 1000, LocalDate.now().plusDays(10), true, "Gramo");
                f.setSeVendePorGramos(true);
                productos.add(f);
            }
            // Por pieza
            productos.add(new Frescos("FRE-0006", "Leche Entera", "Lala", "1L", 22.0, 28.0, 60, 12,
                    LocalDate.now().plusDays(15), true, "Litro"));
            productos.add(new Frescos("FRE-0007", "Crema Ácida", "Alpura", "450ml", 25.0, 34.0, 40, 10,
                    LocalDate.now().plusDays(10), true, "Pieza"));
            productos.add(new Frescos("FRE-0008", "Yogurt Natural", "Danone", "900g", 30.0, 42.0, 30, 5,
                    LocalDate.now().plusDays(12), true, "Pieza"));
            productos.add(new Frescos("FRE-0009", "Mantequilla", "Lala", "90g", 15.0, 22.0, 50, 10,
                    LocalDate.now().plusDays(30), true, "Pieza"));
            productos.add(new Frescos("FRE-0010", "Jugo Fresco", "Jumex", "1L", 18.0, 25.0, 45, 10,
                    LocalDate.now().plusDays(5), true, "Pieza"));

        } catch (Exception e) {
            System.err.println("Error al cargar los datos iniciales: " + e.getMessage());
        }
    }

    public List<Producto> obtenerTodosProductos() {
        return new ArrayList<>(productos);
    }

    /**
     * Aquí filtro los productos por su categoría. Yo usé streams porque se ve
     * más experto, pero básicamente es ir revisando uno por uno su tipo
     * (instanceof).
     */
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
     * Aquí busco por el código de barras que es lo más común. Yo lo puse
     * así para que sea rápido encontrar las cosas.
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
     * Como mis compañeros a veces no se saben el código, esta es la otra opción.
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
     * O si no me sé el código ni el ID, lo busco por su nombre.
     * Batallé con los mayúsculas pero ya con el ignoreCase quedó.
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

            // Si el Admin no puso código, le creamos uno nosotros
            if (producto.getCodigoBarras() == null || producto.getCodigoBarras().trim().isEmpty()) {
                producto.setCodigoBarras(generarCodigoInterno());
            }

            // No podemos tener dos productos con el mismo código, eso sería un relajo.
            if (buscarProducto(producto.getCodigoBarras()) != null)
                return false;
            productos.add(producto);
            notifyListeners();
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
                notifyListeners();
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
                notifyListeners();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
