/**
 * 
 */
package com.baz.divisas.general.config;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;

import com.baz.divisas.general.util.LogApp;

// TODO: Auto-generated Javadoc
/**
 * The Class PropPromoEspacio.
 *
 * @author B86960
 */
public class PropPromoEspacio {
	
	/** The path config. */
	private static String PATH_CONFIG;
	
	/** The Constant FILE_CONFIG. */
	private static final String FILE_CONFIG = "APIPortalDivisas.properties";
	
	/** The auth usuario. */
	private static String authUsuario;
	
	/** The auth clave. */
	private static String authClave;
	
	/** The url token. */
	private static String urlToken;
	
	/** The url tableros. */
	private static String urlTableros;
	
	
	
	/** The url token. */
	private static String urlTokenV2;
	
	/** The url tableros. */
	private static String urlTablerosV2;
	
	/** The client id. */
	private static String clientId;
	
	/** The cliente name. */
	private static String clienteName;
	
	/** The cliente secret. */
	private static String clienteSecret;
	
	/** The public key. */
	private static String publicKey;
	

	static {
		
		if((System.getProperty("jboss.server.base.dir") != null)) {
			PATH_CONFIG =System.getProperty("jboss.server.base.dir") + File.separator + "config" + File.separator;
		}
		else {
			PATH_CONFIG = "." + File.separator + "properties" + File.separator + "properties_des" + File.separator;
		}
		try {
			
			Properties properties = ExternalConfigLoader.loadProperties(
					FilenameUtils.normalize(PATH_CONFIG + FILE_CONFIG));
			
			authUsuario = properties.getProperty("IDUSUARIOPROMOESPACIOS").trim();
			authClave = properties.getProperty("CVEPROMOESPACIOS").trim();
			urlToken = properties.getProperty("URLTOKENTABLEROS").trim();
			urlTableros = properties.getProperty("URLTABLEROS").trim();
			
			urlTokenV2= properties.getProperty("URLTOKENTABLEROSV2").trim();			
			urlTablerosV2 = properties.getProperty("URLTABLEROSV2").trim();
			clientId= properties.getProperty("CLIENTID").trim();
			clienteName= properties.getProperty("CLIENTANAME").trim();
			clienteSecret= properties.getProperty("CLIENTSECRET").trim();
			publicKey= properties.getProperty("PUBLICKEY").trim();

		}
		catch (Exception e) {
			LogApp.excepcion(e, "Error al cargar PropClientes");
		}
		
	}



	/**
	 * Gets the auth usuario.
	 *
	 * @return the authUsuario
	 */
	public static String getAuthUsuario() {
		return authUsuario;
	}



	/**
	 * Gets the auth clave.
	 *
	 * @return the authClave
	 */
	public static String getAuthClave() {
		return authClave;
	}



	/**
	 * Gets the url token.
	 *
	 * @return the urlToken
	 */
	public static String getUrlToken() {
		return urlToken;
	}



	/**
	 * Gets the url tableros.
	 *
	 * @return the urlTableros
	 */
	public static String getUrlTableros() {
		return urlTableros;
	}



	/**
	 * Gets the client id.
	 *
	 * @return the client id
	 */
	public static String getClientId() {
		return clientId;
	}



	/**
	 * Gets the cliente name.
	 *
	 * @return the cliente name
	 */
	public static String getClienteName() {
		return clienteName;
	}



	/**
	 * Gets the cliente secret.
	 *
	 * @return the cliente secret
	 */
	public static String getClienteSecret() {
		return clienteSecret;
	}



	/**
	 * Gets the public key.
	 *
	 * @return the public key
	 */
	public static String getPublicKey() {
		return publicKey;
	}



	/**
	 * Gets the url token V 2.
	 *
	 * @return the url token V 2
	 */
	public static String getUrlTokenV2() {
		return urlTokenV2;
	}



	/**
	 * Gets the url tableros V 2.
	 *
	 * @return the url tableros V 2
	 */
	public static String getUrlTablerosV2() {
		return urlTablerosV2;
	}
	
}
