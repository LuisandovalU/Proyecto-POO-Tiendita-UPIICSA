package com.tienda;

import com.tienda.controller.PedidoProveedorController;
import com.tienda.controller.ProductoController;
import com.tienda.model.Abarrotes;
import com.tienda.model.PedidoProveedor;
import com.tienda.model.Producto;
import com.tienda.model.Proveedor;
import com.tienda.model.TiempoAire;

public class TestRestructuring {
    public static void main(String[] args) {
        System.out.println("Starting Tiendita Verification...");

        testAirtimeConstraints();
        testSupplierStockFlow();
        testAutomaticDiscounts();

        System.out.println("\nVerification Complete!");
    }

    private static void testAutomaticDiscounts() {
        System.out.println("\n--- Testing Automatic Discounts (Polimorfismo Unit IV) ---");
        com.tienda.model.Venta venta = new com.tienda.model.Venta();
        com.tienda.model.Frescos leche = new com.tienda.model.Frescos(
                "LECHE-01", "Leche Entera", "Lala", "1L", 20.0, 25.0, 10, 5,
                java.time.LocalDate.now().plusDays(1), true, "Litro");

        venta.agregarProducto(leche);
        double totalAntes = venta.getTotal();
        venta.finalizarVenta(); // Aquí se aplica la lógica de Polimorfismo
        double totalDespues = venta.getTotal();

        System.out.println("Precio Leche (casi caduca): $" + totalAntes);
        System.out.println("Total tras finalizar (20% descuento): $" + totalDespues);

        if (totalDespues < totalAntes) {
            System.out.println("[OK] Descuento por caducidad aplicado correctamente via Polimorfismo.");
        } else {
            System.err.println("[FAIL] No se aplicó el descuento automático.");
        }
    }

    private static void testAirtimeConstraints() {
        System.out.println("\n--- Testing Airtime Constraints & Validation ---");
        TiempoAire ta = new TiempoAire();
        System.out.println("Allowed Amounts: " + TiempoAire.MONTOS_PERMITIDOS);

        try {
            System.out.println("Testing valid amount 100...");
            ta.validarMonto(100);
            System.out.println("[OK] Valid amount accepted.");
        } catch (IllegalArgumentException e) {
            System.err.println("[FAIL] Valid amount rejected: " + e.getMessage());
        }

        try {
            System.out.println("Testing invalid amount 33...");
            ta.validarMonto(33);
            System.err.println("[FAIL] Invalid amount 33 was accepted!");
        } catch (IllegalArgumentException e) {
            System.out.println("[OK] Invalid amount correctly rejected with exception: " + e.getMessage());
        }
    }

    private static void testSupplierStockFlow() {
        System.out.println("\n--- Testing Supplier & Relationship Alignment ---");
        ProductoController pc = new ProductoController();
        PedidoProveedorController ppc = new PedidoProveedorController(pc);

        Proveedor prov = new Proveedor("UML Supplier", "555-0101", "QA Auditor");
        Producto p = new Abarrotes("999", "Test Product", "Brand", "1kg", 10.0, 15.0, 5, 2, null, "Box");
        pc.agregarProducto(p);

        // Test Relationship: Proveedor -solicita-> Producto
        prov.getProductosSolicitados().add(p);
        System.out.println("Proveedor solicita: " + prov.getProductosSolicitados().size() + " productos.");

        // Test Relationship: PedidoProveedor -se_pide_a-> Proveedor
        ppc.iniciarPedido();
        PedidoProveedor pedido = ppc.getPedidoActual();
        pedido.setProveedor(prov);
        ppc.agregarProductoAPedido(p, 10);
        ppc.guardarPedido();

        System.out.println("Pedido para proveedor: " + pedido.getProveedor().getNombreEmpresa());

        ppc.marcarComoEntregado(pedido);

        int finalStock = pc.buscarPorCodigoBarras("999").getStockActual();
        System.out.println("Stock after Delivery (5 + 10): " + finalStock);

        if (finalStock == 15 && pedido.getProveedor().equals(prov)) {
            System.out.println("[OK] UML Relationships and Stock flow are perfect.");
        } else {
            System.err.println("[FAIL] Relationship or Stock update issue.");
        }
    }
}
