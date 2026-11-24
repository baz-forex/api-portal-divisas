# Spec Delta

## Purpose

Proporcionar el catálogo de referencia de Divisas mediante las 14 operaciones
legacy de `/api/catalogos`, preservando el comportamiento observable del
monolito Java durante la convivencia y el cutover hacia NestJS.

## ADDED Requirements

### Requirement: Exposición compatible de rutas Catalogos

El sistema SHALL exponer las 14 operaciones de `/api/catalogos` con los mismos
métodos, rutas, casing y semántica observable del monolito Java.

#### Scenario: Rutas de consulta

- **WHEN** un consumidor solicita cualquiera de las rutas GET de paises, estados, divisas, identificaciones, estatus de cotización, tipos de operación, tipos de cliente o campañas
- **THEN** el sistema responde mediante la ruta exacta legacy, incluyendo las mayúsculas de `TipoOperacion`, `TipoCliente`, `PerfilCliente`, `TipoLimite`, `TipoAcumulado`, `Estatus` y `RangoEdades`

#### Scenario: Rutas de escritura

- **WHEN** un consumidor envía `PUT /api/catalogos/divisas` o `POST /api/catalogos/divisas/registrar`
- **THEN** el sistema procesa la operación correspondiente sin cambiar método, ruta ni casing

#### Scenario: Exclusión de rutas TEF

- **WHEN** un consumidor solicita una ruta bajo `/api/tef/catOperaciones`
- **THEN** esa ruta no forma parte de esta capacidad ni es atendida por el microservicio de Catalogos

### Requirement: Lectura de catálogos con semántica Java

El sistema SHALL devolver los registros de las colecciones legacy con los
filtros, proyecciones, ordenamientos, exclusiones y nombres de campos observados
en Java.

#### Scenario: Catálogos ordenados

- **WHEN** se consulta paises, estados, divisas, tipos o campañas con registros
- **THEN** el resultado conserva el orden Java correspondiente y los campos observables de cada modelo

#### Scenario: Divisa OLP excluida

- **WHEN** se consulta `GET /api/catalogos/divisas`
- **THEN** el resultado excluye el documento cuyo identificador sea `OLP`

#### Scenario: Identificaciones proyectadas

- **WHEN** se consulta `GET /api/catalogos/identificaciones`
- **THEN** cada resultado usa el identificador derivado de `id_cliente_amigo` y conserva `descripcion` y `activo`

#### Scenario: Estatus de cotización proyectado

- **WHEN** se consulta `GET /api/catalogos/estatus-cotizacion`
- **THEN** el resultado expone la descripción derivada de `compra.estatus_banca` y el estado activo conforme al comportamiento Java

#### Scenario: Resultado sin información

- **WHEN** una consulta no encuentra registros
- **THEN** el sistema responde HTTP 404 con la semántica `SIN_RESULTADOS` y el detalle de ausencia definido por Java

### Requirement: Envolvente y errores compatibles

El sistema SHALL conservar la forma observable de `ResponseApi` y los estados,
mensajes, códigos, detalles y campos opcionales que produce Java, permitiendo
únicamente diferencias documentadas en folio, timestamps, IDs o correlation IDs
no deterministas.

#### Scenario: Respuesta exitosa con resultado

- **WHEN** una operación termina correctamente y produce datos
- **THEN** la respuesta contiene la envolvente compatible, `mensaje`, `folio` y `resultado`, sin exponer propiedades internas de persistencia no presentes en Java

#### Scenario: Respuesta exitosa sin resultado

- **WHEN** una actualización termina correctamente sin payload de resultado
- **THEN** la respuesta conserva la forma de éxito compatible con Java y no agrega un `resultado` artificial

#### Scenario: Petición inválida

- **WHEN** el cuerpo de una operación de escritura incumple las validaciones efectivas del monolito
- **THEN** el sistema responde HTTP 400 con la estructura y mensaje de error compatibles, sin ejecutar escrituras parciales no autorizadas

#### Scenario: Error interno o dependencia fallida

- **WHEN** ocurre un error no recuperable en persistencia, Bitácora, regiones o segmentos
- **THEN** el sistema responde HTTP 500 con el detalle contractual permitido y registra la correlación sin incluir secretos, tokens ni PII

### Requirement: Autorización mediante proxy corporativo

El sistema SHALL aceptar las operaciones de Catalogos únicamente cuando el
proxy corporativo haya verificado los headers `token` y `x-id-acceso`, usando la
configuración corporativa equivalente a la referencia aprobada.

