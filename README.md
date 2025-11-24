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
title Diagrama de Clases - Código Implementado (Actual)
skinparam classAttributeIconSize 0
skinparam linetype ortho

interface Serializable

class Exception {}

class ErrorInventario extends Exception {
  + ErrorInventario(mensaje: String)
}

class DatosInvalidosError extends ErrorInventario {
  + DatosInvalidosError(mensaje: String)
}

class StockInsuficienteError extends ErrorInventario {
  + StockInsuficienteError(mensaje: String)
}

class NombreInvalidoError extends ErrorInventario {
  + NombreInvalidoError(mensaje: String)
}

class FormatoNumericoError extends ErrorInventario {
  + FormatoNumericoError(mensaje: String)
}

class Producto implements Serializable {
  - String nombre
  - double precio
  - int stock
  + Producto(nombre: String, precio: double, stock: int)
  + getNombre(): String
  + getPrecio(): double
  + getStock(): int
  + setStock(stock: int): void
  + ajustarStock(cantidad: int): void
}

class Proveedor implements Serializable {
  - String nombre
  - String contacto
  + Proveedor(nombre: String, contacto: String)
  + getNombre(): String
  ' Nota: Falta getContacto()
}

class Movimiento {
  - String tipo
  - Producto producto
  - int cantidad
  + Movimiento(tipo: String, producto: Producto, cantidad: int)
  + validarDatos() throws DatosInvalidosError
  + aplicar() throws StockInsuficienteError
  ' Nota: No implementa Serializable
}


class Inventario {
  ' Esta clase es puramente estática (Utilidad)
  + {static} guardarProductos(productos: ArrayList<Producto>): void
  + {static} cargarProductos(): ArrayList<Producto>
}

class Main {
  ' El Main tiene el estado (la lista de productos)
  - {static} ArrayList<Producto> productos
  - {static} Scanner sc
  + {static} main(args: String[]): void
  + {static} registrarProducto(): void
  + {static} entradaStock(): void
  + {static} salidaStock(): void
  + {static} verProductos(): void
  + {static} buscarProducto(nombre: String): Producto
}


' 1. El Main es dueño de la lista de Productos
Main "1" -- Producto : contiene (lista estática) >

' 2. Main usa Inventario para guardar (pero no para cargar)
Main ..> Inventario : usa (guardarProductos) >

' 3. Movimiento depende de Producto para operar
Movimiento "1" --> "1" Producto : modifica >

' 4. Inventario depende de Producto para serializar
Inventario ..> Producto : usa >

' 5. El resto de clases de dominio están aisladas del flujo principal (Main)
Proveedor -[hidden]-> Main 

' Dependencias de Excepciones
Movimiento ..> DatosInvalidosError : lanza >
Movimiento ..> StockInsuficienteError : lanza >

