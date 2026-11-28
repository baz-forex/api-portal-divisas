# Análisis técnico del proyecto API Portal Divisas

## 1. Resumen ejecutivo

**API Portal Divisas** es un backend empresarial REST para Banco Azteca / Grupo Salinas. Centraliza servicios relacionados con la operación de divisas, tipos de cambio, sucursales, regiones, segmentos, límites operativos, clientes, campañas, configuraciones digitales y reportería.

El sistema es un **monolito modular**, organizado por dominios funcionales y con una arquitectura clásica por capas:

```text
Cliente HTTP
    ↓
Controller / REST API
    ↓
Service / lógica de negocio
    ↓
DAO / acceso a datos
    ↓
MongoDB y servicios corporativos externos
```

No es un frontend completo. La aplicación expone APIs JSON y Swagger UI; el archivo JSP existente está vacío y no se encontraron tecnologías de frontend como React, Angular o Vue.

## 2. Tipo de sistema

- Backend REST empresarial.
- Plataforma administrativa y operativa para el negocio de cambio de divisas.
- Aplicación orientada a integración con sistemas corporativos internos.
- Monolito modular desplegable como archivo WAR.
- Sistema con autenticación, autorización por roles, bitácora y provisioning SCIM.
- Aplicación principalmente stateless desde el punto de vista HTTP, con JWT.

## 3. Stack tecnológico

| Área | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Framework principal | Spring Boot 2.6.7 |
| Web síncrono | Spring MVC / `spring-boot-starter-web` |
| Cliente reactivo HTTP | Spring WebFlux / `WebClient` |
| Red reactiva | Reactor Netty |
| Seguridad | Spring Security |
| Tokens | JJWT 0.9.1, JWT RS256 |
| Base de datos | MongoDB |
| Driver MongoDB | `mongodb-driver-sync` 5.6.0 |
| Documentación | Swagger 2 / Springfox 2.9.2 |
| Logging | Log4j2 |
| Serialización | Jackson, Gson, org.json |
| Validación | Spring Boot Validation / Bean Validation |
| Archivos | Apache POI 4.1.2, Commons IO |
| Correo | JavaMail |
| Criptografía | Bouncy Castle, RSA, AES, AES-GCM |
| IAM | Personify Provisioning 1.4.1, SCIM |
| Build | Maven |
| Empaquetado | WAR |
| Servidor destino | Tomcat/JBoss |
| Pruebas | JUnit 5, Mockito, Spring Security Test |
| Cobertura | JaCoCo 0.8.8 |

## 4. Estructura del código

La raíz Java se encuentra en `src/main/java/com/baz/divisas/`.

### Módulos de negocio

- `api/tef`: gestión de tipos de cambio y operación TEF.
  - Regiones y segmentos.
  - Sucursales.
  - Spots.
  - Spreads.
  - Límites.
  - Estrategias.
  - Competencias.
  - Parametría.
  - PromoEspacio.
  - Tarjetas masivas.
  - Catálogos operativos.
- `api/core`: funcionalidades centrales.
  - Clientes.
  - Certificación de sucursales.
  - Beneficios, campañas, seguros y cupones.
  - Configuraciones.
  - Reportería.
- `api/digital`: configuraciones y reportería del canal digital.
- `api/catalogos`: catálogos generales.
- `api/usuarios`: usuarios, módulos, menús y permisos.
- `api/auth`: autenticación de clientes y emisión de tokens.
- `api/bitacora`: registro de acciones.
- `api/siex`: configuración SIEX y campos restringidos.
- `api/apigee`: integración con Apigee.
- `scim`: endpoints y almacenamiento para provisioning SCIM.
- `security`: filtros JWT y servicios de identidad.
- `general`: configuración transversal, DTOs, excepciones, utilidades, filtros y factorías.

### Organización interna habitual

Los dominios suelen separar sus componentes en:

```text
controller/
service/
dao/
model/
dto/
util/
```

También existe una separación entre servicios de interfaz e implementaciones en varios módulos.

## 5. Arquitectura de ejecución

La clase de entrada es:

```text
com.baz.divisas.ApiPortalDivisasApplication
```

