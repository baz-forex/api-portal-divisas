# Plan de pruebas de paridad

## Capas

| Capa | Alcance | Evidencia |
|---|---|---|
| Unit | invariantes de catálogo, mappers, errores y policies | casos sintéticos, cobertura de ramas críticas |
| Integration | `@gs/documentdb`, índices, agregaciones y updates | ejecución contra MongoDB local y DocumentDB objetivo |
| Contract | rutas, casing, headers, envelope y códigos HTTP | comparación OpenAPI/runtime y fixtures |
| Security | matriz de tokens/roles/recursos | resultados 401/403 y revisión de logs |
| E2E | consumer → gateway/ACL → servicio → datos | escenarios felices y negativos |
| Persistencia | conteos, estados, idempotencia y compensación | snapshots sin PII y reconciliación |
| Rendimiento | latencia, concurrencia y payloads representativos | SLO aprobado; no usar datos productivos |

## Casos Catalogos

- las 14 operaciones del contrato;
- lista vacía y colección con registros;
- `catMonedas` excluyendo `OLP`;
- cambio de estatus existente/inexistente;
- alta de divisa duplicada;
- fallo de actualización de regiones o segmentos;
- repetición de la misma solicitud;
- datos con casing, fechas y campos opcionales históricos;
- error de dependencia, timeout y fallback;
- usuario sin rol y recurso no autorizado.

## Comparación

Registrar request sintético, status, headers permitidos, body normalizado, tiempo, lecturas/escrituras esperadas y diferencia. Ignorar únicamente campos no deterministas documentados como `folio`, timestamps o ids generados.
