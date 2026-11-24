# ADR: autenticación y autorización

- **Estado:** propuesto, requiere aprobación de seguridad.
- **Decisión:** mapear el comportamiento Java a `@gs/auth` y `@gs/proxy` sin elegir IdP ni formato de token no demostrado.
- **Motivo:** el AS-IS usa JWT RS256 local, roles amplios, IP/User-Agent y headers técnicos.
- **Requisitos:** preservar 401/403; validar issuer/audience/JWKS/revocación si el contrato corporativo los exige; prohibir bypass por claims no verificados; redacción con `@gs/logger`.
- **Divergencias:** CORS wildcard, CSRF disabled y ausencia de autorización por operación no se corrigen silenciosamente.
- **Evidencia:** inventarios y matriz de paridad de seguridad.
