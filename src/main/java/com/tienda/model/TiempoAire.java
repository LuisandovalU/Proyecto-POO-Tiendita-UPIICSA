package com.tienda.model;

import java.util.Arrays;
import java.util.List;

/**
 * ESPECIALIZACIÓN: TiempoAire (Unidad III - Herencia)
 * Esta clase hereda de Producto. TiempoAire ES UN producto pero digital,
 * no es físico como los demás.
 */
public class TiempoAire extends Producto {
    // ATRIBUTOS ESPECÍFICOS (Unidad II):
    // Solo tienen sentido para recargas electrónicas.
    private String compania;
    private String numeroCelular;

    // REGLAS DE NEGOCIO de mi sistema
    public static final List<Integer> MONTOS_PERMITIDOS = Arrays.asList(10, 20, 50, 100, 200, 500);
    public static final List<String> COMPANIAS_PERMITIDAS = Arrays.asList("Telcel", "Movistar", "AT&T", "Unefon");

    public TiempoAire() {
        super();
        // Como los campos del papá son private, uso los setters para inicializar
        setCodigoBarras("TA-REC");
        setNombre("Recarga Electrónica");
        setMarca("Varios");
        setTamanoGramaje("N/A");
        setPrecioCompra(0);
        setPrecioVenta(0);
        setStockActual(999); // Valor simbólico porque es servicio digital
        setStockMinimo(0);
    }

    public TiempoAire(String codigoBarras, String nombre, String marca, String tamanoGramaje,
            double precioCompra, double precioVenta, int stockActual, int stockMinimo,
            String compania, String numeroCelular) {
        // Uso el constructor del papá
        super(codigoBarras, nombre, marca, tamanoGramaje, precioCompra, precioVenta, stockActual, stockMinimo);
        this.compania = compania;
        this.numeroCelular = numeroCelular;
    }

    @Override
    public void mostrarDetalles() {
        // Polimorfismo: muestro lo que es importante para la recarga
        System.out.println("SERVICIO: " + getNombre());
        System.out.println("Compañía: " + compania);
        System.out.println("Número: " + numeroCelular);
    }

    @Override
    public String getDetallesEspecificos() {
        return "Compañía: " + compania + ", Cel: " + numeroCelular;
    }

    // Getters y Setters (Encapsulamiento)
    public String getCompania() {
        return compania;
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

    /**
     * Valida que el monto sea uno de los permitidos por la tienda.
     */
    public void validarMonto(double monto) throws IllegalArgumentException {
        if (!MONTOS_PERMITIDOS.contains((int) monto)) {
            throw new IllegalArgumentException("Monto no válido para Tiempo Aire: $" + monto +
                    ". Montos permitidos: " + MONTOS_PERMITIDOS);
        }
    }

    // --- ABSTRACCIÓN (Unidad I) ---
    // Como es un servicio digital, para mi sistema el stock es infinito,
    // por eso sobreescribo estos métodos para que no bajen las recargas.

    @Override
    public int getStockActual() {
        return 999;
    }

    @Override
    public void setStockActual(int stockActual) {
        // No hago nada porque el stock digital no se acaba
    }

    @Override
    public boolean verificarStock() {
        return true;
    }
}
