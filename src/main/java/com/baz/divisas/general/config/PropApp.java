package com.baz.divisas.general.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;

import com.baz.divisas.general.util.LogApp;

/**
 * Clase de propiedades de la aplicacion.
 *
 * @author B813196
 */
public final class PropApp {

	/** The path config. */
	private static String PATH_CONFIG;

	/** The Constant FILE_CONFIG. */
	private static final String FILE_CONFIG = "APIPortalDivisas.properties";

	/** The Constant MS_MIN. */
	private static final long MS_MIN = TimeUnit.MINUTES.toMillis(1);

	/** The ip mail. */
	private static String ipMail;
	
	/** The host mail. */
	private static String hostMail;
	
	/** The remitente mail. */
	private static String remitenteMail;
	
	/** The destinatarios notificaciones. */
	public static String destinatariosNotificaciones;

	/** The ip mail certificacion. */
	private static String ipMailCertificacion;
	
	/** The host mail certificacion. */
	private static String hostMailCertificacion;
	
	/** The remitente mail certificacion. */
	private static String remitenteMailCertificacion;
	
	/** The correo gerente. */
	private static String correoGerente;

	/** The private key path. */
	private static String privateKeyPath;
	
	/** The token jwt expiracion. */
	private static long tokenJwtExpiracion;

	/** The algoritmo AES. */
	private static String algoritmoAES;
	
	/** The algoritmo RSA. */
	private static String algoritmoRSA;
	
	/** The Constant mapIdentificaciones. */
	private static final Map<String, String> mapIdentificaciones = new HashMap<>();
	
	/** The grant type api. */
	private static String grantTypeApi;
	
	/** The url apigee on prem. */
	private static String urlApigeeOnPrem;
	/** The url token api. */
	private static String urlTokenApi;
	
	/** The url tipo cambio tesoreria. */
	private static String urlTipoCambioTesoreria;
	
	/** The reporteria operacion alnova. */
	private static String reporteriaOperacionAlnova;
	
	/** The reporteria regulatorios alnova. */
	private static String reporteriaRegulatoriosAlnova;
	
	/** The reporte spot operacion alnova. */
	private static String reporteSpotOperacionAlnova;
	
	/** The reporte spot regulatorios alnova. */
	private static String reporteSpotRegulatoriosAlnova;

	/** The clave negocio. */
	private static String claveNegocio;
	
	/** The url api AWSINT. */
	private static String urlApiAWSINT; 
	
	/** The url token api AWSINT. */
	private static String urlTokenApiAWSINT;
	
	/** The url registra tipos cambio MM. */
	private static String urlRegistraTiposCambioMM;
	
	/** The consumer key api AWS. */
	private static String consumerKeyApiAWS;
	
	/** The consumer secret api AWS. */
	private static String consumerSecretApiAWS;
	
	/** The consumer key api. */
	private static String consumerKeyApi;
	
	/** The consumer secret api. */
	private static String consumerSecretApi;
	
	/** The sucursal MM. */
	private static String sucursalMM;	
	
	//Divisas
	/** The url token api AWSINT. */
	private static String urlApiSeguridadDivisas;
	
	/** The url token api AWSINT. */
	private static String urlRegistraTiposCambioDivisas;
	
	/** The consumer key api. */
	private static String consumerKeyApiDivisas;
	
	/** The consumer secret api. */
	private static String consumerSecretApiDivisas;


	/** The url consulta parametros. */
	//Parametros de sucursal
	private static String urlConsultaParametros;
	
	/** The url gestion parametros. */
	private static String urlGestionParametros;
	
	/** The url gestion parametros masicos. */
	private static String urlGestionParametrosMasivos;
	
	/** The url consulta asignaciones parametros. */
	private static String urlConsultaAsignacionesParametros;
	
	/** The url gestion asignaciones. */
	private static String urlGestionAsignaciones;
	
	/** The url gestion asignaciones masivas. */
	private static String urlGestionAsignacionesMasivas;
	
	/** The consumer key parametros. */
	private static String consumerKeyParametros;
	
	/** The consumer secret parametros. */
	private static String consumerSecretParametros;
	
	/** The load prop. */
	private static boolean loadProp;

