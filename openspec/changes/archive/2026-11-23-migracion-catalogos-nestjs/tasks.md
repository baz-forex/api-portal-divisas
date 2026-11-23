# Tasks

## 1. Contratos, ownership y decisiones corporativas

- [x] 1.1 Construir la matriz endpoint → método → controller → servicio → DAO → base → colección → seguridad para los 22 recursos de `docs/BASE_CATALOGS.md`; verificar que cada entrada tenga estado, fuente de evidencia y discrepancias contra `docs/api-portal-divisas.yaml`.
- [x] 1.2 Clasificar cada recurso como referencia, Terminal Financiero, configuración operativa, interno o perteneciente a otro bounded context; verificar la aprobación de ownership por Divisas y Terminal Financiero.
- [x] 1.3 Reconciliar Swagger 2.0, controllers Java, servicios, modelos, respuestas y pruebas; verificar que cada diferencia de ruta, payload, código HTTP o `ResponseApi` tenga decisión y aprobación del propietario de la API.
- [x] 1.4 Reconciliar el mecanismo documentado de JWT del proxy, `ProxyAuthGuard`, `x-id-acceso`, `UseEncryptionV2`, `EncryptionMethod` y sus variables con Seguridad/APIGEE; verificar la matriz Java → NestJS y la aprobación de los headers y errores 401/403. Referencia para la implementacón del mecanismo de acceso a la API `docs/AUTH-API-MECHANISM.md`.
- [x] 1.5 Confirmar acceso seguro a AWS CodeArtifactory, rotación de cualquier credencial existente y compatibilidad de `@gs/logger@0.1.1` con Node.js 26.12.1/NestJS 11; verificar instalación sin secretos versionados. Usa el perfil AWS `APP_COINPRO_QA` en este equipo local para acceder a CodeArtifactory para descargar, instalar y confirmar las dependencias corporativas.
- [x] 1.6 Confirmar imagen base Docker, política de ejecución y reglas corporativas de SonarQube, Checkmarx y cobertura; verificar la entrega de los criterios al equipo responsable de plataforma.
- [x] 1.7 Registrar en el inventario del template las dependencias de ejecución `@gs/auth@0.1.1`, `@gs/crypto@0.1.1`, `@gs/documentdb@0.1.1`, `@gs/errors@0.1.1`, `@gs/health@0.1.1`, `@gs/logger@0.1.1`, `@gs/proxy@0.1.1`, `@gs/swagger@0.1.2`, `@gs/utils@0.1.1` y `@gsa/decorators@~0.0.7`; verificar versiones en el lockfile. Usa el archivo docs/install-dependencies.sh para descargar, instalar y confirmar las dependencias corporativas.
- [x] 1.8 Configurar `.npmrc` para AWS CodeArtifactory mediante variables de entorno y agregar `pnpm.overrides` para fijar `@gs/utils` en `0.1.1`; verificar que no existan credenciales, tokens ni llaves en el archivo o en el historial. Usa el archivo docs/install-dependencies.sh para descargar, instalar y confirmar las dependencias corporativas.
- [x] 1.9 Resolver y fijar versiones compatibles de NestJS 11, TypeScript, `reflect-metadata`, `class-validator`, `class-transformer`, RxJS y Mongoose 9; verificar instalación limpia y ausencia de conflictos de peer/transitive dependencies.

## 2. Template NestJS y runtime local

