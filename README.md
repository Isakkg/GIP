# *Sistema de Gestión de Inventarios*

---

## *1. Descripción del problema*

*La tienda presenta pérdidas porque no sabe cuándo debe reabastecer ciertos productos. El sistema actual, basado en registros manuales o hojas de cálculo, no genera alertas de stock bajo, no valida operaciones y no permite analizar la información de forma histórica. Esto provoca falta de mercancía, exceso de inventario o decisiones poco precisas.*

*El sistema digital propuesto permite registrar productos, gestionar entradas y salidas de stock, validar datos, y mostrar alertas cuando un producto llega a niveles críticos. El diseño considera además la integración de proveedores, movimientos históricos y persistencia de datos.*

---

## *2. Requisitos funcionales implementados*

*Funcionalidades actualmente operativas en el código:*

- *Registrar productos.*
- *Aumentar stock (entrada).*
- *Disminuir stock (salida).*
- *Validación de stock insuficiente.*
- *Visualización de productos con alerta de stock bajo.*
- *Uso de excepciones personalizadas.*

*Funcionalidades contempladas en el diseño UML (pero no integradas totalmente en la versión actual del código):*

- *Gestión completa de proveedores.*
- *Movimientos como objetos persistentes.*
- *Persistencia global de inventario, proveedores y movimientos.*

---

## *3. Instrucciones de uso*

### *Ejecutar el programa*
1. *Abrir la carpeta del proyecto que contiene los archivos `.java`.*  
2. *Compilar todos los archivos*
3. *Ejecutar el programa principal:*
4. *El menú aparecerá automáticamente en la consola.*

---

### *Uso del menú principal*

*Elegir una de las opciones mostradas por el sistema:*

1. *Registrar producto*  
- *Ingresar nombre, precio y stock inicial.*  
- *El sistema validará los datos antes de registrarlo.*

2. *Entrada de stock*  
- *Ingresar el nombre del producto.*  
- *Ingresar la cantidad a añadir.*  
- *Si no existe o la cantidad no es válida, se mostrará un mensaje de error.*

3. *Salida de stock*  
- *Ingresar el nombre del producto.*  
- *Ingresar la cantidad a retirar.*  
- *Si la cantidad supera el stock disponible, el sistema mostrará “Stock insuficiente”.*

4. *Ver productos*  
- *Se muestra la lista completa de productos.*  
- *Si algún producto tiene menos de 5 unidades, aparece la alerta de “Stock bajo”.*

---

### *Interacción y validaciones*

- *Ingresar siempre información correcta al sistema.*  
- *Si existe un error (producto no encontrado, cantidad inválida, stock insuficiente), el sistema lo notificará sin detener la ejecución.*  
- *El usuario puede seguir utilizando el menú cuantas veces desee.*

---

### *Finalizar el programa*

- *Cerrar la consola cuando ya no se requiera usar el sistema.*  
- *En versiones con persistencia, guardar los datos antes de salir para conservar el inventario.*

---

## *4. Diagramas UML*

### *4.1. Diagrama de clases (PlantUML)*

```plantuml
@startuml
title Sistema de Gestión de Inventario
skinparam classAttributeIconSize 0

interface Validable {
+ validarDatos(): void
}

interface Persistible {
+ guardarDatos(): void
+ cargarDatos(): void
}

class Producto implements Validable {
- int id
- String nombre
- String categoria
- int stock
- double precio
- int reorderLevel
+ validarDatos(): void
+ ajustarStock(cantidad: int): void
+ esBajoStock(): boolean
}

class Proveedor {
- int id
- String nombre
- String contacto
- List<Producto> productos
+ agregarProducto(producto: Producto): void
+ mostrarInfo(): void
}

abstract class Movimiento implements Validable {
- int id
- String tipo
- Producto producto
- int cantidad
- LocalDateTime fecha
+ validarDatos(): void
+ aplicarMovimiento(): void
}

class IngresoMovimiento extends Movimiento {
+ aplicarMovimiento(): void
}

class SalidaMovimiento extends Movimiento {
+ aplicarMovimiento(): void
}

class Inventario implements Persistible {
- List<Producto> productos
- List<Proveedor> proveedores
- List<Movimiento> movimientos
+ agregarProducto(producto: Producto): void
+ agregarProveedor(proveedor: Proveedor): void
+ registrarMovimiento(mov: Movimiento): void
+ mostrarProductos(): void
+ alertasStockBajo(): void
+ guardarDatos(): void
+ cargarDatos(): void
}

abstract class ErrorInventario extends Exception {
+ mensaje: String
}

class StockInsuficienteError extends ErrorInventario
class DatosInvalidosError extends ErrorInventario

Inventario "1" o-- "*" Producto
Inventario "1" o-- "*" Proveedor
Inventario "1" o-- "*" Movimiento

Proveedor "1" o-- "*" Producto
Movimiento "*" --> "1" Producto
@enduml

