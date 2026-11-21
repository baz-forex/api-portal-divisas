# Mecanismo de autenticación y llaves para consumir la API

## Resumen ejecutivo

La API utiliza dos mecanismos complementarios para sus endpoints públicos:

1. Un token JWT firmado por el proxy corporativo/Apigee, validado por `ProxyAuthGuard`.
2. Un `x-id-acceso`, usado por `@gs/crypto` para consultar al servicio de seguridad y obtener las llaves de cifrado de la solicitud y de la respuesta.

Por lo tanto, para los endpoints protegidos normalmente se deben enviar ambos encabezados:

```http
<PROXY_HEADER_NAME>: <JWT_DEL_PROXY>
<SECURITY_HEADER_NAME>: <ID_DE_ACCESO>
```

En la configuración y documentación del proyecto, los nombres esperados suelen ser:

```http
<PROXY_HEADER_NAME>: <JWT_DEL_PROXY>
x-id-acceso: <ID_DE_ACCESO>
```

El nombre real del primer encabezado depende de `PROXY_HEADER_NAME` y el del segundo depende de `SECURITY_HEADER_NAME`. No se deben colocar secretos reales en este documento.

## 1. Flujo de una solicitud protegida

El flujo efectivo es:

```text
Cliente
  |
  | 1. Envía JWT del proxy + x-id-acceso
  v
ProxyAuthGuard
  |
  | 2. Verifica la firma del JWT con PROXY_SECRET_KEY
  v
KeysGuard, agregado por UseEncryptionV2
  |
  | 3. Consulta el servicio de seguridad usando x-id-acceso
  | 4. Envía también el token del proxy al servicio de seguridad
  | 5. Obtiene las llaves de seguridad
  v
EncryptionInterceptorV2
  |
  | 6. Descifra body/query/params de entrada
  | 7. Ejecuta el controlador y el caso de uso
  | 8. Cifra los valores de la respuesta
  v
Cliente
```

Los controladores aplican explícitamente `@UseGuards(ProxyAuthGuard)` y `@UseEncryptionV2(...)`. Por ejemplo, el endpoint de creación de bloques conserva ambos decoradores en [create-country.controller.ts](../src/modules/countries/presentation/http/controllers/country.controller.ts:49).

`ProxyModule` y `CryptoModule` se registran desde el módulo común y se exportan globalmente en [common.module.ts](../src/modules/common/common.module.ts:8).

## 2. Token del proxy

### 2.1 Qué valida la API

`ProxyAuthGuard`, provisto por el paquete privado `@gs/proxy`, realiza estas acciones:

- Obtiene de configuración el nombre del encabezado y el secreto público:
  - `PROXY_HEADER_NAME`.
  - `PROXY_SECRET_KEY`.
- Lee el token desde `request.headers[PROXY_HEADER_NAME]`.
- Construye una llave pública PEM con el contenido de `PROXY_SECRET_KEY`.
- Verifica la firma del JWT con `jsonwebtoken.verify`.
- Si la verificación falla, lanza una excepción de autenticación del proxy.

La configuración del paquete está en `@gs/proxy/dist/config/configuration.js` dentro de `node_modules`; el guard utilizado por los controladores es `ProxyAuthGuard`.

### 2.2 Tipo de llave

- `ASYMMETRIC_CRYPTO_PRIVATE_KEY`: llave privada en Base64, formato DER PKCS#8.
- `PROXY_SECRET_KEY`: llave pública derivada, en Base64, formato DER SPKI.
- El JWT se firma con `RS256`.
- La llave privada y la pública deben corresponder; el script lo valida antes de emitir el token.

La llave privada firma el JWT en el emisor. La API solamente necesita la llave pública para comprobar que el token fue firmado por el emisor autorizado.

### 2.3 Consideraciones importantes del token

- El guard espera el valor del JWT directamente en el encabezado configurado.
- No hay evidencia en el guard de que se agregue o quite el prefijo `Bearer `.
- Por ejemplo, si `PROXY_HEADER_NAME=Authorization`, el valor esperado por el guard es el JWT, no necesariamente `Bearer <JWT>`.
- El script de generación agrega `exp`, `iss`, `aud` y `jti`, pero el `ProxyAuthGuard` actual configura `ignoreExpiration: true` y no exige explícitamente `issuer` ni `audience`.
- En consecuencia, la expiración incluida en el JWT no se hace cumplir por este guard. Esto debe confirmarse con el equipo que mantiene `@gs/proxy` o con el proxy corporativo antes de considerar el control como una expiración efectiva.

### 2.4 El proyecto no emite el token del proxy

No existe en este repositorio un controlador de login o un endpoint público que emita el JWT requerido por `ProxyAuthGuard`. El token debe obtenerse del proxy/Apigee o generarse únicamente para desarrollo con las llaves correspondientes.

