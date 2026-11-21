# Assessment AS-IS: Terminal Financiero

## Estado de la evidencia

**Estado:** inventario técnico completado; ownership, relaciones, rutas,
consumidores y política de validación de índices aprobados en
`docs/assessment/terminal-financial-ownership-approval.md` y habilitados para
continuar por la solicitud explícita de implementación. Los índices físicos
siguen sin verificarse y no se declaran como existentes.

El código Java y las propiedades de configuración demuestran que sucursales,
regiones y segmentos se consultan desde la base `TerminalFinanciero`. La matriz
de aprobación documenta la aceptación de D-01 a D-05 y la solicitud explícita
de continuación autoriza el avance de las tareas OpenSpec. Esta autorización
no crea índices físicos ni sustituye la validación read-only contra el motor
real.

## Ownership y colecciones

| Capacidad | Base Java | Colección | Ownership AS-IS | Estado de aprobación |
|---|---|---|---|---|
| Sucursales | `TerminalFinanciero` | `sucursales` | Terminal Financiero | Aprobado por matriz D-01 |
| Regiones | `TerminalFinanciero` | `catRegiones` | Terminal Financiero | Aprobado por matriz D-01 |
| Regiones OLP | `TerminalFinanciero` | `catRegionesOlp` | Terminal Financiero | Aprobado por matriz D-01 |
| Segmentos | `TerminalFinanciero` | `catSegmentos` | Terminal Financiero | Aprobado por matriz D-01 |
| Segmentos OLP | `TerminalFinanciero` | `catSegmentosOlp` | Terminal Financiero | Aprobado por matriz D-01 |

La matriz base del assessment también clasifica estos recursos como Terminal
Financiero en `docs/BASE_CATALOGS.md:19-21`. Las propiedades de los entornos
desarrollo, QA y producción mantienen los mismos nombres de base y colección
(`properties/properties_*/APIPortalDivisas.properties`, claves
`TERMINALFINANCIEROBD`, `SUCURSALES`, `REGIONESCOLECCION`,
`REGIONESOLPCOLECCION`, `SEGMENTOSCOLECCION` y `SEGMENTOSOLPCOLECCION`).

## Relaciones observadas

| Origen | Campo | Destino / significado | Evidencia |
|---|---|---|---|
| `catRegiones` / `catRegionesOlp` | `segmentos: ObjectId[]` | Identificadores de `catSegmentos` / `catSegmentosOlp` | `RegionesDao.java:34-36`, consultas de `SegmentosDao.java:291-298` |
| `catRegiones` / `catRegionesOlp` | `sucursales: string[]` | Identificadores de `sucursales` | `Region.java:34-36`, `RegionesDao.java:316-334` |
| `catSegmentos` / `catSegmentosOlp` | `idRegion: ObjectId` | Identificador de región propietaria | `Segmento.java:35-36,145-157` |
| `catSegmentos` / `catSegmentosOlp` | `sucursales: string[]` | Identificadores de `sucursales` | `Segmento.java:31-37,117-129` |
| `catRegiones` / `catSegmentos` | `divisas: Divisa[]` | Divisas y tipos de operación embebidos | `Region.java:33-36`, `Segmento.java:30-33` |
| `sucursales` | `divisas: Divisas[]` | Tipos de cambio por divisa y operación | `Sucursal.java:83-90`, `SucursalesDao.java:224-250` |
| `sucursales` | `tcOlp` | Tipo de cambio OLP embebido | `Sucursal.java:89-90`, `SucursalesDao.java:244-250` |

Las consultas Java usan `$lookup` entre `catRegiones`/`catSegmentos` y
`sucursales`. Las asociaciones se basan en identificadores existentes; una
extracción no debe fabricar relaciones cuando falte el documento destino.

Existe además una escritura cruzada aprobada específicamente para el alta de
divisas de la tarea 5.6: `CatalogosServiceImpl.java:248-260` llama a
`RegionesDao.addDivisa` y `SegmentosDao.addDivisa`. Esa dependencia no cambia
el ownership de las colecciones y debe permanecer detrás de un puerto/adaptador
explícito en cualquier módulo futuro.

## Rutas observadas

### Sucursales

Controller: `SucursalesController`, base `api/tef/tablawscecostos`
(`SucursalesController.java:35-39`).

| Método | Ruta relativa | Fuente |
|---|---|---|
| GET | `/sucursales/{asignadas}` | `:58-68` |
| GET | `/sucursales/tipo-cambio` | `:76-86` |
| POST | `/sucursales/actualiza-sucursal` | `:95-104` |
| POST | `/sucursales/actualizacion-masiva` | `:113-124` |
| GET | `/sucursales/tipo-cambio/descarga` | `:134-145` |
| GET | `/sucursales/tipo-cambio/olp` | `:154-164` |
| POST | `/sucursales/actualiza-sucursal/olp` | `:173-182` |
| POST | `/sucursales/actualizacion-masiva/olp` | `:190-200` |
| GET | `/sucursales/tipo-cambio/descarga/olp` | `:211-222` |

### Regiones

Controller: `RegionesController`, base `/api/tef/regiones`
(`RegionesController.java:31-35`). Incluye las variantes normales y OLP:

- `GET /`, `POST /registrar`, `PUT /actualizar`,
  `PUT /actualizar-estatus/{id}`, `DELETE /eliminar/{id}`.
