# Design

## Context

El monolito Java implementa `CatalogosController`, `CatalogosServiceImpl` y
`CatalogosDao` sobre la base `CatalogosDivisas`. Las consultas aplican
ordenamientos, proyecciones y exclusiones específicas; las escrituras afectan
`catMonedas` y, durante el alta, también regiones, segmentos y Bitácora.

El destino
`/Users/phinder2024/Devs/BAZ/backend/divisas/microservice-dvs-catalogs` está
vacío. La referencia corporativa disponible muestra un interceptor de respuesta
con `mensaje`, `folio` y `resultado`, y control de acceso mediante
`@gs/proxy`, con nombres de header configurables. El gateway/proxy es el
responsable de verificar `token` y `x-id-acceso`.

La fuente de verdad funcional será el comportamiento ejecutable Java. Bruno
aportará las solicitudes y escenarios de prueba en
`/Users/phinder2024/Devs/BAZ/backend/divisas/coleccion-bruno-apd`.

## Goals / Non-Goals

**Goals:**

- Implementar las 14 operaciones de `/api/catalogos` en el microservicio
  autorizado.
- Mantener compatibilidad observable de rutas, casing, payloads, estados,
  errores y ordenamientos.
- Aislar el dominio de NestJS, Mongoose/DocumentDB, HTTP y detalles del proxy
  mediante puertos y adaptadores.
- Ejecutar el alta de divisa como una orquestación explícita con idempotencia,
  compensación y evidencia correlacionada.
- Permitir comparación Java/NestJS para lecturas y cutover controlado para
  escrituras.
- Mantener el rollback mediante el gateway sin retirar inmediatamente el
  monolito.

**Non-Goals:**

- Implementar `/api/tef/catOperaciones/*`.
- Migrar, backfillear o transformar datos existentes.
- Duplicar escrituras durante shadow traffic.
- Cambiar el proveedor de identidad, el contrato del proxy o el mecanismo de
  autorización.
- Validar compatibilidad entre versiones de MongoDB y DocumentDB; el motor
  configurado se asumirá operativo y la falta de verificación quedará como
  riesgo.
- Cambiar infraestructura AWS, gateway, CI/CD o IaC.

## Decisions

### 1. Módulo de capacidad y límites de dependencia

Se creará un módulo `catalogs` con separación de dominio, aplicación,
infraestructura y presentación. El dominio representará catálogos, divisas,
límites iniciales y resultados de operación sin importar NestJS, Mongoose,
DocumentDB ni librerías de autenticación.

La aplicación dependerá de puertos para:

- consultas y escrituras de catálogos;
- auditoría de Bitácora;
- propagación de divisas a regiones;
- propagación de divisas a segmentos;
- generación de respuestas y correlación operacional.

Los adaptadores de infraestructura implementarán esos puertos. Aunque varias
colecciones estén en la misma base, el acceso a regiones, segmentos y Bitácora
no se expondrá directamente al dominio ni se reutilizarán modelos de otros
contextos.

**Alternativa descartada:** copiar el controller, service y DAO Java como
clases NestJS con acceso directo a todas las colecciones. Esto preservaría
acoplamiento, impediría probar compensaciones aisladamente y consolidaría
dependencias cruzadas no gobernadas.

### 2. Contrato HTTP y casing legacy

La capa de presentación declarará exactamente las 13 rutas y sus 14
operaciones, incluyendo el casing heredado. Los DTO HTTP y mappers conservarán
los nombres observables (`id`, `_id` cuando aplique, `descripcion`, `activo`,
restricciones y rangos) sin exponer documentos BSON.

La respuesta se centralizará en un adaptador de contrato. Este compondrá la
envolvente compatible con `ResponseApi` y el comportamiento de
`ResponseInterceptor`, evitando envolventes anidadas. Los errores de validación,
ausencia, autorización y dependencias se traducirán a los estados y mensajes
Java caracterizados, mientras que `folio`, timestamps, IDs y correlation IDs se
compararán como valores no deterministas documentados.

**Alternativa descartada:** devolver directamente documentos de persistencia o
usar una envolvente NestJS genérica sin caracterización Java. Rompería nombres
de campos, omisión de propiedades y consumidores legacy.

### 3. Seguridad delegada al proxy

Cada controller aplicará el guard corporativo equivalente a `@gs/proxy` y
usará configuración para los nombres de `token` y `x-id-acceso`. El servicio no
creará un mecanismo alternativo de JWT, no confiará en claims no verificados y
no aceptará acceso directo sin el contexto de autorización definido por el
proxy.

Los headers sensibles se excluirán o redactarán en logs. Las pruebas
contractuales cubrirán ausencia, rechazo y aceptación de ambos headers mediante
fixtures sintéticos; no se almacenarán tokens reales.

**Alternativa descartada:** reimplementar autenticación dentro del dominio o
mantener la cadena JWT del monolito en el nuevo servicio. La autorización
efectiva del destino ya está delegada al proxy corporativo.

### 4. Persistencia y mapeos explícitos

Los adaptadores de persistencia usarán la configuración corporativa de
conexión, nombres de base y colecciones. Cada colección tendrá un mapper
documento-entidad/DTO propio. Las consultas reproducirán:

- orden por `descripcion` para paises y estados;
- orden por `_id` para divisas, tipos y campañas;
- exclusión de `OLP` para divisas;
- proyección de `id_cliente_amigo` para identificaciones;
- proyección de `compra.estatus_banca` para estatus de cotización;
- forma histórica de rangos de edades y restricciones.

Las consultas y operaciones de persistencia se cubrirán con pruebas de contrato
del adaptador y fixtures sintéticos. No se realizará una matriz de
compatibilidad ni una verificación independiente del motor DocumentDB en este
cambio.

