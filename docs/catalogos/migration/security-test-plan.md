# Plan de pruebas de seguridad

## Fixtures

Usar únicamente tokens sintéticos, llaves efímeras de prueba, subjects ficticios y datos no personales. Nunca copiar tokens, llaves o headers de producción.

## Matriz

| Grupo | Casos | Resultado esperado |
|---|---|---|
| Público | `auth/client`, token SCIM y documentación | acceso solo a rutas declaradas públicas |
| Ausencia | sin Authorization | 401 y envelope compatible |
| Formato | Bearer vacío, esquema incorrecto, token truncado | 401; sin stack trace ni secreto en logs |
| Criptografía | firma manipulada, algoritmo inesperado, llave equivocada | 401 |
| Vigencia | expirado, `iat` futuro dentro/fuera de tolerancia | resultado aprobado por seguridad |
| Claims | subject ausente, contexto IP/User-Agent distinto, issuer/audience incorrectos | 401 o rechazo acordado |
| Roles | `ROLE_DIVISAS_PORTAL`, `ROLE_DSI_SCIM`, rol insuficiente | 200/403 según matriz |
| Recursos | usuario/tenant ajeno, id inexistente y recurso filtrado | 403/404 según contrato aprobado |
| Headers | `x-id-usuario`, `x-id-modulo`, `x-id-acceso` ausentes/manipulados | validar confianza y no elevar privilegios |
| CORS/CSRF | orígenes, credenciales, métodos y headers | respuesta acorde a configuración aprobada |
| Propagación | llamada Catalogos → dependencia | identidad mínima, correlation ID, sin token duplicado en logs |
| Observabilidad | request/response con valores sensibles | redacción verificable |

## Evidencia

Guardar status, body sanitizado, correlation ID, regla aplicada y versión del fixture. La aprobación requiere paridad 401/403 y ausencia de bypass.
