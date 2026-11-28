# Matriz de paridad de seguridad

| Superficie | Regla Java AS-IS | Control NestJS candidato | Permitido | Denegado/evidencia |
|---|---|---|---|---|
| `/api/catalogos/**` | `ROLE_DIVISAS_PORTAL` en `WebSecurityConfig:80` | guard `@gs/auth` + permiso de operación | JWT válido y rol autorizado | sin token, token inválido/expirado, rol ausente → 401/403 legacy |
| `/api/auth/client` | público | endpoint público explícito | solicitud válida | payload inválido → error legacy |
| `/scim/v2/token` | público según `/**/token` | guard público + validación SCIM | credenciales sintéticas válidas | credenciales inválidas → 401 |
| `/scim/v2/**` | `ROLE_DSI_SCIM` | guard SCIM y policy de provisioning | principal SCIM autorizado | rol insuficiente → 403 |
| `ServiceProviderConfig` | runtime público dentro de SCIM; no evidenciado en YAML | contrato explícito antes de extracción | respuesta igual al runtime | divergencia de contrato bloquea cutover |
| `x-id-usuario`/`x-id-modulo` | se copian al principal | solo headers validados por proxy | identidad correlacionada | header manipulado o inconsistente → rechazo |
| IP/User-Agent | claims JWT comparados con request | preservar solo si seguridad lo confirma | contexto compatible | mismatch → no autenticar |
| CORS/CSRF | CORS wildcard, CSRF disabled | configuración aprobada por plataforma | escenarios documentados | origen/método no permitido según política |
| recurso/tenant | no hay enforcement general evidenciado | policy de aplicación por recurso | owner/permiso comprobado | recurso de otro tenant/owner → 403/404 acordado |

## Escenarios mínimos

Token ausente, formato Bearer incorrecto, firma manipulada, expiración, algoritmo inesperado, claims de contexto ausentes, issuer/audience incorrectos si aplican, rol insuficiente, acceso a recurso ajeno, CORS y propagación interna.
