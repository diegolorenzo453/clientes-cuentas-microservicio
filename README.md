# Clientes y Cuentas Bancarias

Microservicio REST desarrollado con Spring Boot para gestionar clientes y cuentas bancarias. El proyecto está preparado como prueba técnica, con foco en funcionalidad completa, código limpio, arquitectura por capas, documentación OpenAPI, validaciones y tests automatizados.

## Tecnologías

- Java 17
- Spring Boot 3.3.5
- Maven
- Spring Web
- Spring Data JPA
- Bean Validation
- H2 Database
- Lombok
- Springdoc OpenAPI
- JUnit 5 y MockMvc

## Funcionalidades

- Consulta de clientes con sus cuentas bancarias.
- Consulta de clientes mayores de edad.
- Consulta de clientes cuya suma total de cuentas supera una cantidad determinada.
- Consulta de cliente por DNI.
- Alta de una cuenta bancaria para un cliente existente.
- Alta de una cuenta bancaria creando el cliente asociado si no existe.
- Actualización del saldo de una cuenta bancaria.
- Validación de tipos de cuenta mediante enum (`NORMAL`, `PREMIUM`, `JUNIOR`).
- Manejo centralizado de errores `400` y `404`.
- Documentación Swagger/OpenAPI.
- Colección de Postman con casos de prueba.

## Requisitos

- Java 17 o superior
- Maven 3.9 o superior

## Ejecución

Desde la raíz del proyecto:

```bash
mvn spring-boot:run
```

La API queda disponible en:

```text
http://localhost:8080
```

Recursos útiles:

| Recurso | URL |
| --- | --- |
| Swagger UI | `http://localhost:8080/swagger-ui.html` |
| OpenAPI JSON | `http://localhost:8080/v3/api-docs` |
| H2 Console | `http://localhost:8080/h2-console` |

Configuración de H2:

| Campo | Valor |
| --- | --- |
| JDBC URL | `jdbc:h2:mem:clientesdb` |
| Usuario | `sa` |
| Password | vacío |

## Tests

Para ejecutar la suite de tests:

```bash
mvn test
```

Los tests combinan pruebas de integración con Spring Boot Test y MockMvc, más pruebas unitarias puras de dominio. Cubren consultas, altas, actualización de saldo, validaciones `400`, recursos no encontrados `404`, reglas de saldo, mayoría de edad y cálculo de saldo total.

## Colección de Postman

El proyecto incluye una colección de Postman lista para importar:

```text
docs/postman/postman_collection.json
```

La colección usa la variable `baseUrl`, configurada por defecto con:

```text
http://localhost:8080
```

Incluye peticiones para los casos correctos, errores `404`, validaciones `400`, Swagger y OpenAPI.

## Endpoints

| Método | Ruta | Descripción |
| --- | --- | --- |
| `GET` | `/clientes` | Obtiene todos los clientes con sus cuentas bancarias. |
| `GET` | `/clientes/mayores-de-edad` | Obtiene los clientes mayores de edad. |
| `GET` | `/clientes/con-cuenta-superior-a/{amount}` | Obtiene clientes cuya suma total de cuentas supera la cantidad indicada. |
| `GET` | `/clientes/{nationalId}` | Obtiene un cliente por DNI con sus cuentas bancarias. |
| `POST` | `/cuentas` | Crea una cuenta bancaria. Si el cliente no existe, puede crearlo en la misma petición. |
| `PUT` | `/cuentas/{accountId}` | Actualiza el saldo de una cuenta bancaria. |

## Ejemplos de uso

Obtener todos los clientes:

```bash
curl http://localhost:8080/clientes
```

Obtener clientes mayores de edad:

```bash
curl http://localhost:8080/clientes/mayores-de-edad
```

Filtrar clientes por saldo total:

```bash
curl http://localhost:8080/clientes/con-cuenta-superior-a/100000
```

Obtener cliente por DNI:

```bash
curl http://localhost:8080/clientes/11111111A
```

Crear una cuenta para un cliente existente:

