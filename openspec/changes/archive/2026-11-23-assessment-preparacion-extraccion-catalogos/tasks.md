# Tasks

## 1. Baseline AS-IS y contratos

- [x] 1.1 Crear `docs/assessment/java-module-inventory.md` con versiones, módulos, capas, controllers, services, DAOs, validaciones, excepciones, pruebas y procesos asíncronos; verificar cada hallazgo con ruta, clase y método.
- [x] 1.2 Crear `docs/assessment/api-inventory.md` con los 280 operations del OpenAPI, los mappings encontrados en Java, request/response, códigos HTTP y diferencias entre contrato y runtime; verificar conteos y rutas mediante scripts de lectura.
- [x] 1.3 Crear `docs/assessment/integration-inventory.md` con Middleware, Apigee, Tesorería, parametría, correo, SCIM y proxy; verificar cada integración contra configuración y clase consumidora sin incluir valores sensibles.
- [x] 1.4 Registrar en `docs/assessment/risks-and-assumptions.md` hechos, hipótesis, conflictos documentales, decisiones pendientes y límites del assessment; verificar que no existan secretos, tokens ni PII.

## 2. Dominio, dependencias y ownership

- [x] 2.1 Crear `docs/architecture/context-map.md` para `/api/bitacora`, `/api/catalogos`, `/api/core`, `/api/digital`, `/api/siex`, `/api/tef` y `/api/usuarios`, incluyendo `auth` y `scim` como capacidades adyacentes; verificar que cada endpoint del inventario tenga un contexto.
- [x] 2.2 Crear `docs/architecture/domain-model.md` con capacidades, agregados candidatos, invariantes evidenciadas y reglas aún no confirmadas; verificar separación explícita entre hechos y hipótesis.
- [x] 2.3 Crear `docs/architecture/dependency-map.md` con el flujo endpoint → módulo → service → DAO → colección → integración externa; verificar que los accesos cruzados de Core, Digital y TEF estén identificados.
- [x] 2.4 Crear `docs/architecture/data-ownership.md` con propietario de escritura, dependencias de solo lectura, colecciones legacy compartidas y ACL propuesta; verificar que no se proponga dual-write sin reconciliación.
- [x] 2.5 Crear `docs/assessment/mongodb-documentdb-compatibility.md` con bases, colecciones, esquemas, índices, agregaciones, array filters, bulk writes, transacciones ausentes y operaciones pendientes de validar; verificar que no se presenten pruebas DocumentDB no ejecutadas como evidencia.

## 3. Seguridad y criptografía

- [x] 3.1 Crear `docs/assessment/spring-security-inventory.md` con filtros, endpoints públicos, roles, JWT, principal, 401/403, CORS, CSRF, sesiones y pruebas existentes; verificar cobertura de `WebSecurityConfig`, `AuthTokenFilter`, `JwtUtils` y handlers.
- [x] 3.2 Crear `docs/architecture/security-as-is.md` con el flujo de confianza actual y sus riesgos; verificar que incluya la discrepancia OpenAPI/runtime de `ServiceProviderConfig` y los permisos por UI sin enforcement endpoint evidenciado.
- [x] 3.3 Crear `docs/architecture/security-to-be.md` con el mapeo candidato a `@gs/auth`, `@gs/proxy`, guards, principal tipado y filtros de error, sin elegir un IdP no demostrado; verificar que no cambie silenciosamente el contrato de identidad.
- [x] 3.4 Crear `docs/migration/security-parity-matrix.md` con endpoint, regla Java, control NestJS, escenario permitido/denegado y evidencia requerida; verificar escenarios de token ausente, inválido, expirado, manipulado, rol insuficiente y recurso no autorizado.
- [x] 3.5 Crear `docs/migration/security-test-plan.md` con pruebas negativas, 401/403, CORS/CSRF, propagación de identidad y aislamiento de recursos; verificar que no use tokens reales.
- [x] 3.6 Crear `docs/assessment/cryptographic-inventory.md` con JWT RS256, BCrypt, AES-CBC/HMAC, AES-GCM, RSA-OAEP, formatos de llaves, logs peligrosos y compatibilidad histórica; verificar que todos los valores sensibles estén omitidos.

## 4. Diseño TO-BE del piloto Catalogos