Está anotada con `@SpringBootApplication` y extiende `SpringBootServletInitializer`, por lo que soporta:

- Ejecución local mediante Spring Boot.
- Despliegue tradicional en contenedor Java mediante WAR.

El contexto de aplicación configurado es:

```text
/ApiPortalDivisas
```

El descriptor `jboss-web.xml` define el mismo contexto y `jboss-deployment-structure.xml` excluye los subsistemas de logging y JAX-RS del servidor para evitar conflictos con las dependencias de la aplicación.

## 6. API REST

Los endpoints de negocio se agrupan principalmente bajo:

```text
/ApiPortalDivisas/api/**
```

Los endpoints SCIM se agrupan bajo:

```text
/ApiPortalDivisas/scim/**
```

### Principales grupos de endpoints

```text
/api/auth
/api/catalogos
/api/usuarios
/api/bitacora
/api/core/clientes
/api/core/beneficios
/api/core/configuraciones
/api/core/sucursales
/api/digital/configuraciones
/api/digital/reporteria
/api/siex/configuraciones
/api/tef/regiones
/api/tef/region-segmento
/api/tef/region-sucursal-segmento
/api/tef/spots
/api/tef/spreads
/api/tef/limites-*
/api/tef/parametria
/api/tef/sucursales
/api/tef/competencias
/scim/v2/**
```

Se utilizan los métodos HTTP `GET`, `POST`, `PUT`, `PATCH` y `DELETE`. Algunos endpoints permiten cargas multipart y descargas de archivos.

### Formatos de respuesta

La aplicación utiliza DTOs genéricos como `ResponseApi`, `EstatusResponse` y `Paginacion`. Las respuestas suelen estar normalizadas con:

- Estatus de operación.
- Mensajes o errores.
- Datos de respuesta.
- Información de paginación cuando aplica.

## 7. Persistencia y datos

La persistencia se realiza con el driver nativo de MongoDB, sin Spring Data MongoDB.

Características observadas:

- Uso directo de `MongoClient`, `MongoDatabase` y `MongoCollection`.
- Uso combinado de `Document` y POJOs.
- Codec registries para mapear modelos Java.
- Una factoría Singleton (`FactoryMongoDB`) administra la conexión.
- Los nombres de bases de datos y colecciones se cargan desde propiedades externas.

Se identifican diferentes bases funcionales, entre ellas:

- Autenticación y usuarios.
- Catálogos.
- Middleware.
- Terminal financiero.
- Monitoreo y bitácora.
- SIEX.

Esto indica que la aplicación funciona como una capa de acceso y orquestación sobre varios conjuntos de datos MongoDB relacionados con distintos sistemas corporativos.

### Bases de datos configuradas

La aplicación utiliza una misma conexión MongoDB (`URIMONGODB`) y selecciona distintas bases mediante propiedades de configuración. Las siguientes bases están declaradas en `APIPortalDivisas.properties` para los ambientes de desarrollo, QA y producción:

| Base de datos | Propiedad de configuración | Uso funcional observado |
|---|---|---|
| `Autenticacion` | `AUTHBD` | Usuarios, módulos, clientes API, llaves y recursos SCIM. |
| `Middleware` | `MIDDLEWAREBD` | Clientes, operaciones, notificaciones, cotizaciones, parametría, beneficios, seguros y datos de integración. |
| `MonitoreoClientes` | `MONITOREOCLIENTESBD` | Bitácoras y monitoreo de acciones relacionadas con clientes. |
| `CatalogosDivisas` | `CATALOGOSBD` | Catálogos generales: monedas, países, estados, identificaciones, estatus y catálogos de campañas. |
| `Incidencias` | `INCIDENCIASBD` | Incidencias BCD y reportería asociada. |
| `TerminalFinanciero` | `TERMINALFINANCIEROBD` | Regiones, segmentos, sucursales, spots, spreads, límites, tipos de cambio, parametría y operación TEF/OLP. |
| `SIEX` | `SIEXBD` | Campos restringidos y configuraciones del sistema SIEX. |

#### Resumen por ambiente

