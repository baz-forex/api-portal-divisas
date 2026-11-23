# nestjs-service-foundation Specification

## Purpose

Esta capacidad define el comportamiento transversal mínimo de los servicios NestJS extraídos para que puedan ejecutarse de forma segura, observable, comprobable y compatible con los contratos del monolito durante la migración progresiva.

## Requirements

### Requirement: Validar configuración antes de aceptar tráfico

El servicio SHALL validar sus variables de configuración obligatorias antes de iniciar el servidor HTTP y SHALL fallar de forma explícita si la configuración es inválida.

#### Scenario: Configuración válida

- **WHEN** el servicio inicia con todas las variables requeridas y valores válidos
- **THEN** queda disponible para aceptar tráfico y expone su estado de salud

#### Scenario: Configuración inválida

- **WHEN** falta una variable obligatoria o su valor no cumple las reglas
- **THEN** el proceso no acepta tráfico y comunica el error sin imprimir secretos

### Requirement: Mantener una respuesta de error segura y compatible

El servicio SHALL devolver errores con una forma consistente y compatible con `ResponseApi` cuando el endpoint migrado lo requiera, sin exponer stack traces, credenciales ni datos sensibles.

#### Scenario: Error controlado

- **WHEN** una validación o regla de negocio rechaza una solicitud
- **THEN** la respuesta contiene el código HTTP, mensaje y detalles aprobados para el contrato

#### Scenario: Error inesperado

- **WHEN** ocurre una excepción no controlada
- **THEN** la API responde con un error seguro, genera un folio correlacionable y no expone detalles internos

### Requirement: Redactar información sensible en todos los logs

Los logs SHALL impedir la exposición de contraseñas, tokens, credenciales, datos bancarios y datos personales en headers, cuerpos, parámetros, errores y transportes.

#### Scenario: Solicitud con secreto

- **WHEN** una solicitud contiene un token, contraseña o credencial
- **THEN** el valor se omite o redacta antes de enviarse a cualquier transporte de logging

#### Scenario: Error con datos sensibles

- **WHEN** una excepción contiene datos personales o bancarios
- **THEN** el evento registrado conserva contexto técnico suficiente sin incluir el valor sensible

### Requirement: Exponer liveness y readiness diferenciados

El servicio SHALL distinguir entre salud del proceso y disponibilidad de dependencias críticas.

#### Scenario: Proceso vivo con dependencia caída

- **WHEN** el proceso está funcionando pero una dependencia crítica no está disponible
- **THEN** liveness permanece saludable y readiness indica que el servicio no debe recibir tráfico

#### Scenario: Servicio listo

- **WHEN** el proceso y sus dependencias críticas están disponibles
- **THEN** readiness responde saludable y permite recibir tráfico

### Requirement: No aceptar tráfico antes de completar el arranque

El servicio SHALL permanecer fuera de servicio hasta completar configuración, conexión de dependencias críticas y registro de sus componentes.

#### Scenario: Arranque incompleto

- **WHEN** una dependencia crítica no ha sido inicializada
- **THEN** el servicio no declara readiness ni procesa solicitudes de negocio

### Requirement: Exponer health checks corporativos

El servicio SHALL exponer el contrato corporativo de salud para distinguir proceso vivo de servicio listo y verificar sus dependencias críticas.

#### Scenario: Health check disponible

- **WHEN** un consumidor autorizado consulta los endpoints de salud
- **THEN** recibe el formato corporativo y el estado correcto de liveness o readiness

#### Scenario: Dependencia crítica no disponible

- **WHEN** una dependencia crítica falla
- **THEN** readiness refleja indisponibilidad sin exponer secretos ni detalles internos

### Requirement: Estandarizar excepciones HTTP

Las excepciones de validación, autenticación, autorización, ausencia y errores internos SHALL transformarse al mecanismo corporativo y conservar el contrato aprobado del endpoint.

#### Scenario: Excepción conocida

- **WHEN** un caso de uso genera una excepción HTTP conocida
- **THEN** la respuesta contiene el código, mensaje, detalles y folio aprobados

#### Scenario: Excepción desconocida

- **WHEN** ocurre un error no clasificado
- **THEN** la respuesta usa el estado interno estandarizado y no revela stack trace ni secretos

### Requirement: Publicar documentación Swagger corporativa

La documentación de la API SHALL incluir los esquemas corporativos de éxito, error, seguridad y respuestas definidos para el servicio.

#### Scenario: Generación de documentación

- **WHEN** se genera el documento Swagger del servicio
- **THEN** contiene las rutas confirmadas, sus DTOs, seguridad y esquemas corporativos

#### Scenario: Contrato incompatible

- **WHEN** un endpoint no puede representarse sin cambiar el contrato legacy
- **THEN** la diferencia queda identificada y no se publica silenciosamente como compatible

### Requirement: Seleccionar backend de persistencia

El servicio SHALL seleccionar mediante configuración validada si utiliza MongoDB local o Amazon DocumentDB, sin mezclar credenciales ni certificados entre entornos.

#### Scenario: MongoDB local seleccionado

- **WHEN** la configuración indica MongoDB local
- **THEN** el servicio conecta al backend local y expone readiness conforme a su disponibilidad

#### Scenario: Amazon DocumentDB seleccionado

- **WHEN** la configuración indica DocumentDB y proporciona sus parámetros válidos
- **THEN** el servicio utiliza la configuración de DocumentDB y no requiere valores productivos en el repositorio

#### Scenario: Backend no válido

- **WHEN** la selección o sus parámetros son inválidos
- **THEN** el servicio no inicia tráfico de negocio y comunica el error de configuración sin secretos
