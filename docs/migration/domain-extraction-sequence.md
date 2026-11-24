# Secuencia propuesta de extracción de dominios

## Estado y alcance

Esta es una **secuencia preliminar de ejecución**, basada en el assessment AS-IS. No constituye autorización de implementación, cutover ni cambio de ownership. Debe ser revisada por arquitectura, seguridad, datos y responsables funcionales antes de iniciar cada ola.

La secuencia distingue:

- **capacidades transversales:** habilitadores que deben estar disponibles en todos los microservicios, pero no necesariamente se extraen como bounded contexts independientes;
- **bounded contexts funcionales:** candidatos a proyectos/microservicios independientes;
- **gates:** condiciones que pueden cambiar el orden propuesto.

## Secuencia general

| Orden | Capacidad/contexto | Tipo | Proyecto futuro de referencia | Propuesta y justificación | Gate principal |
|---:|---|---|---|---|---|
| 0 | Seguridad, autenticación y autorización | transversal | librerías/adaptadores corporativos; no asumir microservicio dedicado | Debe resolverse primero la paridad con `WebSecurityConfig`, `AuthTokenFilter`, `JwtUtils`, 401/403, roles, claims y límites de confianza. No se debe extraer un endpoint sin control de acceso equivalente. | proveedor de identidad, formato de token, gateway, issuer/audience/JWKS, revocación y matriz de permisos |
| 0 | Observabilidad, health, errores y correlación | transversal | capacidades comunes de cada proyecto NestJS | `@gs/logger`, `@gs/health`, `@gs/errors`, correlation/trace IDs, redacción y métricas son condiciones de operación y rollback para cada extracción. | estándares corporativos, SLO, alertas y política de datos sensibles |
| 0 | Proxy, criptografía y persistencia | transversal | `@gs/proxy`, `@gs/crypto`, `@gs/documentdb` detrás de puertos | Deben definirse los adaptadores para llamadas externas, formatos criptográficos históricos y compatibilidad MongoDB/DocumentDB sin acoplar el dominio. | contratos externos, golden vectors y pruebas contra la versión DocumentDB objetivo |
| 1 | Catalogos | bounded context funcional | `microservice-dvs-catalogs` | Primer piloto por su superficie acotada, operaciones de lectura deterministas y colecciones identificables. Permite validar ACL, paridad, ownership y rollback con menor amplitud. | ownership de `catMonedas`, dependencias TEF/Bitacora y contrato legacy reconciliado |
| 2 | Bitacora | capacidad transversal funcional | proyecto independiente por definir | Segundo candidato por su modelo relativamente simple y valor transversal. Se extrae después de definir contrato, retención, PII y forma de invocación para no convertirla en una dependencia síncrona incontrolada. | contrato de auditoría, retención, clasificación de PII y estrategia HTTP/eventos |
| 3 | SIEX | bounded context especializado | proyecto independiente por definir | Candidato posterior con responsabilidad acotada y persistencia propia. Su especialización permite una extracción más aislada que Core, Digital o TEF. | owner funcional, consumidores y compatibilidad de consultas SIEX |
| 4 | Digital | bounded context funcional | proyecto independiente por definir | Superficie menor que Core y con capacidades distinguibles de parametría/reportería. Debe extraerse después de resolver la propiedad de la parametría y sus dependencias Middleware. | ownership de Middleware/parametría, contratos de reportería y exportaciones |
| 5 | Core | bounded context amplio, dividido por capacidades | varios proyectos o módulos independientes por definir | No debe extraerse como bloque único. Se propone comenzar por capacidades con ownership claro, separando clientes, configuración, beneficios, sucursales y reportería. Su amplitud y dependencia de Middleware/Catalogos justifican una fase posterior. | submapas internos, ownership de Middleware, contratos de clientes y paridad por capacidad |
| 6 | Usuarios | bounded context funcional sensible | proyecto independiente por definir | Se aborda después de estabilizar los contratos de identidad y autorización. Incluye usuarios, perfiles y módulos, con dependencia directa de `Autenticacion` y permisos. | decisión IAM, ownership de usuarios/módulos y matriz de permisos |
| 7 | SCIM | capacidad adyacente de provisioning | proyecto independiente por definir o capability de IAM | Se separa de `Usuarios` conceptualmente, pero no debe migrarse antes de resolver IAM, provisioning, token SCIM y ownership de recursos. | contrato SCIM, autoridad del proveedor IAM, roles, revocación y pruebas negativas |
| 8 | TEF | bounded context amplio y altamente acoplado | varios proyectos por subcapacidad, por definir | Último candidato por sus múltiples subcapacidades, estructuras anidadas, operaciones multi-colección, TerminalFinanciero, Apigee y Tesorería. Debe dividirse en subcontextos como spots, regiones/segmentos, límites, sucursales y parametría. | contratos externos, ownership de TerminalFinanciero, resiliencia y compatibilidad de operaciones MongoDB |

## Tratamiento de Auth, seguridad y SCIM

`Auth` no se considera una extracción funcional posterior a Catalogos. Es un **habilitador transversal**: cada microservicio debe integrar autenticación y autorización aprobadas antes de exponer rutas. Si posteriormente se decide crear `microservice-dvs-auth`, su diseño y extracción deben aprobarse como una iniciativa de IAM separada.

La seguridad debe avanzar en la ola 0 mediante assessment, contratos, guards/adaptadores, paridad 401/403, redacción y pruebas negativas. Esto no autoriza a modificar `api-portal-divisas` ni a cambiar el proveedor de identidad.

`SCIM` sí representa una capacidad adyacente de provisioning, pero su extracción se mantiene después de `Usuarios` o coordinada con el equipo IAM. No debe confundirse con la validación JWT de las APIs de negocio.

## Orden no estrictamente lineal

Las olas 3 y 4 pueden intercambiarse si SIEX no tiene owner disponible o si Digital obtiene antes un contrato de parametría aprobado. Core no debe comenzar hasta completar sus submapas. Usuarios y SCIM pueden adelantarse por una decisión corporativa de IAM, pero no por conveniencia de la migración.

El orden se debe reevaluar al finalizar cada extracción con:

1. ownership de datos aprobado;
2. contrato y consumidores reconciliados;
3. paridad funcional y de seguridad;
4. compatibilidad DocumentDB demostrada;
5. rollback y observabilidad operativos;
6. ausencia de dual-write no aprobado.

## Evidencia utilizada

- `docs/architecture/context-map.md`
- `docs/architecture/dependency-map.md`
- `docs/architecture/data-ownership.md`
- `docs/migration/pilot-selection.md`
- `docs/migration/roadmap.md`
- `docs/assessment/spring-security-inventory.md`
- `docs/assessment/integration-inventory.md`
