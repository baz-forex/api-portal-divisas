# Roadmap Strangler Fig

| Fase | Entrada | Actividades | Salida/criterio |
|---|---|---|---|
| 0. Baseline | inventarios actuales | reconciliar OpenAPI, mappings, consumidores, seguridad y datos | baseline aprobado y gaps clasificados |
| 1. Ownership | `data-ownership.md` | aprobar owner de `catMonedas`, catálogos de campañas, Bitacora y referencias TEF | owner único o bloqueo explícito |
| 2. ACL/contrato | mapping Catalogos | definir DTO, errores, headers, ACL y límites de lectura/escritura | contrato legacy probado con fixtures |
| 3. Implementación | diseño aprobado | crear el servicio NestJS en su proyecto independiente correspondiente, fuera de `api-portal-divisas`, con librerías corporativas | unit/integration y health aprobados |
| 4. Shadow/paridad | servicio desplegado en entorno controlado | comparar respuestas, status, latencia, logs y efectos de datos | diferencia dentro de umbral aprobado |
| 5. Cutover gradual | ownership y rollback listos | enrutar primero lecturas de bajo riesgo; después escrituras aprobadas | métricas estables y fallback probado |
| 6. Reconciliación | tráfico parcial | comparar conteos, hashes permitidos y auditoría; corregir divergencias | consistencia aceptada |
| 7. Retiro | ventana de estabilidad | retirar rutas legacy solo con consumidores migrados | decommission aprobado |

## Dependencias

Seguridad, ownership y compatibilidad DocumentDB bloquean el cutover. AWS, gateway, observabilidad y despliegue son responsabilidades externas; aquí solo se documentan interfaces requeridas.

## Ubicación de los proyectos

Cada microservicio de la migración debe implementarse en un proyecto nuevo e independiente. El assessment y el monolito legacy permanecen en `api-portal-divisas`.

Para el primer piloto, `Catalogos`, el proyecto aprobado es:

```text
/Users/phinder2024/Devs/BAZ/backend/divisas/microservice-dvs-catalogs
```

No se deben guardar componentes runtime de la migración dentro de `api-portal-divisas`.

## Rollback general

Desactivar routing de Catalogos, devolver tráfico al monolito, congelar escrituras nuevas si procede, conservar evidencia de diferencias y reconciliar antes de reintentar. No retirar código legacy durante la fase de shadow.
