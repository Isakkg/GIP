# *Sistema de Gestión de Inventarios*

---

## *1. Descripción del problema*

*La tienda presenta pérdidas porque no sabe cuándo debe reabastecer ciertos productos. El sistema actual, basado en registros manuales o hojas de cálculo, no valida operaciones y no permite analizar la información de forma histórica. Esto provoca falta de mercancía, exceso de inventario o decisiones poco precisas.*

*El sistema digital propuesto permite registrar productos, gestionar entradas y salidas de stock, y validar datos. El diseño considera además la integración de proveedores, movimientos históricos y persistencia de datos.*

---

## *2. Requisitos funcionales implementados*

*Funcionalidades actualmente operativas en el código:*

- *Registrar productos.*
- *Aumentar stock (entrada).*
- *Disminuir stock (salida).*
- *Validación de stock insuficiente.*
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
title Diagrama de Clases 
skinparam classAttributeIconSize 0
skinparam linetype ortho

interface Serializable
class Exception

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
}

class Movimiento {
  - String tipo
  - Producto producto
  - int cantidad
  + Movimiento(tipo: String, producto: Producto, cantidad: int)
  + validarDatos() throws DatosInvalidosError
  + aplicar() throws StockInsuficienteError
}


class SistemaInventario {
  - ArrayList<Producto> productos
  - ArrayList<Proveedor> proveedores

  + SistemaInventario()
  + registrarProducto(nombre: String, precio: double, stock: int): void
  + registrarProveedor(nombre: String, contacto: String): void
  + realizarEntrada(nombreProducto: String, cantidad: int): void
  + realizarSalida(nombreProducto: String, cantidad: int): void
  + obtenerProductos(): ArrayList<Producto>
  + buscarProducto(nombre: String): Producto
  - guardarCambios(): void
}


class GestorPersistencia {
  + {static} guardarProductos(productos: ArrayList<Producto>): void
  + {static} cargarProductos(): ArrayList<Producto>
}


class Main {
  - {static} Scanner sc
  - {static} SistemaInventario sistema
  + {static} main(args: String[]): void
  - {static} menu(): void
}



Exception <|-- ErrorInventario
ErrorInventario <|-- DatosInvalidosError
ErrorInventario <|-- StockInsuficienteError
ErrorInventario <|-- NombreInvalidoError
ErrorInventario <|-- FormatoNumericoError


Main ..> SistemaInventario : usa >


SistemaInventario "1" o-- "*" Producto : administra >
SistemaInventario "1" o-- "*" Proveedor : administra >


SistemaInventario ..> Movimiento : crea y aplica >


SistemaInventario ..> GestorPersistencia : usa para\nguardar/cargar >


Movimiento "1" --> "1" Producto : modifica stock >


Movimiento ..> DatosInvalidosError : lanza >
Movimiento ..> StockInsuficienteError : lanza >

@enduml
```
<img width="1986" height="894" alt="dLRBRjim4BmBq3yGFjbjdBPN8J0I96aHe0aC-DZsG5DhXub42YJbvj7yUvUa7hHCksvEDiYTsUNiBAbpRQWoTLcaYU6c07B5wLhHaf8So6L1DMYI9lgHYuhYCc6uf2-CKNn" src="https://github.com/user-attachments/assets/f3f79117-76ce-4beb-9b5a-d2fb98dd2d77" />

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


  end
else Inventario vacío
  Main -> Usuario : Muestra "No hay productos registrados"
end
@enduml
```
<img width="583" height="572" alt="image" src="https://github.com/user-attachments/assets/c622a52f-d0f5-4184-8c1d-dadfc63ce0db" />

## Principios de Programación Orientada a Objetos (POO)

Abstracción
El sistema representa elementos reales del inventario mediante clases específicas.
Producto modela los artículos con sus atributos esenciales; Movimiento abstrae una operación de entrada o salida de stock; Proveedor encapsula la información de los proveedores; e Inventario abstrae la capa de persistencia de datos.
Cada clase refleja solo los aspectos necesarios de la entidad que representa, evitando detalles irrelevantes.

