# Seguridad AS-IS y límites de confianza

## Flujo de confianza

```text
Cliente / gateway
  └─ Authorization: Bearer <JWT>
       └─ WebSecurityConfig
            └─ AuthTokenFilter
                 ├─ firma/expiración con llave local
                 ├─ subject → UserDetailsServiceImpl
                 ├─ IP + User-Agent
                 └─ x-id-usuario / x-id-modulo → principal
                      └─ regla ROLE_DSI_SCIM o ROLE_DIVISAS_PORTAL
                           └─ controller → service → DAO/external
```

## Hechos y riesgos

| Tema | Hecho | Riesgo/pendiente |
|---|---|---|
| `ServiceProviderConfig` SCIM | existe en `DiscoveryController` en `/scim/v2/ServiceProviderConfig` | no aparece en el YAML revisado; reconciliar contrato OpenAPI/runtime |
| Autenticación | JWT propio RS256 y principal local | no asumir proveedor corporativo ni migrar a otro IdP sin aprobación |
| Roles | reglas amplias `ROLE_DSI_SCIM`/`ROLE_DIVISAS_PORTAL` | permisos UI no demuestran autorización por endpoint/recurso |
| CORS/CSRF | CORS wildcard con credenciales; CSRF deshabilitado | revisar modelo de clientes y gateway antes de cambiar |
| Sesión | stateless | verificar expiración y propagación en llamadas internas |
| Logs | `LoggingFilter` captura request/response; servicios imprimen headers/respuestas | posible exposición de tokens, PII y datos operativos |

## Consecuencia para Catalogos

El piloto debe probar exactamente 401/403 y el envelope legacy antes de proponer mejoras. La seguridad TO-BE puede endurecer controles, pero cualquier divergencia requiere ADR, aprobación de seguridad y plan de compatibilidad.
