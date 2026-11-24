# Tasks

## 1. Baseline del microservicio

- [x] 1.1 Crear el baseline NestJS/TypeScript estricto en `/Users/phinder2024/Devs/BAZ/backend/divisas/microservice-dvs-catalogs`, con scripts de build, lint, type-check y pruebas; verificar que el proyecto vacío compila y ejecuta su smoke test sin usar `microservice-dvs-catalogs-old`.
- [x] 1.2 Configurar las dependencias corporativas aprobadas para proxy, errores, health, logger, swagger y persistencia; verificar instalación reproducible y ausencia de credenciales o tokens en archivos versionados.
- [x] 1.3 Definir validación de configuración para headers de proxy, conexión, base y nombres de colecciones; verificar que el arranque rechaza configuración incompleta y que los nombres de headers por defecto son `token` y `x-id-acceso`.
- [x] 1.4 Configurar bootstrap, correlation ID, health/readiness, logging estructurado, filtro de excepciones y documentación OpenAPI; verificar respuesta de health y redacción de headers sensibles en logs.
- [x] 1.5 Configurar reglas de dependencias de arquitectura para impedir imports de NestJS, Mongoose, HTTP o seguridad desde `domain`; verificar la regla con el análisis estático del proyecto.

## 2. Caracterización del contrato Java

- [x] 2.1 Crear el inventario versionado de las 14 operaciones sobre 13 rutas, incluyendo método, casing, request, response, estados y colección; verificarlo contra `CatalogosController.java`, `CatalogosServiceImpl.java` y la colección `CATALOGOS` de Bruno.
- [x] 2.2 Construir fixtures sintéticos de Java para resultados con datos, listas vacías, identificadores inexistentes, duplicados, validaciones y errores de dependencias; verificar que no contienen tokens, secretos, PII ni datos productivos.
- [x] 2.3 Implementar el contrato de respuesta compatible con `ResponseApi` y el comportamiento de `ResponseInterceptor`, incluyendo omisión de campos vacíos y campos no deterministas; verificar respuestas exitosas y de error sin envolventes anidadas.
- [x] 2.4 Implementar normalización de comparación para ignorar únicamente folio, timestamps, IDs generados y correlation IDs documentados; verificar que cualquier diferencia funcional o de casing falla la comparación.

## 3. Dominio y puertos de aplicación

- [x] 3.1 Crear modelos de dominio para catálogo, país/nacionalidad, rango de edades, divisa y límites iniciales sin dependencias de framework; verificar invariantes de identificador, descripción, activo, restricciones y rangos mediante pruebas unitarias.
- [x] 3.2 Definir puertos de lectura y escritura de catálogos, auditoría Bitácora, propagación a regiones y propagación a segmentos; verificar que los casos de uso solo dependen de abstracciones.
- [x] 3.3 Implementar casos de uso de las 12 consultas con las reglas Java de filtros, ordenamientos, exclusión de `OLP` y proyecciones; verificar cada caso con fixtures de resultados y ausencia de información.
- [x] 3.4 Implementar el caso de uso de actualización de divisa limitando la escritura al campo `activo`; verificar actualización existente, identificador inexistente, repetición idempotente y ausencia de modificación de otros campos.
- [x] 3.5 Implementar el caso de uso de alta de divisa con validación, detección de duplicados, límites `410`–`413`, estado inicial inactivo y contexto de operación; verificar que una repetición o concurrencia no duplica la divisa.
- [x] 3.6 Implementar compensación y estados de etapa para fallos de Bitácora, inserción, regiones y segmentos; verificar que ningún fallo se reporta como éxito y que los escenarios de región/segmento dejan evidencia correlacionada.

## 4. Persistencia y dependencias externas

- [x] 4.1 Implementar adaptadores y mappers explícitos para `catNacionalidades`, `catEstados`, `catMonedas`, `catIdentificaciones`, `catEstatusCotizacion`, `catTipoOperacion`, `catTipoCliente` y catálogos de campañas; verificar forma de documento y DTO mediante pruebas de adaptador.
- [x] 4.2 Implementar las consultas con sus filtros, proyecciones y ordenamientos legacy, incluyendo `id_cliente_amigo`, `compra.estatus_banca` y exclusión de `OLP`; verificar los pipelines y operaciones con dobles de persistencia y fixtures.
- [x] 4.3 Implementar el adaptador de escritura de `catMonedas` con detección de duplicidad, actualización de `activo` y compensación de inserción; verificar que no se ejecutan escrituras cuando la validación o duplicidad falla.
- [x] 4.4 Implementar adaptadores aislados para Bitácora, regiones y segmentos con contratos de éxito, error, timeout y respuesta parcial; verificar que los puertos no exponen modelos ni colecciones ajenas al dominio.
- [x] 4.5 Configurar la persistencia corporativa sin ejecutar una matriz de compatibilidad DocumentDB; verificar únicamente que el adaptador recibe la configuración esperada y que sus pruebas contractuales usan fixtures controlados.