	static {

		if ((System.getProperty("jboss.server.base.dir") != null)) {
			PATH_CONFIG = System.getProperty("jboss.server.base.dir") + File.separator + "config"
					+ File.separator;
		}
		else {
			PATH_CONFIG = "." + File.separator + "properties" + File.separator + "properties_des"
					+ File.separator;
		}

		try {

			Properties properties = ExternalConfigLoader.loadProperties(
					FilenameUtils.normalize(PATH_CONFIG + FILE_CONFIG));

			// Configuracion de servidor de correo
			ipMail = properties.getProperty("IPMAIL").trim();
			hostMail = properties.getProperty("HOSTMAIL").trim();
			remitenteMail = properties.getProperty("REMITENTEMAIL").trim();
			destinatariosNotificaciones = properties.getProperty("DESTINATARIOSNOTIFICACIONES")
					.trim();

			// Configuracion JWT
			privateKeyPath = ExternalConfigLoader.resolvePath("JWT_PRIVATE_KEY_PATH",
					PATH_CONFIG + "divisas-ece-private");
			tokenJwtExpiracion = MS_MIN
					* Integer.parseInt(properties.getProperty("MINTOKENJWTEXPIRACION").trim());

			// Configuracion algoritmos de encriptacion
			algoritmoAES = properties.getProperty("ALGORITMOAES").trim();
			algoritmoRSA = properties.getProperty("ALGORITMORSA").trim();

			// Configuracion de tipos de identificacion
			int numIdentificaciones = Integer
					.parseInt(properties.getProperty("NUMIDENTIFICACIONES").trim());
			for (int i = 1; i <= numIdentificaciones; i++) {
				mapIdentificaciones.put(properties.getProperty("IDIDENTIF" + i).trim(),
						properties.getProperty("DESCIDENTIF" + i).trim());
			}

			// Configuración Correo Certificación de Sucursales
			ipMailCertificacion = properties.getProperty("IPMAILCERTIFICACION").trim();
			hostMailCertificacion = properties.getProperty("HOSTMAILCERTIFICACION").trim();
			remitenteMailCertificacion = properties.getProperty("REMITENTEMAILCERTIFICACION")
					.trim();
			correoGerente = properties.getProperty("CORREOGERENTE").trim();
			grantTypeApi = properties.getProperty("GRANTTYPEAPI").trim();
			urlApigeeOnPrem = properties.getProperty("URLAPIGEE").trim();
			urlTokenApi = properties.getProperty("URLTOKENAPI").trim();
			urlTipoCambioTesoreria = properties.getProperty("URLTIPOCAMBIOTESORERIA").trim();
			claveNegocio = properties.getProperty("CLAVENEGOCIOTESORERIA").trim();
			reporteriaOperacionAlnova= properties.getProperty("REPORTERIAOPERACIONALNOVA").trim();
			reporteriaRegulatoriosAlnova= properties.getProperty("REPORTERIAREGULATORIOSNALNOVA").trim();
			reporteSpotOperacionAlnova= properties.getProperty("REPORTETIPOCAMBIOSPOTOPERACION").trim();
			reporteSpotRegulatoriosAlnova= properties.getProperty("REPORTETIPOCAMBIOSPOTREGULATORIOS").trim();
			
			urlApiAWSINT = properties.getProperty("URLAPIAWSINT").trim();
			urlTokenApiAWSINT = properties.getProperty("URLTOKENAPIAWSINT").trim();
			consumerKeyApiAWS = properties.getProperty("CONSUMERKEYAPIAWS").trim();
			consumerSecretApiAWS = properties.getProperty("CONSUMERSECRETAPIAWS").trim();
			urlRegistraTiposCambioMM = properties.getProperty("URLREGISTRATIPOSCAMBIOMM").trim();
			sucursalMM = properties.getProperty("SUCURSALMM").trim();	
			consumerKeyApi = properties.getProperty("CONSUMERKEYAPI").trim();
			consumerSecretApi = properties.getProperty("CONSUMERSECRETAPI").trim();
			//Divisas
			urlApiSeguridadDivisas= properties.getProperty("URLSEGURIDADDIVISAS").trim();
			urlRegistraTiposCambioDivisas= properties.getProperty("URLREGISTRATIPOSCAMBIODIVISAS").trim();
			consumerKeyApiDivisas= properties.getProperty("CONSUMERKEYAPIAWSDIVISAS").trim();
			consumerSecretApiDivisas= properties.getProperty("CONSUMERSECRETAPIAWSDIVISAS").trim();
			
			//Parametros de sucursal
			urlConsultaParametros = properties.getProperty("URLCONSULTAPARAMETROSDIVISAS").trim();			
			urlGestionParametros = properties.getProperty("URLGESTIONPARAMETROS").trim();			
			urlGestionParametrosMasivos = properties.getProperty("URLGESTIONPARAMETROSMASIVOS").trim();			
			urlConsultaAsignacionesParametros = properties.getProperty("URLCONSULTAASIGNACIONESPARAMETROS").trim();			
			urlGestionAsignaciones = properties.getProperty("URLGESTIONASIGNACIONES").trim();			
			urlGestionAsignacionesMasivas = properties.getProperty("URLGESTIONASIGNACIONESMASIVAS").trim();			
			consumerKeyParametros = properties.getProperty("CONSUMERKEYAPIPARAMETROS").trim();			
			consumerSecretParametros = properties.getProperty("CONSUMERSECRETAPIPARAMETROS").trim();
			loadProp = true;
		}
		catch (Exception e) {
			LogApp.excepcion(e, "Error al cargar propiedades de la aplicacion");
		}

	}

