# Estado objetivo preliminar

## Arquitectura de convivencia

La migración debe implementarse en un **proyecto nuevo e independiente** con el stack NestJS/TypeScript aprobado:

- **Ruta:** `/Users/phinder2024/Devs/BAZ/backend/divisas/microservice-dvs-catalogs`
- **Nombre del microservicio:** `microservice-dvs-catalogs`
- **Responsabilidad:** contener todos los componentes de software de la migración de Catalogos y sus futuras capacidades asociadas.

El proyecto actual `api-portal-divisas` es únicamente el monolito Java legacy y fuente del assessment. **No deben guardarse en este proyecto componentes de implementación de la migración**, incluyendo módulos NestJS, controllers, casos de uso, dominio, adaptadores, repositorios, DTOs runtime, configuración o pruebas del nuevo microservicio. La implementación futura debe realizarse exclusivamente en el proyecto nuevo.

```text
                         /Users/phinder2024/Devs/BAZ/backend/divisas
                                      |
                     +----------------+----------------+
                     |                                 |
       api-portal-divisas (legacy/assessment)   microservice-dvs-catalogs (nuevo)
                     |                                 |
               Monolito Java                  presentation
               rutas no extraídas              application
                                                  domain
                                                  infrastructure
                                      ^
                                      |
                              Gateway / ACL / contrato
```

`microservice-dvs-catalogs` no comparte entidades, DTOs ni repositorios Java. La ACL traduce el contrato legacy a modelos internos y encapsula cualquier lectura temporal del monolito.

## Estructura propuesta

```text
microservice-dvs-catalogs/
  src/modules/catalogos/
    domain/          # entidades, value objects, invariantes y puertos
    application/     # casos de uso y DTO internos
    infrastructure/  # DocumentDB, proxy, crypto, logging
    presentation/    # controllers, DTO HTTP, guards, filtros
    catalogos.module.ts
```

`domain` no importa NestJS, Mongoose, AWS SDK, HTTP ni librerías corporativas. Los tokens de NestJS se usan solo en composición.

## Librerías corporativas

| Librería | Uso objetivo |
|---|---|
| `@gs/auth` | guard, principal, autorización y validación aprobada |
| `@gs/crypto` | compatibilidad de formatos y protección criptográfica |
| `@gs/documentdb` | conexión, repositorios y health de MongoDB/DocumentDB |
| `@gs/errors` | errores, filtros HTTP y envelope legacy |
| `@gs/health` | readiness/liveness y dependencias |
| `@gs/logger` | logs estructurados, correlation ID y redacción |
| `@gs/proxy` | llamadas a gateway/monolito/servicios externos |
| `@gs/swagger` | OpenAPI compatible y documentación de headers |
| `@gs/utils` | utilidades aprobadas, no reglas de dominio |
| `@gsa/decorators` | decoradores corporativos de entrada, trazabilidad o seguridad |

## Operación segura

- métricas de latencia, errores, diferencias de paridad y fallback;
- correlation/request/trace IDs;
- logs allowlist, sin Authorization, llaves, PII ni cuerpos completos;
- timeouts y retries solo para operaciones idempotentes;
- rollback de routing al monolito mientras el owner de escritura no se haya transferido.
