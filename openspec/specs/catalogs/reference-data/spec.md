# reference-data Specification

## Purpose

Esta capacidad proporciona los catálogos de referencia que consumen las APIs de Divisas, manteniendo sus rutas, resultados, validaciones y respuestas observables durante la coexistencia entre Java y NestJS.

## Requirements

### Requirement: Consultar catálogos de referencia

La API SHALL exponer consultas compatibles para países/nacionalidades, estados, divisas, identificaciones, estatus de cotización, tipos de operación, tipos de cliente y catálogos de campañas.

#### Scenario: Consulta con información

- **WHEN** un consumidor autorizado consulta un catálogo que contiene registros
- **THEN** la API responde con HTTP 200 y un `ResponseApi` cuyo `resultado` contiene los registros del catálogo

#### Scenario: Consulta de catálogo vacío

- **WHEN** un consumidor autorizado consulta un catálogo sin registros
- **THEN** la API responde con HTTP 404 y conserva el código, mensaje y detalle de ausencia de información del contrato legacy

### Requirement: Preservar rutas y representación de catálogos

La API SHALL conservar las rutas legacy documentadas para los catálogos de referencia y SHALL representar los campos públicos de cada registro sin renombrarlos ni eliminar valores observables.

#### Scenario: Consulta por ruta legacy

- **WHEN** el consumidor invoca una ruta de catálogo documentada bajo `/portal-divisas/configuraciones/api/catalogos`
- **THEN** la respuesta mantiene la forma y los nombres de campos esperados por el consumidor legacy

#### Scenario: Orden observable

- **WHEN** un catálogo cuya implementación legacy ordena sus registros es consultado
- **THEN** los registros se devuelven en el mismo orden observable, salvo una diferencia aprobada en el contrato

### Requirement: Actualizar el estatus de una divisa

La API SHALL permitir actualizar el estatus de una divisa mediante el contrato legacy y SHALL distinguir entre una divisa encontrada y una inexistente.

#### Scenario: Divisa existente

- **WHEN** se solicita actualizar una divisa existente con un estatus válido
- **THEN** la API actualiza el estatus y responde HTTP 200 con el contrato `ResponseApi`

#### Scenario: Divisa inexistente

- **WHEN** se solicita actualizar una divisa cuyo identificador no existe
- **THEN** la API responde HTTP 404 con la estructura legacy de ausencia de información

### Requirement: Registrar una divisa con sus relaciones iniciales

El registro de una divisa SHALL conservar las reglas legacy de duplicidad, valores iniciales y propagación a las estructuras relacionadas antes de considerarse exitoso.

#### Scenario: Registro exitoso

- **WHEN** se registra una divisa inexistente con los datos requeridos
- **THEN** la API crea la divisa, inicializa sus relaciones requeridas y responde HTTP 200

#### Scenario: Divisa duplicada

- **WHEN** se registra una divisa cuyo identificador ya existe
- **THEN** la API rechaza la operación con HTTP 400 y comunica la duplicidad mediante el contrato legacy

#### Scenario: Fallo de propagación

- **WHEN** la divisa se crea pero falla su actualización en una estructura relacionada
- **THEN** la API no declara la operación como exitosa y responde HTTP 500 con un detalle compatible con el comportamiento legacy

### Requirement: Validar solicitudes de modificación

Las operaciones de alta y actualización SHALL rechazar solicitudes que no contengan los campos requeridos por el contrato de catálogo.

#### Scenario: Solicitud sin identificador requerido

- **WHEN** una solicitud de alta o actualización omite el identificador requerido
- **THEN** la API responde HTTP 400 sin ejecutar una escritura

#### Scenario: Solicitud válida

- **WHEN** la solicitud contiene los campos requeridos y tipos compatibles
- **THEN** la API permite que la operación continúe hacia la regla de negocio correspondiente
