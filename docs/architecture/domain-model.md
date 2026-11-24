# Modelo de dominio preliminar

## Método

Se separan hechos observados en clases/DAOs de hipótesis de diseño. Ningún agregado o invariante queda aprobado por este documento.

## Capacidades y agregados candidatos

| Capacidad | Agregado candidato | Evidencia | Invariantes observadas o por confirmar |
|---|---|---|---|
| Catalogos | `Divisa` | `CatalogosServiceImpl.creaDivisa`, `CatalogosDao` | id único; alta inicial inactiva; límites por operaciones 410–413; confirmar reglas funcionales |
| Catalogos | `CatalogoSimple` | modelos `Catalogo`, `CatPaisNacionalidad` | lectura ordenada y envelope `ResponseApi`; confirmar semántica de activo |
| Campañas | `CatalogoCampania` | `catTipoCliente`, `catPerfilClienteCampanias`, `catRangoEdadesCampanias` | catálogos de referencia; no confundir con agregado de campañas de Core |
| Bitacora | `EntradaBitacora` | `BitacoraServiceImpl`, `BitacoraDao` | registrar acción y contexto; confirmar retención y PII permitida |
| Usuarios | `Usuario` / `Modulo` | `UsuariosDao`, `ModulosDao`, `UserDetailsImpl` | identidad, módulos y permisos; ownership con IAM pendiente |
| TEF | `Region`, `Segmento`, `Sucursal`, `Limite` | DAOs TEF y modelos `general` | relaciones anidadas y actualizaciones cruzadas; no extraer como agregado Catalogos |

## Reglas no confirmadas

- Si una divisa debe existir primero en Catalogos o en TerminalFinanciero.
- Si `activo=false` significa alta pendiente, baja lógica o estado operativo.
- Si los catálogos de campañas son administrados por Catalogos o consumidos desde Core.
- Si Bitacora es requisito transaccional para una escritura o solo auditoría eventual.
- Si permisos de UI equivalen a autorización backend por operación.

## Decisiones de diseño futuras

- Mantener modelos de dominio propios en NestJS.
- Usar mappers explícitos desde documentos legacy.
- No importar modelos Java, DTO HTTP ni documentos Mongo al dominio.
- Convertir invariantes confirmadas en pruebas de paridad antes del cutover.