- [x] 4.1 Crear `docs/architecture/target-state.md` para la convivencia monolito–Catalogos NestJS con arquitectura hexagonal, ACL y ownership; verificar que `domain` no dependa de NestJS, Mongoose, AWS SDK ni DTO HTTP.
- [x] 4.2 Crear `docs/architecture/catalogos-design.md` con módulos, casos de uso, puertos, adaptadores, mappers y límites de lectura/escritura; verificar que use `@gs/documentdb`, `@gs/errors`, `@gs/health`, `@gs/logger`, `@gs/swagger`, `@gs/utils` y `@gsa/decorators` en las capas adecuadas.
- [x] 4.3 Documentar en `docs/contracts/catalogos-legacy-mapping.md` el mapeo de rutas, DTO, errores y colecciones del piloto sin modificar `docs/api-portal-divisas_v1.0.yaml`; verificar compatibilidad de casing, rutas históricas y respuestas.
- [x] 4.4 Documentar en `docs/adr/ADR-001-bounded-contexts.md`, `docs/adr/ADR-005-mongodb-documentdb.md`, `docs/adr/ADR-008-data-ownership.md` y `docs/adr/ADR-security-authentication-authorization.md` las decisiones que requieran aprobación; verificar que las divergencias permanezcan como decisiones explícitas.

## 5. Migración, paridad y rollback

- [x] 5.1 Crear `docs/migration/roadmap.md` con fases Baseline, ownership, ACL, implementación futura, shadow, cutover y retiro; verificar que cada fase tenga entrada, salida y dependencia.
- [x] 5.2 Crear `docs/migration/pilot-selection.md` justificando Catalogos frente a Bitacora y TEF; verificar que incluya riesgos de colecciones compartidas y criterios de entrada.
- [x] 5.3 Crear `docs/migration/java-nestjs-matrix.md` con equivalencias Controller/Service/DAO/DTO/excepciones/seguridad y precauciones; verificar trazabilidad hacia clases Java reales.
- [x] 5.4 Crear `docs/migration/parity-test-plan.md` con unit, integration, contract, e2e, persistencia, rendimiento y comparación legacy/NestJS; verificar que incluya resultados funcionales, errores y efectos sobre datos.
- [x] 5.5 Crear `docs/migration/cutover-and-rollback-criteria.md` con ownership único, umbrales de diferencia, rollback de routing, reconciliación y condiciones de retiro; verificar que no dependa de dual-write no aprobado.
- [x] 5.6 Crear `docs/migration/domain-extraction-sequence.md` con la secuencia propuesta y justificada de bounded contexts, incluyendo capacidades transversales de seguridad, Auth, SCIM, observabilidad, health, errores, proxy, criptografía y persistencia.

## 6. Verificación documental

- [x] 6.1 Ejecutar una revisión cruzada de inventarios, matrices, diseño, riesgos y roadmap y registrar la evidencia en `docs/assessment/cross-review.md`; verificar que no existan endpoints, colecciones o integraciones sin clasificación.
- [x] 6.2 Revisar todos los artefactos para eliminar secretos, tokens, claves, valores de `.env`, PII y documentos Mongo y registrar la evidencia en `docs/assessment/documentation-safety-review.md`; verificar mediante búsqueda de patrones sensibles.
- [x] 6.3 Ejecutar `openspec validate --changes` y `openspec status --change assessment-preparacion-extraccion-catalogos`; verificar que el cambio quede completo con `specs` deliberadamente omitido por `skip_specs: true`.
- [x] 6.4 Reejecutar la revisión cruzada, actualizar `docs/assessment/cross-review.md` incluyendo `docs/migration/domain-extraction-sequence.md` y verificar su coherencia con context-map, pilot-selection, roadmap y security-to-be.
- [x] 6.5 Reejecutar la revisión de seguridad documental, actualizar `docs/assessment/documentation-safety-review.md` incluyendo el nuevo artefacto y verificar que no contenga secretos, tokens, PII ni documentos Mongo.

## Workflow follow-up

- Revisar los artefactos con arquitectura, seguridad, datos y responsables funcionales.
- Crear un cambio OpenSpec separado para implementar Catalogos únicamente después de aprobar el assessment.
- Ejecutar `/opsx-apply` solo mediante una solicitud posterior y explícita.
