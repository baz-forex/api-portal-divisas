package com.baz.divisas.security.jwt;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.baz.divisas.general.config.PropApp;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Clase JwtUtils
 * 
 * @author B813196
 * itoxtle
 */

@Component
public class JwtUtils {

	private static final long JWT_EXPIRACION = PropApp.getTokenJwtExpiracion();
	
	private HttpServletRequest request;
	private PrivateKey privateKey;
	
	
	public JwtUtils() {
		super();
	}

	@Autowired
	public JwtUtils(HttpServletRequest request) throws IOException {
		super();
		this.request = request;
		this.privateKey = getPrivateKeyFromPEM(PropApp.getPrivateKeyPath()); 
	}

	/**
	 * Metodo que genera JWT de autenticacion
	 * @param authentication - Objeto de autenticacion
	 * @return JWT
	 */
	public String generateJwtToken(UserDetails userPrincipal) {
		
		return buildJwtToken(userPrincipal.getUsername(), JWT_EXPIRACION);
	}
	
	/**
	 * Metodo que genera JWT de autenticacion
	 * @param idusuario - Identificador de usuario
	 * @return JWT
	 */
	public String generateJwtToken(String idusuario) {

		return buildJwtToken(idusuario, JWT_EXPIRACION);
	}
	
	/**
	 * Metodo que construye el JWT
	 * @param user - Usuario
	 * @param expiracion - Tiempo en Ms de expiracion
	 * @return JWT
	 */
	private String buildJwtToken(String user, long expiracion) {
		
		Map<String, Object> claimsPayload = new HashMap<>();
		claimsPayload.put(Constant.IP, request.getRemoteAddr());
		claimsPayload.put(Constant.USER_AGENT, request.getHeader(Constant.USER_AGENT));
		
		return Jwts.builder()
				.setClaims(claimsPayload)
				.setSubject((user))
				.setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plusMillis(expiracion)))
				.signWith(SignatureAlgorithm.RS256, privateKey)
				.compact();
		
	}

	/**
	 * Metodo que obtiene el usuario contenido en un JWT
	 * @param token
	 * @return
	 */
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	/**
	 * Metodo que obtiene la IP contenida en un JWT
	 * @param token
	 * @return
	 */
	public String  getIpFromJwtToken(String token) {
		return (String) Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token).getBody().get(Constant.IP);
	}

	/**
	 * Metodo que obtiene el Navegador contenido en un JWT
	 * @param token
	 * @return
	 */
	public String getNavegadorFromJwtToken(String token) {
		return (String) Jwts.parser().setSigningKey(privateKey).parseClaimsJws(token).getBody().get(Constant.USER_AGENT);
	}

	/**
	 * Metodo que devuelve el tiempo de expiracion del JWT
	 * @return
	 */
	public long getJwtExpirationMs() {
		return JWT_EXPIRACION;
	}

	/**
	 * Metodo de validacion del token
	 * Formato, expiracion
	 * @param authToken - JWT 
	 * @return boolean
	 * @throws IOException 
	 */
	public boolean validateJwtToken(String authToken) {
		try {
			
			Jwts.parser().setSigningKey(privateKey).parseClaimsJws(authToken);
			return true;
		} 
		catch (JwtException e) {
			throw e;
		} 
		catch (IllegalArgumentException e) {
			LogApp.mensaje("Illegal Argument", e);
		}

		return false;
	}
	
	/**
	 * Metodo de extraccion del token contenido en el Header de Autorizacion
	 * @param request - Request
	 * @return
	 */
	public String parseJwt(HttpServletRequest request) {
		
		String headerAuth = request.getHeader(Constant.AUTHORIZATION);

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(Constant.BEARER)) {
			return headerAuth.substring(Constant.LENGTH_BEARER, headerAuth.length());
		}

		return null;
	}
	
	private static PrivateKey getPrivateKeyFromPEM(String filename) throws IOException {

		PrivateKey key;
		byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
		Reader targetReader = new StringReader(IOUtils.toString(keyBytes, UTF_8.name()));

		try (PEMParser pem = new PEMParser(targetReader)) {
			JcaPEMKeyConverter jcaPEMKeyConverter = new JcaPEMKeyConverter();
			Object pemContent = pem.readObject();
			if (pemContent instanceof PEMKeyPair) {
				PEMKeyPair pemKeyPair = (PEMKeyPair) pemContent;
				KeyPair keyPair = jcaPEMKeyConverter.getKeyPair(pemKeyPair);
				key = keyPair.getPrivate();
			} 
			else if (pemContent instanceof PrivateKeyInfo) {
				PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) pemContent;
				key = jcaPEMKeyConverter.getPrivateKey(privateKeyInfo);
			} 
			else {
				throw new IllegalArgumentException("Unsupported private key format '" +
						pemContent.getClass().getSimpleName() + '"');
			}
		}

		return key;
	}
}
