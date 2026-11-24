# Design

## Context

El estado actual y la motivación están descritos en `proposal.md`. El diseño se apoya en la evidencia levantada en:

- `pom.xml`, `README.md` y `src/main/java/`.
- `docs/api-portal-divisas_v1.0.yaml`.
- `docs/BASE_ARCHITECTURE_SPECIFICATION_v1_0.md`.
- `AGENTS.md`.
- Configuración de `properties/` y nombres de variables de `.env`, sin copiar valores sensibles.
- Metadatos de MongoDB local en `localhost:27019`.

El monolito usa Spring Boot 2.6.7, Java 17, MongoDB Driver Sync 5.6.0, JWT propio, DAOs nativos y seis bases de negocio. La extracción debe conservar contratos observables y convivir con el monolito. Los siete prefijos de endpoint son límites de extracción obligatorios, pero `/api/core` y `/api/tef` contienen subcapacidades con cohesión interna desigual.

## Goals / Non-Goals

**Goals:**

- Producir un baseline documental trazable desde endpoint hasta persistencia y dependencia externa.
- Preparar `Catalogos` como primer piloto, manteniendo inicialmente el contrato legacy.
- Separar hechos, hipótesis, riesgos, preguntas abiertas y decisiones pendientes.
- Definir ownership lógico de datos, ACL y límites de confianza.
- Diseñar el mapa Java → NestJS sin introducir dependencias de framework en el dominio.
- Incorporar seguridad, cifrado, observabilidad, errores y DocumentDB desde la fase de assessment.
- Definir paridad funcional, paridad de seguridad, rollback y criterios de cutover.

**Non-Goals:**

- Implementar NestJS, guards, repositorios, esquemas Mongoose o adaptadores.
- Cambiar el proveedor de identidad, algoritmo criptográfico, permisos o contrato HTTP.
- Migrar datos, activar dual-write o enrutar tráfico real.
- Ejecutar migraciones, despliegues, cambios AWS, CI/CD o infraestructura.
- Declarar compatibilidad DocumentDB como validada sin pruebas contra el motor objetivo.

## Decisions

### 1. Assessment documental antes de implementación

Se generarán artefactos de inventario y diseño antes de cualquier cambio runtime. Esto permite detectar divergencias entre código, OpenAPI, configuración y MongoDB.

**Alternativas consideradas:**

- Implementar directamente `Catalogos`: descartado por riesgo de romper consumidores y por ownership compartido.
- Crear primero un template NestJS: pospuesto hasta fijar decisiones de seguridad, errores, persistencia y observabilidad.

### 2. Catalogos como primer piloto

`/api/catalogos` es el primer piloto porque tiene 14 operaciones, modelos relativamente acotados y colecciones de catálogo identificables. Sin embargo, algunas colecciones son leídas o modificadas por Core y TEF; por ello la primera fase debe definir ownership y contratos de lectura antes de extraer escrituras.

**Alternativas consideradas:**

- `Bitacora`: transversal y fácil de consumir, pero su extracción temprana introduce una dependencia síncrona o un contrato de eventos para todos los módulos.
- `TEF`: descartado como primer piloto por su alta cohesión interna, estructuras anidadas, escrituras multi-colección y dependencias externas.

### 3. Conservación del contrato legacy mediante ACL

La implementación de este diseño no se realizará dentro de `api-portal-divisas`. El piloto se desarrollará exclusivamente en el proyecto independiente:

```text
/Users/phinder2024/Devs/BAZ/backend/divisas/microservice-dvs-catalogs
```

El repositorio actual se utiliza como fuente legacy y de assessment; no debe almacenar módulos NestJS, casos de uso, dominio, adaptadores, repositorios, DTOs runtime, configuración ni pruebas del nuevo microservicio.

El futuro servicio `Catalogos`, dentro de `microservice-dvs-catalogs`, expondrá inicialmente el contrato existente y traducirá modelos legacy a modelos internos mediante un Anti-Corruption Layer. No se copiarán directamente entidades Java ni documentos Mongo al dominio NestJS.

Flujo previsto:

```text
Consumidor
   |
Contrato legacy / gateway
   |
   microservice-dvs-catalogs
   |-- presentation: DTO, validación, errores
   |-- application: casos de uso
   |-- domain: reglas y modelos propios
   |-- infrastructure: @gs/documentdb y adaptadores
   |
Colecciones propiedad de Catalogos
```

### 4. Ownership explícito y extracción progresiva de escrituras

Cada colección tendrá un propietario de escritura. Durante la convivencia:

- Las lecturas podrán mantenerse en el monolito mediante contrato o ACL.
- No se permitirá dual-write sin mecanismo de reconciliación aprobado.
- Las escrituras se transferirán solo después de paridad y baseline.
- Las colecciones compartidas se clasificarán como `owned`, `read-only dependency`, `legacy shared` o `unresolved`.

### 5. Librerías corporativas como adaptadores transversales

El diseño TO-BE reservará responsabilidades para:

