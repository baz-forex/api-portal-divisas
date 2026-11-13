# AGENTS.md — Política de arquitectura y migración Java → NestJS

**Versión:** 1.1.0  
**Última actualización:** 2026-11-13  
**Propósito:** instrucciones operativas para OpenCode/OpenSpec durante el assessment, diseño y migración incremental de una API monolítica Java/Spring Boot/Spring Security/MongoDB a microservicios NestJS/TypeScript/MongoDB o Amazon DocumentDB.

> **Documento rector:** `docs/ARQUITECTURA_MIGRACION_BACKEND_JAVA_A_NESTJS_v1_1.md` (v1.1.0). Si falta, informa y evita inventar decisiones. No sobrescribas políticas vigentes de otro repositorio sin conciliación. Este documento es una plantilla específica para el proyecto de migración, no una política universal de toda la organización.

## 1. Alcance y autoridad

- **Incluido:** assessment del monolito, DDD, Hexagonal/Clean Architecture, APIs, datos, mensajería, resiliencia, pruebas, observabilidad, **análisis y migración de Spring Security**, contratos con sistemas externos y documentación.
- **Excluido:** diseño/operación de infraestructura AWS, DevOps, CI/CD, IaC, configuración de cuentas/redes y despliegues; el equipo AWS administra esas capacidades. Documenta interfaces y requisitos de aplicación para coordinarse con él.
- **No asumido:** el proveedor de identidad, tipo de token, topología MongoDB/DocumentDB, librerías privadas, versión de Java/NestJS, ni decisiones corporativas sin evidencia.
- Orden de referencia: instrucciones superiores y políticas corporativas aplicables > ADR y specs aprobadas > documento rector > este AGENTS.md > skills cargadas > patrones observados. Ante contradicción, registra el conflicto y solicita decisión antes de cambios de comportamiento.

## 2. Objetivos arquitectónicos

1. Analiza funcionalidad y contratos existentes antes de traducir clases.
2. Identifica capacidades de negocio, bounded contexts y data ownership; no dividas servicios por controller, tabla ni entidad aislada.
3. Aplica DDD estratégico/táctico cuando agregue valor; Hexagonal + Clean Architecture como una única regla de dependencias hacia el dominio; SOLID de forma pragmática.
4. Mantén Domain libre de imports de NestJS, Mongoose, AWS SDK, HTTP y frameworks de seguridad.
5. Usa puertos internos y adaptadores de infraestructura para persistencia, colas, sistemas externos y mecanismos de identidad.
6. Evita consultas directas a datos propiedad de otro servicio y librerías corporativas que compartan modelos de dominio entre bounded contexts.
7. Conserva compatibilidad de contratos públicos y resultados críticos; propone cambios deliberados mediante ADR.
8. Migra gradualmente con Strangler Fig, Anti-Corruption Layer, pruebas de paridad y estrategia de reversión.

## 3. Stack objetivo: referencia, no imposición

- Node.js LTS aprobado; NestJS + TypeScript `strict`; gestor pnpm cuando esté normalizado.
- REST + OpenAPI; validación de entrada con `class-validator` o Zod según estándar aprobado.
- MongoDB / Amazon DocumentDB con Mongoose o driver oficial bajo puertos; **probar consultas, índices, transacciones y change streams contra la versión real de DocumentDB**.
- SQS/EventBridge con AWS SDK v3 **solo si aplica y está aprobado**; idempotencia, DLQ, backoff y Outbox según requisitos.
- Vitest/Jest, pruebas de integración/contrato/e2e y SonarQube según estándares del repositorio.
- OpenTelemetry, logging estructurado y correlation/trace IDs, sin fuga de secretos o datos sensibles.
- Librerías privadas versionadas para capacidades transversales aprobadas; no duplicar capacidades corporativas disponibles.

## 4. Estructura por capacidad (NestJS)

Estructura preferida en cada módulo de negocio:

```text
src/modules/<capability>/
  domain/           # entities, value-objects, aggregates, domain events, ports de dominio
  application/      # use-cases, application ports, DTO internos
  infrastructure/   # mongoose/documentdb, messaging, external-service/security adapters
  presentation/     # controllers, HTTP DTO, guards y filtros
  <capability>.module.ts  # composición de dependencias
```

