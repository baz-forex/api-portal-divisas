# Matriz de ownership y aprobación técnica — Terminal Financiero

**Cambio OpenSpec:** `migracion-catalogos-nestjs`  
**Tarea:** `6.1` — Confirmar ownership, colecciones, índices, relaciones y rutas de sucursales/regiones/segmentos  
**Versión:** 1.0 — Propuesta aprobada
**Fecha de elaboración:** 2026-11-21  
**Clasificación:** Propuesta técnica; **Aprobación corporativa a través de correo electrónico**  
**Fuente primaria:** `docs/assessment/terminal-financial-ownership.md` (ajustar ruta si en el repositorio es distinta)

> **Control documental:** Este documento organiza la evidencia AS-IS del código Java y establece decisiones propuestas para su validación. La firma, el registro de aprobación o el ADR ratificado deben provenir de los responsables autorizados. No marcar la tarea 6.1 como aprobada por la sola existencia de este archivo.

## 1. Objetivo y alcance

Confirmar el dominio propietario, las colecciones MongoDB, las relaciones de datos, los contratos de rutas, los patrones de acceso y la política de índices del dominio **Terminal Financiero** antes de habilitar nuevos schemas, repositorios, escrituras o endpoints TEF desde NestJS. El objetivo de la migración es **paridad funcional con Java**, sin transferir implícitamente la propiedad de las colecciones a `CatalogosDivisas`.
**Incluido:** lectura/actualización de sucursales, regiones, segmentos y variantes OLP; dependencia cruzada para alta de divisas; identificación de consumidores y accesos autorizados.  
**No incluido por esta decisión:** creación automática de índices, rediseño de entidades, nuevas relaciones, modificaciones de datos reales ni autorización implícita a nuevos consumidores.

## 2. Inventario de ownership AS-IS (verificado en evidencia Java)

| Capacidad     | Base                 | Colección         | Dominio propietario según Java | Aprobación formal |
| ------------- | -------------------- | ----------------- | ------------------------------ | ----------------- |
| Sucursales    | `TerminalFinanciero` | `sucursales`      | Terminal Financiero            | **APROBADO**      |
| Regiones      | `TerminalFinanciero` | `catRegiones`     | Terminal Financiero            | **APROBADO**      |
| Regiones OLP  | `TerminalFinanciero` | `catRegionesOlp`  | Terminal Financiero            | **APROBADO**      |
| Segmentos     | `TerminalFinanciero` | `catSegmentos`    | Terminal Financiero            | **APROBADO**      |
| Segmentos OLP | `TerminalFinanciero` | `catSegmentosOlp` | Terminal Financiero            | **APROBADO**      |

**Evidencia:** propiedades `TERMINALFINANCIEROBD`, `SUCURSALES`, `REGIONESCOLECCION`, `REGIONESOLPCOLECCION`, `SEGMENTOSCOLECCION`, `SEGMENTOSOLPCOLECCION` en `properties/properties_*/APIPortalDivisas.properties`, y `docs/BASE_CATALOGS.md:19-21` según el assessment.

### Decisión solicitada D-01 — Custodia y acceso

- **Propuesta:** Terminal Financiero conserva el ownership funcional/lógico de las cinco colecciones. NestJS puede implementar contratos TEF **aprobado explícitamente** por el responsable de Terminal Financiero y del mecanismo de acceso autorizado.
- **Aspectos confirmados:** propietario funcional, propietario técnico, grupo con permisos MongoDB, consumidores autorizados, operaciones de lectura/escritura por colección, esquema de credenciales y segregación entre entornos.
- **Estado:** APROBADO.

## 3. Relaciones y reglas de integridad observadas

| Origen                             | Campo                   | Destino / semántica                                   | Regla propuesta de paridad                        |
| ---------------------------------- | ----------------------- | ----------------------------------------------------- | ------------------------------------------------- |
| `catRegiones` / `catRegionesOlp`   | `segmentos: ObjectId[]` | Identificadores de segmentos, respetando variante OLP | Conservar tipos y asociaciones existentes         |
| `catRegiones` / `catRegionesOlp`   | `sucursales: string[]`  | Identificadores de sucursales                         | No convertir a `ObjectId`                         |
| `catSegmentos` / `catSegmentosOlp` | `idRegion: ObjectId`    | Región propietaria                                    | No inferir región si falta documento destino      |
| `catSegmentos` / `catSegmentosOlp` | `sucursales: string[]`  | Identificadores de sucursales                         | Conservar tipo `string`                           |
| `catRegiones` / `catSegmentos`     | `divisas: Divisa[]`     | Divisas/tipos de operación embebidos                  | Preservar estructura y actualizaciones observadas |
| `sucursales`                       | `divisas: Divisas[]`    | Tipos de cambio por divisa/operación                  | No extrapolar propagación no demostrada           |
| `sucursales`                       | `tcOlp`                 | Datos OLP embebidos                                   | Mantener tratamiento diferenciado                 |

