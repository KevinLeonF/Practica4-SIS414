# Ejercicio-1

## Paso 3

### ¿Qué hace la anotación @Entity y qué significa el atributo tableName?

+ `@Entity`: marca la clase como una entidad gestionada.
+ `tableName`: define el nombre de la tabla asociada a la entidad.

### ¿Cuál es el propósito de la anotación @Id y cómo se usa en esta clase?

`@Id` identifica **el campo que actúa como clave primaria** (primary key) de la entidad. Es decir, señala qué atributo se usará para **identificar de forma única** cada registro en la tabla o colección.

### ¿Qué hace la anotación @GeneratedValue y qué significa el atributo strategy?

`@GeneratedValue` le dice al sistema que el valor del campo marcado con `@Id`**debe generarse automáticamente**, `strategy` indica el tipo de generación (automática, secuencial, etc.).

### ¿Cómo se usan las anotaciones @Column y qué información proporcionan?

`@Column` se aplica sobre los atributos que representan **columnas de una tabla**.
Sirve para indicar el **nombre de la columna** que corresponde a ese campo de la clase.

### ¿Qué papel juegan las anotaciones de Lombok (@NoArgsConstructor, @AllArgsConstructor, @Getter, @Setter) en esta clase?

* `@NoArgsConstructor`: crea un **constructor vacío**, es decir sin ningun parametro.
* `@AllArgsConstructor`: crea un **constructor esta vez con todos los parámetros**.
* `@Getter`: genera automáticamente los **métodos getter** para todos los atributos.
* `@Setter`: genera automáticamente los **métodos setter** para todos los atributos.

### ¿Qué tipo de datos se utilizan para los atributos id, createdAt y updatedAt, y por qué son apropiados para esos campos?

`id`: tipo `Long` poque es apropiado para identificadores únicos incrementales.

`createdAt` y `updatedAt`: tipo `LocalDateTime` → representan **fecha y hora exacta** de creación/modificación.

### ¿Cómo funciona el método toString y qué información devuelve sobre la instancia de User, y qué ventajas tiene usar String.format en este contexto?

El método `toString()` devuelve una **representación en texto** de la instancia de una clase.
En el caso de `User`, Convierte el objeto a texto legible , Muestra los valores de sus atributos

Las ventajas de String.format son: permite **dar formato claro y ordenado** al texto, evita concatenaciones largas (`"User: " + id + " - " + username`), facilita el mantenimiento y la lectura del código.

## Paso 5

### ¿Qué hace la clase `InMemoryRepository` y qué interfaces implementa?

* Es un **repositorio genérico en memoria** que permite guardar, buscar, actualizar y eliminar entidades sin necesidad de una base de datos real.
* Implementa la interfaz `Repository<ID, E>`, que define operaciones básicas de CRUD (crear, leer, actualizar, eliminar).

### ¿Cuál es el propósito del mapa `storage` y cómo se utiliza?

`storage` es un **mapa que guarda las entidades en memoria**, usando el ID como clave y la entidad como valor. Se utiliza para almacenar nuevas entidades, buscarlas por ID, listar todas o eliminarlas.

### ¿Cómo se genera un ID único para las entidades si no se proporciona uno?

Se usa un **contador automático** que incrementa con cada nueva entidad. Si la entidad no tiene ID asignado, se le genera uno único y secuencial para garantizar que no haya colisiones.

### ¿Qué hace el método `getId` y cómo accede al campo ID de una entidad?

`getId` obtiene el valor del **campo que actúa como clave primaria** (`@Id`) de la entidad, lo hace usando **reflexión**, lo que permite acceder incluso a campos privados de la clase.

### ¿Cómo funciona el método `save` y qué hace si el ID es nulo o cero?

Guarda la entidad en memoria, si la entidad no tiene ID (es nula o cero), genera un ID automáticamente antes de guardarla luego la añade al mapa `storage`.

### ¿Qué hace el método `findById` y cómo busca una entidad por su ID?

Busca en el mapa `storage` usando la clave (ID), devuelve la entidad si existe, o un valor vacío si no se encuentra.

### ¿Cómo funciona el método `findAll` y qué devuelve?

Devuelve **todas las entidades almacenadas** en el repositorio, básicamente devuelve la lista de valores del mapa `storage`.

### ¿Qué hace el método `delete` y cómo elimina una entidad del almacenamiento?

Elimina la entidad del repositorio usando su ID como clave, una vez eliminado, la entidad ya no aparece en búsquedas ni en la lista completa.

### ¿Cómo funciona el método `update` y qué hace si la entidad no existe en el almacenamiento?

Reemplaza la entidad existente con la misma clave (ID) por los nuevos valores. Si la entidad no existe en el repositorio, normalmente **lanza un error o excepción**, ya que no se puede actualizar algo que no está guardado.

## Paso 6


Qué hace el método `getRepository` y qué parámetros recibe?