- `domain` no importa frameworks.
- `application` depende del dominio y abstracciones, nunca de adaptadores concretos.
- `infrastructure` implementa puertos; `presentation` traduce protocolos a casos de uso.
- Vincula puertos con tokens concretos de NestJS: las interfaces TypeScript no existen en runtime.
- Security Guards y autenticación técnica son preocupaciones de entrada/infraestructura; políticas puras de autorización de negocio pueden vivir en dominio/aplicación mediante abstracciones.

## 5. Spring Security: assessment y migración OBLIGATORIOS

La migración **sí incluye autenticación y autorización**. No repitas la exclusión antigua. El objetivo es preservar el comportamiento y los controles de seguridad vigentes, integrando el proveedor corporativo actual cuando proceda; cambiar IdP o mecanismo criptográfico exige aprobación.

### 5.1 Inspeccionar el Java AS-IS

- Identifica versión de Spring Security, `SecurityFilterChain`/configuración legacy, request matchers y orden, filtros, interceptores y políticas por método.
- Inventaría `AuthenticationProvider`, `UserDetailsService`, validadores de token, sesiones, cookies, JWT, OAuth2, OIDC, API keys y componentes propios **solo si existen**.
- Determina IdP real, emisor, audiencia, JWKS/certificados, firma, expiración, scopes, roles/claims, revocación y propagación de contexto.
- Identifica autorización por operación, recurso, usuario, tenant y reglas ABAC/RBAC; registra 401/403, responses y excepciones.
- Revisa CORS, CSRF, manejo de estado/sesión, auditoría, logging seguro y cualquier dependencia con gateway.
- En cada hallazgo cita archivo, clase, método o test. Separa hechos, hipótesis y preguntas.

### 5.2 Diseño TO-BE en NestJS

- Mapea filtro/cadena de seguridad a middleware, guards o interceptors según responsabilidad y orden.
- Mapea autenticación a estrategias/adaptadores de validación y construcción de principal tipado; **no presupongas Passport, Cognito ni JWT si el código no lo demuestra**.
- Mapea `@PreAuthorize`, roles/scopes y permisos a guards/policies por recurso; evita autorización basada solo en claims no verificados.
- Define propagación segura de identidad a casos de uso, llamadas internas y eventos sin exponer credenciales.
- Mantén contratos observables de 401/403 y errores; documenta excepciones acordadas mediante ADR.
- No desactives CSRF, CORS u otros controles sin evidenciar que el modelo de autenticación lo permite y obtener aprobación.
- La migración debe respetar mínimos privilegios y límites de confianza; coordina con la plataforma, pero no modifiques su infraestructura.

### 5.3 Pruebas y criterios de aceptación

- Define matriz `endpoint/operación -> regla Java -> control NestJS -> escenario de prueba -> evidencia`.
- Prueba acceso público, ausencia de credenciales, credenciales inválidas/expiradas, emisor/audiencia incorrectos, firmas manipuladas, roles/scopes insuficientes y casos de autorización por recurso/tenant cuando apliquen.
- Verifica no regresión de permisos, ausencia de bypass, paridad 401/403, tratamiento de errores y propagación segura de identidad.
- Evita secretos reales, tokens de producción y datos personales en fixtures, logs, ejemplos y documentación.
- Sin evidencias de equivalencia de seguridad y aprobación requerida, no propongas un cutover definitivo.

### 5.4 Artefactos exigidos

```text
docs/assessment/spring-security-inventory.md
docs/architecture/security-as-is.md
docs/architecture/security-to-be.md
docs/migration/security-parity-matrix.md
docs/migration/security-test-plan.md
```

Los ADR documentarán decisiones de IdP, mecanismos de validación, autorización, manejo de sesión y cualquier divergencia respecto al monolito.

## 6. Persistencia y consistencia

- Inventaría colecciones, índices, consultas, agregaciones, transacciones, streams, tipos BSON y dependencias Spring Data.
- Prohíbe dar por hecho que MongoDB y DocumentDB son equivalentes; registra matriz de compatibilidad por versión de motor.
- Define ownership y repositorios con mappers explícitos entre documentos y entidades de dominio.
- No ejecutes migraciones destructivas, scripts sobre datos corporativos ni operaciones de producción sin autorización.
- Exige consistencia e idempotencia para escrituras reintentables y eventos; Outbox únicamente cuando esté justificado y validado para el motor.