## 5. Presentación HTTP y autorización

- [x] 5.1 Crear controllers para las 13 rutas exactas y 14 operaciones, preservando mayúsculas y rutas de campañas; verificar mediante pruebas de routing que `/api/tef/catOperaciones/*` no está registrado.
- [x] 5.2 Crear DTOs de entrada para actualización y alta con las validaciones efectivas del Java AS-IS; verificar HTTP 400, mensajes y ausencia de efectos cuando el payload es inválido.
- [x] 5.3 Integrar el guard corporativo del proxy para `token` y `x-id-acceso`; verificar solicitudes autorizadas, header ausente, header rechazado y que el caso de uso no se ejecuta ante rechazo.
- [x] 5.4 Integrar presenter/interceptor y filtro de excepciones para conservar HTTP, `codigo`, `mensaje`, `folio`, `info`, `detalles` y `resultado` según el caso Java; verificar GET, PUT, POST, 404, 400 y 500.
- [x] 5.5 Añadir documentación OpenAPI para las 14 operaciones y los dos headers de seguridad sin publicar valores reales; verificar que el documento no incluye rutas TEF excluidas ni secretos.

## 6. Pruebas contractuales y de paridad

- [x] 6.1 Crear suite contractual para las 14 operaciones usando escenarios de `/Users/phinder2024/Devs/BAZ/backend/divisas/coleccion-bruno-apd/LEGACY/CATALOGOS`; verificar método, URL, casing, headers, body y respuesta normalizada.
- [x] 6.2 Crear matriz negativa de autorización y errores para token/x-id-acceso ausentes o rechazados, payload inválido, datos inexistentes, duplicidad, timeout y dependencia fallida; verificar estados 400/401/403/404/500 conforme al contrato aprobado.
- [x] 6.3 Crear pruebas E2E detrás del gateway/proxy en integración o preproducción; verificar que la autorización real del proxy y la respuesta del servicio coinciden con las fixtures sintéticas.
- [x] 6.4 Crear harness de comparación Java/NestJS para lecturas con los mismos datos y headers; verificar HTTP, headers permitidos, body normalizado, errores, lecturas observables y latencia sin comparar campos no deterministas.
- [x] 6.5 Crear pruebas controladas de actualización y alta con snapshots antes/después; verificar escritura única, efectos sobre `catMonedas`, regiones, segmentos y Bitácora, compensación y reconciliación.
- [x] 6.6 Verificar que shadow traffic solo replica lecturas o llamadas idempotentes autorizadas; demostrar mediante logs y métricas que PUT/POST no se duplican.

## 7. Operación, rollback y aprobación

- [x] 7.1 Implementar métricas y logs de volumen, latencia, errores, timeouts, retries, compensaciones, diferencias de paridad y fallback, con redacción de credenciales; verificar campos `requestId`, `correlationId` y `traceId`.
- [x] 7.2 Preparar procedimiento de convivencia con Java, incluyendo routing inicial, comparación de lecturas, habilitación controlada de escrituras y observación; verificarlo en integración sin retirar el monolito.
- [x] 7.3 Ensayar rollback mediante gateway hacia Java y congelamiento de escrituras NestJS; verificar que no se realizan borrados automáticos y que se conserva evidencia para reconciliación.
- [x] 7.4 Obtener revisión del equipo de operación interna sobre Catalogos, Bitácora, TEF, Core, Datos y Seguridad; verificar que quedan aprobados los contratos de dependencia, logs y reconciliación.
- [x] 7.5 Obtener aprobación del gerente de área y del líder técnico para paridad, ventana de observación, SLO/NFR y cutover; verificar que los criterios de bloqueo y responsables quedan registrados.
- [x] 7.6 Ejecutar la batería final de type-check, lint, arquitectura, unit, contract, integration y E2E; verificar que todos los escenarios de `reference-catalogs` están cubiertos y que no se declara compatibilidad DocumentDB no verificada.

## Workflow follow-up

- Archivar el cambio únicamente después de completar las tareas, reconciliar las especificaciones y obtener las aprobaciones requeridas.
- Ejecutar la verificación OpenSpec antes de solicitar el cutover.
