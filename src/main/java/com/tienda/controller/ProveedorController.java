package com.tienda.controller;

import com.tienda.model.Proveedor;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para gestionar proveedores
 */
public class ProveedorController {
    private List<Proveedor> proveedores;

    public ProveedorController() {
        this.proveedores = new ArrayList<>();
        inicializarProveedoresEjemplo();
    }

    /**
     * Inicializa con proveedores de ejemplo
     */
    private void inicializarProveedoresEjemplo() {
        try {
            proveedores.add(new Proveedor("Distribuidora ABC", "555-1234", "Juan Pérez"));
            proveedores.add(new Proveedor("Suministros XYZ", "555-5678", "María González"));
            proveedores.add(new Proveedor("Alimentos Premium", "555-9012", "Carlos Rodríguez"));
        } catch (Exception e) {
            System.err.println("Error al inicializar proveedores: " + e.getMessage());
        }
    }

    /**
     * Obtiene todos los proveedores
     */
    public List<Proveedor> obtenerTodosProveedores() {
        return new ArrayList<>(proveedores);
    }

    /**
     * Busca un proveedor por nombre de empresa
     */
    public Proveedor buscarPorNombreEmpresa(String nombreEmpresa) {
        try {
            return proveedores.stream()
                    .filter(p -> p.getNombreEmpresa().equals(nombreEmpresa))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            System.err.println("Error al buscar proveedor: " + e.getMessage());
            return null;
        }
    }

    /**
     * Agrega un nuevo proveedor
     */
    public boolean agregarProveedor(Proveedor proveedor) {
        try {
            if (proveedor == null) {
                return false;
            }
            // Verificar que no exista un proveedor con el mismo nombre
            if (buscarPorNombreEmpresa(proveedor.getNombreEmpresa()) != null) {
                return false;
            }
            proveedores.add(proveedor);
            return true;
        } catch (Exception e) {
            System.err.println("Error al agregar proveedor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza un proveedor existente
     */
    public boolean actualizarProveedor(Proveedor proveedor) {
        try {
            if (proveedor == null) {
                return false;
            }
            int index = proveedores.indexOf(proveedor);
            if (index >= 0) {
                proveedores.set(index, proveedor);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al actualizar proveedor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un proveedor
     */
    public boolean eliminarProveedor(String nombreEmpresa) {
        try {
            Proveedor proveedor = buscarPorNombreEmpresa(nombreEmpresa);
            if (proveedor != null) {
                proveedores.remove(proveedor);
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
            return false;
        }
    }
}
