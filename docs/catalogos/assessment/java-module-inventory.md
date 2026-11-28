# Inventario Java AS-IS

## Alcance y método

Inventario generado a partir de `pom.xml`, `src/main/java/`, `src/test/java/` y configuración versionada. Los números son evidencia del árbol actual; no representan cobertura funcional ni tráfico productivo.

## Baseline

| Elemento | Evidencia | Resultado |
|---|---|---:|
| Java | `pom.xml:21-27` | 17 |
| Spring Boot | `pom.xml:7-12` | 2.6.7 |
| Artefacto | `pom.xml:14-17` | `ApiPortalDivisas` 0.0.26, WAR |
| MongoDB driver sync | `pom.xml:114-119` | 5.6.0 |
| JWT | `pom.xml:102-107`, `JwtUtils.java:96-102` | JJWT 0.9.1 / RS256 |
| Archivos Java producción | `src/main/java/` | 637 |
| Controllers | anotaciones `@RestController`/`@Controller` | 39 |
| Archivos Java de prueba | `src/test/java/` | 188 |
| Declaraciones de mapping extraídas | anotaciones HTTP en controllers | 337 |
| Operaciones OpenAPI | `docs/api-portal-divisas_v1.0.yaml` | 280 en 249 paths |

La extracción de mappings incluye SCIM, autenticación, Swagger y rutas fuera de los siete prefijos. Por eso no debe compararse directamente con las operaciones del contrato sin normalizar rutas alternativas y operaciones duplicadas.

## Capas y capacidades

| Capacidad | Evidencia representativa | Persistencia/integración | Estado |
|---|---|---|---|
| `catalogos` | `CatalogosController`, `CatalogosServiceImpl`, `CatalogosDao` | `CatalogosDivisas` | candidato piloto |
| `bitacora` | `BitacoraController`, `BitacoraServiceImpl`, `BitacoraDao` | colecciones de bitácora | transversal |
| `core` | clientes, configuraciones, sucursales, beneficios, reportería | Middleware, CatalogosDivisas, Incidencias | alta amplitud |
| `digital` | configuraciones y reportería | Middleware | acoplado a parametría |
| `siex` | `ConfiguracionesController` y DAO | SIEX | capacidad especializada |
| `tef` | spots, regiones, segmentos, límites, parametría, sucursales | TerminalFinanciero y Apigee/Tesorería | posponer |
| `usuarios` | `UsuariosController`, `UsuariosServiceImpl`, DAOs | Autenticacion | seguridad/administración |
| `auth` | `AuthController`, `AuthClientServiceImpl` | Autenticacion | capacidad adyacente |
| `scim` | controllers REST y `MongoStorage` | Autenticacion | provisioning adyacente |

## Validación, errores y asincronía

- Validación: `@Valid`, `@Validated`, `ValidaObjectIdAspect`, `ValidaFormatoFecha` y DTOs bajo `src/main/java/com/baz/divisas/general/annotation` y módulos.
- Errores: `GlobalExceptionHandlerController.java:30-126` convierte validación, credenciales, autorización, seguridad y errores no controlados a `ResponseApi`.
- Envelope: `ResponseApi.java:17-60` contiene `codigo`, `mensaje`, `folio`, `info`, `detalles`, `resultado`; el `HttpStatus` no se serializa.
- Asincronía no gestionada: `new Thread(...)` en `ClientesServiceImpl`, `SpotsServiceImpl`, `MailServiceCertificacion`, `PromoEspacioServiceImpl` y `ArchivosExcel`; `ExecutorService` en carga masiva y reportes. No hay evidencia de cola durable, outbox o reintentos transaccionales.

## Pruebas existentes

Hay pruebas de controller/service/DAO para Catalogos, Bitacora, Core, Digital, TEF, seguridad, criptografía, SCIM y Apigee. Son pruebas unitarias o de slice con mocks; no prueban equivalencia completa contra DocumentDB ni paridad endpoint a endpoint.

## Gaps de inventario

1. Confirmar operación real de cada mapping con tráfico/gateway y no solo anotaciones.
2. Completar trazabilidad de métodos de servicio a DAO y colección para endpoints no incluidos en el piloto.
3. Identificar propietarios funcionales y consumidores de cada módulo.
4. Separar rutas legacy no presentes en OpenAPI antes de un cutover.
