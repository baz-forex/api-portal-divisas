# Design

## Context

El monolito expone catálogos desde `CatalogosController` y recursos TEF desde controllers separados. `CatalogosServiceImpl` orquesta consultas y también el alta de divisas, que escribe `catMonedas` y propaga la divisa a estructuras de regiones y segmentos, además de registrar bitácora. El acceso Java usa el driver MongoDB directamente y no se observó una transacción multi-documento explícita.

El contrato disponible es Swagger 2.0 y utiliza `/portal-divisas/configuraciones` como base path objetivo, Bearer global y `ResponseApi` como envoltura común. El mecanismo operativo documentado utiliza `@gs/proxy` para `ProxyAuthGuard` y `@gs/crypto` para `UseEncryptionV2` y obtención de llaves mediante el identificador de acceso. El template debe consumir el conjunto corporativo de paquetes con versiones fijadas.

## Goals / Non-Goals

**Goals:**

- Crear una base NestJS 11.x reutilizable para servicios de la migración.
- Mantener compatibilidad de rutas, respuestas, validaciones y estados HTTP de los endpoints incluidos.
- Separar dominio, aplicación, infraestructura y presentación.
- Aislar Mongoose 9.x, módulos corporativos, logging, identidad y dependencias externas mediante puertos.
- Proporcionar ejecución local con Docker y pruebas con Vitest.
- Implementar logging seguro con redacción.
- Entregar un template ejecutable con los módulos corporativos obligatorios y variables de entorno documentadas sin secretos.
- Permitir coexistencia y rollback hacia el monolito Java.

**Non-Goals:**

- No modificar controllers, servicios o datos del monolito en esta propuesta.
- No construir infraestructura AWS, administrar el servicio DocumentDB, redes, secretos, IaC ni pipelines CI/CD; solo integrar la configuración de aplicación y validar en el entorno autorizado.
- No seleccionar ni implementar un proveedor de identidad nuevo.
- No migrar automáticamente los 22 recursos como un único microservicio.
- No activar escrituras multi-colección en producción sin validar consistencia, compensación y aprobación.
- No incluir secretos, credenciales reales o datos productivos en fixtures.

## Decisions

### 1. Un template con límites de capacidad, no un servicio por controller

La base se organizará por capacidad:

```text
src/
  shared/
  health/
  modules/
    foreign-exchange/
    financial-terminal/
    operational-configuration/
```

Cada módulo seguirá:

```text
domain/
application/
infrastructure/
presentation/
```

La separación inicial permite entregar un servicio ejecutable sin fijar prematuramente si los módulos terminarán en uno o varios despliegues. Los límites definitivos dependerán del ownership aprobado por Divisas y Terminal Financiero.

### 2. NestJS 11, Node.js 26 y pnpm como baseline

El template utilizará NestJS 11.x en la última versión soportada por la organización, Node.js 26.12.1 y pnpm 11.5.3, versión fijada por la imagen corporativa aprobada. Se habilitará TypeScript strict y scripts neutrales para build, lint, unit, integration, e2e y coverage.

Se elige NestJS 11 porque es la versión corporativamente confirmada y evita introducir NestJS 12 sin aprobación. La versión exacta de cada dependencia deberá quedar fijada en el lockfile durante implementación.

### 3. Dependencias corporativas obligatorias

El `package.json` del template incluirá como dependencias de ejecución:

| Paquete | Versión |
|---|---:|
| `@gs/auth` | `0.1.1` |
| `@gs/crypto` | `0.1.1` |
| `@gs/documentdb` | `0.1.1` |
| `@gs/errors` | `0.1.1` |
| `@gs/health` | `0.1.1` |
| `@gs/logger` | `0.1.1` |
| `@gs/proxy` | `0.1.1` |
| `@gs/swagger` | `0.1.2` |
| `@gs/utils` | `0.1.1` |
| `@gsa/decorators` | `~0.0.7` |

También se fijarán versiones compatibles de NestJS 11, TypeScript, `reflect-metadata`, `class-validator`, `class-transformer`, RxJS y Mongoose 9.

El `.npmrc` utilizará el repositorio privado corporativo **AWS CodeArtifactory** sin credenciales en texto plano. Las credenciales se resolverán desde el entorno autorizado por AWS. `pnpm.overrides` fijará `@gs/utils` en `0.1.1` para impedir que dependencias transitivas introduzcan otra versión.

`@gs/auth` y `@gsa/decorators` se instalarán aunque el primer módulo de catálogos no los importe directamente; esto mantiene la base alineada con el servicio corporativo y permite activar sus capacidades posteriormente.

### 4. Integración de módulos corporativos