@enduml
```
<img width="1859" height="969" alt="eflesflnfelnfsljnfe+" src="https://github.com/user-attachments/assets/8fb53bc5-377d-4394-8054-1b3712a89038" />

## *5. Casos de uso principales*

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

## *6. Diagramas De Secuencia*
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
<img width="627" height="523" alt="skjbkfjbdskjbfgjkd" src="https://github.com/user-attachments/assets/d809885e-06bc-4fc3-9107-df1b69e1304a" />

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
<img width="591" height="656" alt="lkjklkjkllk" src="https://github.com/user-attachments/assets/17286ddf-5e0a-4ef9-b270-c49faa7efbfb" />

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
<img width="733" height="773" alt="fkds" src="https://github.com/user-attachments/assets/ba25200d-0032-496d-8b51-eb17f2e1e5c1" />

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
<img width="583" height="572" alt="image" src="https://github.com/user-attachments/assets/c622a52f-d0f5-4184-8c1d-dadfc63ce0db" />

## Principios de Programación Orientada a Objetos (POO)

Abstracción
El sistema modela entidades reales mediante clases. Producto representa artículos del inventario; Movimiento modela una operación de entrada o salida; Inventario centraliza y administra los objetos del sistema.

Encapsulamiento
Los atributos (nombre, precio, stock) están protegidos y solo pueden modificarse a través de métodos como ajustarStock() o getters/setters. Esto evita inconsistencias y asegura control del estado.

Herencia
En el diseño UML, Movimiento actúa como clase abstracta que agrupa atributos y comportamientos comunes. IngresoMovimiento y SalidaMovimiento heredan de ella y redefinen la lógica correspondiente.

Polimorfismo
El método aplicarMovimiento() se comporta de forma distinta según si se trata de una entrada o una salida de stock. Esto permite tratar los movimientos de forma general sin perder especialización.

## Principios SOLID aplicados

S — Responsabilidad Única (SRP)

Producto gestiona únicamente atributos y métodos del producto.

Inventario controla colecciones y persistencia.

Movimiento se encarga de modificar el stock.

Excepciones define errores específicos del dominio.

O — Abierto/Cerrado (OCP)
Se pueden agregar nuevos tipos de movimientos sin modificar Inventario ni Producto, solo creando nuevas subclases de Movimiento.

L — Sustitución de Liskov (LSP)
IngresoMovimiento y SalidaMovimiento pueden usarse donde se espera un objeto Movimiento, sin romper la lógica del sistema.

I — Segregación de Interfaces (ISP)
En el UML se separan interfaces chicas como Validable y Persistible, evitando forzar a las clases a implementar métodos innecesarios.

D — Inversión de Dependencias (DIP)
El diseño teórico utiliza dependencias hacia interfaces (Validable, Persistible) en lugar de depender de implementaciones concretas. Esto reduce acoplamiento y facilita la evolución del sistema.

## Patrón Singleton en el sistema

El patrón Singleton funciona bien en este proyecto porque el inventario debe existir solo una vez durante toda la ejecución. Tener varias copias del inventario generaría datos inconsistentes. Con Singleton, todas las operaciones (registrar, ingresar stock, retirar stock y ver productos) trabajan sobre la misma instancia, garantizando que los cambios se reflejen en todo el sistema.

Además, centraliza la persistencia: la misma instancia es la que carga y guarda los datos, evitando duplicados y facilitando el control del estado del inventario.

## 1. Principios de POO

**¿Cómo aplicaste abstracción, encapsulación, herencia y polimorfismo?**

> Aplicamos abstracción separando el sistema en clases como Producto, Movimiento y Proveedor, donde cada clase representa una entidad real del sistema.
> 
> 
> Usamos **encapsulación** declarando los atributos como `private` y accediendo a ellos por medio de getters y setters.
> 
> Implementamos **herencia** en la jerarquía de excepciones, donde `ErrorInventario` es la clase base y `DatosInvalidosError` y `StockInsuficienteError` heredan de ella.
> 
> El **polimorfismo** se da cuando manejo diferentes tipos de excepciones usando la clase base `ErrorInventario` en los bloques `catch`.
> 

**Clase base y clases derivadas**

> La clase base es ErrorInventario y las clases derivadas son DatosInvalidosError y StockInsuficienteError, que extienden su comportamiento.
> 

**Polimorfismo dinámico**

> Aplicamos polimorfismo dinámico en el manejo de excepciones, porque puedemos capturar distintos errores usando la clase padre y Java decide en tiempo de ejecución qué tipo de excepción es.
> 

---

## 2. Relación entre clases

**Relaciones entre clases**

> Existe una asociación entre Movimiento y Producto, porque un movimiento siempre está relacionado con un producto.
> 
> 
> Hay una forma de **agregación** entre `Inventario` y la lista de `Producto`, ya que el inventario gestiona productos pero estos pueden existir independientemente.
> 

**Interfaz vs clase abstracta**

> En este proyecto usé clases concretas en lugar de interfaces porque la lógica era sencilla y no necesitaba múltiples implementaciones. En una versión más avanzada podría usar interfaces para repositorios de persistencia.
> 

**Sobrecarga o sobreescritura**

> Implementamos sobreescritura en las excepciones al heredar el comportamiento de Exception y personalizar los mensajes de error mediante el constructor.
> 

---

## 3. Diseño UML

**¿El UML refleja el código?**

> Sí, el diagrama refleja las relaciones reales entre las clases. Durante la implementación ajusté el diseño agregando el método ajustarStock() en Producto.
> 

**Herramienta usada**

> Usé PlantUML / herramientas UML en línea y me aseguré de que las clases, atributos y relaciones coincidieran con el código desarrollado.
> 

---

## 4. Patrones de diseño

**¿Qué patrón usaste?**

> Apliqué una idea del patrón Facade, donde la clase Inventario centraliza las operaciones de registrar productos, entradas y salidas.
> 

**Motivación**

> Elegí este patrón para simplificar la interacción con el sistema y evitar que la clase Main manejara toda la lógica.
> 

---

## 5. Principios SOLID

**Principio aplicado**

> Apliqué el Principio de Responsabilidad Única (SRP) separando cada clase con una sola función:
> 
> 
> `Producto` representa datos, `Movimiento` gestiona operaciones y `Inventario` administra la colección.
> 

**Bajo acoplamiento y alta cohesión**

> Diseñé las clases para que cada una tenga responsabilidades claras y que se comuniquen solo mediante métodos públicos.
> 

---

## 6. Excepciones y manejo de errores

**Excepciones personalizadas**

> Implementé ErrorInventario, DatosInvalidosError y StockInsuficienteError.
> 
> 
> Se lanzan cuando el usuario introduce datos inválidos o intenta retirar más stock del disponible.
> 

**Evitar que el sistema se detenga**

> Uso bloques try-catch para capturar errores y permitir que el programa siga ejecutándose.
> 

---

## 7. Persistencia

**¿Cómo guardas los datos?**

> Implementé persistencia mediante serialización de objetos en archivos, para que los productos se mantengan aunque el programa se cierre.
> 

**Clases responsables**

> La clase Inventario es la encargada de guardar y cargar los datos.
> 

---

## 8. Interfaz de Usuario

**Tecnología usada**

> Utilicé una interfaz de consola usando Scanner.
> 

**Separación de responsabilidades**

> La interacción con el usuario está en la clase Main, mientras que la lógica de negocio está en otras clases.
> 

---

## 9. Calidad del código

**Estructura del código**

> Organicé el proyecto en clases separadas por responsabilidad y mantuve el código modular.
> 

**Convenciones**

> Usé nombres claros como Producto, Movimiento, Inventario, métodos como getStock() y ajustarStock().
> 

---

## 10. Requisitos funcionales

**Extras implementados**

> Agregué validaciones avanzadas, alertas de stock bajo y control de errores amigable.
> 

**Parte más difícil**

> La parte más compleja fue controlar correctamente las excepciones y validaciones del usuario para evitar que el programa se detuviera.
>