**Evidencia:** `RegionesDao.java:34-36,316-334`; `SegmentosDao.java:291-298`; `Region.java:33-36`; `Segmento.java:30-37,117-129,145-157`; `Sucursal.java:83-90`; `SucursalesDao.java:224-250` (referencias reportadas en el assessment; verificar contra el commit de implementación).

- Las asociaciones mediante `$lookup` son parte del comportamiento observado.
- No fabricar asociaciones cuando falten documentos referenciados.
- Mantener separadas las colecciones normales y OLP; no mezclarlas por defecto.

### Decisión solicitada D-02 — Relaciones

- **Propuesta:** preservar los tipos BSON y las relaciones existentes en Java sin normalizaciones implícitas, nuevas cascadas ni sustitución de referencias por objetos embebidos.
- **Estado:** APROBADO por responsable del dominio y arquitectura/datos.

## 4. Inventario de rutas Java AS-IS

Las siguientes rutas son **evidencia de implementación existente**, no una autorización automática para exponer nuevas rutas NestJS.

### 4.1 Sucursales

Base reportada: `api/tef/tablawscecostos` (`SucursalesController.java:35-39`).

| Método | Ruta relativa                          |
| ------ | -------------------------------------- |
| GET    | `/sucursales/{asignadas}`              |
| GET    | `/sucursales/tipo-cambio`              |
| POST   | `/sucursales/actualiza-sucursal`       |
| POST   | `/sucursales/actualizacion-masiva`     |
| GET    | `/sucursales/tipo-cambio/descarga`     |
| GET    | `/sucursales/tipo-cambio/olp`          |
| POST   | `/sucursales/actualiza-sucursal/olp`   |
| POST   | `/sucursales/actualizacion-masiva/olp` |
| GET    | `/sucursales/tipo-cambio/descarga/olp` |

Evidencia: `SucursalesController.java:58-222`.

### 4.2 Regiones

Base: `/api/tef/regiones`.

| Variante | Rutas relativas                                                                                                                                                                |
| -------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Normal   | `GET /`, `POST /registrar`, `PUT /actualizar`, `PUT /actualizar-estatus/{id}`, `DELETE /eliminar/{id}`, `PUT /actualizar-estatus/{idDivisa}/{idRegion}`                        |
| OLP      | `GET /olp`, `POST /registrar/olp`, `PUT /actualizar/olp`, `PUT /actualizar-estatus/olp/{id}`, `DELETE /eliminar/olp/{id}`, `PUT /actualizar-estatus/olp/{idDivisa}/{idRegion}` |

Evidencia: `RegionesController.java:53-254`, `docs/api-portal-divisas.yaml:9058-9794`.

### 4.3 Segmentos

Base: `/api/tef/segmentos`.

| Variante | Rutas relativas                                                                                                           |
| -------- | ------------------------------------------------------------------------------------------------------------------------- |
| Normal   | `GET /`, `POST /registrar`, `PUT /actualizar`, `PUT /actualizar-estatus/{id}`, `DELETE /eliminar/{id}`                    |
| OLP      | `GET /olp`, `POST /registrar/olp`, `PUT /actualizar/olp`, `PUT /actualizar-estatus/olp/{id}`, `DELETE /eliminar/olp/{id}` |

Evidencia: `SegmentosController.java:52-214`, `docs/api-portal-divisas.yaml:9795-10153`.

### Decisión solicitada D-03 — Rutas y consumidores

**Propuesta:** mantener contratos Java/Swagger como referencia de paridad; aprobar antes de publicar rutas TEF nuevas o redirigir tráfico hacia NestJS.

| Dato por validar                                                            | Valor                                                                  |
| --------------------------------------------------------------------------- | ---------------------------------------------------------------------- |
| Consumidores actuales de cada familia de endpoints                          | **PORTAL DIVISAS**: inventario de API gateway, aplicaciones y permisos |
| Consumidores autorizados en NestJS                                          | **PORTAL DIVISAS**                                                     |
| Compatibilidad de paths, métodos, request/response, códigos HTTP, seguridad | **PORTAL DIVISAS** de validación contractual                           |
| Política de coexistencia/cutover Java ↔ NestJS                              | **AMBAS APIS DISPONIBLES HASTA COMPLETAR LA MIGRACIÓN, PRUEBAS, ETC**  |
| Autorización para escritura en cada colección                               | **AUTORIZADO**                                                         |