- **Logging:** `WinstonModule`, `loggerConfig` y `loggerHttpConfig`, encapsulados por el adaptador de logging del servicio. La redacción central se ejecutará antes de todos los transports.
- **Health:** `HealthModule` como módulo de salud corporativo, con liveness/readiness y pruebas de integración.
- **Errores:** `@gs/errors` como mecanismo estándar de excepciones HTTP y filtro compatible con `ResponseApi`.
- **Swagger:** `@gs/swagger` integrado con `SwaggerModule`, utilizando sus decoradores, constantes y esquemas corporativos de respuestas.
- **Proxy:** `ProxyModule` y `ProxyAuthGuard` para validar el JWT del proxy en las rutas protegidas. El servicio no emitirá credenciales de consumidores.
- **Crypto:** `CryptoModule` y soporte de `UseEncryptionV2`/`EncryptionMethod` para los endpoints cuyo contrato lo requiera. El cifrado y la obtención de llaves se mantendrán fuera del dominio.
- **DocumentDB:** `@gs/documentdb` como adaptador/configuración seleccionable entre MongoDB local y Amazon DocumentDB.

### 5. Mongoose 9 detrás de puertos y mappers

Los schemas Mongoose 9 y modelos de persistencia vivirán únicamente en `infrastructure`. Los casos de uso dependerán de interfaces de repositorio y los mappers separarán documentos de entidades o DTOs. La configuración de dependencias fijará la rama 9.x y su lockfile.

La implementación deberá considerar el soporte de Node.js requerido por Mongoose 9, el tipado más estricto de filtros de consulta y los cambios de comportamiento documentados para pipelines de actualización y validadores.

No se compartirán modelos de dominio entre bounded contexts. Las colecciones de `CatalogosDivisas` y `TerminalFinanciero` se tratarán como ownership separado hasta que los responsables lo confirmen.

### 6. Contrato legacy como fuente de compatibilidad

`docs/api-portal-divisas.yaml` será la línea base contractual junto con el comportamiento real de controllers, servicios y pruebas Java. Se verificará cada endpoint contra ambos artefactos porque el Swagger 2.0 puede contener divergencias.

La API NestJS utilizará el base path `/portal-divisas/configuraciones`, conservará las rutas relativas aprobadas, el formato `ResponseApi`, los códigos HTTP, el folio y el comportamiento de ausencia de información. El documento OpenAPI generado por NestJS se validará mediante comparación contractual; el cambio de base path quedará documentado y aprobado.

### 7. Lecturas antes que escrituras multi-colección

La primera entrega priorizará consultas de catálogos. Las operaciones de alta o actualización de divisas se habilitarán después de documentar:

- invariantes de `catMonedas`;
- propagación a regiones y segmentos;
- bitácora;
- idempotencia;
- comportamiento ante fallos parciales;
- compatibilidad real del motor objetivo.

Como el código Java realiza compensaciones manuales y no se observó una transacción explícita, no se asumirá que Mongoose 9 o DocumentDB proporcionen equivalencia. La escritura se implementará mediante un caso de uso orquestador y puertos separados, con transacción o compensación solo después de validación en el entorno autorizado por AWS.

### 8. Identidad mediante adaptador corporativo

El template definirá un puerto de identidad que transforme la credencial corporativa en un principal tipado. Guards o middleware de entrada aplicarán autenticación y autorización, pero el dominio no conocerá NestJS ni el proveedor.

No se implementará fallback permisivo. Las rutas protegidas fallarán cerradas si el adaptador no está configurado. El contrato final de emisor, audiencia, firma, JWKS, roles, scopes y headers de contexto será aportado por Seguridad/APIGEE antes de integrar endpoints protegidos.

### 9. Logging corporativo con redacción global

Se conservará `@gs/logger@0.1.1` mediante un adaptador para evitar que el resto de la aplicación dependa directamente de Winston o de la configuración concreta del paquete. La integración concreta utilizará `WinstonModule`, `loggerConfig` y `loggerHttpConfig`.

La redacción se ejecutará antes de cualquier transporte y cubrirá headers, body, query, errores y excepciones. Las listas del paquete corporativo serán complementarias, no el único control.

Las pruebas deberán demostrar que tokens, contraseñas, credenciales, datos bancarios y PII nunca aparecen en los logs.

### 10. Configuración de entorno y secretos

El repositorio incluirá `.env.example` sin valores reales para variables de proxy, cifrado, DocumentDB, MongoDB local, AWS/S3 y logging. Las variables de llaves, tokens, contraseñas, URLs privadas y credenciales deberán aparecer únicamente como nombres y placeholders no sensibles.

La selección del backend de persistencia será explícita mediante configuración validada. El template no incluirá certificados, llaves privadas ni credenciales de AWS CodeArtifactory, proxy, AWS o DocumentDB.

### 11. Pruebas por niveles

