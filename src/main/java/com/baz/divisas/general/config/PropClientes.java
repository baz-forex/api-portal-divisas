package com.baz.divisas.general.config;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;

import com.baz.divisas.general.util.LogApp;

/**
 * Clase de propiedades de la aplicacion.
 *
 * @author B813196
 */
public final class PropClientes {
	
	/** The path config. */
	private static String PATH_CONFIG;
	
	/** The Constant FILE_CONFIG. */
	private static final String FILE_CONFIG = "APIPortalDivisas.properties";	
	
	/** The auth usuario. */
	private static String authUsuario;
	
	/** The auth clave. */
	private static String authClave;
	
	/** The auth usuario admin. */
	private static String authUsuarioAdmin;
	
	/** The auth clave admin. */
	private static String authClaveAdmin;
	
	/** The url middleware. */
	private static String urlMiddleware;
	
	/** The url ws auth token. */
	private static String urlWsAuthToken;
	
	/** The url ws busq id cliente. */
	private static String urlWsBusqIdCliente;
	
	/** The url ws busq dinamica clientes. */
	private static String urlWsBusqDinamicaClientes;
	
	/** The url ws actualiza clientes. */
	private static String urlWsActualizaClientes;
	
	/** The url ws tipo cambio. */
	private static String urlWsTipoCambio;
	
	/** The bitacora carga masiva clientes. */
	private static String bitacoraCargaMasivaClientes;
	
	/** The bitacora actualizacion clientes. */
	private static String bitacoraActualizacionClientes;
	
	/** The clientes agrupados. */
	private static String clientesAgrupados;
	
	/** The bitacora carga hist alto riesgo. */
	private static String bitacoraCargaHistAltoRiesgo;
	
	/** The clientes alto riesgo. */
	private static String clientesAltoRiesgo;
	
	/** The bitacora cupones TA. */
	private static String bitacoraCuponesTA;
	
	/** The cupones TA. */
	private static String cuponesTA;
	
	/** The cupones beneficios. */
	private static String cuponesBeneficios;
	
	/** The bitacora cupones CRM. */
	private static String bitacoraCuponesCRM;
	
	/** The cupones Mas Salud. */
	private static String cuponesMasSalud;
	
	/** The bitacora cupones Mas Salud. */
	private static String bitacoraCuponesMasSalud;
	
	/** The bitacora cupones alianzas. */
	private static String bitacoraCuponesAlianzas;
	
	/** The inventario costal santa. */
	private static String inventarioCostalSanta;
	
	/** The operaciones costal santa. */
	private static String operacionesCostalSanta;
	
	/** The Constant FORMATOFECHABDCOMPLETA. */
	public static final String FORMATOFECHABDCOMPLETA = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	
	

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
			
			authUsuario = properties.getProperty("AUTHUSR").trim();
			authClave = properties.getProperty("AUTHCLAVE").trim();
			authUsuarioAdmin = properties.getProperty("AUTHUSRADMIN").trim();
			authClaveAdmin = properties.getProperty("AUTHCLAVEADMIN").trim();
			urlMiddleware = properties.getProperty("URLMIDDLEWARE").trim();
			urlWsAuthToken = properties.getProperty("URLAUTHTOKEN").trim();
			urlWsBusqIdCliente = properties.getProperty("URLBSQIDCLIENTE").trim();
			urlWsBusqDinamicaClientes = properties.getProperty("URLBSQDINAMICACLIENTE").trim();
			urlWsActualizaClientes = properties.getProperty("URLACTUALIZACLIENTE").trim();
			urlWsTipoCambio = properties.getProperty("URLTIPOCAMBIO").trim();
			bitacoraCargaMasivaClientes = properties.getProperty("BITACORACARGAMASIVACTESCOLECCION").trim();
			bitacoraActualizacionClientes = properties.getProperty("BITACORAACTUALIZACLIENTESCOLECCION").trim();
			clientesAgrupados = properties.getProperty("CLIENTESAGRUPADOS").trim();
			bitacoraCargaHistAltoRiesgo = properties.getProperty("BITACORAALTORIESGO").trim();
			clientesAltoRiesgo = properties.getProperty("CLIENTESALTORIESGO").trim();
			
			bitacoraCuponesTA= properties.getProperty("BITACORACUPONESTA").trim();			
			cuponesTA= properties.getProperty("CUPONESTACOLECCION").trim();
			
			cuponesBeneficios = properties.getProperty("CUPONESBENEFICIOS").trim();
			bitacoraCuponesCRM = properties.getProperty("BITACORACUPONESCRM").trim();
			
			cuponesMasSalud = properties.getProperty("CUPONESMASSALUD").trim();
			bitacoraCuponesMasSalud = properties.getProperty("BITACORACUPONESMASSALUD").trim();
			
			bitacoraCuponesAlianzas = properties.getProperty("BITACORACUPONESALIANZAS").trim();
			