| Ambiente | Bases de datos declaradas |
|---|---|
| Desarrollo | `Autenticacion`, `Middleware`, `MonitoreoClientes`, `CatalogosDivisas`, `Incidencias`, `TerminalFinanciero`, `SIEX` |
| QA | `Autenticacion`, `Middleware`, `MonitoreoClientes`, `CatalogosDivisas`, `Incidencias`, `TerminalFinanciero`, `SIEX` |
| Producción | `Autenticacion`, `Middleware`, `MonitoreoClientes`, `CatalogosDivisas`, `Incidencias`, `TerminalFinanciero`, `SIEX` |

La diferencia entre ambientes está principalmente en el servidor, replica set, TLS y demás parámetros de conexión; los nombres lógicos de las bases se mantienen iguales.

#### Lista completa de colecciones por base

La siguiente lista corresponde a los valores efectivos declarados en `APIPortalDivisas.properties`. Los nombres se mantienen iguales en desarrollo, QA y producción.

##### `Autenticacion`

- `bitacoraLlavesAplicacion`
- `llavesAplicacion`
- `usuariosPortalDivisas`
- `modulosPortalDivisas`
- `authClientesApiDivisas`
- `scimUsuarios`
- `scimGrupos`

##### `Middleware`

- `negociosMW`
- `notificacionesCajeroMW`
- `bitacoraNotificacionesCajeroMW`
- `bitacoraCargaActualizaClientes`
- `bitacoraActualizacionClientes`
- `parametriaCotizacionesMW`
- `campanias`
- `parametriaRascadito`
- `ganadoresPromocionDigital`
- `cuestionarios`
- `respuestasCuestionarios`
- `cuestionariosHistorico`
- `bitacoraCuestionarios`
- `operacionesMW`
- `cotizaciones`
- `seguros`
- `bitacoraClientesAltoRiesgo`
- `clientesAltoRiesgo`
- `bitacoraCuponesTA`
- `cuponesTA`
- `operacionesGolOro`
- `inventarioMundialVerde`
- `empleadosBloqueados`
- `inventarioVeranoMundialista`
- `operacionesVeranoMundialista`
- `cuponesBeneficios`
- `bitacoraCuponesCRM`
- `cuponesMasSalud`
- `bitacoraCuponesMasSalud`
- `bitacoraCuponesAlianzas`
- `inventarioCostalSanta`
- `operacionesCostalSanta`
- `clientesAgrupados`

##### `MonitoreoClientes`

- `bitacoraPortalDivisas`

##### `CatalogosDivisas`

- `catDiccionarioQR`
- `catNacionalidades`
- `catEstados`
- `catSegmentacionSucursal`
- `catIdentificaciones`
- `catEstatusCotizacion`
- `catSegmentoLealtad`
- `catSegmentoRentabilidad`
- `catValoresRentabilidad`
- `catUbicacionSucursales`
- `catTipoCliente`
- `catPerfilClienteCampanias`
- `catTipoLimiteCampanias`
- `catTipoAcumuladoCampanias`
- `catEstatusCampanias`
- `catRangoEdadesCampanias`
- `catBloqueosOperativos`

##### `Incidencias`

- `incidenciasMW`

##### `TerminalFinanciero`

- `catLimitesDivisas`
- `catLimitesClientes`
- `catLimitesRegiones`
- `catLimitesRegionesSegmentos`
- `catLimitesSucursal`
- `catEstrategia`
- `catMonedas`
- `catTipoOperacion`
- `catRegiones`
- `catSegmentos`
- `catSpot`
- `catSpreads`
- `catRegionesEstrategias`
- `catRegionesSegmentosEstrategias`
- `catRegionSegmento`
- `catRegionSucursal`
- `sucursales`
- `catSucursales`
- `competenciasPromoEspacio`
- `catRegionSegmentoSucursal`
- `parametriaGeneral`
- `topeCaja`
- `divisa`
- `cajero`
- `cajaAnclada`
- `cajaPrincipal`
- `catRegionesOlp`
- `catSegmentosOlp`

##### `SIEX`

- `catCamposRestringidos`

> Nota: esta relación documenta las colecciones declaradas en la configuración y su agrupación según las clases `PropBD`, `PropBDCatalogos` y `PropBDSiex`. No implica que todas sean consultadas en cada ejecución o por todos los endpoints.