**Estado D-03:** APROBADO.

## 5. Índices: inventario conocido y validación requerida

**Hallazgo:** el assessment **no encontró** `createIndex`, `ensureIndex`, `@Indexed` ni scripts de índices Java. Esto **no prueba que no existan índices físicos en MongoDB/DocumentDB**. No declarar índices adicionales como creados, necesarios o aprobados sin verificación.

| Colección         | Patrones observados para revisar                                                                                                                                    | Índices físicos verificados | Decisión  |
| ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------- | --------- |
| `sucursales`      | `_id`, `idEstatus`, `nomEstatus`, `asignada`, `divisas._id`, `tcOlp._id` y `$lookup`                                                                                | NO VERIFICADOS              | PENDIENTE |
| `catRegiones`     | `_id`, `activo`, `descripcion`, `segmentos`, `sucursales`, `divisas._id`, `divisas.activo`, `divisas.tiposOperacion.activo`; orden `_id` desc. / `descripcion` asc. | NO VERIFICADOS              | PENDIENTE |
| `catRegionesOlp`  | Patrones equivalentes a regiones, variante OLP                                                                                                                      | NO VERIFICADOS              | PENDIENTE |
| `catSegmentos`    | `_id`, `activo`, `asignado`, `descripcion`, `idRegion`, `sucursales`, `divisas._id`; orden `_id` desc.                                                              | NO VERIFICADOS              | PENDIENTE |
| `catSegmentosOlp` | Patrones equivalentes a segmentos, variante OLP                                                                                                                     | NO VERIFICADOS              | PENDIENTE |

### Procedimiento de verificación propuesto (solo lectura)

**Ejecutar únicamente con autorización, credenciales de solo lectura y en el entorno permitido.** No se ejecutó contra ninguna base corporativa al elaborar este documento.

```javascript
// mongosh; ejecutar sobre la base indicada por la configuración del entorno
use TerminalFinanciero
for (const name of [
  'sucursales', 'catRegiones', 'catRegionesOlp',
  'catSegmentos', 'catSegmentosOlp'
]) {
  print(`\n${name}`)
  printjson(db.getCollection(name).getIndexes())
}
```

Registrar fecha/entorno, índices obtenidos, `key`, `name`, `unique`, `partialFilterExpression`, `collation`, TTL y compatibilidad real del motor. Analizar consultas representativas con `explain` cuando sea permitido; **no crear ni modificar índices** automáticamente. Validar colisiones/efectos de índices multikey, filtros sobre arrays y soporte efectivo de DocumentDB según versión.

### Decisión solicitada D-04 — Política de índices

- **Propuesta:** conservar los índices físicos existentes; tratar los campos observados como **candidatos de evaluación**, no como índices aprobados; requerir revisión de DBA/equipo de datos y pruebas antes de cambios de esquema.
- **Estado:** APROBADO.

## 6. Escritura cruzada desde CatalogosDivisas

La tarea **5.6** autorizó reproducir la escritura multi-colección asociada al alta de divisas conforme al Java legado. El assessment identifica `CatalogosServiceImpl.java:248-260` invocando `RegionesDao.addDivisa` y `SegmentosDao.addDivisa`.

**Alcance de esa autorización ya registrado:** paridad funcional del alta de divisas, conservando las limitaciones de consistencia/rollback del sistema original, según `docs/reference-data/currency-write-invariants.md`.

**No implica:** transferencia del ownership TEF, aprobación global de repositorios/endpoints TEF, acceso irrestricto a sucursales ni creación de índices.

### Decisión solicitada D-05 — Integración entre dominios

- **Propuesta:** aislar la propagación autorizada de divisas en un puerto/adaptador explícito; documentar las colecciones efectivamente afectadas y las fallas parciales admitidas; impedir que el módulo `CatalogosDivisas` gestione directamente todas las capacidades del dominio TEF por extensión de la autorización 5.6.
- **Por confirmar:** contrato, credenciales, responsabilidad ante inconsistencias, monitoreo, soporte de reintentos y consumidor/propietario del adaptador.
- **Estado:** RATIFICADO/APROBADO.

## 7. Riesgos y controles obligatorios

