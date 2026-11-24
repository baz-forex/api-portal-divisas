# Inventario de integraciones AS-IS

## Evidencia observada

| Integración | Componentes | Evidencia | Datos/uso | Riesgo |
|---|---|---|---|---|
| Middleware | `ClientesServiceImpl`, `MiddlewareService`, `ClientesDao`, configuraciones Digital/Core | `src/main/java/com/baz/divisas/api/core/clientes/services/MiddlewareService.java:59-198` | búsquedas, consultas y actualizaciones de clientes; parametría y cotizaciones en `Middleware` | no se observa una política común de timeout/retry en el código revisado |
| Apigee on-premise | `SpotService`, `SpotsServiceImpl`, DTOs `TokenApigee` | `api/tef/spots/services/impl/SpotService.java:31-82`, `SpotsServiceImpl.java:405-441` | token y tipos de cambio de Tesorería | logs de URL, headers/respuestas y token; revisar redacción |
| Apigee AWS interno | `ParametriaService`, `TiposCambioService`, `ParametriaSucursalesImpl` | `api/tef/parametria/services/ParametriaService.java:88-108`, `TiposCambioService.java:148-231` | token, llaves y gestión de parametría/tipos de cambio | dependencia de headers técnicos y endpoints configurados externamente |
| Tesorería | `SpotService`, servicios TEF de tipos de cambio | `SpotService.java:62-82`, `TiposCambioService.java:148-231` | consultas/registro de tipos de cambio | acoplamiento síncrono y comportamiento no documentado en OpenAPI |
| Correo | `MailService`, `MailServiceCertificacion`, `CampaniasDao`, `ClientesServiceImpl` | `general/util/MailService.java`, `MailServiceCertificacion.java` | notificaciones y certificaciones | se ejecuta con `new Thread`; no hay garantía durable de entrega |
| SCIM/IAM | controllers SCIM, `MongoStorage`, dependencia `personify-provisioning` | `pom.xml:154-158`, `scim/rest/*`, `scim/storage/impl/MongoStorage.java` | provisioning de recursos y token SCIM | ownership de usuarios y roles debe aprobarse antes de extraer |
| Proxy/gateway | headers `Authorization`, `x-id-usuario`, `x-id-modulo`, `x-id-acceso` | `general/model/Constant.java:33-38` y servicios TEF | propagación de identidad/acceso | no está demostrado qué headers transforma el gateway |
| MongoDB | `FactoryMongoDB`, DAOs nativos | `general/factory/FactoryMongoDB.java:14-45` | siete bases funcionales observadas localmente | URI y nombres provienen de propiedades externas; no se copian valores |

## Configuración

Las clases `PropApp`, `PropBD`, `PropBDCatalogos`, `PropBDSiex`, `PropClientes` y `PropPromoEspacio` cargan nombres de variables y URLs desde archivos de propiedades externos. Este documento no reproduce valores, credenciales, llaves ni URIs.

## Pendientes

1. Obtener contratos de timeout, retry, circuit breaker, rate limit y autenticación de cada dependencia.
2. Confirmar si Apigee/gateway termina TLS, transforma headers o reintenta solicitudes.
3. Definir el ownership de los contratos Middleware y Tesorería.
4. Sustituir la asincronía basada en threads por un mecanismo aprobado antes de extraer flujos con correo.
