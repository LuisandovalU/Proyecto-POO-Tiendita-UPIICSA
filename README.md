# Sistema de Punto de Venta - Tienda de la Esquina

Sistema completo de Punto de Venta desarrollado en Java con Swing y FlatLaf, siguiendo el patrón de diseño MVC (Modelo-Vista-Controlador) y cumpliendo con los requerimientos del diagrama de clases UML y especificaciones de negocio.

## Características Principales

### Arquitectura y Diseño
- ✅ **Patrón MVC**: Separación clara en paquetes `model`, `view` y `controller`
- ✅ **Herencia y Polimorfismo**: Jerarquía completa de productos con clases abstractas
- ✅ **Interfaz Moderna**: Dark Mode con FlatLaf
- ✅ **Validaciones Robustas**: Try-catch en todos los puntos de entrada
- ✅ **Preparado para Persistencia**: Interfaces DAO para mapeo objeto-relacional

### Funcionalidades de Negocio

#### 1. Gestión de Productos
- Jerarquía completa: Frescos, Abarrotes, Dulcería, Limpieza, Otros, TiempoAire
- Validación de stock con verificación de stock mínimo
- Código de barras para cada producto

#### 2. Sistema de Promociones
- Promociones con fechas de inicio y fin
- Aplicación automática de descuentos según fechas
- Gestión de productos perecederos próximos a caducar

#### 3. Sistema de Usuarios y Permisos
- **Administrador**: Acceso completo, reportes, pedidos
- **Vendedor**: Ventas, cobros, devoluciones
- **Encargado**: Ventas + verificar entregas

## Estructura del Proyecto

```
Tienda/
├── src/main/java/com/tienda/
│   ├── model/          # Clases del modelo
│   ├── view/           # Clases de la interfaz gráfica
│   ├── controller/     # Controladores (MVC)
│   ├── dao/            # Interfaces DAO para persistencia
│   └── main/           # Clase principal
├── pom.xml
└── README.md
```

## Requisitos

- Java 11 o superior
- Maven 3.6 o superior

## Instalación y Ejecución

### Usando Maven

1. Compilar: `mvn clean compile`
2. Ejecutar: `mvn exec:java`

## Uso del Sistema

El sistema inicia directamente con **perfil de Vendedor** por defecto, sin necesidad de login.

1. **Realizar Ventas**: 
   - Seleccione categoría en la barra lateral
   - Agregue productos haciendo clic en "Agregar"
   - Vea el carrito en el panel derecho
   - Seleccione forma de pago y finalice la venta

2. **Funciones Disponibles**:
   - Ventas de productos
   - Cobros y cambio
   - Procesar devoluciones
   - Ver promociones aplicadas automáticamente

## Cumplimiento del Temario UPIICSA

### Unidad III: Herencia y Polimorfismo
- ✅ Herencia en jerarquía de productos y usuarios
- ✅ Polimorfismo con métodos abstractos

### Unidad IV: Interfaces Gráficas y Excepciones
- ✅ Swing + FlatLaf con Dark Mode
- ✅ Patrón MVC
- ✅ Manejo de Excepciones con try-catch

### Unidad V: Persistencia
- ✅ Interfaces DAO para mapeo objeto-relacional

## Tecnologías

- Java 11
- Swing
- FlatLaf 3.2.5
- Maven
