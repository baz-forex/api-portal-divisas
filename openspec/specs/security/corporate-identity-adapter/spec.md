# corporate-identity-adapter Specification

## Purpose

Esta capacidad permite que los servicios extraídos validen y propaguen la identidad corporativa de forma verificable, conservando las decisiones de acceso del monolito sin acoplar el dominio a un proveedor o mecanismo de autenticación no aprobado.

## Requirements

### Requirement: Validar la identidad corporativa antes de autorizar

Las rutas protegidas SHALL aceptar únicamente un principal construido a partir de credenciales verificadas por el adaptador corporativo aprobado.

#### Scenario: Credencial válida

- **WHEN** una solicitud contiene una credencial válida conforme al contrato corporativo
- **THEN** el servicio construye un principal tipado y continúa con la autorización de la operación

#### Scenario: Credencial ausente

- **WHEN** una ruta protegida recibe una solicitud sin credencial
- **THEN** responde HTTP 401 con el formato aprobado y no ejecuta lógica de negocio

#### Scenario: Credencial inválida o expirada

- **WHEN** la credencial no puede verificarse, está expirada o incumple emisor o audiencia aprobados
- **THEN** responde HTTP 401 y no establece contexto autenticado

### Requirement: Aplicar autorización por operación

El servicio SHALL evaluar roles, scopes, permisos y restricciones de contexto definidos para cada endpoint antes de ejecutar una operación protegida.

#### Scenario: Principal autorizado

- **WHEN** el principal verificado tiene el permiso requerido para el endpoint
- **THEN** la operación puede ejecutarse

#### Scenario: Principal autenticado sin permiso

- **WHEN** el principal está autenticado pero no tiene autorización suficiente
- **THEN** responde HTTP 403 y no modifica datos

### Requirement: No confiar en headers de identidad no verificados

Los headers de usuario, módulo, tenant o contexto SHALL tratarse como datos no confiables hasta que sean validados o derivados del principal corporativo.

#### Scenario: Header consistente

- **WHEN** un header de contexto coincide con la identidad y permisos verificados
- **THEN** puede propagarse al caso de uso conforme al contrato aprobado

#### Scenario: Header manipulado o inconsistente

- **WHEN** un header contradice el principal o no cumple las reglas de confianza
- **THEN** la solicitud se rechaza con el estado de seguridad aprobado y no ejecuta la operación

### Requirement: Fallar de forma segura ante ausencia del adaptador

El servicio SHALL permanecer cerrado para rutas protegidas mientras no exista un adaptador corporativo aprobado y operativo.

#### Scenario: Adaptador no configurado

- **WHEN** el servicio recibe una solicitud protegida sin configuración válida del adaptador
- **THEN** no permite el acceso y registra únicamente información técnica no sensible

#### Scenario: Dependencia de identidad no disponible

- **WHEN** la validación corporativa no puede completarse por indisponibilidad de su dependencia
- **THEN** el servicio no concede acceso por fallback y responde según el contrato de indisponibilidad aprobado

### Requirement: Mantener paridad observable de seguridad

Los servicios migrados SHALL documentar y probar la correspondencia entre reglas Java, controles NestJS y respuestas 401/403 para cada endpoint incluido.

#### Scenario: Ruta pública aprobada

- **WHEN** una ruta está declarada explícitamente como pública por el contrato aprobado
- **THEN** puede procesar la solicitud sin credencial, sin heredar accidentalmente una política protegida

#### Scenario: Ruta protegida migrada

- **WHEN** una ruta catalogada como protegida se invoca bajo cada escenario negativo definido
- **THEN** conserva la decisión 401/403 y la forma de error aprobada

### Requirement: Autenticar solicitudes mediante el proxy corporativo

Las rutas protegidas SHALL validar el JWT del proxy mediante el encabezado y la clave pública configurados, sin emitir credenciales de consumidores desde el microservicio.

#### Scenario: JWT del proxy válido

- **WHEN** una solicitud contiene un JWT válido en el encabezado corporativo configurado
- **THEN** `ProxyAuthGuard` permite continuar hacia la autorización y el caso de uso

#### Scenario: JWT ausente o inválido

- **WHEN** falta el encabezado del proxy o la firma no puede verificarse
- **THEN** la API responde HTTP 401 y no ejecuta el caso de uso

### Requirement: Proteger payloads con cifrado V2 cuando el contrato lo requiera

Los endpoints configurados para cifrado SHALL requerir el identificador de acceso, resolver sus llaves autorizadas y descifrar la entrada antes del caso de uso y cifrar la respuesta después de ejecutarlo.

#### Scenario: Solicitud cifrada válida

- **WHEN** una solicitud contiene el JWT del proxy, el identificador de acceso y un payload compatible
- **THEN** el servicio resuelve las llaves, procesa el payload descifrado y devuelve la respuesta cifrada aprobada

#### Scenario: Identificador de acceso ausente o inválido

- **WHEN** falta el encabezado de seguridad o no pueden obtenerse sus llaves
- **THEN** la API rechaza la solicitud antes del caso de uso y no expone los valores recibidos

#### Scenario: Fallo de descifrado

- **WHEN** el payload no puede descifrarse o no cumple el formato criptográfico
- **THEN** la API responde con el error estandarizado y no ejecuta una escritura

### Requirement: Respetar el método criptográfico aprobado

El servicio SHALL seleccionar únicamente los métodos y versiones de cifrado declarados por el contrato del endpoint, incluyendo `EncryptionMethod` y `UseEncryptionV2` cuando correspondan.

#### Scenario: Método configurado

- **WHEN** el endpoint declara un método criptográfico soportado
- **THEN** el servicio aplica ese método para entrada y salida conforme al contrato

#### Scenario: Método no soportado

- **WHEN** una solicitud intenta usar un método no aprobado
- **THEN** la API rechaza la operación sin ejecutar lógica de negocio ni revelar material criptográfico
