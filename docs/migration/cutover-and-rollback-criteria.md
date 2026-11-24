# Criterios de cutover y rollback

## Precondiciones de cutover

- owner único de cada colección afectada;
- sin dual-write no aprobado;
- paridad funcional y de seguridad aprobada;
- queries e índices validados contra DocumentDB;
- métricas, logs redacted, health y alertas activos;
- contrato de gateway/proxy confirmado;
- fallback al monolito ensayado;
- responsables funcionales, seguridad y datos firman la ventana.

## Umbrales propuestos para aprobación

Los valores finales deben ser definidos por negocio/SRE. Como condición cualitativa: cero bypass de seguridad, cero errores críticos no explicados, cero escrituras duplicadas y todas las diferencias de contrato clasificadas. Latencia, error rate y disponibilidad deben cumplir el SLO aprobado durante la ventana de observación.

## Rollback

1. Desactivar el routing de las rutas Catalogos.
2. Enviar nuevas solicitudes al monolito.
3. Mantener el owner de escritura legacy hasta reconciliar.
4. Congelar o aislar escrituras del servicio si existe riesgo de divergencia.
5. Comparar auditoría, conteos y estados; corregir con procedimiento aprobado.
6. Registrar causa, ventana, impacto y decisión de reintento.

## Condiciones de bloqueo

Firma/token no compatible, 401/403 divergentes, colección compartida sin owner, pérdida de datos, inconsistencia multi-colección, logs con secretos, timeout sin límite o incompatibilidad DocumentDB.