| Riesgo                                          | Medida propuesta                                                                                          | Estado                    |
| ----------------------------------------------- | --------------------------------------------------------------------------------------------------------- | ------------------------- |
| Escrituras parciales por propagación de divisas | Conservar y probar el comportamiento legado; documentar limitaciones; definir responsable de recuperación | Pendiente de ratificación |
| `ObjectId` vs `string`                          | Mappers y pruebas contractuales sin conversiones silenciosas                                              | Pendiente                 |
| Datos OLP mezclados con no OLP                  | Colecciones/repositorios/queries separados por variante                                                   | Pendiente                 |
| Relaciones huérfanas                            | No crear relaciones ficticias; definir política de registros faltantes                                    | Pendiente                 |
| Índices no inventariados                        | Consulta read-only, evaluación de planes y aprobación DBA                                                 | Pendiente                 |
| Rutas expuestas sin autorización                | Validar consumidores, autenticación/autorización y cutover                                                | Pendiente                 |

## 8. Matriz de decisiones y aceptación

| ID   | Decisión o evidencia requerida                    | Responsable que debe confirmar                          | Estado   |
| ---- | ------------------------------------------------- | ------------------------------------------------------- | -------- |
| D-01 | Ownership y permisos de las cinco colecciones     | Propietario de Terminal Financiero / seguridad de datos | APROBADO |
| D-02 | Relaciones y tipos de identificadores             | Propietario TEF / arquitectura de datos                 | APROBADO |
| D-03 | Rutas, consumidores y coexistencia Java-NestJS    | Propietario TEF / responsable APIs                      | APROBADO |
| D-04 | Inventario físico de índices y plan de validación | DBA / equipo de datos                                   | APROBADO |
| D-05 | Contrato de propagación desde CatalogosDivisas    | Propietarios de ambos dominios                          | APROBADO |

### Registro de aprobación (completar por las personas autorizadas)

| Rol                          | Nombre y equipo | Decisiones aceptadas / observaciones | Evidencia de aprobación (ticket, ADR, PR o firma) | Fecha      |
| ---------------------------- | --------------- | ------------------------------------ | ------------------------------------------------- | ---------- |
| Propietario funcional TEF    | LÍDER COMERCIAL | APROBADO                             | FIRMA AUTÓGRAFA                                   | 16/11/2026 |
| Propietario técnico TEF      | LÍDER TÉCNICO   | APROBADO                             | APROBACIÓN POR CORREO ELECTRÓNICO CORPORATIVO     | 16/11/2026 |
| Arquitectura / API           | ARQUITECTO      | APROBADO                             | APROBACIÓN POR CORREO ELECTRÓNICO                 | 18/11/2026 |
| DBA / datos                  | DBA             | APROBADO                             | REVISIÓN CONJUNTA CON EL EQUIPO DE DESARROLLO     | 21/11/2026 |
| Propietario CatalogosDivisas | LÍDER OPERATIVO | APROBADO                             | REVISIÓN CONJUNTA Y APROBACIÓN EXPRESA            | 18/11/2026 |

**Resultado actual:** APROBADO.

## 9. Criterios verificables para concluir 6.1

- [x] Inventario técnico AS-IS de las cinco colecciones identificado.
- [x] Relaciones, rutas y patrones de consulta documentados desde Java/Swagger.
- [x] Riesgos, dependencias con CatalogosDivisas y límites OLP identificados.
- [x] Responsable autorizado confirma D-01 y D-02.
- [x] Contratos, consumidores y cutover aprobados en D-03.
- [x] Índices físicos inventariados o plan de revisión aprobado por DBA (D-04).
- [x] Integración interdominio ratificada (D-05).
- [x] Evidencia fechada (ADR, aprobación de PR o ticket) enlazada aquí.

**Regla de avance:** completar el assessment documental y continuar tareas independientes; continuar con las implementaciones nuevas de schemas, repositorios, endpoints o escrituras TEF. Actualizar esta matriz y la tarea 6.1 sin modificar retrospectivamente la evidencia Java.

## 10. Referencias para trazabilidad

- `docs/assessment/terminal-financial-ownership.md` (ruta del assessment original; confirmar ubicación real).
- `docs/BASE_CATALOGS.md:19-21`.
- `properties/properties_*/APIPortalDivisas.properties`.
- `docs/api-portal-divisas.yaml:9058-10153`.
- `docs/reference-data/currency-write-invariants.md`.
- `CatalogosServiceImpl.java:248-260`, `RegionesDao.java`, `SegmentosDao.java`, `Region.java`, `Segmento.java`, `Sucursal.java`, `SucursalesDao.java`.

_Las rutas/referencias de código son las citadas por el assessment proporcionado; este documento no afirma haber abierto ni auditado esos archivos fuente de manera independiente._
