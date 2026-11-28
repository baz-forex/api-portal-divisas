# Ownership de datos y ACL

## Clasificación propuesta

| Dominio/colección | Owner de escritura propuesto | Consumidores observados | Clasificación actual | ACL requerida |
|---|---|---|---|---|
| Catálogos simples de `CatalogosDivisas` | Catalogos | Catalogos, Core, TEF | `candidate-owned` | contrato de lectura versionado |
| `catMonedas` | pendiente de aprobación Catalogos/TEF | Catalogos, Core/TEF por referencias | `legacy shared` | impedir extracción de escritura hasta decisión |
| regiones/segmentos de TerminalFinanciero | TEF | Catalogos durante alta de divisa | `owned-by-tef` | comando/contrato de TEF; no acceso directo |
| bitácoras | pendiente de auditoría | todos los contextos | `legacy shared` | adaptador de auditoría o evento aprobado |
| usuarios/módulos | IAM/Usuarios pendiente | Security, SCIM, API | `legacy shared` | ACL de identidad y provisioning |
| parametría Middleware | Core/Digital pendiente | Core, Digital, TEF | `legacy shared` | API de configuración, no colección cruzada |
| datos SIEX | SIEX | SIEX y reportes | `owned-by-siex` | ACL de consulta aprobada |

## Reglas de convivencia

1. Un solo owner de escritura por colección.
2. Lectura cruzada solo mediante puerto/ACL documentado.
3. No dual-write sin idempotencia, reconciliación, métricas y aprobación.
4. Los documentos legacy se mapean a modelos internos; no se comparten clases.
5. Los cambios de ownership deben incluir backfill, baseline, ventana de congelamiento y rollback.

## ACL del piloto Catalogos

```text
Consumidor legacy ──contrato actual──> Catalogos ACL ──> caso de uso
Catalogos ──puerto de lectura──> catálogos propios
Catalogos ──puerto externo──> TEF (regiones/segmentos), solo si alta de divisa lo requiere
Catalogos ──puerto auditoría──> Bitacora
```

La primera versión debe priorizar lecturas y dejar las escrituras de `catMonedas` detrás de un flag de routing y ownership aprobado.
