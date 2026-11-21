# Spec Delta

## Purpose

Esta capacidad controla los recursos de configuración y operación asociados a catálogos, evitando exponer como catálogo independiente información interna, de clientes o de operación hasta que su ownership y contrato hayan sido aprobados.

## ADDED Requirements

### Requirement: Clasificar recursos de configuración

Cada recurso de ubicación, segmentación, diccionario QR, perfil, rentabilidad o bloqueo SHALL tener una clasificación explícita como público, interno o perteneciente a otro bounded context antes de migrarse.

#### Scenario: Recurso con ownership confirmado

- **WHEN** arquitectura y el responsable funcional confirman owner, consumidores y contrato
- **THEN** el recurso puede incorporarse a un módulo de extracción con reglas y pruebas propias

#### Scenario: Recurso sin ownership confirmado

- **WHEN** no existe owner o contrato aprobado
- **THEN** el recurso no se expone como endpoint nuevo ni se realizan escrituras contra sus colecciones

### Requirement: Mantener compatibilidad de consultas de configuración

Las consultas públicas de configuración que sean aprobadas SHALL conservar sus rutas, parámetros, representación y códigos HTTP observables durante la coexistencia.

#### Scenario: Consulta pública aprobada

- **WHEN** un consumidor autorizado invoca una consulta de configuración aprobada
- **THEN** recibe la envoltura `ResponseApi` y los datos compatibles con el contrato Java

#### Scenario: Consulta de uso interno

- **WHEN** una funcionalidad interna necesita un recurso clasificado como interno
- **THEN** el recurso se consume mediante un puerto autorizado y no se convierte automáticamente en una API pública

### Requirement: Proteger operaciones de bloqueos y segmentación

Las operaciones de bloqueos, segmentación y configuración SHALL validar autorización, entrada y ownership antes de modificar datos.

#### Scenario: Operación autorizada

- **WHEN** una solicitud válida proviene de un principal con permiso para el recurso
- **THEN** la operación se ejecuta y devuelve el contrato aprobado

#### Scenario: Principal sin permiso

- **WHEN** el principal no cuenta con el permiso requerido
- **THEN** la API responde HTTP 403 y no modifica datos

#### Scenario: Entrada inválida

- **WHEN** la solicitud no cumple las reglas de validación del recurso
- **THEN** la API responde HTTP 400 y no modifica datos

### Requirement: Evitar escrituras cruzadas no autorizadas

Un módulo de configuración SHALL no modificar colecciones propiedad de clientes, operaciones, regiones o segmentos sin un contrato de aplicación aprobado.

#### Scenario: Dependencia de otro contexto

- **WHEN** una operación requiere actualizar datos propiedad de otro contexto
- **THEN** utiliza un puerto o contrato explícito y registra la dependencia para aprobación

#### Scenario: Dependencia no aprobada

- **WHEN** no existe contrato para una escritura cruzada
- **THEN** la operación se rechaza o se mantiene fuera de la extracción y no realiza una escritura directa
