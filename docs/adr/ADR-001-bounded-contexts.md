# ADR-001: límites de bounded contexts

- **Estado:** aprobado.
- **Decisión:** mantener los siete prefijos como límites iniciales de análisis, pero dividir `core` y `tef` por capacidades internas antes de extraer.
- **Motivo:** los prefijos no siempre equivalen a cohesión; TEF y Core comparten datos y dependencias.
- **Consecuencia:** `Catalogos` se trata como primer piloto; Bitacora permanece transversal y TEF no es piloto.
- **Evidencia:** `docs/architecture/context-map.md`, `docs/assessment/api-inventory.md`.
- **Reversión:** volver a clasificar endpoints si ownership o límites funcionales aprobados contradicen el mapa.