- [x] 2.1 Crear el proyecto base con NestJS 11.x soportado, Node.js 26.12.1, pnpm 11.5.3, Mongoose 9.x y TypeScript strict; verificar instalación reproducible, lockfile, compilación y ejecución local con todas las dependencias corporativas declaradas. El proyecto template base debe quedar en esta ubicación (fuera de este proyecto): ~/Devs/BAZ/backend/divisas. Antes de realizar alguna implementación debes generar una copia reusable para otros microservicios.
- [x] 2.2 Crear la estructura hexagonal de `shared`, `health` y módulos por capacidad; verificar que dominio y aplicación no importen NestJS, Mongoose 9, HTTP ni paquetes de infraestructura.
- [x] 2.3 Implementar configuración validada y arranque fail-fast; crear `.env.example` con variables de proxy, cifrado, MongoDB local/DocumentDB, AWS/S3 y logging sin valores reales; verificar que una configuración válida habilite el proceso y una inválida impida aceptar tráfico sin imprimir secretos.
- [x] 2.4 Implementar el envelope de respuesta y filtro de errores compatible con `ResponseApi`; verificar códigos, folio, mensajes, detalles, errores inesperados y ausencia de stack traces en respuestas. Consultar proyecto de referencia [Reference project](#reference-project).
- [x] 2.5 Configurar Mongoose 9 mediante puertos, mappers y repositorios; verificar conexión local, cierre ordenado, filtros tipados, validadores de actualización y que fallas de persistencia no expongan credenciales ni documentos completos.
- [x] 2.6 Crear Docker local y documentación de ejecución sin incluir infraestructura AWS, secretos ni configuración productiva; verificar build, arranque, health checks y ejecución de pruebas desde el contenedor.
- [x] 2.7 Publicar documentación OpenAPI del servicio y su relación con el contrato Swagger 2.0 legacy; verificar rutas, métodos, seguridad, esquemas y comparación contractual.
- [x] 2.8 Integrar `@gs/health` mediante `HealthModule` y `@gs/errors` mediante el filtro corporativo de excepciones HTTP; verificar pruebas de integración de liveness, readiness, errores estandarizados y `ResponseApi`. Consultar proyecto de referencia [Reference project](#reference-project).
- [x] 2.9 Integrar `@gs/swagger` con `SwaggerModule` y sus esquemas corporativos de respuestas y seguridad; verificar generación del documento Swagger y pruebas de integración de rutas, DTOs y esquemas. Consultar proyecto de referencia [Reference project](#reference-project).
- [x] 2.10 Integrar `@gs/documentdb` con configuración seleccionable entre MongoDB local y Amazon DocumentDB; verificar conexión, TLS/certificados cuando correspondan, readiness y aislamiento de credenciales por entorno. Consultar proyecto de referencia [Reference project](#reference-project).

## 3. Logging y salud

- [x] 3.1 Integrar `@gs/logger@0.1.1` mediante `WinstonModule`, `loggerConfig` y `loggerHttpConfig`; verificar inicialización, transports soportados y compatibilidad con NestJS 11/Node.js 26. Consultar proyecto de referencia [Reference project](#reference-project).
- [x] 3.2 Implementar sanitización central para headers, body, query, errores y excepciones; verificar con pruebas que no se registren tokens, contraseñas, credenciales, datos bancarios ni PII en ningún transport. Consultar proyecto de referencia [Reference project](#reference-project).
- [x] 3.3 Implementar liveness y readiness diferenciados; verificar que una dependencia caída afecte readiness sin convertir una falla de dependencia en un falso estado de liveness.

## 4. Identidad, proxy y cifrado

- [x] 4.1 Definir el puerto de identidad, principal tipado y contrato de autorización, incluyendo la presencia de `@gs/auth` y `@gsa/decorators` aunque el módulo inicial no los utilice directamente; verificar compilación con un adaptador fake. Consultar proyecto de referencia [Reference project](#reference-project).
- [x] 4.2 Integrar `@gs/proxy` mediante `ProxyModule` y `ProxyAuthGuard`; verificar autenticación del proxy, encabezado configurable, firma inválida, token ausente, errores 401 y ausencia de emisión de credenciales desde el servicio. Consultar proyecto de referencia [Reference project](#reference-project).
- [x] 4.3 Integrar `@gs/crypto` mediante `CryptoModule`, `UseEncryptionV2` y `EncryptionMethod`; verificar obtención de llaves con `x-id-acceso`, descifrado de entrada, cifrado de salida, método no soportado y rechazo antes del caso de uso. Consultar proyecto de referencia [Reference project](#reference-project).
- [x] 4.4 Implementar autorización por endpoint, rol, scope, permiso y contexto según la matriz aprobada; verificar escenarios 401/403 y ausencia de ejecución de negocio cuando el acceso falla.
- [x] 4.5 Validar `token`, `x-id-aceso` y cualquier header adicional contra el principal; verificar rechazo de headers manipulados o inconsistentes. Consultar proyecto de referencia [Reference project](#reference-project).
- [x] 4.6 Documentar y ejecutar pruebas de paridad de seguridad con Seguridad/APIGEE; verificar que ninguna ruta protegida tenga fallback permisivo.

## 5. Catálogos de referencia

- [x] 5.1 Crear entidades, value objects, DTOs, casos de uso y puertos para países, estados, divisas, identificaciones, estatus de cotización, tipos de operación, tipos de cliente y catálogos de campañas; verificar reglas unitarias y separación de capas. El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 5.2 Crear schemas, mappers y repositorios Mongoose 9 para las colecciones de `CatalogosDivisas`; verificar nombres de colección, proyecciones, filtros tipados, ordenamientos, índices y comportamiento de actualización contra la evidencia Java. El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 5.3 Implementar endpoints de consulta bajo las rutas legacy confirmadas; verificar HTTP 200, `ResponseApi`, orden observable, campos y HTTP 404 cuando no existan resultados. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 5.4 Implementar validación de solicitudes de alta y actualización; verificar HTTP 400 sin escritura para identificadores o campos requeridos ausentes. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 5.5 Documentar invariantes e idempotencia del alta de divisa y sus escrituras en `catMonedas`, regiones, segmentos y bitácora; verificar aprobación antes de habilitar la implementación. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 5.6 Implementar actualización y alta de divisas mediante un caso de uso orquestador, transacción o compensación aprobada; verificar duplicidad, éxito, fallos parciales, rollback y no repetición de efectos. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 5.7 Ejecutar pruebas de contrato y paridad de todos los endpoints de referencia contra fixtures Java anonimizados; verificar payloads, códigos, errores y orden de resultados. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).

## 6. Catálogos de Terminal Financiero

- [x] 6.1 Confirmar ownership, colecciones, índices, relaciones y rutas de sucursales, regiones y segmentos; verificar matriz aprobada por Terminal Financiero. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 6.2 Crear schemas, mappers y repositorios Mongoose 9 para `sucursales`, `catRegiones`, `catRegionesOlp`, `catSegmentos` y `catSegmentosOlp` cuando correspondan al alcance aprobado; verificar consultas, filtros tipados e índices contra fixtures controlados. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 6.3 Implementar las rutas TEF confirmadas para sucursales, regiones y segmentos; verificar parámetros obligatorios, respuesta compatible y comportamiento de ausencia. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 6.4 Implementar y probar la representación de relaciones entre sucursales, regiones, segmentos y divisas; verificar que relaciones inconsistentes no produzcan datos inventados. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 6.5 Ejecutar pruebas de contrato y paridad de Terminal Financiero; verificar diferencias documentadas y aprobación del propietario de la API. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).

## 7. Configuración operativa y escrituras cruzadas

- [x] 7.1 Clasificar ubicación, segmentación, diccionario QR, perfiles, rentabilidad y bloqueos como público, interno u otro bounded context; verificar owner, consumidores y contrato por recurso. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 7.2 Implementar únicamente las consultas públicas aprobadas; verificar que recursos internos se consuman mediante puertos y no se expongan accidentalmente. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 7.3 Implementar validación y autorización de operaciones aprobadas de bloqueos, segmentación y configuración; verificar HTTP 400/403 y ausencia de escritura en escenarios negativos. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 7.4 Proteger escrituras cruzadas mediante contratos explícitos y adaptadores; verificar que no exista acceso directo a colecciones ajenas sin aprobación. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).
- [x] 7.5 Ejecutar pruebas de integración de configuración y documentar recursos aplazados; verificar que cada aplazamiento tenga motivo, owner pendiente y criterio de reanudación. Consultar proyecto de referencia [Reference project](#reference-project). El código nuevo que vayas generando debes dejarlo en el nuevo proyecto creado [New project](#new-project).

## 8. Calidad, compatibilidad y migración gradual

- [x] 8.1 Configurar Vitest para unitarias, integración y e2e con scripts neutrales para la plataforma CI corporativa; verificar ejecución local y reporte de cobertura.
- [x] 8.2 Crear fixtures anonimizados y una suite de comparación Java/NestJS; verificar respuestas, errores, validaciones y resultados para cada endpoint migrado.
- [x] 8.3 Ejecutar análisis ESLint y preparar configuración compatible con SonarQube y Checkmarx sin crear pipelines; verificar que los reportes puedan ser consumidos por la plataforma autorizada.
- [x] 8.4 Validar consultas, índices, actualizaciones y health checks en el entorno DocumentDB administrado por AWS; verificar compatibilidad y registrar cualquier diferencia sin modificar infraestructura. **Cierre autorizado con limitación: AWS Cloud es responsable del ambiente corporativo y no existe un entorno disponible para esta iteración; la validación externa queda como gate de despliegue.**
- [x] 8.5 Definir runbook de coexistencia, criterios de éxito, desvío gradual y rollback hacia Java; verificarlo mediante una prueba controlada o simulación aprobada.
- [x] 8.6 Obtener aprobación final de Seguridad, arquitectura, propietario de API, consumidores afectados y responsables funcionales; verificar que no queden bloqueadores abiertos para el primer endpoint. **Cierre administrativo autorizado por el solicitante; las aprobaciones externas y el cutover productivo permanecen como gates explícitos.**

## Workflow follow-up

- Archivar el cambio después de revisar y aprobar todos los artefactos.
- Iniciar `openspec apply` únicamente mediante una solicitud explícita posterior.

## Reference project

Proyecto de referencia que contiene implementaciones productivas: /Users/phinder2024/Devs/BAZ/backend/TDM_COINPRO/microservice-tdm-superviser.

## New project

Todo el código nuevo que vas generando debes dejarlo en el nuevo microservicio `/Users/phinder2024/Devs/BAZ/backend/divisas/microservice-dvs-catalogs`
