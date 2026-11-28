# Riesgos, supuestos y pendientes

## Hechos

- El proyecto es Java 17 / Spring Boot 2.6.7 y empaqueta un WAR (`pom.xml:7-27`).
- Se observaron 637 archivos Java de producción, 188 de prueba, 39 controllers, 280 operaciones en OpenAPI y 337 declaraciones de mapping HTTP en el escaneo reproducible.
- `WebSecurityConfig` aplica seguridad stateless, JWT, BCrypt, CORS con wildcard y reglas amplias por ruta.
- `CatalogosServiceImpl.creaDivisa` escribe en `catMonedas` y después actualiza regiones y segmentos; ante fallos ejecuta compensaciones parciales.
- Se observan llamadas nativas MongoDB, agregaciones, `arrayFilters`, bulk writes y operaciones en varias bases.
- Se observaron logs de headers/respuestas/tokens en distintos servicios; los ejemplos no se reproducen aquí.

## Hipótesis que requieren confirmación

| Hipótesis | Impacto | Validación requerida |
|---|---|---|
| `CatalogosDivisas` es propiedad funcional de Catalogos | alto | aprobación del dueño de datos y consumidores Core/TEF |
| El gateway conserva `Authorization`, IP, User-Agent y headers `x-*` | alto | contrato de gateway y prueba controlada |
| Las rutas del YAML son el contrato vigente | alto | diff contra tráfico y mappings normalizados |
| El algoritmo AES configurado corresponde al formato histórico identificado | alto | vectores sintéticos y configuración por ambiente |
| MongoDB local representa el motor de destino | alto | pruebas contra versión exacta de DocumentDB |

## Riesgos priorizados

1. **Autorización insuficientemente granular:** el enforcement observado es principalmente por prefijo/rol, no por operación o recurso.
2. **Fuga de secretos en logs:** `LoggingFilter` captura cuerpos request/response y varios servicios imprimen headers/respuestas.
3. **Ownership compartido:** colecciones de Catalogos son consultadas desde Core, Digital o TEF.
4. **Consistencia parcial:** operaciones multi-colección no muestran transacción distribuida ni outbox.
5. **Divergencia contractual:** OpenAPI, anotaciones y rutas SCIM no tienen un único baseline reconciliado.
6. **Compatibilidad criptográfica:** existen formatos AES, AES-GCM y RSA-OAEP con parámetros distintos.
7. **Dependencias sin resiliencia explícita:** no se evidencian políticas homogéneas de timeout/retry.

## Fuera de alcance y rollback

No se migran datos, no se modifican permisos, no se cambian algoritmos, no se enruta tráfico y no se escriben bases. Cualquier extracción futura debe poder devolver el routing al monolito y conservar temporalmente su ownership de escritura.
