package com.baz.divisas.general.config;

import java.io.File;
import java.util.Properties;

import com.baz.divisas.general.util.LogApp;

import org.apache.commons.io.FilenameUtils;

public final class PropBD {

	private static String PATH_CONFIG;

	private static final String FILE_CONFIG = "APIPortalDivisas.properties";

	private static String uriMongoDB;

	private static String authBD;
	private static String llavesColeccion;
	private static String llavesClienteColeccion;
	private static String usuariosColeccion;
	private static String modulosColeccion;
	private static String authClientesApiDivColeccion;
	private static String scimUsuariosColeccion;
	private static String scimGruposColeccion;

	private static String middlewareBD;
	private static String negociosMW;
	private static String notificacionesMW;
	private static String bitacoraNotificacionesMW;
	private static String operacionesMW;
	private static String cotizacionesApp;
	private static String parametriaCotizacionesMW;
	private static String parametriaRascadito;
	private static String seguros;
	
	private static String monitoreoBD;
	private static String bitacoraColeccion;

	private static String incidenciasBD;
	private static String incidenciasBCDColeccion;

	// BD Catalogos Divisas
	private static String catValoresRentabilidad;
	private static String catSegmentoRentabilidad;
	private static String catSegmentoLealtad;
	private static String catMonedasColeccion;
	private static String catTipoOperacionColeccion;
	private static String  catUbicacionSucursales;

	private static String terminalFinancieroBD;
	private static String limitesClienteColeccion;
	private static String regionesColeccion;
	private static String segmentosColeccion;
	private static String catSpot;
	private static String catSpreads;
	private static String sucursales;
	private static String parametriaGeneral;
	private static String cuestionarios;
	private static String competenciasPromoEspacio;
	private static String respuestasCuestionarios;
	private static String cuestionariosHistorico;
	private static String bitacoraCuestionarios;
	private static String topeCaja;
	private static String divisa;
	private static String cajero;
	private static String cajaAnclada;
	private static String cajaPrincipal;
	
	

	private static String regionesColeccionOlp;
	private static String segmentosColeccionOlp;

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

			// Configuracion de Propiedades para la conexion a la BD
			uriMongoDB = properties.getProperty("URIMONGODB").trim();

			// Configuracion BD Auth
			authBD = properties.getProperty("AUTHBD").trim();
			llavesColeccion = properties.getProperty("LLAVESCOLECCION").trim();
			llavesClienteColeccion = properties.getProperty("LLAVESCLIENTECOLECCION").trim();
			usuariosColeccion = properties.getProperty("USUARIOSCOLECCION").trim();
			modulosColeccion = properties.getProperty("MODULOSCOLECCION").trim();
			authClientesApiDivColeccion = properties.getProperty("AUTHCLIENTESAPIDIVCOLECCION")
					.trim();
			scimUsuariosColeccion = properties.getProperty("SCIMUSUARIOSCOLECCION").trim();
			scimGruposColeccion = properties.getProperty("SCIMGRUPOSCOLECCION").trim();

			// Configuracion BD Middeware
			middlewareBD = properties.getProperty("MIDDLEWAREBD").trim();
			negociosMW = properties.getProperty("NEGOCIOSMWCOLECCION").trim();
			notificacionesMW = properties.getProperty("NOTIFICACIONESMWCOLECCION").trim();
			bitacoraNotificacionesMW = properties.getProperty("BITACORANOTIFICACIONESMWCOLECCION")
					.trim();
			operacionesMW = properties.getProperty("OPERACIONESMWCOLECCION").trim();
			cotizacionesApp = properties.getProperty("COTIZACIONESCOLECCION").trim();
			parametriaCotizacionesMW = properties.getProperty("PARAMETRIAMWCOTIZACIONES").trim();
			parametriaRascadito = properties.getProperty("PARAMETRIARASCADITO").trim();
			seguros = properties.getProperty("SEGUROS").trim();
			cuestionarios = properties.getProperty("CUESTIONARIO").trim();
			respuestasCuestionarios = properties.getProperty("RESPUESTASCUESTIONARIOS").trim();
			cuestionariosHistorico = properties.getProperty("CUESTIONARIOSHISTORICO").trim();
			bitacoraCuestionarios = properties.getProperty("BITACORACUESTIONARIOS").trim();

			// Configuracion BD Monitoreo Clientes
			monitoreoBD = properties.getProperty("MONITOREOCLIENTESBD").trim();
			bitacoraColeccion = properties.getProperty("BITACORACOLECCION").trim();

			// Configuracion BD Incidencias
			incidenciasBD = properties.getProperty("INCIDENCIASBD").trim();
			incidenciasBCDColeccion = properties.getProperty("INCIDECNIASBCDCOLECCION").trim();

			// Configuracion BD Catalogos Divisas
			catMonedasColeccion = properties.getProperty("CATMONEDASCOLECCION");
			catTipoOperacionColeccion = properties.getProperty("CATTIPOOPERACION");
			catSegmentoLealtad = properties.getProperty("CATSEGMENTOLEALTAD").trim();
			catSegmentoRentabilidad = properties.getProperty("CATSEGMENTORENTABILIDAD").trim();
			catValoresRentabilidad = properties.getProperty("CATVALORESRENTABILIDAD").trim();
			catUbicacionSucursales = properties.getProperty("CATUBICACIONSUCURSALES").trim();
			
