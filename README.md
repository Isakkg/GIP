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

### *4.1. Diagrama de clases*

```
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

```

2. Casos de uso principales

Actores

Administrador / Usuario del sistema.

Casos de uso

Registrar producto.

Entrada de stock.

Salida de stock.

Ver productos.

Id: CDU1—Registrar Producto

Breve descripción:
El usuario registra un nuevo producto en el inventario.

Actores primarios:
Usuario.

Precondiciones:
1. El sistema está en ejecución.
2. El usuario conoce los datos del producto.
3. Debe existir conexión con el módulo de inventario.

Flujo principal:
1. El usuario selecciona la opción "Registrar producto".
2. El sistema solicita nombre, precio y stock inicial.
3. El usuario ingresa los datos.
4. El sistema valida la información.
   4.1 El nombre no debe estar vacío.
   4.2 El precio y el stock deben ser valores numéricos válidos.
5. El sistema crea el producto.
6. El sistema lo agrega al inventario.

Postcondiciones:
1. El producto queda disponible para futuras operaciones.

Flujos alternativos:
DatosInválidos.

Id: CDU2—Entrada de Stock

Breve descripción:
El usuario aumenta el stock de un producto existente.

Actores primarios:
Usuario.

Precondiciones:
1. Debe existir al menos un producto registrado.
2. El usuario conoce el nombre del producto.
3. La cantidad debe ser positiva.

Flujo principal:
1. El usuario selecciona "Entrada de stock".
2. El sistema solicita el nombre.
3. El usuario lo ingresa.
4. El sistema busca el producto.
5. El sistema solicita la cantidad.
6. El usuario ingresa la cantidad.
7. El sistema valida la cantidad.
   7.1 Debe ser numérica.
   7.2 Debe ser mayor que cero.
8. El sistema suma la cantidad al stock.
9. El sistema confirma la operación.

Postcondiciones:
1. El stock del producto aumenta.

Flujos alternativos:
ProductoNoEncontrado.
CantidadInválida.

Id: CDU3—Salida de Stock

Breve descripción:
El usuario retira stock de un producto registrado.

Actores primarios:
Usuario.

Precondiciones:
1. Deben existir productos registrados.
2. La cantidad debe ser positiva.
3. El usuario conoce el nombre del producto.

Flujo principal:
1. El usuario selecciona "Salida de stock".
2. El sistema solicita el nombre.
3. El usuario lo ingresa.
4. El sistema busca el producto.
5. El sistema solicita la cantidad.
6. El usuario ingresa la cantidad.
7. El sistema valida la cantidad.
   7.1 Debe ser numérica.
   7.2 Debe ser mayor que cero.
8. El sistema verifica el stock disponible.
   8.1 La cantidad no debe superar el stock actual.
9. El sistema descuenta la cantidad.
10. El sistema confirma la operación.

Postcondiciones:
1. El stock se actualiza.
2. Si el stock queda por debajo de 5, se muestra alerta de stock bajo.

Flujos alternativos:
ProductoNoEncontrado.
CantidadInválida.
StockInsuficiente.

Id: CDU4—Ver Productos

Breve descripción:
El usuario visualiza los productos registrados.

Actores primarios:
Usuario.

Precondiciones:
1. Debe existir al menos un producto registrado.

Flujo principal:
1. El usuario selecciona "Ver productos".
2. El sistema muestra la lista.
3. El sistema identifica niveles de stock.
   3.1 Si stock < 5, muestra alerta de stock bajo.

Postcondiciones:
1. El usuario conoce el estado actual del inventario.

Flujos alternativos:
InventarioVacío.

1) Registrar Producto
```
@startuml
title CU-01 Registrar Producto

actor Usuario
participant Main
participant Producto

Usuario -> Main : Selecciona "Registrar producto"
Main -> Usuario : Solicita nombre, precio, stock inicial
Usuario -> Main : Ingresa datos
Main -> Main : Valida datos

alt Datos válidos
  Main -> Producto : new Producto(nombre, precio, stock)
  Main -> Main : agrega producto a la lista
  Main -> Usuario : Confirma "Producto registrado"
else Datos inválidos
  Main -> Usuario : Muestra error de datos
end
@enduml

```

2) Entrada de Stock
```
@startuml
title CU-02 Entrada de Stock

actor Usuario
participant Main
participant Producto

Usuario -> Main : Selecciona "Entrada de stock"
Main -> Usuario : Solicita nombre del producto
Usuario -> Main : Ingresa nombre
Main -> Main : buscarProducto(nombre)

alt Producto encontrado
  Main -> Usuario : Solicita cantidad a ingresar
  Usuario -> Main : Ingresa cantidad
  Main -> Main : Valida cantidad > 0

  alt Cantidad válida
    Main -> Producto : ajustarStock(+cantidad)
    Main -> Usuario : Confirma "Entrada registrada"
  else Cantidad inválida
    Main -> Usuario : Muestra error de cantidad
  end

else Producto no encontrado
  Main -> Usuario : Muestra error "Producto no encontrado"
end
@enduml
```
3) Salida de Stock
```
@startuml
title CU-03 Salida de Stock

actor Usuario
participant Main
participant Producto

Usuario -> Main : Selecciona "Salida de stock"
Main -> Usuario : Solicita nombre del producto
Usuario -> Main : Ingresa nombre
Main -> Main : buscarProducto(nombre)

alt Producto encontrado
  Main -> Usuario : Solicita cantidad a retirar
  Usuario -> Main : Ingresa cantidad
  Main -> Main : Valida cantidad > 0

  alt Cantidad válida
    Main -> Main : Verifica stock suficiente (cantidad <= stock)

    alt Stock suficiente
      Main -> Producto : ajustarStock(-cantidad)
      Main -> Usuario : Confirma "Salida registrada"
    else Stock insuficiente
      Main -> Usuario : Muestra error "Stock insuficiente"
    end

  else Cantidad inválida
    Main -> Usuario : Muestra error de cantidad
  end

else Producto no encontrado
  Main -> Usuario : Muestra error "Producto no encontrado"
end
@enduml
```
4) Ver Productos
```
@startuml
title CU-04 Ver Productos

actor Usuario
participant Main
participant Producto

Usuario -> Main : Selecciona "Ver productos"
Main -> Main : Recorre lista productos

alt Inventario no vacío
  loop Por cada producto
    Main -> Producto : getNombre()
    Main -> Producto : getPrecio()
    Main -> Producto : getStock()
    Main -> Usuario : Muestra datos del producto

    alt Stock < 5
      Main -> Usuario : Muestra "ALERTA: Stock bajo"
    end
  end
else Inventario vacío
  Main -> Usuario : Muestra "No hay productos registrados"
end
@enduml
```