```bash
curl -X POST http://localhost:8080/cuentas \
  -H "Content-Type: application/json" \
  -d '{
    "dniCliente": "11111111A",
    "tipoCuenta": "NORMAL",
    "total": 50000
  }'
```

Crear una cuenta y un cliente nuevo:

```bash
curl -X POST http://localhost:8080/cuentas \
  -H "Content-Type: application/json" \
  -d '{
    "dniCliente": "66666666F",
    "tipoCuenta": "PREMIUM",
    "total": 150000,
    "cliente": {
      "nombre": "Laura",
      "apellido1": "Garcia",
      "apellido2": "Martin",
      "fechaNacimiento": "1990-04-15"
    }
  }'
```

Actualizar el saldo de una cuenta:

```bash
curl -X PUT http://localhost:8080/cuentas/1 \
  -H "Content-Type: application/json" \
  -d '{
    "total": 180000
  }'
```

## Contrato JSON

Aunque el código interno usa nombres en inglés, el contrato REST mantiene los nombres solicitados en el enunciado mediante `@JsonProperty`.

Ejemplo de respuesta de cliente:

```json
{
  "dni": "11111111A",
  "nombre": "Juan",
  "apellido1": "Perez",
  "apellido2": "Lopez",
  "fechaNacimiento": "1959-09-12",
  "cuentas": [
    {
      "id": 1,
      "dniCliente": "11111111A",
      "tipoCuenta": "PREMIUM",
      "total": 150000.00
    }
  ]
}
```

Ejemplo de error `404`:

```json
{
  "timestamp": "2026-05-16T19:07:00.009709700Z",
  "status": 404,
  "error": "Resource not found",
  "details": [
    "Bank account not found with id 9999"
  ]
}
```

## Datos iniciales

La aplicación inicializa una base de datos H2 en memoria con clientes y cuentas bancarias mediante `DataInitializer`.

Al detener la aplicación, los datos se eliminan porque H2 se ejecuta en memoria y JPA está configurado con `create-drop`.

## Arquitectura

El proyecto sigue una separación por capas inspirada en arquitectura hexagonal:

```text
src/main/java/com/example/customeraccounts
├── application
│   ├── command
│   └── service
├── domain
│   ├── model
│   └── port
└── infrastructure
    ├── config
    ├── persistence
    │   ├── adapter
    │   ├── entity
    │   └── repository
    └── web
        ├── controller
        ├── dto
        ├── exception
        └── mapper
```

Responsabilidades principales:

| Capa | Responsabilidad |
| --- | --- |
| `domain` | Modelos de negocio y puertos de persistencia. |
| `application` | Casos de uso, comandos y orquestación de lógica. |
| `infrastructure.persistence.adapter` | Adaptadores que implementan los puertos del dominio. |
| `infrastructure.persistence.entity` | Entidades JPA usadas por Hibernate. |
| `infrastructure.persistence.repository` | Repositorios Spring Data JPA. |
| `infrastructure.web.controller` | Controladores REST. |
| `infrastructure.web.dto` | Requests y responses HTTP. |
| `infrastructure.web.exception` | Manejo centralizado de errores. |
| `infrastructure.web.mapper` | Conversión entre dominio, comandos y DTOs. |
| `infrastructure.config` | Configuración OpenAPI y carga de datos iniciales. |

## Decisiones técnicas

- El dominio no depende de Spring, JPA ni de DTOs HTTP.
- La capa de aplicación trabaja contra puertos (`CustomerRepositoryPort`, `BankAccountRepositoryPort`), no contra repositorios Spring Data directamente.
- Los controladores se mantienen finos y delegan la conversión de DTOs en `CustomerAccountRestMapper`.
- La lógica de mayoría de edad y suma total de cuentas vive en el modelo `Customer`.
- El endpoint `POST /cuentas` cubre cliente existente, cliente nuevo informado en la petición y cliente mínimo cuando solo llega el DNI.
- El tipo de cuenta se modela como enum de dominio (`AccountType`) para restringir valores a `NORMAL`, `PREMIUM` y `JUNIOR`.
- La consulta de clientes con cuentas evita el patrón N+1 cargando todas las cuentas de los clientes en una única consulta y agrupándolas en memoria.
- Los nombres internos de clases, archivos, métodos y variables están en inglés.
- Las rutas y propiedades JSON se mantienen en español para respetar el contrato del enunciado.
- El banner de arranque se personalizó en `src/main/resources/banner.txt`.

