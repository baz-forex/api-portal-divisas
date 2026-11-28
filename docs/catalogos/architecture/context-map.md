# Mapa de contextos y límites

## Contextos obligatorios

| Contexto | Prefijo | Responsabilidad observada | Dependencias principales | Madurez para extracción |
|---|---|---|---|---|
| Bitacora | `/api/bitacora` | registro y consulta de acciones | casi todos los módulos | transversal; extraer después de definir contrato |
| Catalogos | `/api/catalogos` | catálogos de países, estados, divisas, identificaciones y campañas | CatalogosDivisas; regiones/segmentos para alta de divisa | primer piloto condicionado a ownership |
| Core | `/api/core` | clientes, configuración, beneficios, sucursales y reportes | Middleware, CatalogosDivisas, Incidencias | dividir por capacidades internas |
| Digital | `/api/digital` | parametría y reportería digital | Middleware | requiere ownership de parametría |
| SIEX | `/api/siex` | campos restringidos y configuración SIEX | SIEX | candidato posterior especializado |
| TEF | `/api/tef` | spots, regiones, segmentos, sucursales, límites, parametría y tesorería | TerminalFinanciero, Apigee, Tesorería | posponer por acoplamiento |
| Usuarios | `/api/usuarios` | usuarios, perfiles y módulos | Autenticacion | coordinar con IAM/SCIM |

## Capacidades adyacentes

- `auth`: `/api/auth`, emisión/validación de clientes y token SCIM.
- `scim`: `/scim/v2/*`, provisioning y almacenamiento de recursos.
- `apigee`: adaptadores de token/llaves para integraciones TEF.
- `health/observabilidad`: actualmente distribuida entre configuración y logging; debe ser transversal en NestJS.

## Reglas de frontera

1. Un contexto no debe leer directamente colecciones propiedad de otro contexto.
2. Las lecturas heredadas se realizarán mediante ACL, contrato HTTP o réplica aprobada.
3. Las colecciones compartidas se mantienen `legacy shared` hasta que exista propietario único.
4. Bitacora se trata como capacidad transversal, no como dependencia para ocultar reglas de negocio.
5. Cada endpoint de `api-inventory.md` debe conservar su contexto o quedar explícitamente clasificado como `auth`, `scim` o auxiliar.
