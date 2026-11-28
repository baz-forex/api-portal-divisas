# Inventario AS-IS de Spring Security

## Cadena observada

| Componente | Responsabilidad | Evidencia |
|---|---|---|
| `WebSecurityConfig` | cadena stateless, CORS, CSRF, endpoints públicos, roles por prefijo | `general/config/WebSecurityConfig.java:27-84` |
| `AuthTokenFilter` | extrae Bearer, valida JWT, carga usuario y construye `Authentication` | `security/jwt/AuthTokenFilter.java:23-83` |
| `JwtUtils` | genera/valida JWT RS256, subject, expiración, IP y User-Agent | `security/jwt/JwtUtils.java:44-178` |
| `UserDetailsServiceImpl` | resuelve principal desde clientes/usuarios | `security/services/UserDetailsServiceImpl.java` |
| `UserDetailsImpl` | authority `ROLE_<username>` y headers de sesión | `security/services/UserDetailsImpl.java:14-85` |
| `AuthEntryPointJwt` | respuesta de no autenticado | `security/jwt/AuthEntryPointJwt.java` |
| `AuthAccessDeniedHandler` | respuesta de acceso denegado | `security/jwt/AuthAccessDeniedHandler.java` |
| `GlobalExceptionHandlerController` | errores de credenciales, permisos, validación y excepciones | `general/exception/GlobalExceptionHandlerController.java:30-126` |

## Reglas de request

- Públicos: `/**/token`, `/**/validaEstatus`, `/**/auth/client`, Swagger, `/public` e `/index.jsp`.
- SCIM: `/**/scim/**` requiere `ROLE_DSI_SCIM`.
- APIs: `/**/api/**` requiere `ROLE_DIVISAS_PORTAL`.
- Resto: autenticado.
- Filtro JWT antes de `UsernamePasswordAuthenticationFilter`.
- Sesión: `STATELESS`.
- CSRF: deshabilitado.
- CORS: `allowCredentials=true`, origen/header/método wildcard y `Content-Disposition` expuesto.

## Identidad y claims

`JwtUtils` firma con una llave privada cargada desde una ruta de configuración, algoritmo RS256, subject de usuario, `iat`, `exp`, IP y User-Agent. La validación usa la misma llave privada local; no hay evidencia en el código revisado de issuer, audience, JWKS, rotación o revocación.

`AuthTokenFilter` compara la IP remota y User-Agent actuales contra los claims. Los headers `x-id-modulo` y `x-id-usuario` se copian al principal sin evidencia de validación de integridad independiente.

## Autorización

No se encontraron anotaciones `@PreAuthorize`, `@Secured` o `@RolesAllowed` en producción. La regla efectiva es por prefijo de ruta/rol. `UserDetailsImpl` deriva una autoridad desde el username/client id.

## Pruebas existentes

Hay pruebas para `WebSecurityConfig`, `SecurityUtils`, `LoggingFilter`, JWT indirectamente mediante servicios y controladores, además de pruebas de auth/SCIM. No constituyen una matriz completa de token ausente, firma manipulada, issuer/audience o aislamiento por recurso.

## Riesgos

1. CORS wildcard con credenciales.
2. Ausencia de issuer/audience/JWKS/revocación evidenciada.
3. Autorización por ruta, sin enforcement de permisos de recurso.
4. Exposición potencial de datos sensibles en logs.
5. La comparación IP/User-Agent puede romper detrás de proxy si no hay contrato de headers confiable.