## Uso de Streams, Optionals y lambdas

El proyecto incluye uso explícito de Streams, lambdas y Optionals en puntos relevantes:

| Recurso | Ejemplo |
| --- | --- |
| `CustomerAccountService#findCustomersWithAccounts` | `stream().map(this::withBankAccounts).toList()` |
| `CustomerAccountService#findAdultCustomers` | `filter(customer -> customer.isAdult(today))` |
| `CustomerAccountService#createBankAccount` | `findByNationalId(...).or(...).orElseGet(...)` |
| `Customer#totalBalance` | `map(BankAccount::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add)` |
| `CustomerAccountRestMapper` | Transformación de listas de cuentas con `stream()` |

## Validaciones y errores

Las peticiones de entrada usan Bean Validation:

- `@NotBlank` para campos de texto obligatorios.
- `@NotNull` para importes obligatorios.
- `@DecimalMin("0.0")` para evitar saldos negativos.
- `@Valid` para validar el cliente anidado al crear una cuenta.
- `AccountType` para validar que el tipo de cuenta sea `NORMAL`, `PREMIUM` o `JUNIOR`.

Los errores se gestionan en `ApiExceptionHandler`, devolviendo respuestas homogéneas con `timestamp`, `status`, `error` y `details`.

## Evaluación de AbstractCoreCrud

Se revisó la librería local `com.nortempo:libraries:nrtp-library-commons-rest`. En el entorno aparece `AbstractCoreCrudController`, pero no aparecen `AbstractCoreCrudReadService` ni `AbstractCoreCrudWriteService` con esos nombres.

No se ha usado `AbstractCoreCrudController` por estos motivos:

- La clase exige DTOs que extienden `AuditableDTO<?, ?>` y servicios `IReadService` / `IWriteService` propios de la librería.
- El ejercicio pide endpoints específicos y filtros de negocio, no un CRUD genérico.
- Encajarlo obligaría a acoplar los DTOs y parte del diseño a una infraestructura externa.
- Para esta prueba técnica resulta más claro mostrar puertos, adaptadores, casos de uso, validaciones y tests propios.

Si en la entrevista pidieran usar esa librería corporativa, el siguiente paso razonable sería crear una rama separada, añadir la dependencia `nrtp-library-commons-rest` y adaptar solo un recurso CRUD genérico, manteniendo estos endpoints de negocio fuera del controlador base.

## Checklist del enunciado

| Requisito | Estado | Referencia |
| --- | --- | --- |
| Spring Boot 3.x, Java 17 y Maven | Completado | `pom.xml` |
| Base de datos H2 en memoria | Completado | `application.yml` |
| Datos iniciales | Completado | `DataInitializer` |
| Arquitectura por capas | Completado | `domain`, `application`, `infrastructure` |
| `GET /clientes` | Completado | `CustomerController` |
| `GET /clientes/mayores-de-edad` | Completado | `CustomerController`, `CustomerAccountService` |
| `GET /clientes/con-cuenta-superior-a/{amount}` | Completado | `CustomerController`, `CustomerAccountService` |
| `GET /clientes/{nationalId}` | Completado | `CustomerController` |
| `POST /cuentas` | Completado | `BankAccountController` |
| `PUT /cuentas/{accountId}` | Completado | `BankAccountController` |
| Swagger/OpenAPI | Completado | `OpenApiConfig`, anotaciones `@Operation` |
| Tests automatizados | Completado | `CustomerAccountsApplicationTests` |
| Colección Postman | Completado | `docs/postman/postman_collection.json` |
