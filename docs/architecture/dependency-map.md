# Mapa de dependencias endpoint → dominio → datos

## Método

`docs/assessment/api-inventory.md` contiene la extracción completa de mappings. Este documento resume las dependencias por capacidad y marca como pendiente cualquier enlace que no pueda demostrarse con imports y llamadas estáticas.

| Contexto | Controller(s) representativos | Service(s) | DAO(s) | Bases/colecciones observadas | Externos |
|---|---|---|---|---|---|
| Catalogos | `CatalogosController` | `CatalogosServiceImpl` | `CatalogosDao`; `RegionesDao`; `SegmentosDao` | CatalogosDivisas; regiones/segmentos de TerminalFinanciero según configuración | Bitacora |
| Bitacora | `BitacoraController` | `BitacoraServiceImpl` | `BitacoraDao` | bitácora configurada | invocado por múltiples servicios |
| Core clientes | `ClientesController` | `ClientesServiceImpl`, `MiddlewareService` | `ClientesDao` | Middleware; CatalogosDivisas | Middleware, correo |
| Core configuración | `ConfiguracionesController` | `ConfiguracionesServiceImpl`, `PerfilDeClientesServiceImpl` | `ConfiguracionesDao`, `PerfilDeClientesDao` | Middleware, CatalogosDivisas | correo/archivos |
| Core beneficios | controllers de campañas, cupones, seguros y rascadito | servicios de beneficios | DAOs correspondientes | Middleware | correo/archivos |
| Digital | `ConfiguracionesDigitalController`, `ReporteriaController` | servicios Digital | `ConfiguracionesDigitalDao`, `CotizacionesDao` | Middleware y CatalogosDivisas | exportación de archivos |
| SIEX | `ConfiguracionesController` | `ConfiguracionesServiceImpl` | `ConfiguracionesDao` | SIEX | archivos |
| TEF | controllers de spots, regiones, segmentos, límites, sucursales y parametría | servicios TEF | DAOs por subcapacidad | TerminalFinanciero | Apigee, Tesorería, AWS interno |
| Usuarios/Auth/SCIM | `UsuariosController`, `AuthController`, SCIM controllers | servicios de usuarios/auth, `MongoStorage` | `UsuariosDao`, `ModulosDao`, `AuthClientDao` | Autenticacion | IAM/SCIM |

## Accesos cruzados relevantes

- `CatalogosServiceImpl` actualiza regiones y segmentos al crear una divisa.
- Core configuración lee catálogos de nacionalidades e identificaciones.
- Servicios TEF mantienen estructuras de divisas, regiones, segmentos y límites en TerminalFinanciero.
- Bitacora se invoca desde Catalogos, Core y TEF.
- `CotizacionesDao` usa una colección de Middleware y una colección de estatus en CatalogosDivisas.

## Gaps

La resolución final por método DAO/colección para los 337 mappings requiere análisis de llamadas dinámicas, configuración de colecciones y pruebas de contrato. No se debe usar la columna `No determinada` como autorización para extraer.
