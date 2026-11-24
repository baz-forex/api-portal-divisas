# Proposal

## Why

El monolito API Portal Divisas concentra siete límites de extracción obligatorios, autenticación, SCIM, cifrado, integraciones externas y múltiples bases MongoDB. Antes de implementar un microservicio NestJS para `Catalogos`, se necesita consolidar evidencia AS-IS, ownership de datos, controles de seguridad, contratos y criterios de paridad.

Este cambio formaliza un assessment documental y prepara una extracción incremental con Strangler Fig, sin alterar el comportamiento del monolito ni introducir código runtime.

## What Changes

- Consolidar el inventario AS-IS de Java 17, Spring Boot, módulos, controllers, services, DAOs, configuraciones, pruebas e integraciones.
- Crear una matriz de endpoints `endpoint -> módulo -> service -> DAO -> colección -> dependencia externa`.
- Documentar los siete bounded contexts obligatorios por prefijo:
  `/api/bitacora`, `/api/catalogos`, `/api/core`, `/api/digital`,
  `/api/siex`, `/api/tef` y `/api/usuarios`.
- Tratar `auth` y `scim` como capacidades adyacentes de seguridad/provisioning que no pueden omitirse del mapa de extracción.
- Documentar Spring Security AS-IS y mapear sus responsabilidades a controles NestJS candidatos.
- Documentar algoritmos, formatos, llaves, compatibilidad histórica y riesgos criptográficos sin revelar secretos.
- Documentar bases, colecciones, índices, agregaciones, operaciones de escritura y riesgos MongoDB/DocumentDB.
- Definir ownership preliminar de datos y Anti-Corruption Layers para la convivencia Java/NestJS.
- Preparar el diseño TO-BE de un microservicio `Catalogos` usando las librerías corporativas aprobadas:
  `@gs/auth`, `@gs/crypto`, `@gs/documentdb`, `@gs/errors`, `@gs/health`,
  `@gs/logger`, `@gs/proxy`, `@gs/swagger`, `@gs/utils` y `@gsa/decorators`.
- Establecer que la implementación del piloto se realizará exclusivamente en el proyecto nuevo
  `/Users/phinder2024/Devs/BAZ/backend/divisas/microservice-dvs-catalogs`, cuyo nombre es
  `microservice-dvs-catalogs`; `api-portal-divisas` conservará únicamente el monolito legacy y su documentación.
- Documentar una secuencia propuesta de extracción para los bounded contexts y capacidades transversales,
  incluyendo seguridad, autenticación, observabilidad, health, errores, criptografía, proxy, Auth y SCIM.
- Definir el roadmap Strangler Fig, el primer piloto, pruebas de paridad, criterios de rollback y aprobaciones pendientes.
- No modificar endpoints, seguridad, persistencia, contratos, infraestructura ni código Java durante esta fase.

## Capabilities

### New Capabilities

No se introducen capacidades runtime en esta fase. El cambio produce únicamente artefactos de assessment y preparación arquitectónica; por ello declara `skip_specs: true`.

### Modified Capabilities

Ninguna. No existen especificaciones publicadas en `openspec/specs/` y no se modifican requisitos funcionales.

## Impact

- **Documentación:** artefactos de propuesta, diseño y tareas en este cambio OpenSpec.
- **Código Java:** solo será analizado; no se modifica.
- **API legacy:** se documentan contratos, diferencias y riesgos; no se cambian rutas ni respuestas.
- **Seguridad:** se documenta el comportamiento actual y el diseño candidato; no se cambia autenticación ni autorización.
- **Datos:** se documenta ownership, acceso y compatibilidad; no se ejecutan migraciones ni escrituras en MongoDB.
- **Futuro microservicio:** `Catalogos` queda preparado como primer piloto en el proyecto independiente `microservice-dvs-catalogs`, condicionado a aprobación de contratos, seguridad, ownership y paridad.
- **Fuera de alcance:** implementación NestJS, cambios de infraestructura AWS, CI/CD, IaC, despliegues, migraciones destructivas y cutover de tráfico.