- `@gs/auth`: autenticación, autorización, principal y guards aprobados.
- `@gs/crypto`: compatibilidad criptográfica y protección de payloads.
- `@gs/documentdb`: conexión local MongoDB y destino DocumentDB.
- `@gs/errors`: errores HTTP y filtros corporativos.
- `@gs/health`: health checks.
- `@gs/logger`: logs estructurados, correlación y sanitización.
- `@gs/proxy`: autenticación y comunicación vía proxy corporativo.
- `@gs/swagger`: documentación OpenAPI.
- `@gs/utils` y `@gsa/decorators`: utilidades y validación transversal.

El dominio no importará NestJS, Mongoose, AWS SDK ni estas librerías directamente; la composición ocurrirá en infraestructura y módulos.

### 6. Seguridad por paridad, no por sustitución

El baseline conservará el comportamiento demostrado de `WebSecurityConfig`, `AuthTokenFilter`, `JwtUtils`, handlers 401/403 y generación de client secrets. Las divergencias, como el uso de IP/User-Agent, CORS wildcard, CSRF deshabilitado y ausencia de autorización por operación, se documentarán como riesgos y decisiones de seguridad, no se corregirán silenciosamente.

### 7. Evidencia MongoDB sin exponer datos

El assessment usará nombres de bases, colecciones, conteos, esquemas inferidos, índices y operaciones encontradas en el código. No se incorporarán documentos, valores de clientes, tokens, secretos ni PII en los artefactos.

### 8. Roadmap Strangler Fig con reversión

La migración se dividirá en:

1. Baseline y reconciliación de contrato.
2. Ownership y ACL de Catalogos.
3. Implementación futura del servicio.
4. Pruebas de paridad shadow/contract/e2e.
5. Transferencia gradual de rutas.
6. Reconciliación y retiro posterior.

Rollback inicial: devolver el enrutamiento al monolito y conservar el propietario de escritura legacy hasta completar criterios de cierre.

### 9. Secuencia propuesta de extracción

La secuencia completa se documenta en `docs/migration/domain-extraction-sequence.md`. Se establece una ola 0 de capacidades transversales —seguridad, autenticación/autorización, observabilidad, health, errores, proxy, criptografía y persistencia— antes de extraer bounded contexts funcionales.

La propuesta funcional es: `Catalogos`, `Bitacora`, `SIEX`, `Digital`, `Core` dividido por capacidades, `Usuarios`, `SCIM` y finalmente `TEF`. `Auth` se trata como habilitador transversal y `SCIM` como provisioning coordinado con IAM, no como una dependencia que pueda omitirse del orden. La secuencia es revisable si cambian ownership, contratos, dependencias o decisiones corporativas de IAM.

## Risks / Trade-offs

- **[Colecciones compartidas]** `catMonedas` y catálogos relacionados son usados fuera de `/api/catalogos` → clasificar accesos y prohibir extracción de escritura hasta definir ownership.
- **[Contrato divergente]** El YAML documenta 280 operaciones y el código contiene más mappings → generar diff de rutas antes de publicar el contrato del piloto.
- **[Seguridad heredada]** JWT local, roles por ruta y permisos de UI no equivalen necesariamente a autorización por operación → construir matriz endpoint/rol/permiso y pruebas negativas.
- **[Fuga de secretos]** Existen logs de tokens y headers Bearer → bloquear cualquier reutilización de esos patrones en el diseño NestJS y añadir escenarios de revisión de logs.
- **[Compatibilidad criptográfica]** Hay formatos AES/RSA distintos → conservar vectores de compatibilidad y no rotar algoritmos durante el piloto.
- **[DocumentDB]** MongoDB local no demuestra compatibilidad del motor destino → marcar pruebas DocumentDB como criterio bloqueante de aceptación.
- **[Consistencia]** No hay transacciones distribuidas ni outbox actual → evitar dual-write y definir reconciliación antes de transferir escrituras.
- **[Observabilidad]** El logging legacy captura request/response completos → exigir redacción, correlation ID y payload allowlist en el diseño objetivo.
- **[Scope creep]** Core y TEF contienen subdominios grandes → mantenerlos como contextos obligatorios, pero posponer su extracción hasta completar sus submapas internos.

## Migration Plan

1. Validar este cambio documental y las decisiones de alcance.
2. Crear los inventarios y matrices en el orden: API, Java, integraciones, seguridad, criptografía, datos y ownership.
3. Diseñar el contrato de Catalogos y el ACL sin cambiar el YAML legacy.
4. Obtener revisión de arquitectura, seguridad, datos y responsables funcionales.
5. En una fase posterior, crear la implementación NestJS únicamente mediante un cambio aprobado separado dentro de `/Users/phinder2024/Devs/BAZ/backend/divisas/microservice-dvs-catalogs`; no agregar implementación al repositorio `api-portal-divisas`.
6. Ejecutar paridad contra el monolito antes de transferir tráfico.
7. Habilitar rollback al monolito mientras exista escritura legacy o diferencia crítica.

## Open Questions

- ¿Qué consumidor y equipo funcional aprobarán el ownership de cada colección de `Catalogos`?
- ¿Qué versión exacta de DocumentDB será el destino de las pruebas de compatibilidad?
- ¿El gateway conserva o transforma `Authorization`, IP, User-Agent, `x-id-usuario` y `x-id-modulo`?
- ¿Debe `Catalogos` conservar exactamente las rutas y respuestas actuales o se aprobará una versión API nueva?
- ¿Qué SLO, volumen y ventana de cutover aplican al piloto?