**Alternativa descartada:** compartir documentos de base de datos como modelos
de dominio. Haría observable la estructura interna y dificultaría una futura
separación de ownership.

### 5. Orquestación del alta de divisa

El caso de uso de alta seguirá una secuencia explícita:

1. validar el request según las reglas efectivas Java;
2. comprobar existencia por `_id`;
3. registrar la acción en Bitácora;
4. construir la divisa inactiva con límites `410` a `413`;
5. insertar en `catMonedas`;
6. propagar la divisa a regiones mediante su puerto;
7. propagarla a segmentos mediante su puerto;
8. devolver éxito solo si todas las etapas requeridas terminan correctamente.

El caso de uso mantendrá un contexto de operación con `folio`/correlation ID,
estado de cada etapa y datos mínimos para compensación. La unicidad de `_id`,
la detección de duplicados y la coordinación por operación evitarán duplicar
divisas en reintentos o concurrencia. Ante un fallo posterior a la inserción,
el adaptador ejecutará la compensación aprobada y devolverá error interno sin
declarar éxito.

No se asumirá una transacción distribuida entre Catalogos, TEF y Bitácora. La
consistencia se logrará mediante pasos idempotentes, compensación explícita,
registro de estado y reconciliación operativa.

**Alternativa descartada:** dual-write o transacción distribuida implícita
entre colecciones/contextos. No existe una estrategia aprobada para ello y
shadow traffic no puede ejecutar escrituras duplicadas.

### 6. Pruebas y comparación

Se construirán capas de prueba alineadas con el plan existente:

- unitarias para reglas, mappers, respuesta y compensación;
- integración para adaptadores con fixtures sintéticos;
- contractuales para las 14 operaciones, casing, headers y envolvente;
- end-to-end a través del gateway/proxy;
- paridad contra el monolito en integración o preproducción;
- pruebas negativas de autorización, duplicidad, ausencia y fallos de
  dependencias.

Las solicitudes Bruno se normalizarán para el contrato del proxy. Las
comparaciones ignorarán únicamente campos no deterministas previamente
identificados. Las escrituras no se ejecutarán en shadow; el alta y la
actualización se probarán en un entorno controlado con snapshots y
reconciliación.

### 7. Convivencia, cutover y rollback

Java y NestJS se desplegarán simultáneamente detrás del gateway. Primero se
compararán lecturas mediante fixtures y, si se autoriza, tráfico anonimizado.
Después de aprobar paridad funcional y de seguridad se habilitará el routing
controlado de escrituras.

El rollback desactivará las rutas de NestJS en el gateway y las devolverá al
monolito. Si NestJS ya ejecutó escrituras, se congelarán nuevas escrituras del
servicio y se realizará reconciliación aprobada por los responsables de datos;
no se borrarán ni sobrescribirán documentos automáticamente.

## Risks / Trade-offs

- **[Riesgo]** El alta sigue cruzando Catalogos, TEF y Bitácora sin transacción
  distribuida. **Mitigación:** puertos, pasos idempotentes, compensación,
  snapshots y reconciliación antes del cutover.
- **[Riesgo]** `catMonedas` y otras colecciones tienen consumidores externos al
  microservicio. **Mitigación:** conservar nombres y documentos legacy,
  documentar owner operativo y evitar dual-write durante shadow.
- **[Riesgo]** El comportamiento de respuesta Java no coincide completamente
  con el interceptor corporativo de referencia. **Mitigación:** presenter y
  filtro de errores contractuales, fixtures de comparación y prohibición de
  envolventes anidadas.
- **[Riesgo]** La autorización puede variar entre acceso directo y gateway.
  **Mitigación:** pruebas E2E detrás del proxy y bloqueo de bypass en la
  configuración del servicio.
- **[Riesgo]** No se verificará DocumentDB. **Mitigación:** registrar la
  asunción explícita, mantener queries conservadoras y exigir aprobación
  operativa antes de declarar el cutover.
- **[Riesgo]** Folios, tiempos y datos compartidos dificultan comparar
  respuestas. **Mitigación:** normalización determinista y lista cerrada de
  campos ignorables.
- **[Riesgo]** Un rollback posterior a escrituras puede dejar divergencia.
  **Mitigación:** no duplicar escrituras en shadow, routing reversible,
  congelamiento y procedimiento de reconciliación autorizado.

## Migration Plan

1. Crear el baseline NestJS y configurar dependencias corporativas, proxy,
   logging, health y correlación.
2. Implementar modelos, mappers, puertos, adaptadores de catálogo y las 12
   consultas.
3. Implementar actualización de divisa con su contrato de validación y
   respuesta.
4. Implementar el alta multi-colección con Bitácora, regiones, segmentos,
   idempotencia y compensación.
5. Ejecutar pruebas unitarias, contractuales, integración y negativas con
   fixtures Bruno.
6. Desplegar Java y NestJS en integración/preproducción detrás del gateway.
7. Comparar lecturas; no duplicar operaciones de escritura.
8. Obtener aprobación del equipo de operación interna, gerente de área y líder
   técnico.
9. Habilitar routing controlado de Catalogos hacia NestJS y observar métricas,
   errores y reconciliación.
10. Ante un criterio de bloqueo, revertir routing al Java, congelar escrituras
    NestJS si procede y ejecutar reconciliación aprobada.

Los valores definitivos de SLO, volumen, concurrencia y duración de observación
son decisiones operativas posteriores y no cambian esta arquitectura.

## Open Questions

- Los umbrales finales de latencia, error rate y ventana de observación deberán
  ser confirmados por el gerente de área y el líder técnico antes del cutover.
- El equipo de operación interna deberá confirmar el procedimiento concreto para
  reconciliar una escritura NestJS si se activa rollback después del alta de una
  divisa.
