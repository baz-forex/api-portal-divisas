# Mapeo del contrato legacy de Catalogos

## Endpoints

| Método | Ruta legacy | Controller | Service | DAO/colección |
|---|---|---|---|---|
| GET | `/api/catalogos/paises` | `CatalogosController#getCatPaisNacionalidad` | `getCatPaisNacionalidad` | `CatalogosDao#findCatPaisNacionalidad` → `catNacionalidades` |
| GET | `/api/catalogos/estados` | `getCatEstados` | `getCatEstados` | `catEstados` |
| GET | `/api/catalogos/divisas` | `getCatDivisas` | `getCatDivisas` | `catMonedas`, excluye `OLP` |
| PUT | `/api/catalogos/divisas` | `actualizaDivisa` | `actualizaEstatusDivisa` | `catMonedas.activo` |
| POST | `/api/catalogos/divisas/registrar` | `registraDivisa` | `creaDivisa` | `catMonedas` + regiones/segmentos + Bitacora |
| GET | `/api/catalogos/identificaciones` | `getCatIdentificaciones` | `getCatIdentificaciones` | agregación sobre `catIdentificaciones` |
| GET | `/api/catalogos/estatus-cotizacion` | `getCatEstatusCotizacion` | `getCatEstatusCotizacion` | proyección de `catEstatusCotizacion` |
| GET | `/api/catalogos/TipoOperacion` | `getCatTipoOperacion` | `getCatTipoOperacion` | `catTipoOperacion` |
| GET | `/api/catalogos/TipoCliente` | `getCatTipoCliente` | `getCatTipoCliente` | `catTipoCliente` |
| GET | `/api/catalogos/campanias/PerfilCliente` | `getCatPerfilClienteCampanias` | mismo nombre | `catPerfilClienteCampanias` |
| GET | `/api/catalogos/campanias/TipoLimite` | `getCatTipoLimiteCampanias` | mismo nombre | `catTipoLimiteCampanias` |
| GET | `/api/catalogos/campanias/TipoAcumulado` | `getCatTipoAcumuladoCampanias` | mismo nombre | `catTipoAcumuladoCampanias` |
| GET | `/api/catalogos/campanias/Estatus` | `getCatEstatusCampanias` | mismo nombre | `catEstatusCampanias` |
| GET | `/api/catalogos/campanias/RangoEdades` | `getCatRangoEdadesCampanias` | mismo nombre | `catRangoEdadesCampanias` |

Son 13 paths y 14 operaciones porque `/api/catalogos/divisas` tiene GET y PUT.

## DTO y respuesta

- Request de escritura: `Catalogo` con `@Valid`; confirmar campos obligatorios en `Catalogo.java`.
- Response: `ResponseApi` con `codigo`, `mensaje`, `folio`, `info`, `detalles` y `resultado`.
- Lista vacía: `SIN_RESULTADOS` con detalle `SIN_INFORMACION`.
- Error de alta duplicada: `PETICION_INCORRECTA`; errores de persistencia: `ERROR_INTERNO`.
- Mantener casing exacto de rutas históricas hasta aprobar versionado.

## Compatibilidad

El YAML no se modifica en esta tarea. Antes del piloto se debe comparar esta tabla con OpenAPI, gateway y tráfico sintético. Las respuestas binarias, headers técnicos y errores no descritos deben incorporarse a pruebas de contrato.
