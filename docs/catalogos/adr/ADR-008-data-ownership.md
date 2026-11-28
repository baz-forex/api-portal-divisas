# ADR-008: ownership y ACL de datos

- **Estado:** aprobado.
- **Decisión:** un owner de escritura por colección; lecturas cruzadas solo por ACL/contrato.
- **Motivo:** `catMonedas`, regiones, segmentos y Bitacora tienen accesos cruzados.
- **Regla:** no dual-write sin idempotencia, reconciliación, observabilidad y aprobación.
- **Evidencia:** `docs/catalogos/architecture/data-ownership.md`.
- **Reversión:** routing al monolito y conservación del owner legacy mientras se resuelven diferencias.
