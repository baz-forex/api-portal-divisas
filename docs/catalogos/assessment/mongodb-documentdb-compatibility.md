# MongoDB / DocumentDB: inventario y compatibilidad

## Evidencia de MongoDB local

Consulta de metadatos vía conexión de solo lectura a `localhost:27019`; no se leyeron documentos personales.

| Base               | Colecciones |
| ------------------ | ----------: |
| Autenticacion      |           9 |
| CatalogosDivisas   |          55 |
| Incidencias        |           1 |
| Middleware         |          46 |
| MonitoreoClientes  |           8 |
| SIEX               |          24 |
| TerminalFinanciero |          21 |

También existen bases técnicas `admin`, `config`, `local` y `test`.

## Colecciones del piloto

| Colección                   | Conteo local | Campos inferidos                                                         | Índices observados |
| --------------------------- | -----------: | ------------------------------------------------------------------------ | ------------------ |
| `catNacionalidades`         |          248 | `_id`, `descripcion`, `activo`, restricciones y arreglo de equivalencias | `_id_`             |
| `catEstados`                |           32 | `_id`, `descripcion`, `activo`                                           | `_id_`             |
| `catMonedas`                |            4 | `_id`, `descripcion`, `activo`, `limites[]` embebidos                    | `_id_`             |
| `catIdentificaciones`       |            6 | ids de cliente, descripción y activo                                     | `_id_`             |
| `catEstatusCotizacion`      |            4 | subdocumentos `compra` y `venta`                                         | `_id_`             |
| `catTipoOperacion`          |            4 | `_id`, `descripcion`, `activo`                                           | `_id_`             |
| `catTipoCliente`            |            2 | `_id`, `descripcion`, `activo`                                           | `_id_`             |
| `catPerfilClienteCampanias` |            2 | `_id`, `descripcion`, `activo`                                           | `_id_`             |
| `catTipoLimiteCampanias`    |            4 | `_id`, `descripcion`, `activo`                                           | `_id_`             |
| `catTipoAcumuladoCampanias` |            4 | `_id`, `descripcion`, `activo`                                           | `_id_`             |
| `catEstatusCampanias`       |            5 | `_id`, `descripcion`, `activo`                                           | `_id_`             |
| `catRangoEdadesCampanias`   |            6 | `_id`, `descripcion`, `minimo`, `maximo`                                 | `_id_`             |

Los conteos y esquemas son una fotografía local; no son datos de producción.

## Operaciones que requieren compatibilidad

- `find`, `sort`, proyecciones y filtros por campos embebidos.
- Agregaciones con `$project`, `$match`, `$sort` y `$lookup` observadas en DAOs.
- `updateOne`, `updateMany`, `deleteMany` y compensaciones.
- `arrayFilters` en DAOs TEF.
- `insertMany` con `ordered=false` en cargas masivas.
- Operaciones multi-colección sin transacción explícita.

## Matriz de riesgo

| Característica | Evidencia                       | Compatibilidad DocumentDB | Validación                           |
| -------------- | ------------------------------- | ------------------------- | ------------------------------------ |
| CRUD y `_id`   | todos los DAOs                  | pendiente por versión     | integración contra motor destino     |
| agregaciones   | `CatalogosDao`, Core, TEF       | pendiente                 | pipelines representativos            |
| array filters  | `SpotsDao`, regiones/segmentos  | pendiente                 | pruebas de actualización anidada     |
| bulk unordered | `CuponesBeneficiosDao`          | pendiente                 | duplicados e idempotencia            |
| transacciones  | no evidenciadas                 | no asumir                 | decisión de consistencia             |
| índices        | principalmente `_id_` en piloto | pendiente                 | explain/latencia y creación aprobada |

Se ordena y se asume compatibilidad DocumentDB contra la versión exacta del destino.