#### Scenario: Solicitud autorizada

- **WHEN** la solicitud llega con `token` y `x-id-acceso` válidos verificados por el proxy
- **THEN** la operación continúa hacia el caso de uso solicitado

#### Scenario: Token ausente o inválido

- **WHEN** falta `token` o el proxy lo rechaza
- **THEN** la solicitud no alcanza la operación de negocio y conserva el resultado HTTP de no autorización definido por el contrato de gateway

#### Scenario: Identificador de acceso ausente o inválido

- **WHEN** falta `x-id-acceso` o el proxy lo rechaza
- **THEN** la solicitud no alcanza la operación de negocio y conserva el resultado de autorización denegada definido por el contrato de gateway

#### Scenario: Credenciales no se registran

- **WHEN** una solicitud autorizada o rechazada se registra para diagnóstico
- **THEN** los valores de `token`, `x-id-acceso` y cualquier credencial se redactan completamente

### Requirement: Actualización idempotente del estatus de divisa

El sistema SHALL actualizar únicamente el campo `activo` de la divisa
identificada por `id` y SHALL conservar el resultado Java para identificadores
existentes o inexistentes.

#### Scenario: Actualización de divisa existente

- **WHEN** se envía un catálogo válido con un `id` existente y un valor `activo`
- **THEN** se actualiza ese estado, no se modifican otros campos y se devuelve éxito compatible

#### Scenario: Actualización de divisa inexistente

- **WHEN** se envía un catálogo válido cuyo `id` no existe
- **THEN** no se inserta ningún documento y se devuelve `SIN_RESULTADOS` con HTTP 404

#### Scenario: Repetición de actualización

- **WHEN** se repite la misma actualización con el mismo `id` y estado
- **THEN** el estado final permanece igual y la respuesta conserva la semántica de actualización existente

### Requirement: Registro completo de una nueva divisa

El sistema SHALL registrar una divisa nueva con sus límites iniciales y
propagar las operaciones iniciales a regiones y segmentos, además de registrar
la acción en Bitácora, manteniendo los resultados y compensaciones observables
del flujo Java.

#### Scenario: Alta exitosa

- **WHEN** se solicita el alta de una divisa inexistente con `id` y `descripcion` válidos
- **THEN** se crea la divisa inactiva con límites para `410`, `411`, `412` y `413`, se registra Bitácora, se propaga a regiones y segmentos y se responde HTTP 200

#### Scenario: Alta duplicada

- **WHEN** se solicita el alta de una divisa cuyo `id` ya existe
- **THEN** no se crean nuevos registros y se responde HTTP 400 con el detalle de duplicidad compatible con Java

#### Scenario: Fallo al propagar a regiones

- **WHEN** la inserción de la divisa termina pero no se puede propagar a regiones
- **THEN** el sistema ejecuta la compensación definida, responde HTTP 500 y deja evidencia correlacionada del fallo

#### Scenario: Fallo al propagar a segmentos

- **WHEN** la propagación a regiones termina pero la propagación a segmentos falla
- **THEN** el sistema ejecuta la compensación definida, responde HTTP 500 y no declara el alta como exitosa

#### Scenario: Repetición concurrente del alta

- **WHEN** dos solicitudes intentan registrar simultáneamente la misma divisa
- **THEN** como máximo una solicitud declara alta exitosa y las restantes conservan la semántica de duplicidad o error controlado sin duplicar documentos

### Requirement: Paridad verificable y rollback de convivencia

El sistema SHALL permitir comparar el comportamiento Java y NestJS en un
ambiente controlado antes del cutover y SHALL permitir devolver el routing al
monolito sin duplicar escrituras durante shadow traffic.

#### Scenario: Comparación de lecturas

- **WHEN** una solicitud de lectura se ejecuta contra Java y NestJS con los mismos datos y headers
- **THEN** se comparan HTTP, headers permitidos, cuerpo normalizado, errores, latencia y lecturas observables

#### Scenario: Shadow sin dual-write

- **WHEN** se habilita shadow traffic durante la convivencia
- **THEN** solo se duplican lecturas e invocaciones idempotentes autorizadas, nunca el alta de divisas ni actualizaciones que escriban datos

#### Scenario: Activación de rollback

- **WHEN** se detecta una divergencia crítica, pérdida de datos, bypass de autorización o escritura divergente
- **THEN** el gateway deja de enrutar nuevas solicitudes a NestJS, devuelve el tráfico al monolito y conserva la evidencia para reconciliación aprobada