El paquete `@gs/proxy` también contiene un `ProxyGuard` que puede solicitar un token mediante `client_credentials`, usando `PROXY_AUTH_USER`, `PROXY_AUTH_PASSWORD` y `PROXY_AUTH_URL`; sin embargo, los controladores de este proyecto usan `ProxyAuthGuard`, no `ProxyGuard`.

## 3. `x-id-acceso` y obtención de llaves

### 3.1 Función del `x-id-acceso`

El `x-id-acceso` no sustituye al JWT del proxy. Es un identificador que permite al servicio consultar las llaves asociadas al acceso.

El nombre del encabezado se configura mediante `SECURITY_HEADER_NAME`; el servicio exige que exista antes de consultar las llaves. La configuración y validación de estas variables están en [validate-env.ts](../src/validate-env.ts:33) y [configuration.ts](../src/config/configuration.ts:35).

### 3.2 Consulta al servicio de seguridad

`KeysService`, incluido en `@gs/crypto`, hace aproximadamente lo siguiente:

1. Lee `SECURITY_HEADER_NAME`, `SECURITY_SERVICE_URL`, `SECURITY_PREFIX`, `SECURITY_SERVICE_VERSION` y `SECURITY_SERVICE_KEYS_PATH`.
2. Obtiene el valor de `request.headers[SECURITY_HEADER_NAME]`.
3. Obtiene el JWT desde `request.headers[PROXY_HEADER_NAME]`.
4. Ejecuta una solicitud `GET` con esta forma:

```text
SECURITY_SERVICE_URL
  + SECURITY_PREFIX
  + SECURITY_SERVICE_VERSION
  + SECURITY_SERVICE_KEYS_PATH
  + "/"
  + <ID_DE_ACCESO>
```

5. Envía el token del proxy al servicio de seguridad en el encabezado configurado por `PROXY_HEADER_NAME`.
6. Recibe las credenciales/llaves asociadas al acceso.
7. Descifra las llaves recibidas usando `SECURITY_ENCRYPTION_SYMMETRIC_KEY` y `SECURITY_ENCRYPTION_HMACKEY`.

Si el `x-id-acceso` no existe, está vencido, no es válido o falla la consulta al servicio de seguridad, la solicitud se rechaza antes de ejecutar el caso de uso.

## 4. Llaves usadas para cifrar y descifrar datos

La respuesta del servicio de seguridad contiene, conceptualmente, estos valores:

- `accesoPrivado` → llave privada de la sesión/acceso.
- `accesoPublico` → llave pública de la sesión/acceso.
- `accesoSimetrico` → llave simétrica.
- `codigoAutentificacionHash` → llave HMAC.
- `idAcceso` → identificador del acceso.

`KeysGuard` coloca las llaves resueltas en el objeto de la solicitud:

```text
request.privateKey
request.publicKey
request.symmetricKey
request.hmacKey
```

Después, `EncryptionInterceptorV2` usa esas llaves para recorrer y descifrar los valores de `body`, `query` y `params`. Tras ejecutar el controlador, cifra los valores de la respuesta.

En los endpoints de catálogos se usa `UseEncryptionV2`, por lo que el cliente debe conocer el contrato de cifrado vigente, además del contrato de autenticación. El cifrado no es un reemplazo de HTTPS ni del token del proxy.

## 5. Encabezados que debe enviar el consumidor

|   # | Encabezado                                          | Valor                           | Propósito                                                                     | ¿Quién lo valida?         |
| --: | --------------------------------------------------- | ------------------------------- | ----------------------------------------------------------------------------- | ------------------------- |
|   1 | `<PROXY_HEADER_NAME>`                               | JWT firmado por el proxy        | Autenticar que la solicitud proviene de un consumidor autorizado por el proxy | `ProxyAuthGuard`          |
|   2 | `<SECURITY_HEADER_NAME>`; normalmente `x-id-acceso` | Identificador de acceso vigente | Localizar las llaves de cifrado asociadas al consumidor                       | `KeysGuard`/`KeysService` |

Ejemplo abstracto:

```http
POST https://<host>/<prefix>/v1/bloques
Content-Type: application/json
<PROXY_HEADER_NAME>: <JWT_DEL_PROXY>
x-id-acceso: <ID_DE_ACCESO>

{
  "...": "valores según el contrato cifrado"
}
```

El prefijo, versión y URL base se configuran mediante `MICROSERVICE_URL`, `MICROSERVICE_PREFIX` y `MICROSERVICE_VERSION`. El arranque usa versionamiento URI y registra Swagger en `<prefix>/v<version>/swagger`, como se observa en [main.ts](../src/main.ts:44).

## 6. Endpoints protegidos

