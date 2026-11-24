# Matriz Java → NestJS

| Java AS-IS | NestJS TO-BE | Evidencia/precaución |
|---|---|---|
| `@RestController` | `presentation/controllers` | conservar ruta, método, casing y status |
| `@RequestBody @Valid` | DTO HTTP + `@gsa/decorators`/validator aprobado | comparar mensajes y errores |
| `CatalogosServiceImpl` | application use cases | extraer reglas, no copiar dependencias estáticas |
| `CatalogosDao` | puerto `CatalogRepository` + adapter `@gs/documentdb` | mapper explícito; no exponer `Document` |
| `ResponseApi` | adapter `@gs/errors`/response mapper | conservar codigo, mensaje, folio, detalles y resultado |
| `WebSecurityConfig` | guard/middleware `@gs/auth` | no elegir IdP sin evidencia |
| `AuthTokenFilter`/`JwtUtils` | adapter de autenticación aprobado | paridad de firma, expiración y contexto |
| `GlobalExceptionHandlerController` | filtro `@gs/errors` | preservar 401/403 y errores funcionales |
| `FactoryMongoDB` | `@gs/documentdb` y health | timeouts, pool, readiness y motor real |
| `LoggingFilter`/`LogApp` | `@gs/logger` | redacción y correlation ID; no copiar cuerpos |
| `WebClient`/threads | `@gs/proxy` y mecanismo asíncrono aprobado | timeout/retry/idempotencia explícitos |
| `AESUtils`/RSA/CryptoService | `@gs/crypto` detrás de puerto | golden vectors y compatibilidad de formatos |

## Precauciones

- `CatalogosServiceImpl.creaDivisa` es un flujo multi-contexto, no un CRUD aislado.
- Las interfaces TypeScript requieren tokens runtime para inyección.
- `domain` no debe importar NestJS, Mongoose, AWS SDK, HTTP ni librerías corporativas.
- La matriz es de diseño; no demuestra que exista un equivalente implementado.
