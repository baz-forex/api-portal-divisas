# ADR-005: compatibilidad MongoDB/DocumentDB

- **Estado:** propuesto, pendiente de pruebas contra versión destino.
- **Decisión:** encapsular persistencia detrás de puertos y no declarar equivalencia por usar MongoDB local.
- **Motivo:** se observan agregaciones, `arrayFilters`, bulk writes y operaciones multi-colección.
- **Criterio:** ningún cutover hasta pasar pipelines, índices, actualizaciones anidadas, errores, latencia y consistencia contra DocumentDB real.
- **Evidencia:** `docs/assessment/mongodb-documentdb-compatibility.md`.
- **Reversión:** mantener lectura/escritura en monolito si alguna capacidad no es compatible.