	/**
	 * Constructor de la clase.
	 */
	private PropApp() {
		super();
	}

	/**
	 * Gets the ip mail.
	 *
	 * @return the ip mail
	 */
	public static String getIpMail() {
		return ipMail;
	}

	/**
	 * Gets the host mail.
	 *
	 * @return the host mail
	 */
	public static String getHostMail() {
		return hostMail;
	}

	/**
	 * Gets the remitente mail.
	 *
	 * @return the remitente mail
	 */
	public static String getRemitenteMail() {
		return remitenteMail;
	}

	/**
	 * Gets the private key path.
	 *
	 * @return the private key path
	 */
	public static String getPrivateKeyPath() {
		return privateKeyPath;
	}

	/**
	 * Gets the token jwt expiracion.
	 *
	 * @return the token jwt expiracion
	 */
	public static long getTokenJwtExpiracion() {
		return tokenJwtExpiracion;
	}

	/**
	 * Gets the algoritmo AES.
	 *
	 * @return the algoritmo AES
	 */
	public static String getAlgoritmoAES() {
		return algoritmoAES;
	}

	/**
	 * Gets the algoritmo RSA.
	 *
	 * @return the algoritmo RSA
	 */
	public static String getAlgoritmoRSA() {
		return algoritmoRSA;
	}

	/**
	 * Gets the ip mail certificacion.
	 *
	 * @return the ipMailCertificacion
	 */
	public static String getIpMailCertificacion() {
		return ipMailCertificacion;
	}

	/**
	 * Gets the host mail certificacion.
	 *
	 * @return the hostMailCertificacion
	 */
	public static String getHostMailCertificacion() {
		return hostMailCertificacion;
	}

	/**
	 * Gets the remitente mail certificacion.
	 *
	 * @return the remitenteMailCertificacion
	 */
	public static String getRemitenteMailCertificacion() {
		return remitenteMailCertificacion;
	}

	/**
	 * Gets the descripcion identificacion.
	 *
	 * @param id the id
	 * @return the descripcion identificacion
	 */
	public static String getDescripcionIdentificacion(String id) {

		String descripcionId = mapIdentificaciones.get(id);

		if (descripcionId != null) {
			return descripcionId;
		}
		else {
			return id;
		}

	}

	/**
	 * Checks if is load prop.
	 *
	 * @return true, if is load prop
	 */
	public static boolean isLoadProp() {
		return loadProp;
	}

	/**
	 * Gets the destinatarios notificaciones.
	 *
	 * @return the destinatariosNotificaciones
	 */
	public static String getDestinatariosNotificaciones() {
		return destinatariosNotificaciones;
	}

	/**
	 * Gets the correo gerente.
	 *
	 * @return the correoGerente
	 */
	public static String getCorreoGerente() {
		return correoGerente;
	}

	/**
	 * Gets the grant type api.
	 *
	 * @return the grant type api
	 */
	public static String getGrantTypeApi() {
		return grantTypeApi;
	}

	/**
	 * Gets the url token api.
	 *
	 * @return the url token api
	 */
	public static String getUrlTokenApi() {
		return urlTokenApi;
	}

	/**
	 * Gets the consumer key api.
	 *
	 * @return the consumer key api
	 */
	public static String getConsumerKeyApi() {
		return consumerKeyApi;
	}

	/**
	 * Gets the consumer secret api.
	 *
	 * @return the consumer secret api
	 */
	public static String getConsumerSecretApi() {
		return consumerSecretApi;
	}


	/**
	 * Gets the url token api AWSINT.
	 *
	 * @return the url token api AWSINT
	 */
	public static String getUrlTokenApiAWSINT() {
		return urlTokenApiAWSINT;
	}

	/**
	 * Gets the url registra tipos cambio MM.
	 *
	 * @return the url registra tipos cambio MM
	 */
	public static String getUrlRegistraTiposCambioMM() {
		return urlRegistraTiposCambioMM;
	}

	/**
	 * Gets the consumer key api AWS.
	 *
	 * @return the consumer key api AWS
	 */
	public static String getConsumerKeyApiAWS() {
		return consumerKeyApiAWS;
	}

