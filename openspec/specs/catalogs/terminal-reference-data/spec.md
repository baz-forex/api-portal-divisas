# terminal-reference-data Specification

## Purpose

Esta capacidad expone la información de referencia de Terminal Financiero para sucursales, regiones y segmentos, conservando sus rutas, relaciones relevantes y comportamiento observable sin mezclar su ownership con los catálogos de Divisas.

## Requirements

### Requirement: Consultar sucursales

La API SHALL exponer las consultas de sucursales documentadas para Terminal Financiero y SHALL conservar la representación de sus datos públicos.

#### Scenario: Consulta de sucursales con resultados

- **WHEN** un consumidor autorizado consulta una ruta de sucursales con registros disponibles
- **THEN** la API responde HTTP 200 con un `ResponseApi` compatible y los registros esperados

#### Scenario: Consulta de sucursales sin resultados

- **WHEN** una consulta de sucursales no encuentra registros
- **THEN** la API responde con el estado y la estructura de ausencia definidos por el contrato legacy

### Requirement: Consultar regiones

La API SHALL exponer las consultas documentadas de regiones y SHALL distinguir las fuentes o variantes de región que formen parte del contrato observable.

#### Scenario: Región encontrada

- **WHEN** el consumidor solicita regiones con datos disponibles
- **THEN** la respuesta contiene las regiones y los campos públicos esperados por los consumidores legacy

#### Scenario: Región no encontrada

- **WHEN** el consumidor solicita una región o conjunto de regiones sin información
- **THEN** la API responde con el resultado vacío compatible con Java y no inventa registros

### Requirement: Consultar segmentos

La API SHALL exponer las consultas documentadas de segmentos y SHALL conservar los identificadores, descripciones, estatus y relaciones que sean parte del contrato.

#### Scenario: Segmentos disponibles

- **WHEN** el consumidor autorizado consulta segmentos existentes
- **THEN** la API responde HTTP 200 con la misma forma observable de los segmentos legacy

#### Scenario: Segmentos no disponibles

- **WHEN** no existen segmentos para el criterio solicitado
- **THEN** la API responde con el código, mensaje y detalles de ausencia compatibles con el contrato legacy

### Requirement: Preservar rutas TEF y compatibilidad de representación

Las consultas SHALL conservar las rutas bajo `/api/tef/tablawscecostos/sucursales/*`, `/api/tef/regiones/*` y `/api/tef/segmentos/*` que estén confirmadas en la matriz de contrato.

#### Scenario: Consumidor utiliza ruta legacy

- **WHEN** un consumidor invoca una ruta TEF confirmada
- **THEN** la API mantiene método HTTP, parámetros, nombres de campos y envoltura de respuesta esperados

#### Scenario: Campo requerido ausente

- **WHEN** una consulta requiere un parámetro que no fue proporcionado
- **THEN** la API responde HTTP 400 y no ejecuta una consulta ambigua o sin límites

### Requirement: Proteger relaciones entre catálogos TEF

Las operaciones que devuelvan relaciones entre sucursales, regiones, segmentos y divisas SHALL preservar los identificadores y reglas de asociación observados en el contrato legacy.

#### Scenario: Relación válida

- **WHEN** una consulta obtiene una entidad TEF con relaciones existentes
- **THEN** la respuesta incluye únicamente relaciones válidas y compatibles con la fuente de datos autorizada

#### Scenario: Relación inconsistente

- **WHEN** una relación requerida no puede resolverse o presenta datos incompatibles
- **THEN** la API no presenta una relación inventada y registra el fallo según las reglas de logging seguro aprobadas