## 8. Integraciones externas

La aplicación utiliza `WebClient` y Reactor Netty para comunicarse con servicios internos y externos. Se observan integraciones con conceptos y sistemas como:

- Middleware.
- Apigee.
- Tesorería.
- Terminal financiero.
- Servicios digitales.
- PromoEspacio.
- Servicios corporativos de llaves.

El código también utiliza clientes HTTP configurados con resolución DNS explícita mediante Reactor Netty en algunos servicios.

## 9. Seguridad

### Autenticación

- JWT stateless.
- Firma RS256 mediante llave privada configurada externamente.
- Token enviado en el header `Authorization` con esquema Bearer.
- El JWT incluye información de usuario, IP y User-Agent.
- El filtro valida que la IP y el navegador coincidan con los valores del token.
- Los secretos de clientes API se validan mediante BCrypt.

### Autorización

Las rutas se protegen por roles:

```text
ROLE_DSI_SCIM
ROLE_DIVISAS_PORTAL
```

La configuración permite acceso público a endpoints de token, validación de estatus, autenticación de clientes, Swagger y recursos públicos. El resto requiere autenticación.

### Criptografía

Se identifican utilidades para:

- AES.
- AES-GCM.
- RSA-OAEP.
- HMAC.
- Manejo de llaves públicas y privadas.
- Cifrado de parámetros, archivos y datos de integraciones digitales.

### Observaciones de seguridad

1. CORS está configurado con origen comodín (`*`) y credenciales habilitadas. Debe revisarse y restringirse por ambiente.
2. JJWT 0.9.1 y Spring Security basado en `WebSecurityConfigurerAdapter` son componentes antiguos.
3. La validación por IP puede causar problemas detrás de proxies, balanceadores o NAT si no se normaliza la IP real.
4. Las llaves privadas y secretos deben permanecer fuera del repositorio y gestionarse mediante un almacén seguro.
5. Conviene revisar la política de logging para asegurar que no se registren tokens, secretos ni datos sensibles.

## 10. Manejo de errores y observabilidad

Existe un `@RestControllerAdvice` global que centraliza errores como:

- Credenciales inválidas.
- Acceso denegado.
- Errores de validación.
- Headers faltantes.
- JSON inválido.
- Métodos HTTP no permitidos.
- Errores criptográficos.
- Errores internos no controlados.

La aplicación utiliza Log4j2 y un filtro de logging para registrar información de las peticiones. También existe una utilidad transversal para bitácoras de negocio.

## 11. Archivos y reportes

Se soportan operaciones de:

- Carga masiva de clientes.
- Carga de competencias.
- Carga de parametrías.
- Carga de notificaciones.
- Descarga de reportes Excel.
- Generación de archivos CSV.
- Generación de documentos DOCX.
- Descarga de cuestionarios y reportes operativos.

La configuración de multipart limita los archivos a 8 MB:

```properties
spring.servlet.multipart.max-file-size=8MB
spring.servlet.multipart.max-request-size=8MB
```

## 12. SCIM e IAM

El proyecto contiene una implementación SCIM para provisioning de usuarios y grupos. Incluye endpoints de:

- Service Provider Configuration.
- Resource Types.
- Schemas.
- Usuarios y grupos.
- Operaciones bulk.
- Creación, consulta, actualización, patch y eliminación.

La integración se apoya en la dependencia `personify-provisioning` y utiliza MongoDB como almacenamiento.

## 13. Configuración por ambiente

Se identifican archivos de configuración para:

```text
properties/properties_des/
properties/properties_qa/
properties/properties_pro/
```

El archivo principal de cada ambiente es `APIPortalDivisas.properties`.

La configuración contiene URLs, nombres de bases y colecciones, parámetros de negocio, algoritmos criptográficos, rutas de llaves y datos de integración.

Por seguridad, este documento no reproduce valores de conexión, secretos ni contenido de llaves.

## 14. Build y despliegue

El proyecto utiliza Maven y genera un WAR:

```bash
mvn clean package
```

Ejecución local:

```bash
mvn spring-boot:run
```