## 7. APIs, eventos y resiliencia

- Documenta endpoints con métodos, rutas, validación, request/response, códigos HTTP, consumidores y seguridad aplicable.
- Mantén OpenAPI y contratos versionados; no rompas consumidores sin plan aprobado.
- Prefiere REST para intercambios síncronos y mensajería para necesidades asíncronas justificadas.
- Diseña timeouts, límites de reintento, DLQ y circuit breakers según dependencia; evita reintentos no idempotentes.
- Usa CQRS, Saga y Event Sourcing solo cuando el caso de uso y sus garantías lo requieran.

## 8. Pruebas, calidad y observabilidad

- Caracteriza reglas Java antes de implementar equivalentes TypeScript.
- Incluye unit, integration, contract y e2e sobre flujos críticos, incluyendo pruebas negativas de seguridad.
- Cobertura objetivo sujeta a política corporativa; nunca la uses como sustituto de escenarios significativos.
- No declares que se ejecutaron pruebas, SonarQube o escáneres sin evidencias reales.
- Telemetría correlacionable (traceId/requestId), métricas y logs estructurados; redacta tokens, credenciales y PII.

## 9. Reglas OpenSpec / OpenCode

- **Explore (Plan):** solo inspección, inventarios y hallazgos; incluir security AS-IS. No modificar archivos ni producción.
- **Propose (Plan):** propuesta de arquitectura, specs, design, tasks, ADR candidatos y criterios verificables; incluir security TO-BE y paridad.
- **Apply (Build):** solo ejecutar cambios autorizados en tasks; para la primera iteración de assessment, **solo documentación**, nunca implementación de código ni cambios de seguridad en runtime.
- **Archive (Build):** cerrar cuando estén aprobadas y reconciliadas las especificaciones; no archivar hipótesis como decisiones firmes.
- Detecta qué comandos y skills están instalados antes de invocarlos; no afirmes haber aplicado una skill sin cargarla.
- Antes de editar código, declarar plan, archivos afectados, dependencias y pruebas requeridas.
- Para cada cambio, registrar fuentes de evidencia, riesgos, gaps, aprobaciones pendientes y criterios de rollback.

## 10. Entregables mínimos del assessment

- Arquitectura AS-IS y mapa de dependencias Java.
- Inventario de API, módulos, colecciones, integraciones y reglas de negocio.
- Inventario de Spring Security, matrix de rutas/permisos y dependencias del IdP.
- Mapa DDD de bounded contexts candidatos y ownership de datos.
- Arquitectura TO-BE NestJS y diseño de seguridad correspondiente.
- Matriz Java → NestJS, incluida seguridad y casos de prueba de paridad.
- Matriz MongoDB/DocumentDB, riesgos, ADR candidatos y roadmap Strangler Fig.

## 11. Límites operativos y prohibiciones

- No introducir implementación de autenticación nueva sin evaluar Spring Security y dependencias corporativas existentes.
- No omitir autorización por recurso al migrar un endpoint.
- No cambiar identidad, roles o permisos por conveniencia técnica sin aprobación de seguridad.
- No hacer cambios en CI/CD, IaC, despliegues ni infraestructura AWS; coordinar requisitos con el equipo propietario.
- No asumir acceso a secretos, credenciales o datos productivos; nunca solicitarlos para el diagnóstico ordinario.
- No modificar código ni datos durante Explore/Propose.
- No sobreescribir `AGENTS.md` de un proyecto existente sin merge revisado de sus restricciones específicas.

## 12. Checklist de aceptación para iniciar una extracción

- [ ] Bounded context y propietario funcional identificados.
- [ ] Contratos y escenarios legacy caracterizados.
- [ ] Ownership de datos y compatibilidad DocumentDB evaluados.
- [ ] Spring Security AS-IS y permisos por endpoint documentados.
- [ ] Diseño de autenticación/autorización NestJS revisado por responsables.
- [ ] Pruebas negativas y de paridad de seguridad definidas.
- [ ] Riesgos, NFR y criterios de rollback documentados.
- [ ] Cambios implementables aprobados en OpenSpec.

## 13. Control de cambios

| Versión | Fecha | Cambio |
|---|---|---|
| 1.1.0 | 2026-11-13 | Se incorpora migración de Spring Security, pruebas de paridad y artefactos de seguridad. AWS/DevOps sigue excluido. |
