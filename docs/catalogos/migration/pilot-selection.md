# Selección de piloto: Catalogos

## Recomendación

`Catalogos` es el primer piloto por su superficie acotada: 13 paths y 14 operaciones, modelos simples y colecciones identificables en `CatalogosDivisas`. La recomendación no autoriza todavía extraer escrituras.

## Comparativa

| Candidato | Ventajas | Riesgos | Decisión |
|---|---|---|---|
| Catalogos | contrato pequeño, lecturas deterministas, 12 colecciones principales | `catMonedas` y alta de divisa cruzan TEF/Bitacora | piloto condicionado |
| Bitacora | transversal, bajo modelo operativo | dependencia de todos los módulos, retención/PII | segundo candidato |
| TEF | valor de negocio alto | muchas subcapacidades, arrays, multi-colección, Apigee/Tesorería | posponer |

## Criterios de entrada

- owner de escritura aprobado;
- contrato legacy y OpenAPI reconciliados;
- matriz de seguridad 401/403 aprobada;
- colecciones compartidas identificadas;
- compatibilidad de queries del piloto validada contra DocumentDB;
- rollback de routing probado;
- fixtures sintéticos y datos de prueba autorizados.

## Criterios de salida

Paridad funcional y de seguridad, diferencias dentro de umbral, observabilidad sin secretos, latencia/SLO aprobados y decisión explícita sobre retiro o convivencia.