Los siguientes endpoints tienen `ProxyAuthGuard` y `UseEncryptionV2`; para consumirlos se deben enviar el JWT del proxy y el `x-id-acceso`/encabezado de seguridad configurado.

|   # | Método | Ruta                | Módulo | Autenticación y llaves                                  |
| --: | ------ | ------------------- | ------ | ------------------------------------------------------- |
|   1 | POST   | `/paises`           | Paises | JWT del proxy + ID de acceso; entrada y salida cifradas |
|   2 | POST   | `/paises/busquedas` | Paises | JWT del proxy + ID de acceso; entrada y salida cifradas |
|   3 | PUT    | `/paises`           | Paises | JWT del proxy + ID de acceso; entrada y salida cifradas |

La ruta completa agrega el prefijo y la versión configurados, por ejemplo:

```text
<MICROSERVICE_URL><MICROSERVICE_PREFIX>/v<MICROSERVICE_VERSION>/bloques
```

## 7. Endpoints internos sin guardia visible

Puedes existir rutas con sufijo `/interno` que no tienen `@UseGuards(ProxyAuthGuard)`, `@UseEncryptionV2` ni `@ApiSecurity('x-id-acceso')` en sus controladores actuales.

|   # | Método | Ruta                                                    | Módulo | Observación                                          |
| --: | ------ | ------------------------------------------------------- | ------ | ---------------------------------------------------- |
|   1 | POST   | `/paises/configuraciones/funcionales/busquedas/interno` | Paises | No tiene guardia de proxy ni carga visible de llaves |
|   2 | POST   | `/paises/configuraciones/visuales/busquedas/interno`    | Paises | No tiene guardia de proxy ni carga visible de llaves |
|   3 | POST   | `/paises/detalles/busquedas/interno`                    | Paises | No tiene guardia de proxy ni carga visible de llaves |

Estas rutas están destinadas a llamadas internas, la protección se delega a un API Gateway, balanceador, red privada o regla de infraestructura.

## 8. Swagger v2

Para conocer el contrato operativo actual se debe priorizar:

1. Los controladores y sus guards.
2. Las variables de entorno desplegadas.
3. La configuración del proxy corporativo.
4. El Swagger generado por la aplicación.

## 9. Variables de entorno relacionadas

|   # | Variable                            | Uso                                                                                            |
| --: | ----------------------------------- | ---------------------------------------------------------------------------------------------- |
|   1 | `PROXY_HEADER_NAME`                 | Nombre del encabezado que transporta el JWT del proxy                                          |
|   2 | `PROXY_SECRET_KEY`                  | Llave pública usada para verificar la firma del JWT                                            |
|   3 | `SECURITY_HEADER_NAME`              | Nombre del encabezado que transporta el ID de acceso                                           |
|   4 | `SECURITY_SERVICE_URL`              | URL base del servicio que entrega las llaves                                                   |
|   5 | `SECURITY_PREFIX`                   | Prefijo de la ruta del servicio de seguridad                                                   |
|   6 | `SECURITY_SERVICE_VERSION`          | Versión de la ruta del servicio de seguridad                                                   |
|   7 | `SECURITY_SERVICE_KEYS_PATH`        | Segmento de ruta para consultar las llaves                                                     |
|   8 | `SECURITY_ENCRYPTION_SYMMETRIC_KEY` | Llave para descifrar las llaves entregadas por el servicio de seguridad                        |
|   9 | `SECURITY_ENCRYPTION_HMACKEY`       | Llave HMAC para validar integridad al descifrar las llaves entregadas                          |
|  10 | `ASYMMETRIC_CRYPTO_PRIVATE_KEY`     | Llave privada usada por utilidades criptográficas locales y por el generador JWT de desarrollo |
|  11 | `ASYMMETRIC_CRYPTO_PUBLIC_KEY`      | Llave pública usada por utilidades criptográficas locales                                      |

La aplicación valida como obligatorias varias de estas variables al iniciar en [validate-env.ts](../src/validate-env.ts:10). Los valores deben permanecer fuera del código, fixtures, documentación y control de versiones.

## 10. Conclusión

Para consumir correctamente un endpoint protegido se necesita:

1. Obtener un JWT válido del proxy corporativo o generar uno de desarrollo con el par de llaves compatible.
2. Obtener un `x-id-acceso` vigente del servicio de seguridad.
3. Enviar ambos valores en cada solicitud protegida.
4. Enviar y recibir los campos conforme al contrato de cifrado asociado a las llaves del ID de acceso.
5. Usar la URL con el prefijo y la versión URI configurados.

El control principal de acceso es el JWT del proxy; el `x-id-acceso` habilita la obtención de las llaves necesarias para el cifrado de datos. Son controles relacionados, pero no intercambiables.
