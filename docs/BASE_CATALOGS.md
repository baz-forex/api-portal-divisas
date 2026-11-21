# Catálogos, controllers, bases de datos y colecciones

Inventario consolidado de los catálogos identificados en la API.

| No. | Catálogo / recurso                   | Rutas principales                                                     | Controller(s)                                     | BD                   | Colección(es)                     |
| --: | ------------------------------------ | --------------------------------------------------------------------- | ------------------------------------------------- | -------------------- | --------------------------------- |
|   1 | Países / nacionalidades              | `/api/catalogos/paises`                                               | `CatalogosController`                             | `CatalogosDivisas`   | `catNacionalidades`               |
|   2 | Estados                              | `/api/catalogos/estados`                                              | `CatalogosController`                             | `CatalogosDivisas`   | `catEstados`                      |
|   3 | Monedas / divisas                    | `/api/catalogos/divisas`                                              | `CatalogosController`, `CatalogosDao` TEF         | `CatalogosDivisas`   | `catMonedas`                      |
|   4 | Identificaciones                     | `/api/catalogos/identificaciones`                                     | `CatalogosController`                             | `CatalogosDivisas`   | `catIdentificaciones`             |
|   5 | Estatus de cotización                | `/api/catalogos/estatus-cotizacion`                                   | `CatalogosController`                             | `CatalogosDivisas`   | `catEstatusCotizacion`            |
|   6 | Tipo de operación                    | `/api/catalogos/TipoOperacion`, `/api/tef/catOperaciones/operaciones` | `CatalogosController`, `TiposOperacionController` | `CatalogosDivisas`   | `catTipoOperacion`                |
|   7 | Tipo de cliente                      | `/api/catalogos/TipoCliente`                                          | `CatalogosController`                             | `CatalogosDivisas`   | `catTipoCliente`                  |
|   8 | Perfil cliente — campañas            | `/api/catalogos/campanias/PerfilCliente`                              | `CatalogosController`                             | `CatalogosDivisas`   | `catPerfilClienteCampanias`       |
|   9 | Tipo límite — campañas               | `/api/catalogos/campanias/TipoLimite`                                 | `CatalogosController`                             | `CatalogosDivisas`   | `catTipoLimiteCampanias`          |
|  10 | Tipo acumulado — campañas            | `/api/catalogos/campanias/TipoAcumulado`                              | `CatalogosController`                             | `CatalogosDivisas`   | `catTipoAcumuladoCampanias`       |
|  11 | Estatus — campañas                   | `/api/catalogos/campanias/Estatus`                                    | `CatalogosController`                             | `CatalogosDivisas`   | `catEstatusCampanias`             |
|  12 | Rango de edades — campañas           | `/api/catalogos/campanias/RangoEdades`                                | `CatalogosController`                             | `CatalogosDivisas`   | `catRangoEdadesCampanias`         |
|  13 | Sucursales                           | `/api/tef/tablawscecostos/sucursales/*`                               | `SucursalesController`                            | `TerminalFinanciero` | `sucursales`                      |
|  14 | Regiones                             | `/api/tef/regiones/*`                                                 | `RegionesController`                              | `TerminalFinanciero` | `catRegiones`, `catRegionesOlp`   |
|  15 | Segmentos                            | `/api/tef/segmentos/*`                                                | `SegmentosController`                             | `TerminalFinanciero` | `catSegmentos`, `catSegmentosOlp` |
|  16 | Ubicación de sucursales              | Uso interno en consultas de sucursales cercanas                       | `CatalogosDao` TEF / `PromoEspacioServiceImpl`    | `CatalogosDivisas`   | `catUbicacionSucursales`          |
|  17 | Segmentación de sucursales           | `/api/core/configuraciones/segmentos-sucursal*`                       | `ConfiguracionesController`                       | `CatalogosDivisas`   | `catSegmentacionSucursal`         |
|  18 | Diccionario QR / campos restringidos | Operaciones de configuración                                          | `ConfiguracionesController`                       | `CatalogosDivisas`   | `catDiccionarioQR`                |
|  19 | Segmento de lealtad                  | `/api/core/configuraciones/perfil-cliente/lealtad`                    | `ConfiguracionesController`                       | `CatalogosDivisas`   | `catSegmentoLealtad`              |
|  20 | Segmento de rentabilidad             | `/api/core/configuraciones/perfil-cliente/rentabilidad`               | `ConfiguracionesController`                       | `CatalogosDivisas`   | `catSegmentoRentabilidad`         |
|  21 | Valores de rentabilidad              | Operaciones de perfil de cliente                                      | `ConfiguracionesController`                       | `CatalogosDivisas`   | `catValoresRentabilidad`          |
|  22 | Bloqueos operativos                  | `/api/core/clientes/bloqueos/*`                                       | `ClientesController`                              | `CatalogosDivisas`   | `catBloqueosOperativos`           |

## Fuentes principales

- [`CatalogosController.java`](../src/main/java/com/baz/divisas/api/catalogos/CatalogosController.java)
- [`CatalogosDao.java`](../src/main/java/com/baz/divisas/api/catalogos/dao/CatalogosDao.java)
- [`APIPortalDivisas.properties`](../properties/properties_des/APIPortalDivisas.properties)