- **Unitarias:** casos de uso, reglas, mappers y redacción.
- **Integración:** Mongoose 9 contra una base local controlada y adaptadores externos simulados.
- **E2E:** rutas, validación, respuestas y health checks mediante HTTP.
- **Contrato:** comparación de rutas y payloads contra Swagger 2.0 y fixtures Java anonimizados.
- **Seguridad:** autenticación del proxy, ausencia, expiración, firma, emisor/audiencia, permisos, headers manipulados y cifrado cuando el contrato corporativo esté disponible.
- **Corporativas:** health check, `ProxyAuthGuard`, `UseEncryptionV2`, errores estándar y generación de Swagger.

Vitest será el runner aprobado. La cobertura objetivo será 80 %, sujeta a confirmación de la política corporativa.

## Risks / Trade-offs

- **[Contrato Swagger divergente del runtime Java]** → comparar OpenAPI con controllers y pruebas antes de fijar DTOs NestJS; registrar cada diferencia y obtener aprobación.
- **[Alta de divisa deja escrituras parciales]** → comenzar por lecturas, aislar la escritura detrás de un caso de uso, validar transacciones/compensaciones y mantener rollback hacia Java.
- **[Ownership dividido entre dos bases]** → matriz de ownership por colección y módulos separados; prohibir acceso directo a colecciones ajenas sin puerto aprobado.
- **[Paquetes corporativos incompatibles entre sí o con NestJS 11/Node 26]** → instalar versiones fijadas, aplicar el override de `@gs/utils`, validar peer/transitive dependencies y ejecutar pruebas de integración del template.
- **[AWS CodeArtifactory expone credenciales]** → `.npmrc` con variables de entorno, revisión de secretos, rotación de cualquier credencial existente y prohibición de valores reales en commits.
- **[Redacción incompleta de información sensible]** → sanitizador central antes de transports, pruebas con headers/body/errors y revisión de Seguridad.
- **[Proxy o cifrado configurados de forma incompleta]** → validar `ProxyAuthGuard`, `CryptoModule`, `UseEncryptionV2`, `EncryptionMethod` y variables de acceso antes de habilitar rutas protegidas.
- **[Identidad corporativa aún desconocida]** → puerto estable y adaptador reemplazable; no habilitar rutas protegidas con fallback.
- **[CI/CD no pertenece al equipo]** → entregar scripts y documentación de integración, sin crear pipelines ni asumir una plataforma.
- **[Mongoose 9 introduce incompatibilidades de tipado o consultas]** → validar filtros, pipelines, validadores de actualización, índices y consultas contra fixtures antes de integrar cada repositorio.
- **[DocumentDB presenta diferencias con MongoDB local]** → registrar consultas e índices compatibles y ejecutar validación en el entorno AWS administrado antes del cutover.
- **[Migrar todos los recursos como un solo servicio aumenta el acoplamiento]** → tratar los 22 recursos como alcance de assessment, pero extraer por ownership y capacidad.

## Migration Plan

1. Congelar la matriz endpoint → controller → servicio → DAO → colección para los 22 recursos.
2. Confirmar ownership con Divisas y Terminal Financiero.
3. Reconciliar Swagger 2.0, código Java y pruebas.
4. Validar acceso a AWS CodeArtifactory, instalar el conjunto corporativo y verificar el lockfile/override.
5. Construir el template NestJS con todos los módulos corporativos y `.env.example` sin secretos.
6. Ejecutar pruebas de integración de health, proxy, cifrado, errores y Swagger.
7. Implementar primero un endpoint de lectura de referencia y sus pruebas de paridad.
8. Ejecutar pruebas de contrato, seguridad y datos en paralelo contra Java.
9. Desplegar NestJS en modo controlado sin retirar Java.
10. Comparar respuestas, errores y latencia.
11. Desviar tráfico gradualmente por endpoint o capacidad.
12. Ante regresión, enrutar nuevamente al endpoint Java y conservar evidencias.
13. Migrar escrituras de divisas solo después de aprobar consistencia y rollback.
14. Retirar código Java únicamente cuando no existan consumidores activos y la aprobación corresponda.

## Open Questions

- ¿Cuál es el endpoint HTTPS y mecanismo de autenticación definitivo de AWS CodeArtifactory?
- ¿Cuáles son las versiones corporativas exactas de NestJS core, TypeScript, `class-validator`, `class-transformer`, RxJS y `reflect-metadata` que acompañarán al conjunto `@gs/*`?
- ¿Qué variables exactas utilizará `@gs/documentdb` para seleccionar MongoDB local frente a Amazon DocumentDB?
- ¿Qué variables y transports AWS/S3 de logging deben documentarse en `.env.example` según el servicio base?
- ¿Cuál es la imagen base Docker aprobada por CLOUD?
- ¿Cuál es la regla corporativa final para cobertura, SonarQube y Checkmarx?
