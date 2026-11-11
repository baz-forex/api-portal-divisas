package com.baz.divisas.api.auth.service;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.security.SecureRandom;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baz.divisas.api.auth.ClientIdAlreadyExistsException;
import com.baz.divisas.api.auth.dao.AuthClientDao;
import com.baz.divisas.api.auth.model.AuthClient;
import com.baz.divisas.general.model.Constant;
import com.baz.divisas.general.util.LogApp;
import com.mongodb.MongoWriteException;

/**
 * Service AuthClient
 * @author B813196
 * itoxtle
 */
@Service
public class AuthClientServiceImpl{

	private PasswordEncoder passwordEncoder;
	private AuthClientDao authDao;

	/**
	 * Cosntructor de la clase
	 * @param authDao - Dao AuthClient
	 * @param passwordEncoder - PasswordEncoder
	 */
	@Autowired
	public AuthClientServiceImpl(AuthClientDao authDao, PasswordEncoder passwordEncoder) {
		super();
		this.authDao = authDao;
		this.passwordEncoder = passwordEncoder;
		
	}

	
	/**
	 * Metodo para crear un Cliente Api Divisas
	 * @param clientId - ClientId
	 * @return AuthClient
	 */
	public AuthClient createAuthClient(String clientId) {
		if (authDao.findById(clientId) != null) {
			throw new ClientIdAlreadyExistsException(clientId);
		}
		
		String passClient = generatePassword();
		AuthClient authUserDB = new AuthClient();
		authUserDB.setClientId(clientId);
		authUserDB.setClientSecret(passwordEncoder.encode(passClient));

		try {
			authDao.insertClient(authUserDB);
		}
		catch (MongoWriteException e) {
			// La validación previa no elimina una posible condición de carrera.
			if (e.getError() != null && e.getError().getCode() == 11000) {
				throw new ClientIdAlreadyExistsException(clientId);
			}
			throw e;
		}
		
		authUserDB.setClientId(Hex.encodeHexString(authUserDB.getClientId().getBytes(UTF_8)));
		authUserDB.setClientSecret(Hex.encodeHexString(passClient.getBytes(UTF_8)));

		return authUserDB;
	}

	private static String generatePassword() {
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(Constant.INT_16);
		for (int i = 0; i < Constant.INT_16; i++) {
			sb.append(Constant.CHARS.charAt(random.nextInt(Constant.CHARS.length())));
		}
		return sb.toString();
	}


	/**
	 * Metodo que obtiene de BD el client_secret del cliente
	 * @param credentials - Credenciales
	 * @return - AuthClient
	 */
	public AuthClient getAuthClient(String credentials) {
		
		String clientId = credentials.substring(0, credentials.lastIndexOf(Constant.COLON));
		
		return authDao.findById(clientId);

	}
	
	/**
	 * Metodo que parsea el client_secret Hex --> Hash
	 * @param entity - Request
	 * @return - client_secret
	 */
	public String parseClientSecret(Map<String, Object> entity) {
		return parseHexToString(entity.get(Constant.CLIENT_SECRET).toString());
	}


	/**
	 * Metodo que extrae las credenciales del Request
	 * @param entity - Request
	 * @return - Credenciales
	 */
	public String extractCredentials(Map<String, Object> entity) {

		String credential = null;
		if (entity.containsKey(Constant.CLIENT_ID) && entity.containsKey(Constant.CLIENT_SECRET)) {

			String clientId = parseHexToString(entity.get(Constant.CLIENT_ID).toString());
			String clientSecret = parseHexToString(entity.get(Constant.CLIENT_SECRET).toString());

			credential = clientId + Constant.COLON
					+ passwordEncoder.encode(clientSecret);
		}
		return credential;
	}

	private static String parseHexToString(String hexString) {
		byte[] bytes = new byte[0];
		try {
			bytes = Hex.decodeHex(hexString.toCharArray());
			return new String(bytes, UTF_8);
		} 
		catch (DecoderException e) {
			LogApp.mensaje("Error al decodificar hexString: "+ hexString, e);
			return "";
		}
	}

}
