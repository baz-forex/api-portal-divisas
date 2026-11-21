# Proposal

## Why

El monolito Java concentra los catálogos de `CatalogosDivisas`, `TerminalFinanciero` y recursos de configuración operativa en controllers, servicios y DAOs compartidos. Esto dificulta extraer capacidades sin perder la compatibilidad de las rutas, el contrato `ResponseApi`, las reglas de seguridad y las actualizaciones coordinadas de divisas.

La migración se propone ahora para establecer una base NestJS 11 soportada por la organización, caracterizar los contratos legacy y preparar una extracción progresiva de catálogos sobre el template corporativo completo, con Mongoose 9.x, los módulos `@gs/*`/`@gsa/*` aprobados, Docker local y Vitest, sin modificar todavía el runtime Java ni la infraestructura AWS.

## What Changes

- Definir el alcance y ownership de los 22 recursos catalogados en `docs/BASE_CATALOGS.md`, distinguiendo:
  - catálogos de referencia en `CatalogosDivisas`;
  - catálogos de Terminal Financiero;
  - recursos de configuración y operación que no deben extraerse automáticamente como catálogos.
- Caracterizar y preservar las rutas y métodos documentados en `docs/api-portal-divisas.yaml`, incluidos los endpoints de lectura, alta y actualización de divisas.
- Preservar el contrato observable `ResponseApi`, códigos HTTP, validaciones y comportamiento de resultados vacíos.
- Documentar la propagación coordinada del alta de divisa hacia límites, regiones, segmentos y bitácora antes de migrar operaciones de escritura.
- Definir una base técnica NestJS 11.x, Node.js 26.12.1, pnpm 11.5.3 (versión fijada por la imagen corporativa), TypeScript strict, Mongoose 9.x, Vitest, ESLint, OpenAPI y Docker local.
- Incluir como dependencias de ejecución las versiones corporativas de `@gs/auth`, `@gs/crypto`, `@gs/documentdb`, `@gs/errors`, `@gs/health`, `@gs/logger`, `@gs/proxy`, `@gs/swagger`, `@gs/utils` y `@gsa/decorators`.
- Configurar el repositorio privado de dependencias **AWS CodeArtifactory** mediante `.npmrc` sin credenciales versionadas y fijar `@gs/utils` en `0.1.1` mediante `pnpm.overrides`.
- Integrar `@gs/logger` mediante `WinstonModule`, `loggerConfig` y `loggerHttpConfig`, manteniendo redacción de secretos y PII.
- Integrar `HealthModule`, `CryptoModule`, `ProxyModule`, `ProxyAuthGuard`, el mecanismo estándar de errores y los esquemas corporativos de Swagger.
- Mantener soporte para `UseEncryptionV2` y `EncryptionMethod`, incluyendo variables de proxy, cifrado, DocumentDB, AWS/S3 y logging en `.env.example`.
- Permitir seleccionar configuración de persistencia entre MongoDB local y Amazon DocumentDB sin incluir infraestructura AWS en el repositorio.
- Incluir `@gs/auth` y `@gsa/decorators` aunque el primer módulo de catálogos no los utilice directamente.
- Crear pruebas de integración para health check, autenticación del proxy, cifrado, errores estandarizados y generación de Swagger.
- Mantener la autenticación detrás de un puerto/adaptador para el sistema corporativo propio; no introducir un proveedor, algoritmo o bypass nuevo.
- Definir pruebas de paridad funcional, contractual, de seguridad y de datos para la coexistencia Java/NestJS.
- Mantener fuera de alcance la infraestructura AWS/DocumentDB, la plataforma CI/CD y cualquier cambio de producción; solo se documentarán interfaces y evidencias requeridas.

## Capabilities

### New Capabilities

- `catalogs/reference-data`: consulta y administración compatible de países, estados, divisas, identificaciones, estatus de cotización, tipos de operación, tipos de cliente y catálogos de campañas almacenados en `CatalogosDivisas`.
- `catalogs/terminal-reference-data`: consulta compatible de sucursales, regiones y segmentos de `TerminalFinanciero`, incluyendo sus relaciones con divisas y operaciones TEF.
- `catalogs/operational-configuration`: caracterización y extracción controlada de ubicación, segmentación, diccionario QR, perfiles de rentabilidad y bloqueos operativos, con ownership explícito antes de implementar.
- `platform/nestjs-service-foundation`: configuración validada, Mongoose 9 mediante puertos, módulos corporativos de runtime, errores, health checks, Docker local, OpenAPI, testing y logging estructurado.
- `security/corporate-identity-adapter`: validación y propagación del principal corporativo mediante un adaptador pendiente de contrato, preservando 401/403, roles, scopes y headers de contexto sin asumir el proveedor.

### Modified Capabilities

No existen especificaciones registradas en el proyecto para modificar.

## Impact

- **Código legacy analizado:** `CatalogosController`, `CatalogosServiceImpl`, `CatalogosDao`, DAOs TEF de regiones/segmentos, bitácora y pruebas asociadas.
- **APIs:** rutas bajo `/portal-divisas/configuraciones/api/catalogos`, `/api/tef`, `/api/core/configuraciones` y `/api/core/clientes/bloqueos`, según ownership confirmado.
- **Datos:** colecciones de `CatalogosDivisas` y `TerminalFinanciero`; la extracción debe evitar ownership compartido no declarado.
- **Dependencias de ejecución:**
  - `@gs/auth@0.1.1`
  - `@gs/crypto@0.1.1`
  - `@gs/documentdb@0.1.1`
  - `@gs/errors@0.1.1`
  - `@gs/health@0.1.1`
  - `@gs/logger@0.1.1`
  - `@gs/proxy@0.1.1`
  - `@gs/swagger@0.1.2`
  - `@gs/utils@0.1.1`
  - `@gsa/decorators@~0.0.7`
  - NestJS 11, TypeScript, `reflect-metadata`, `class-validator`, `class-transformer`, RxJS y Mongoose 9 compatibles con las versiones corporativas.
- **Seguridad:** aprobación del equipo corporativo de Seguridad de la Información/APIGEE, responsables de controles, arquitectura, propietario de API y consumidores afectados.
- **Operación:** Docker local y scripts neutrales de calidad; no se crearán pipelines ni IaC.
- **Riesgos:** contrato Swagger 2.0 posiblemente divergente del código, alta de divisa multi-colección sin transacción explícita, compatibilidad entre paquetes privados/Node 26/Nest 11/Mongoose 9, configuración insegura de AWS CodeArtifactory y diferencias de configuración entre MongoDB local y DocumentDB.
