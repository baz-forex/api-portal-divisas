# Diseño preliminar de Catalogos

## Proyecto de implementación

Todos los componentes runtime de este diseño deben crearse exclusivamente en el proyecto nuevo:

- **Ruta:** `/Users/phinder2024/Devs/BAZ/backend/divisas/microservice-dvs-catalogs`
- **Nombre:** `microservice-dvs-catalogs`

El repositorio `api-portal-divisas` solo contiene el monolito Java legacy y la documentación del assessment. No deben agregarse allí módulos NestJS, controllers, casos de uso, entidades, adaptadores, repositorios, DTOs runtime, configuración ni pruebas del microservicio.

## Casos de uso

| Caso de uso | Lectura/escritura | Dependencias |
|---|---|---|
| consultar países/nacionalidades | lectura | `catNacionalidades` |
| consultar estados | lectura | `catEstados` |
| consultar divisas | lectura | `catMonedas`, excluir `OLP` como hoy |
| consultar identificaciones | lectura/agregación | `catIdentificaciones` |
| cambiar estatus de divisa | escritura | `catMonedas`, auditoría pendiente |
| registrar divisa | escritura multi-contexto | `catMonedas`, regiones, segmentos, Bitacora |
| consultar estatus cotización | lectura/agregación | `catEstatusCotizacion` |
| consultar catálogos de campañas | lectura | cinco colecciones de campañas |

## Puertos

```text
CatalogRepository
AuditPort
TefReferencePort
LegacyCatalogContractPort
```

Los puertos devuelven modelos de dominio o resultados tipados. No exponen `Document`, `MongoCollection`, `ResponseApi` ni excepciones de infraestructura.

## Adaptadores

- `DocumentDbCatalogRepository`: `@gs/documentdb`, filtros/proyecciones/agregaciones y mappers.
- `LegacyAuditAdapter`: `@gs/proxy` o puerto de auditoría aprobado; no escribir colección ajena directamente.
- `TefReferenceAdapter`: consulta de regiones/segmentos, con timeout y contrato explícito.
- `LegacyHttpController`: DTOs, casing histórico, `@gsa/decorators`, `@gs/swagger`, guard `@gs/auth`.
- `ErrorAdapter`: `@gs/errors`, conserva códigos/status y folio durante convivencia.

## Alta de divisa

El comportamiento Java actual crea una divisa, inicializa cuatro límites, registra Bitacora y actualiza regiones/segmentos. En NestJS debe ser un caso de uso explícito con:

1. idempotency key o comprobación equivalente;
2. owner de escritura único;
3. estrategia de compensación/reconciliación;
4. auditoría confirmada;
5. rechazo de dual-write no aprobado.

No se debe trasladar literalmente la compensación `deleteMany` sin validar sus consecuencias.

## Capas corporativas

`@gs/logger` y `@gs/health` se configuran en infraestructura/composición; `@gs/crypto` solo se usa si el contrato del piloto demuestra payload cifrado. `@gs/utils` no debe contener reglas de Catalogos.