			inventarioCostalSanta = properties.getProperty("INVENTARIOCOSTALSANTA").trim();
			operacionesCostalSanta = properties.getProperty("OPERACIONESCOSTALSANTA").trim();
		}
		catch (Exception e) {
			LogApp.excepcion(e, "Error al cargar PropClientes");
		}
		
	}
	
	/**
	 * Constructor de la clase.
	 */
	private PropClientes() {
		super();
	}

	/**
	 * Gets the auth usuario.
	 *
	 * @return the auth usuario
	 */
	public static String getAuthUsuario() {
		return authUsuario;
	}

	/**
	 * Gets the auth clave.
	 *
	 * @return the auth clave
	 */
	public static String getAuthClave() {
		return authClave;
	}

	/**
	 * Gets the auth usuario admin.
	 *
	 * @return the auth usuario admin
	 */
	public static String getAuthUsuarioAdmin() {
		return authUsuarioAdmin;
	}

	/**
	 * Gets the auth clave admin.
	 *
	 * @return the auth clave admin
	 */
	public static String getAuthClaveAdmin() {
		return authClaveAdmin;
	}

	/**
	 * Gets the url middleware.
	 *
	 * @return the url middleware
	 */
	public static String getUrlMiddleware() {
		return urlMiddleware;
	}

	/**
	 * Gets the url ws auth token.
	 *
	 * @return the url ws auth token
	 */
	public static String getUrlWsAuthToken() {
		return urlWsAuthToken;
	}

	/**
	 * Gets the url ws busq id cliente.
	 *
	 * @return the url ws busq id cliente
	 */
	public static String getUrlWsBusqIdCliente() {
		return urlWsBusqIdCliente;
	}

	/**
	 * Gets the url ws busq dinamica clientes.
	 *
	 * @return the url ws busq dinamica clientes
	 */
	public static String getUrlWsBusqDinamicaClientes() {
		return urlWsBusqDinamicaClientes;
	}

	/**
	 * Gets the url ws actualiza clientes.
	 *
	 * @return the url ws actualiza clientes
	 */
	public static String getUrlWsActualizaClientes() {
		return urlWsActualizaClientes;
	}
	
	

	/**
	 * Gets the url ws tipo cambio.
	 *
	 * @return the urlWsTipoCambio
	 */
	public static String getUrlWsTipoCambio() {
		return urlWsTipoCambio;
	}

	/**
	 * Gets the bitacora carga masiva clientes.
	 *
	 * @return the bitacora carga masiva clientes
	 */
	public static String getBitacoraCargaMasivaClientes() {
		return bitacoraCargaMasivaClientes;
	}

	/**
	 * Gets the bitacora actualizacion clientes.
	 *
	 * @return the bitacora actualizacion clientes
	 */
	public static String getBitacoraActualizacionClientes() {
		return bitacoraActualizacionClientes;
	}

	/**
	 * Gets the clientes agrupados.
	 *
	 * @return the clientesAgrupados
	 */
	public static String getClientesAgrupados() {
		return clientesAgrupados;
	}

	/**
	 * Gets the bitacora carga hist alto riesgo.
	 *
	 * @return the bitacora carga hist alto riesgo
	 */
	public static String getBitacoraCargaHistAltoRiesgo() {
		return bitacoraCargaHistAltoRiesgo;
	}

	/**
	 * Gets the clientes alto riesgo.
	 *
	 * @return the clientes alto riesgo
	 */
	public static String getClientesAltoRiesgo() {
		return clientesAltoRiesgo;
	}

	/**
	 * Gets the bitacora cupones TA.
	 *
	 * @return the bitacora cupones TA
	 */
	public static String getBitacoraCuponesTA() {
		return bitacoraCuponesTA;
	}

	/**
	 * Gets the cupones TA.
	 *
	 * @return the cupones TA
	 */
	public static String getCuponesTA() {
		return cuponesTA;
	}

	/**
	 * Gets the cupones beneficios.
	 *
	 * @return the cupones beneficios
	 */
	public static String getCuponesBeneficios() {
		return cuponesBeneficios;
	}

	/**
	 * Gets the bitacora cupones CRM.
	 *
	 * @return the bitacora cupones CRM
	 */
	public static String getBitacoraCuponesCRM() {
		return bitacoraCuponesCRM;
	}
	
	/**
	 * Gets the cupones Mas Salud.
	 *
	 * @return the cupones Mas Salud
	 */
	public static String getCuponesMasSalud() {
		return cuponesMasSalud;
	}

	/**
	 * Gets the bitacora cupones Mas Salud.
	 *
	 * @return the bitacora cupones Mas Salud
	 */
	public static String getBitacoraCuponesMasSalud() {
		return bitacoraCuponesMasSalud;
	}

	/**
	 * Gets the bitacora cupones alianzas.
	 *
	 * @return the bitacora cupones alianzas
	 */
	public static String getBitacoraCuponesAlianzas() {
		return bitacoraCuponesAlianzas;
	}

	/**
	 * Gets the inventario costal santa.
	 *
	 * @return the inventario costal santa
	 */
	public static String getInventarioCostalSanta() {
		return inventarioCostalSanta;
	}

	/**
	 * Gets the operaciones costal santa.
	 *
	 * @return the operaciones costal santa
	 */
	public static String getOperacionesCostalSanta() {
		return operacionesCostalSanta;
	}
	

}