Encapsulamiento
Los atributos de las clases están declarados como privados y solo se accede a ellos mediante métodos públicos (getNombre(), getPrecio(), getStock(), setStock()).
La modificación del stock está encapsulada en el método ajustarStock(int cantidad), lo que garantiza un control centralizado del estado de los productos.

Herencia
La herencia se aplica en la jerarquía de excepciones personalizada.
ErrorInventario actúa como clase base y es extendida por DatosInvalidosError, StockInsuficienteError, NombreInvalidoError y FormatoNumericoError.
Esto permite reutilizar el comportamiento de una excepción estándar mientras se especializan los diferentes tipos de errores del sistema.

Polimorfismo
El sistema usa polimorfismo en el manejo de excepciones: distintas clases derivadas se pueden tratar como su clase base (ErrorInventario).
Además, en la clase Movimiento, el método aplicar() ejecuta una lógica distinta según el tipo de movimiento, manteniendo una misma interfaz de uso y comportamiento dinámico.

## Principios SOLID aplicados

S — Responsabilidad Única
Cada clase del sistema posee una única responsabilidad:
Producto gestiona datos y operaciones de productos.
Inventario gestiona la persistencia con archivos.
Movimiento valida y aplica modificaciones al stock.
Main administra la interacción con el usuario.
Esto promueve claridad, mantenimiento sencillo y menor acoplamiento.

O — Abierto/Cerrado
Las clases están diseñadas para permitir extensiones sin necesidad de ser modificadas.
Se pueden agregar nuevos tipos de movimientos, nuevas validaciones o nuevos métodos de persistencia sin cambiar las clases existentes, gracias a la separación de responsabilidades.

L — Sustitución de Liskov
Las clases derivadas de ErrorInventario pueden emplearse en cualquier lugar donde se utilice la clase base.
Esto asegura un comportamiento coherente y estable cuando se manejan diferentes tipos de errores en el sistema.

I — Segregación de Interfaces
Aunque esta versión del sistema no implementa interfaces explícitas, la arquitectura sigue el espíritu del principio:
cada clase contiene únicamente los métodos que realmente necesita, evitando estructuras grandes o innecesarias y manteniendo el diseño limpio y modular.

D — Inversión de Dependencias

La lógica principal no depende de detalles concretos de implementación.
La clase Main delega la persistencia a Inventario, y las clases del dominio (Producto, Movimiento) no conocen ni dependen del funcionamiento interno del sistema de archivos.
Esto separa el nivel lógico del nivel técnico y mejora la mantenibilidad.

## Implementación del Patrón Facade

El sistema aplica el patrón Facade mediante la clase Inventario, que centraliza todas las operaciones de persistencia. Esta clase ofrece métodos simples (guardarProductos y cargarProductos) que ocultan la complejidad del manejo de archivos, serialización y manejo de excepciones.

Gracias a esta fachada:

La clase Main solo invoca métodos de alto nivel sin conocer detalles técnicos.

Se reduce el acoplamiento entre la interfaz de usuario y la persistencia.

Se mejora la cohesión y la claridad del diseño.

Permite extender el sistema (nuevos tipos de persistencia o datos almacenados) modificando únicamente Inventario.

En resumen, Inventario actúa como un punto de acceso unificado que simplifica y organiza el uso de la funcionalidad interna del sistema.

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

> Agregué validaciones avanzadas y control de errores amigable.
> 

**Parte más difícil**

> La parte más compleja fue controlar correctamente las excepciones y validaciones del usuario para evitar que el programa se detuviera.
>
## INFORME
[Sistema de Gestión de Inventarios.docx](https://github.com/user-attachments/files/23779656/Sistema.de.Gestion.de.Inventarios.docx)
