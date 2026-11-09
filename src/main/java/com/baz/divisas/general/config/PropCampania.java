package com.baz.divisas.general.config;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;

import com.baz.divisas.general.util.LogApp;

/**
 * Clase de propiedades de la aplicacion.
 *
 * @author B86960
 */
public final class PropCampania {
    
    /** The path config. */
    private static String PATH_CONFIG;
	
	/** The Constant FILE_CONFIG. */
	private static final String FILE_CONFIG = "APIPortalDivisas.properties";	
	
	/** The campanias coleccion. */
	private static String campaniasColeccion;
	
	/** The destinatarios campanias notificacion. */
	private static String destinatariosCampaniasNotificacion;
	
	/** The ganadores promocion digital. */
	private static String ganadoresPromocionDigital;
	
	/** The ganadores gol de oro. */
	private static String ganadoresGolDeOro;
	
	/** The inventario mundial verde. */
	private static String inventarioMundialVerde;
	
	/** The empleados bloqueados gol oro. */
	private static String empleadosBloqueadosGolOro;

	/** The inventario verano mundialista. */
	private static String inventarioVeranoMundialista;
	
	/** The operaciones verano mundialista. */
	private static String operacionesVeranoMundialista;
	
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
			
			campaniasColeccion = properties.getProperty("CAMPANIASCOLECCION").trim();
			destinatariosCampaniasNotificacion = properties.getProperty("DESTINATARIOSCAMPANIAS").trim();
			ganadoresPromocionDigital = properties.getProperty("GANADORESPROMOCIONDIGITAL").trim();
			ganadoresGolDeOro = properties.getProperty("OPERACIONESGOLDEOROCOLECCION").trim();
			inventarioMundialVerde = properties.getProperty("INVENTARIOMUNDIALVERDE").trim();
			empleadosBloqueadosGolOro = properties.getProperty("EMPLEADOSBLOQUEADOSGOLDEORO").trim();
			inventarioVeranoMundialista = properties.getProperty("INVENTARIOVERANOMUNDIALISTA").trim();
			operacionesVeranoMundialista = properties.getProperty("OPERACIONESVERANOMUNDIALISTA").trim();
		}
		catch (Exception e) {
			LogApp.excepcion(e, "Error al cargar PropClientes");
		}
		
	}
	
	/**
	 * Constructor de la clase.
	 */
	private PropCampania() {
		super();
	}

	/**
	 * Gets the campanias coleccion.
	 *
	 * @return the campanias coleccion
	 */
	public static String getCampaniasColeccion() {
		return campaniasColeccion;
	}
	
	/**
	 * Gets the destinatarios campanias notificacion.
	 *
	 * @return the destinatarios campanias notificacion
	 */
	public static String getDestinatariosCampaniasNotificacion() {
		return destinatariosCampaniasNotificacion;
	}


	/**
	 * Gets the ganadores promocion digital.
	 *
	 * @return the ganadores promocion digital
	 */
	public static String getGanadoresPromocionDigital() {
		return ganadoresPromocionDigital;
	}

	/**
	 * Gets the ganadores gol de oro.
	 *
	 * @return the ganadores gol de oro
	 */
	public static String getGanadoresGolDeOro() {
		return ganadoresGolDeOro;
	}

	/**
	 * Gets the inventario mundial verde.
	 *
	 * @return the inventario mundial verde
	 */
	public static String getInventarioMundialVerde() {
		return inventarioMundialVerde;
	}

	/**
	 * Gets the empleados bloqueados gol oro.
	 *
	 * @return the empleados bloqueados gol oro
	 */
	public static String getEmpleadosBloqueadosGolOro() {
		return empleadosBloqueadosGolOro;
	}

	/**
	 * Gets the inventario verano mundialista.
	 *
	 * @return the inventario verano mundialista
	 */
	public static String getInventarioVeranoMundialista() {
		return inventarioVeranoMundialista;
	}

	/**
	 * Gets the operaciones verano mundialista.
	 *
	 * @return the operaciones verano mundialista
	 */
	public static String getOperacionesVeranoMundialista() {
		return operacionesVeranoMundialista;
	}
	
}
