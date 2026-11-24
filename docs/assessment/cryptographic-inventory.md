# Inventario criptográfico AS-IS

## Algoritmos y formatos observados

| Uso | Algoritmo/formato | Evidencia | Compatibilidad/riesgo |
|---|---|---|---|
| JWT | RS256, llave privada PEM cargada por ruta, Base64/JWS | `JwtUtils.java:58-62,96-102,148-175` | no se evidencia JWKS, issuer, audience, rotación o revocación |
| Password/client secret | BCrypt | `WebSecurityConfig.java:67-70` | conservar verificación; no imprimir secretos |
| AES legacy | AES configurable + IV de 16 bytes + HMAC-SHA256 concatenado y Base64 | `AESUtils.java:24-71` | formato depende de `ALGORITMOAES`; requiere vectores sintéticos |
| AES moderno | AES/GCM/NoPadding, IV 12 bytes, tag 128, AAD `hash`, Base64 | `AESUtils.java:98-117` | compatible solo con parámetros exactos |
| RSA pagos digitales | RSA-OAEP SHA-256/MGF1-SHA256, claves X.509/PKCS#8 Base64 | `RSAUtilsPagosDigitales.java:19-77` | validar interoperabilidad y tamaño de payload |
| Promo Espacio | AES-GCM IV 16 bytes + RSA-OAEP SHA-256/MGF1-SHA1 sobre Base64 de clave | `api/tef/promoespacio/services/CryptoService.java:22-96` | formato distinto al RSA anterior; estado `randomKey` mutable en servicio |

## Material sensible

Los archivos de configuración contienen nombres de propiedades y referencias a llaves/secretos. Este inventario omite valores, rutas concretas, tokens y claves. Los logs de servicios TEF y `LoggingFilter` requieren revisión porque pueden capturar headers o respuestas.

## Reglas para migración

1. Implementar compatibilidad detrás de `@gs/crypto`, con versionado de formato.
2. Crear golden vectors sintéticos para cifrado/descifrado y firma.
3. No rotar algoritmos ni llaves durante el piloto sin plan de doble lectura aprobado.
4. No aceptar algoritmos negociados desde entrada del cliente.
5. No registrar plaintext, ciphertext completo, Authorization, llaves ni excepciones con material sensible.
6. Aprobar explícitamente cualquier cambio de OAEP MGF, tamaño de IV, AAD, tag o encoding.
