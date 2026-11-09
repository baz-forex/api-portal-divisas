# API Portal Divisas

API REST backend para el Portal de Divisas de Grupo Salinas (Banco Azteca). Proporciona servicios para la gestión de operaciones con divisas, tipos de cambio, regiones, segmentos, sucursales, límites operativos, campañas y configuraciones del negocio de cambio de divisas.

## Tecnologías

| Componente | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Framework | Spring Boot 2.6.7 |
| Base de datos | MongoDB 5.6 |
| Seguridad | Spring Security + JWT |
| Documentación API | Swagger (Springfox 2.9.2) |
| Build | Maven |
| Empaquetado | WAR (despliegue en Tomcat) |
| Pruebas | JUnit 5 + Mockito |
| Cobertura | JaCoCo |

## Estructura del Proyecto

```
src/main/java/com/baz/divisas/
├── api/
│   ├── tef/                    # Módulos TEF (Tipo de cambio Electra/Financiero)
│   │   ├── regiones/           # Gestión de regiones
│   │   ├── segmentos/          # Gestión de segmentos
│   │   ├── sucursales/         # Tipos de cambio por sucursal
│   │   ├── spots/              # Tipos de cambio spot
│   │   ├── spread/             # Gestión de spreads
│   │   ├── limites/            # Límites por región, segmento, sucursal, cliente y divisa
│   │   ├── regionesestrategias/       # Estrategias por región
│   │   ├── regionessegmento/          # Relación región-segmento
│   │   ├── regionessucursales/        # Relación región-sucursales
│   │   ├── regionessegmentoestrategia/ # Estrategias por segmento
│   │   ├── regionsegmentosucursal/    # Relación región-segmento-sucursal
│   │   ├── competencias/       # Carga y consulta de competencias
│   │   ├── dashboard/          # Dashboard de tipos de cambio
│   │   ├── catalogos/          # Catálogos de tipos de operación
│   │   ├── parametria/         # Parametría operativa y por sucursal
│   │   ├── promoespacio/       # Promoción de espacio (geolocalización y TC)
│   │   └── tarjetas/           # Solicitud masiva de tarjetas
│   ├── core/                   # Módulos core del negocio
│   │   ├── sucursales/         # Certificación de sucursales y cuestionarios
│   │   ├── clientes/           # Gestión de clientes y bloqueos
│   │   ├── beneficios/         # Campañas, premios, seguros y rascadito digital
│   │   ├── configuraciones/    # Configuraciones generales y notificaciones
│   │   └── reporteria/         # Reportería de operaciones e incidencias
│   ├── digital/                # Módulos de canal digital
│   │   ├── configuraciones/    # Parametría de operaciones digitales
│   │   └── reporteria/         # Reportes de cotizaciones app
│   ├── siex/                   # Configuraciones SIEX (campos restringidos)
│   ├── catalogos/              # Catálogos generales (divisas, estados, etc.)
│   ├── usuarios/               # Gestión de usuarios y perfiles
│   ├── auth/                   # Autenticación y generación de tokens
│   ├── llaves/                 # Gestión de llaves de cifrado (RSA/AES)
│   ├── bitacora/               # Registro de bitácora de acciones
│   └── apigee/                 # Integración con Apigee (tokens y llaves)
├── scim/                       # Provisioning SCIM (usuarios y grupos)
├── security/                   # Filtros y utilidades de seguridad (JWT)
└── general/                    # Componentes transversales
    ├── config/                 # Configuración (Spring, Swagger, propiedades)
    ├── util/                   # Utilidades (cifrado, correo, logs, fechas)
    ├── dto/                    # DTOs genéricos de respuesta
    ├── filters/                # Filtro de logging de peticiones
    ├── factory/                # Factory de conexión MongoDB
    ├── annotation/             # Validaciones personalizadas
    ├── model/                  # Modelos genéricos (correo)
    └── exception/              # Manejo global de excepciones
```

## Módulos Principales

### TEF (Tipo de cambio Electra/Financiero)
Gestión completa de tipos de cambio para la red de sucursales:
- **Regiones y segmentos**: Organización jerárquica de sucursales.
- **Spots**: Registro y consulta de tipos de cambio spot (incluye integración con Tesorería).
- **Spreads**: Configuración de diferenciales por divisa.
- **Límites**: Control de límites operativos a nivel de región, segmento, sucursal, cliente y divisa.
- **Estrategias**: Asignación de montos de estrategia por región y segmento.
- **Sucursales**: Consulta y actualización de tipos de cambio por sucursal (TEF y OLP).
- **Competencias**: Carga y consulta de precios de la competencia.
- **Parametría**: Gestión de parámetros operativos y asignaciones a sucursales.
- **PromoEspacio**: Informar tipos de cambio mejorados por geolocalización.

### Core
- **Certificación de sucursales**: Cuestionarios, respuestas y certificación operativa.
- **Clientes**: Búsqueda, actualización, bloqueos operativos, clientes agrupados y alto riesgo.
- **Beneficios**: Campañas, premios, seguros, cupones de tiempo aire y rascadito digital.
- **Configuraciones**: Parametría de búsquedas, segmentación de sucursales, notificaciones y campos restringidos.
- **Reportería**: Operaciones, incidencias BCD, ganadores de promociones e inventarios.

### Digital
- Parametría de operaciones (montos, vigencias, fluctuación, cancelaciones).
- Seguimiento de tipo de cambio y remesas.
- Reporte de cotizaciones y operaciones de la app.

### Seguridad
- Autenticación con JWT (RS256 con llave privada).
- Gestión de llaves RSA/AES para cifrado de datos.
- Integración con SCIM para provisioning de usuarios.
- Clientes API con secretos hasheados.

## Divisas Soportadas

- USD (Dólar estadounidense)
- CAD (Dólar canadiense)
- EUR (Euro)
- GBP (Libra esterlina)

## Requisitos

- Java 17+
- Maven 3.6+
- MongoDB 5.x+
- Servidor de aplicaciones compatible con WAR (Tomcat)

## Compilación

```bash
mvn clean package
```

## Ejecución Local

```bash
mvn spring-boot:run
```

## Pruebas

```bash
mvn test
```

Para generar el reporte de cobertura con JaCoCo:

```bash
mvn verify
```

## Configuración

Los archivos de propiedades se encuentran en el directorio `properties/` organizados por ambiente:

| Ambiente | Directorio |
|---|---|
| Desarrollo | `properties/properties_des/` |
| QA | `properties/properties_qa/` |
| Producción | `properties/properties_pro/` |

El archivo principal de configuración es `APIPortalDivisas.properties`.

## Documentación API

Una vez desplegada la aplicación, la documentación Swagger está disponible en:

```
http://{host}:{puerto}/{context-path}/swagger-ui.html
```

## Despliegue

El proyecto genera un archivo WAR para despliegue en un servidor Tomcat on-premise:

```bash
mvn clean package -DskipTests
```

El artefacto generado se encuentra en `target/ApiPortalDivisas-0.0.20.war`.

## Arquitectura

La aplicación sigue una arquitectura por capas:

1. **Controller**: Recibe las peticiones HTTP y valida los datos de entrada.
2. **Service**: Contiene la lógica de negocio.
3. **DAO**: Acceso directo a MongoDB mediante el driver nativo.

La comunicación con servicios externos se realiza mediante WebClient (WebFlux) y OkHttp para integraciones con APIs internas (Middleware, Apigee, Tesorería, AWS).


## Adicionales

### Parametro para uso de logs en dev console

```jvm options
-Dlog4j.configurationFile=log4j2-dev.properties
```
