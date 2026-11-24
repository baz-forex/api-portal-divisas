# Proposal

## Why

El dominio `Catalogos` del monolito Java expone 14 operaciones sobre 13 rutas y
concentra lecturas, actualización de divisas y alta de divisas con efectos en
colecciones compartidas y dependencias de TEF y Bitácora. El microservicio
destino está vacío, por lo que se requiere definir una migración completa,
contractualmente compatible y ejecutable exclusivamente en
`microservice-dvs-catalogs`.

La migración debe preservar el comportamiento observable del Java AS-IS, usando
el proxy corporativo para autorización, la envolvente compatible con
`ResponseApi` y pruebas de paridad antes del cutover.

## What Changes

- Crear el microservicio NestJS de Catalogos en
  `/Users/phinder2024/Devs/BAZ/backend/divisas/microservice-dvs-catalogs`.
- Implementar las 14 operaciones bajo `/api/catalogos`:
  - 12 operaciones `GET`.
  - `PUT /api/catalogos/divisas`.
  - `POST /api/catalogos/divisas/registrar`.
- Conservar exactamente las rutas, el casing, los métodos HTTP, los códigos,
  los mensajes y la forma observable de las respuestas Java.
- Implementar la envolvente de respuesta compatible con `ResponseApi`,
  considerando el comportamiento de `ResponseInterceptor`.
- Integrar autorización mediante los headers `token` y `x-id-acceso` a través
  del proxy corporativo.
- Implementar persistencia y mapeos explícitos para las colecciones de
  `CatalogosDivisas`.
- Reproducir la actualización de estatus y el flujo de alta de divisas,
  incluidos sus efectos sobre regiones, segmentos y Bitácora.
- Definir compensación, idempotencia, observabilidad y rollback para operaciones
  con escrituras multi-colección.
- Crear pruebas unitarias, de integración, contractuales, end-to-end y de
  paridad Java/NestJS usando fixtures sintéticos y escenarios Bruno.
- Preparar shadow traffic únicamente para lecturas; no duplicar escrituras.
- Excluir completamente `GET/PUT /api/tef/catOperaciones/*`.
- No realizar migración, backfill ni validación específica de compatibilidad
  DocumentDB en este cambio; el funcionamiento del motor se asumirá y se
  registrará como riesgo no verificado.

## Capabilities

### New Capabilities

- `reference-catalogs`: Exposición, consulta, actualización y registro de los
  catálogos del dominio Catalogos con compatibilidad contractual Java.

### Modified Capabilities

- Ninguna.

## Impact

- **Aplicación destino:** nuevo código NestJS en
  `microservice-dvs-catalogs`, organizado por capas de dominio, aplicación,
  infraestructura y presentación.
- **API pública:** 14 operaciones bajo `/api/catalogos`; se preservan rutas
  heredadas, incluyendo las que contienen mayúsculas.
- **Persistencia:** colecciones `catNacionalidades`, `catEstados`, `catMonedas`,
  `catIdentificaciones`, `catEstatusCotizacion`, `catTipoOperacion`,
  `catTipoCliente` y catálogos de campañas.
- **Dependencias:** proxy/autorización corporativa, Bitácora, regiones y
  segmentos TEF.
- **Convivencia:** monolito Java y NestJS coexistirán detrás del gateway durante
  comparación y cutover; el rollback será mediante cambio de routing.
- **Gobernanza:** la paridad y el cutover requerirán aprobación del gerente de
  área y del líder técnico, con participación del equipo de operación interna.
