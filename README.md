# PharmaBackend

API REST de PharmaSoft (Categorías y Clientes) para la actividad autónoma de la
Sesión 7 de Lenguaje de Programación II. Generada porque no se contaba con el
repositorio original del Reto 01: implementa exactamente los endpoints, DTOs
y reglas de negocio descritos en la guía (paginación, orden, baja lógica de
clientes, unicidad de DNI/correo).

## Requisitos
- Java 17+
- Maven (o usa el wrapper si lo agregas: `mvn -N io.takari:maven:wrapper`)

## Ejecutar

```bash
mvn spring-boot:run
```

Queda escuchando en `http://localhost:8080`.

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Consola H2 (base de datos en memoria): http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:pharmadb`
  - Usuario: `sa` / Password: (vacío)

La base de datos es en memoria: se llena con datos de ejemplo al arrancar
(`DataSeeder`) y se reinicia cada vez que detienes la aplicación.

## Base de datos

- **Por defecto: H2 en memoria** (no requiere instalar nada).
- **Oracle:** crea el usuario/esquema y arranca con el perfil `oracle`
  (`mvn spring-boot:run -Dspring-boot.run.profiles=oracle`). Edita la URL en
  `application-oracle.properties` (por defecto `localhost:1521/XEPDB1`) y pasa
  usuario y clave por las variables `ORACLE_USER` y `ORACLE_PASSWORD`.
  Hibernate crea las tablas `categorias` y `clientes` (`ddl-auto=update`).
  Si la tabla ya existía con datos, la identidad puede quedar desfasada de los
  IDs existentes; en ese caso reinicia la identidad de la columna `ID`.

## Endpoints

### Categorías — `/api/v1/categorias`
`GET` (lista completa), `GET /{id}`, `POST`, `PUT /{id}`, `DELETE /{id}` (elimina físicamente).

### Clientes — `/api/v1/clientes`
- `GET ?pagina=0&tamanio=10&ordenarPor=apellidos&direccion=asc` → paginado,
  `ordenarPor` admite `id`, `dni`, `nombres`, `apellidos`, `email` (otro valor → 409).
- `GET /{id}`, `POST`, `PUT /{id}`
- `DELETE /{id}` → baja lógica (`estado: false`); si ya estaba inactivo → 409.

## CORS
Configurado en `config/CorsConfig.java` para aceptar `http://localhost:4200`
(el puerto por defecto de `ng serve`). Si tu frontend corre en otro puerto,
ajusta `allowedOrigins` ahí.