`getRepository` es un método del **EntityManager** que **devuelve un repositorio asociado a una entidad específica, los parámetros típicos son: la **clase de la entidad** que quieres manejar (por ejemplo, `User.class`).

Su función es **obtener o crear el repositorio** que maneja esa clase, permitiendo realizar operaciones CRUD sobre esa entidad.

¿Qué tipo de repositorio devuelve y cómo se instancia?

Básicamente, te da un repositorio genérico, algo así como un InMemoryRepository. Internamente, el EntityManager lo crea, solo la primera vez que lo necesitas. Luego, lo guarda en un mapa interno para usarlo una y otra vez.

Esto garantiza que no haya varias copias del mismo repositorio dando vueltas, y que todas las acciones sobre una entidad específica utilicen la misma colección en la memoria.

¿Cómo se utiliza el `EntityManager` en la arquitectura general del proyecto?

El `EntityManager` actúa como **capa de intermediación** entre los servicios y los repositorios.

Los servicios no acceden directamente a los repositorios; en cambio, piden al EntityManager que les dé el repositorio correspondiente para la entidad.

Esto permite centralizar la **gestión de repositorios y la lógica de persistencia**, facilitando mantenimiento y escalabilidad.

¿Qué ventajas ofrece tener un `EntityManager` en lugar de instanciar repositorios directamente en los servicios?

* **Centralización:** todos los repositorios se gestionan desde un solo lugar.
* **Reutilización:** se evita crear múltiples instancias de repositorios para la misma entidad.
* **Abstracción:** los servicios no necesitan conocer los detalles de la implementación del repositorio.
* **Flexibilidad:** facilita cambiar la implementación de almacenamiento (por ejemplo, pasar de memoria a base de datos) sin modificar los servicios.
* **Mantenimiento:** el código es más limpio y escalable, y sigue la filosofía de separación de capas típica de un mini-ORM o de Spring.

## Paso 7


### ¿Cuál es la responsabilidad principal de la clase `UserService`?

Es centralizar toda la lógica de negocio relacionada con los usuarios, funcionando como intermediario entre la capa de presentación (por ejemplo controladores) y la capa de persistencia

### ¿Cómo se inyecta el repositorio en el servicio y por qué es importante?

El repositorio se inyecta en el servicio, generalmente mediante el constructor, lo que permite desacoplar el servicio de la implementación concreta del repositorio; esto es importante porque facilita pruebas unitarias, mantenimiento y flexibilidad

### ¿Qué hace el método `createUser` y cómo utiliza el DTO `RegisterUserDto`?

Se encarga de recibir los datos del usuario desde un DTO como `RegisterUserDto`, validar que los campos obligatorios estén completos y consistentes, generar un ID único si no se proporciona y asignar las marcas de tiempo de creación y actualización antes de guardar la entidad en el repositorio.

### ¿Cómo funcionan los métodos `getUserById`, `updateUser`, `deleteUser` y `listAllUsers`?

Estos métodos permiten realizar operaciones CRUD sobre los usuarios: `getUserById` busca un usuario por su ID y devuelve un objeto adecuado si existe o vacío si no, `updateUser` reemplaza los datos de un usuario existente, `deleteUser` elimina un usuario del repositorio, y `listAllUsers` devuelve todos los usuarios almacenados.

### ¿Qué ventajas ofrece tener una capa de servicio separada de la capa de repositorio?

Tener una capa de servicio separada permite organizar mejor el código, encapsular la lógica de negocio y mantener los controladores independientes de la forma en que se gestionan los datos. Esto facilita cambios futuros en la persistencia o reglas de negocio sin afectar otras partes del sistema

### ¿Cómo maneja el servicio la creación de IDs y las marcas de tiempo para los usuarios?

El servicio se asegura de generar un ID único para cada usuario si no se proporciona uno y asigna automáticamente las marcas de tiempo de creación y actualización. Esto garantiza que cada usuario tenga un identificador únicoEl servicio se asegura de generar un ID único para cada usuario.

### ¿Qué tipo de objeto devuelve el método `getUserById` si no encuentra un usuario con el ID proporcionado?

El servicio se asegura de generar un ID único para cada usuario si no se proporciona uno y asigna automáticamente las marcas de tiempo de creación y actualización. Esto garantiza que cada usuario tenga un identificador único

### ¿Cómo se asegura el servicio de que los datos del usuario estén completos antes de guardarlos en el repositorio?

El servicio realiza validaciones sobre los datos recibidos a través del DTO, comprobando que los campos obligatorios estén presentes y tengan valores válidos. Solo después de esta verificación se procede a crear o actualizar la entidad en el repositorio, evitando que se almacenen registros incompletos o inconsistentes.

### ¿Qué patrón de diseño se está utilizando al inyectar el repositorio en el servicio a través del constructor?

Se está utilizando el patrón de **Dependency Injection** (inyección de dependencias), que permite desacoplar la clase del servicio de la implementación concreta del repositorio. Esto facilita la prueba de unidades, mejora la modularidad del código y permite cambiar la implementación del repositorio sin modificar el servicio.
