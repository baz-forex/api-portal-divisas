# ADR-005: compatibilidad MongoDB/DocumentDB

- **Estado:** aprobado.
- **Decisión:** encapsular persistencia detrás de puertos y asumir equivalencia por usar MongoDB local.
- **Motivo:** se observan agregaciones, `arrayFilters`, bulk writes y operaciones multi-colección.
- **Criterio:** ningún cutover hasta pasar pipelines, índices, actualizaciones anidadas, errores, latencia y consistencia contra DocumentDB real.
- **Evidencia:** `docs/catalogos/assessment/mongodb-documentdb-compatibility.md`.
- **Reversión:** mantener lectura/escritura en monolito si alguna capacidad no es compatible.