	/**
	 * Gets the consumer secret api AWS.
	 *
	 * @return the consumer secret api AWS
	 */
	public static String getConsumerSecretApiAWS() {
		return consumerSecretApiAWS;
	}

	/**
	 * Gets the sucursal MM.
	 *
	 * @return the sucursal MM
	 */
	public static String getSucursalMM() {
		return sucursalMM;
	}

	/**
	 * Gets the url api AWSINT.
	 *
	 * @return the url api AWSINT
	 */
	public static String getUrlApiAWSINT() {
		return urlApiAWSINT;
	}

	/**
	 * Gets the url apigee on prem.
	 *
	 * @return the url apigee on prem
	 */
	public static String getUrlApigeeOnPrem() {
		return urlApigeeOnPrem;
	}

	/**
	 * Gets the url api seguridad divisas.
	 *
	 * @return the url api seguridad divisas
	 */
	public static String getUrlApiSeguridadDivisas() {
		return urlApiSeguridadDivisas;
	}

	/**
	 * Gets the url registra tipos cambio divisas.
	 *
	 * @return the url registra tipos cambio divisas
	 */
	public static String getUrlRegistraTiposCambioDivisas() {
		return urlRegistraTiposCambioDivisas;
	}

	/**
	 * Gets the consumer key api divisas.
	 *
	 * @return the consumer key api divisas
	 */
	public static String getConsumerKeyApiDivisas() {
		return consumerKeyApiDivisas;
	}

	/**
	 * Gets the consumer secret api divisas.
	 *
	 * @return the consumer secret api divisas
	 */
	public static String getConsumerSecretApiDivisas() {
		return consumerSecretApiDivisas;
	}

	/**
	 * Gets the url consulta parametros.
	 *
	 * @return the url consulta parametros
	 */
	public static String getUrlConsultaParametros() {
		return urlConsultaParametros;
	}

	/**
	 * Gets the url gestion parametros.
	 *
	 * @return the url gestion parametros
	 */
	public static String getUrlGestionParametros() {
		return urlGestionParametros;
	}

	/**
	 * Gets the url gestion parametros masicos.
	 *
	 * @return the url gestion parametros masicos
	 */
	public static String getUrlGestionParametrosMasivos() {
		return urlGestionParametrosMasivos;
	}

	/**
	 * Gets the url consulta asignaciones parametros.
	 *
	 * @return the url consulta asignaciones parametros
	 */
	public static String getUrlConsultaAsignacionesParametros() {
		return urlConsultaAsignacionesParametros;
	}

	/**
	 * Gets the url gestion asignaciones.
	 *
	 * @return the url gestion asignaciones
	 */
	public static String getUrlGestionAsignaciones() {
		return urlGestionAsignaciones;
	}

	/**
	 * Gets the url gestion asignaciones masivas.
	 *
	 * @return the url gestion asignaciones masivas
	 */
	public static String getUrlGestionAsignacionesMasivas() {
		return urlGestionAsignacionesMasivas;
	}

	/**
	 * Gets the consumer key parametros.
	 *
	 * @return the consumer key parametros
	 */
	public static String getConsumerKeyParametros() {
		return consumerKeyParametros;
	}

	/**
	 * Gets the consumer secret parametros.
	 *
	 * @return the consumer secret parametros
	 */
	public static String getConsumerSecretParametros() {
		return consumerSecretParametros;
	}

	/**
	 * Gets the url tipo cambio tesoreria.
	 *
	 * @return the url tipo cambio tesoreria
	 */
	public static String getUrlTipoCambioTesoreria() {
		return urlTipoCambioTesoreria;
	}

	/**
	 * Gets the clave negocio.
	 *
	 * @return the clave negocio
	 */
	public static String getClaveNegocio() {
		return claveNegocio;
	}

	/**
	 * Gets the reporteria operacion alnova.
	 *
	 * @return the reporteria operacion alnova
	 */
	public static String getReporteriaOperacionAlnova() {
		return reporteriaOperacionAlnova;
	}

	/**
	 * Gets the reporteria regulatorios alnova.
	 *
	 * @return the reporteria regulatorios alnova
	 */
	public static String getReporteriaRegulatoriosAlnova() {
		return reporteriaRegulatoriosAlnova;
	}

	/**
	 * Gets the reporte spot operacion alnova.
	 *
	 * @return the reporte spot operacion alnova
	 */
	public static String getReporteSpotOperacionAlnova() {
		return reporteSpotOperacionAlnova;
	}

	/**
	 * Gets the reporte spot regulatorios alnova.
	 *
	 * @return the reporte spot regulatorios alnova
	 */
	public static String getReporteSpotRegulatoriosAlnova() {
		return reporteSpotRegulatoriosAlnova;
	}
	
	
}