			// Configuracion BD Terminal Financiero
			terminalFinancieroBD = properties.getProperty("TERMINALFINANCIEROBD").trim();
			limitesClienteColeccion = properties.getProperty("LIMITESCLIENTECOLECCION").trim();
			regionesColeccion = properties.getProperty("REGIONESCOLECCION").trim();
			segmentosColeccion = properties.getProperty("SEGMENTOSCOLECCION").trim();
			catSpot = properties.getProperty("CATSPOT").trim();
			catSpreads = properties.getProperty("CATSPREADS").trim();
			sucursales = properties.getProperty("SUCURSALES").trim();
			competenciasPromoEspacio = properties.getProperty("COMPETENCIASPROMOESPACIO").trim();
			parametriaGeneral = properties.getProperty("PARAMETRIAGENERAL").trim();

			// Parametros para Topes de Caja
			topeCaja = properties.getProperty("TOPECAJA").trim();
			divisa = properties.getProperty("DIVISA").trim();
			cajero = properties.getProperty("CAJERO").trim();
			cajaAnclada = properties.getProperty("CAJAANCLADA").trim();
			cajaPrincipal = properties.getProperty("CAJAPRINCIPAL").trim();
			
			//Parametros para OLP
			regionesColeccionOlp = properties.getProperty("REGIONESOLPCOLECCION").trim();
			segmentosColeccionOlp = properties.getProperty("SEGMENTOSOLPCOLECCION").trim();
		}
		catch (Exception e) {
			LogApp.excepcion(e, "Error al cargar propiedades de BD");
		}
	}

	private PropBD() {
		super();
	}

	public static String getUriMongoDB() {
		return uriMongoDB;
	}
	public static String getAuthBD() {
		return authBD;
	}
	public static String getUsuariosColeccion() {
		return usuariosColeccion;
	}
	public static String getModulosColeccion() {
		return modulosColeccion;
	}
	public static String getAuthClientesApiDivColeccion() {
		return authClientesApiDivColeccion;
	}
	public static String getScimUsuariosColeccion() {
		return scimUsuariosColeccion;
	}
	public static String getScimGruposColeccion() {
		return scimGruposColeccion;
	}
	public static String getMiddlewareBD() {
		return middlewareBD;
	}
	public static String getNegociosMW() {
		return negociosMW;
	}
	public static String getNotificacionesMW() {
		return notificacionesMW;
	}
	public static String getBitacoraNotificacionesMW() {
		return bitacoraNotificacionesMW;
	}
	public static String getLlavesColeccion() {
		return llavesColeccion;
	}
	public static String getLlavesClienteColeccion() {
		return llavesClienteColeccion;
	}
	public static String getMonitoreoBD() {
		return monitoreoBD;
	}
	public static String getBitacoraColeccion() {
		return bitacoraColeccion;
	}
	public static String getCatValoresRentabilidad() {
		return catValoresRentabilidad;
	}
	public static String getCatSegmentoRentabilidad() {
		return catSegmentoRentabilidad;
	}
	public static String getCatSegmentoLealtad() {
		return catSegmentoLealtad;
	}
	public static String getIncidenciasBD() {
		return incidenciasBD;
	}
	public static String getIncidenciasBCDColeccion() {
		return incidenciasBCDColeccion;
	}
	public static String getOperacionesMW() {
		return operacionesMW;
	}
	public static String getCotizacionesApp() {
		return cotizacionesApp;
	}
	public static String getTerminalFinancieroBD() {
		return terminalFinancieroBD;
	}

	public static String getLimitesClienteColeccion() {
		return limitesClienteColeccion;
	}
	public static String getCatMonedasColeccion() {
		return catMonedasColeccion;
	}
	public static String getCatTipoOperacionColeccion() {
		return catTipoOperacionColeccion;
	}
	public static String getRegionesColeccion() {
		return regionesColeccion;
	}
	public static String getSegmentosColeccion() {
		return segmentosColeccion;
	}

	public static String getCatSpot() {
		return catSpot;
	}

	public static String getCatSpreads() {
		return catSpreads;
	}

	public static String getSucursales() {
		return sucursales;
	}

	public static String getParametriaCotizacionesMW() {
		return parametriaCotizacionesMW;
	}
	public static String getCompetenciasPromoEspacio() {
		return competenciasPromoEspacio;
	}
	/**
	 * @return the cuestionarios
	 */
	public static String getCuestionarios() {
		return cuestionarios;
	}

	/**
	 * @return the parametriaRacadito
	 */
	public static String getParametriaRascadito() {
		return parametriaRascadito;
	}

	/**
	 * @return the parametriaGeneral
	 */
	public static String getParametriaGeneral() {
		return parametriaGeneral;
	}

	/**
	 * @return the respuestasCuestionarios
	 */
	public static String getRespuestasCuestionarios() {
		return respuestasCuestionarios;
	}

	/**
	 * @return the cuestionariosHistorico
	 */
	public static String getCuestionariosHistorico() {
		return cuestionariosHistorico;
	}

	/**
	 * @return the cuestionariosHistorico
	 */
	public static String getBitacoraCuestionarios() {
		return bitacoraCuestionarios;
	}

	/**
	 * @return the topeCaja
	 */
	public static String getTopeCaja() {
		return topeCaja;
	}

	/**
	 * @return the divisa
	 */
	public static String getDivisa() {
		return divisa;
	}

	/**
	 * @return the cajero
	 */
	public static String getCajero() {
		return cajero;
	}

	/**
	 * @return the cajaAnclada
	 */
	public static String getCajaAnclada() {
		return cajaAnclada;
	}

	/**
	 * @return the cajaPrincipal
	 */
	public static String getCajaPrincipal() {
		return cajaPrincipal;
	}

	/**
	 * @return the seguros
	 */
	public static String getSeguros() {
		return seguros;
	}

	public static String getCatUbicacionSucursales() {
		return catUbicacionSucursales;
	}
	
	public static String getRegionesColeccionOlp() {
		return regionesColeccionOlp;
	}

	public static String getSegmentosColeccionOlp() {
		return segmentosColeccionOlp;
	}

}
