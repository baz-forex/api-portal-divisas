# Diseño de seguridad TO-BE preliminar

> Diseño candidato; no selecciona IdP ni cambia el contrato de identidad.

## Capas

| Responsabilidad                         | Adaptador/control candidato                                                            |
| --------------------------------------- | -------------------------------------------------------------------------------------- |
| extracción y validación de credenciales | `@gs/auth` en middleware/guard aprobado                                                |
| principal tipado                        | puerto `AuthenticatedPrincipal` con subject, roles/scopes y headers confiables         |
| autorización de entrada                 | guard por operación; política por recurso cuando el caso de uso lo requiera            |
| proxy y llamadas internas               | `@gs/proxy`, propagando identidad mínima y nunca credenciales innecesarias             |
| errores 401/403                         | `@gs/errors` preservando status y forma legacy durante convivencia                     |
| logs y correlación                      | `@gs/logger`, redacción de Authorization, cookies, llaves, PII y cuerpos por allowlist |
| cifrado/compatibilidad                  | `@gs/crypto`, detrás de un puerto; no llamar librería desde dominio                    |

## Reglas no negociables

1. No elegir Passport, Cognito, OAuth/OIDC o JWKS como hecho sin evidencia del proveedor vigente.
2. Verificar firma, expiración y contexto de emisor/audiencia si el contrato corporativo los exige.
3. Usar `token`, `x-id-acceso` como encabrzado de confianza del gateway.
4. Mantener mínimos privilegios y negar por defecto operaciones no clasificadas.
5. Aplicar autorización por recurso/tenant cuando el dominio lo requiera; los claims no sustituyen la comprobación de ownership.
6. Mantener comportamiento 401/403 durante el piloto y documentar toda divergencia.
7. No deshabilitar CORS/CSRF ni copiar wildcard sin aprobación.

## Dependencias de aprobación

- proveedor de identidad y formato de token;
- contrato de gateway/proxy;
- mapa de roles/scopes/permisos;
- tratamiento de sesiones y revocación;
- política de logging y datos sensibles.