Pruebas:

```bash
mvn test
```

Verificación y cobertura:

```bash
mvn verify
```

El nombre final configurado para el artefacto es:

```text
ApiPortalDivisas.war
```

El proyecto está preparado principalmente para despliegue on-premise en Tomcat/JBoss.

## 15. Pruebas

Se observan pruebas para:

- Controllers.
- Services.
- DAOs.
- DTOs y modelos.
- Seguridad.
- Utilidades criptográficas.
- Integraciones HTTP mediante MockWebServer.
- Reportes.
- Módulos TEF.
- Módulos Core.
- Módulos Digitales.
- SCIM.

El proyecto contiene aproximadamente 635 clases Java de producción y 188 clases Java de pruebas, según el conteo de archivos fuente existente durante la revisión.

JaCoCo se integra en Maven para generar reportes de cobertura.

## 16. Aspectos visuales

No existe una interfaz visual de negocio implementada dentro de este repositorio.

### Elementos visuales disponibles

- Swagger UI generado automáticamente.
- Respuestas JSON para consumidores de la API.
- Descargas de archivos Excel, CSV y DOCX.
- `index.jsp` vacío.

### Elementos no encontrados

- Aplicación SPA.
- Componentes React, Angular o Vue.
- Hojas CSS propias.
- Diseño responsive.
- Sistema visual de navegación.
- Vistas HTML de negocio.
- Recursos estáticos relevantes.

La experiencia visual depende de los clientes consumidores de la API y de Swagger para exploración técnica.

## 17. Fortalezas

- Separación clara por dominios de negocio.
- Cobertura amplia de funcionalidades de divisas.
- Integración con sistemas corporativos.
- Uso de DTOs y manejo global de errores.
- Soporte para autenticación, autorización y SCIM.
- Pruebas automatizadas en numerosos módulos.
- Soporte para cargas masivas y generación de reportes.
- Configuración separada por ambiente.
- Soporte de despliegue como WAR en infraestructura tradicional.

## 18. Riesgos y oportunidades de mejora

### Dependencias y modernización

- Migrar de Spring Boot 2.6 a una versión soportada.
- Evaluar migración de `javax.*` a `jakarta.*` según la versión objetivo.
- Sustituir Springfox por springdoc-openapi.
- Migrar desde `WebSecurityConfigurerAdapter` a `SecurityFilterChain`.
- Actualizar JJWT y dependencias criptográficas.

### Arquitectura

- Reducir el acoplamiento directo de los servicios con MongoDB.
- Centralizar configuración de clientes externos.
- Separar módulos de alto crecimiento si el dominio y el despliegue lo justifican.
- Homogeneizar convenciones de nombres y rutas REST.
- Considerar interfaces de repositorio para facilitar pruebas y evolución.

### Operación

- Incorporar health checks y métricas estandarizadas.
- Definir timeouts, retries y circuit breakers para integraciones externas.
- Revisar límites y validación de cargas masivas.
- Mejorar trazabilidad distribuida mediante correlation IDs.
- Validar cierre y ciclo de vida del `MongoClient`.

### Seguridad

- Restringir CORS por ambiente y consumidor.
- Gestionar secretos con un vault o servicio equivalente.
- Revisar cifrados heredados y configuraciones criptográficas.
- Evitar incluir información sensible en logs.
- Revisar el tratamiento de IP y User-Agent detrás de proxies.

### Documentación

- Mantener sincronizadas la versión del README y la versión de Maven.
- Publicar contratos OpenAPI actualizados.
- Documentar variables de configuración sin exponer secretos.
- Agregar diagramas de contexto, componentes y flujo de autenticación.

## 19. Conclusión

API Portal Divisas es una aplicación backend empresarial grande y especializada, orientada a soportar la operación administrativa y técnica de un ecosistema de divisas. Su diseño actual corresponde a un monolito modular Java/Spring con acceso directo a múltiples bases MongoDB y numerosas integraciones corporativas.

La solución cubre un espectro funcional amplio y cuenta con una base importante de pruebas, pero presenta oportunidades de modernización en dependencias, seguridad, observabilidad, documentación y desacoplamiento de infraestructura.
