package com.tienda.model;

import java.util.Arrays;
import java.util.List;

/**
 * ESPECIALIZACIÓN: TiempoAire (Unidad III - Herencia)
 * Esta clase 'extiende' de Producto. Significa que TiempoAire ES UN Producto,
 * pero con características extras como compañía y número celular.
 */
public class TiempoAire extends Producto {
    // ATRIBUTOS ESPECÍFICOS (Unidad II):
    // Solo tienen sentido para recargas electrónicas, no para abarrotes.
    // --- CONCEPTO DE OBJETO (Unidad I) ---
    // El Tiempo Aire se maneja como un servicio independiente del inventario físico
    // para simplificar la operación del vendedor, abstrayendo la complejidad de
    // la recarga electrónica como un objeto de servicio puro (Unidad I).
    private String compania; // Refleja el operador telefónico (ej. Telcel)
    private String numeroCelular; // El número de 10 dígitos hacia donde va el saldo

    // REGLAS DE NEGOCIO (Constantes - Unidad I):
    // Definimos montos fijos según el requerimiento (10, 20, 50, 100, 200, 500)
    public static final List<Integer> MONTOS_PERMITIDOS = Arrays.asList(10, 20, 50, 100, 200, 500);
    public static final List<String> COMPANIAS_PERMITIDAS = Arrays.asList("Telcel", "Movistar", "AT&T", "Unefon");

    public TiempoAire() {
        super();
        this.codigoBarras = "TA-REC";
        this.nombre = "Recarga Electrónica";
        this.marca = "Varios";
        this.tamanoGramaje = "N/A";
        this.precioCompra = 0;
        this.precioVenta = 0;
        this.stockActual = 999; // Valor inicial simbólico
        this.stockMinimo = 0;
    }

    public TiempoAire(String codigoBarras, String nombre, String marca, String tamanoGramaje,
            double precioCompra, double precioVenta, int stockActual, int stockMinimo,
            String compania, String numeroCelular) {
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.compania = compania;
        this.numeroCelular = numeroCelular;
    }

    // Getters y Setters
    public String getCompania() {
        return compania;
    }

    /**
     * Valida que el monto sea uno de los permitidos: 10, 20, 50, 100, 200, 500,
     * 1000.
     * 
     * @param monto El monto a validar
     * @throws IllegalArgumentException si el monto no está en la lista de
     *                                  permitidos
     */
    public void validarMonto(double monto) throws IllegalArgumentException {
        if (!MONTOS_PERMITIDOS.contains((int) monto)) {
            throw new IllegalArgumentException("Monto no válido para Tiempo Aire: $" + monto +
                    ". Montos permitidos: " + MONTOS_PERMITIDOS);
        }
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    // --- APLICACIÓN DE ABSTRACCIÓN (Unidad I) ---
    // Aquí no validamos stock físico porque el Tiempo Aire es un servicio digital
    // "infinito". Redefinimos los métodos para ignorar el control de inventario.

    @Override
    public int getStockActual() {
        // Siempre devolvemos un valor alto porque es un servicio que no se agota
        return 999;
    }

    @Override
    public void setStockActual(int stockActual) {
        // Ignoramos la resta de stock en finalizarVenta(), aplicando Abstracción
        // El stock de un servicio no disminuye físicamente.
    }

    @Override
    public boolean verificarStock() {
        // Siempre hay disponibilidad por ser servicio digital
        return true;
    }
}