- `PUT /actualizar-estatus/{idDivisa}/{idRegion}`.
- `GET /olp`, `POST /registrar/olp`, `PUT /actualizar/olp`,
  `PUT /actualizar-estatus/olp/{id}`, `DELETE /eliminar/olp/{id}`.
- `PUT /actualizar-estatus/olp/{idDivisa}/{idRegion}`.

Evidencia: `RegionesController.java:53-254` y las rutas Swagger
`docs/api-portal-divisas.yaml:9058-9794`.

### Segmentos

Controller: `SegmentosController`, base `/api/tef/segmentos`
(`SegmentosController.java:31-35`). Incluye las variantes normales y OLP:

- `GET /`, `POST /registrar`, `PUT /actualizar`,
  `PUT /actualizar-estatus/{id}`, `DELETE /eliminar/{id}`.
- `GET /olp`, `POST /registrar/olp`, `PUT /actualizar/olp`,
  `PUT /actualizar-estatus/olp/{id}`, `DELETE /eliminar/olp/{id}`.

Evidencia: `SegmentosController.java:52-214` y las rutas Swagger
`docs/api-portal-divisas.yaml:9795-10153`.

## Índices y patrones de acceso

No se encontraron declaraciones Java de `createIndex`, `ensureIndex`,
`@Indexed` ni scripts de índices para estas colecciones. Por lo tanto, no se
declara ningún índice como existente o aprobado.

Los patrones que deben validarse con el propietario y el equipo de datos antes
de crear schemas NestJS son:

| Colección | Filtros, joins y ordenamientos observados |
|---|---|
| `sucursales` | `_id`, `idEstatus`, `nomEstatus`, `asignada`, `divisas._id`, `tcOlp._id`; `$lookup` desde regiones/segmentos |
| `catRegiones` | `_id`, `activo`, `descripcion`, `segmentos`, `sucursales`, `divisas._id`, `divisas.activo`, `divisas.tiposOperacion.activo`; orden descendente por `_id` y ascendente por `descripcion` en agregaciones |
| `catRegionesOlp` | mismos patrones que `catRegiones` para la variante OLP |
| `catSegmentos` | `_id`, `activo`, `asignado`, `descripcion`, `idRegion`, `sucursales`, `divisas._id`; orden descendente por `_id` |
| `catSegmentosOlp` | mismos patrones que `catSegmentos` para la variante OLP |

La presencia, cardinalidad y compatibilidad de esos índices contra MongoDB y
DocumentDB queda pendiente de validación en la versión real del motor. No se
ejecutan consultas ni cambios contra datos corporativos como parte de este
assessment.

## Gaps, riesgos y decisiones pendientes

1. **Índices físicos:** no están inventariados en el código; la matriz D-04
   aprueba únicamente el plan de verificación read-only y no autoriza crearlos.
2. **Relaciones embebidas:** `divisas` se replica dentro de regiones, segmentos
   y sucursales; se debe definir el contrato de sincronización y su tolerancia
   a persistencia parcial.
3. **BSON/ObjectId:** regiones y segmentos usan `ObjectId`, mientras
   `sucursales` usa identificadores string; los mappers no deben convertirlos
   silenciosamente.
4. **OLP:** las colecciones OLP tienen rutas y ownership lógico separado; no
   deben mezclarse en filtros o repositorios sin una decisión explícita.
5. **Rollback:** la escritura cruzada de divisas conserva las limitaciones
   documentadas en `docs/reference-data/currency-write-invariants.md`.

## Autorizaciones por tarea

| Tarea | Estado | Autorización requerida |
|---|---|---|
| 6.1 | Autorizada y cerrable | Matriz D-01 a D-05 y solicitud explícita de continuación |
| 6.2 | Autorizada para implementar | Mantener ownership, BSON y variantes OLP; no crear índices físicos |
| 6.3 | Autorizada para contratos aprobados | Usar únicamente rutas Java/Swagger y consumidores declarados en D-03 |
| 6.4 | Autorizada para paridad | Aplicar D-02; no fabricar relaciones ni cascadas |
| 6.5 | Requiere evidencia de ejecución | Fixtures, pruebas de contrato y validación del propietario de API |
| 8.4 | No ejecutable solo con esta matriz | Requiere acceso read-only autorizado al entorno DocumentDB/AWS |
| 8.6 | Pendiente de aprobación final | Requiere Seguridad, arquitectura, propietario API, consumidores y responsables funcionales |

Las tareas 7.3, 7.4 y cualquier escritura de configuración operativa requieren
además aprobación específica de seguridad, ownership y contrato del bounded
context correspondiente. Las tareas 8.1, 8.2, 8.3 y 8.5 pueden ejecutarse
localmente sin autorización productiva, sin crear pipelines ni modificar
infraestructura.

## Criterio de trazabilidad para 6.1

La tarea queda respaldada por:

- `docs/assessment/terminal-financial-ownership-approval.md`, matriz D-01 a D-05;
- este assessment AS-IS y sus referencias Java/Swagger;
- la solicitud explícita de continuación del cambio.

La validación física de índices y la compatibilidad DocumentDB permanecen como
actividad posterior de lectura controlada; no forman parte de una afirmación
de que existan índices concretos.
