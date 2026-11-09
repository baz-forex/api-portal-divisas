package com.baz.divisas.general.config;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;

import com.baz.divisas.general.util.LogApp;

public final class PropBDSiex{

	private static String pathConfig;
	private static String fileConfig = "APIPortalDivisas.properties";	
	
	private static String siexBD;
	private static String catCamposRestringidos;

		
	static {

		if((System.getProperty("jboss.server.base.dir") != null)) {
			pathConfig = System.getProperty("jboss.server.base.dir") + File.separator + "config" + File.separator;
		}
		else {
			pathConfig = "." + File.separator + "properties" + File.separator + "properties_des" + File.separator;
		}
		try {

			Properties properties = ExternalConfigLoader.loadProperties(
					FilenameUtils.normalize(pathConfig + fileConfig));
			
			// Configuracion BD SIEX
			siexBD = properties.getProperty("SIEXBD").trim();
			catCamposRestringidos = properties.getProperty("CATCAMPOSRESTRINGIDOS").trim();

		}
		catch (Exception e) {
			LogApp.excepcion(e, "Error al cargar propiedades de BD SIEX");
		}
	}

	private PropBDSiex() {
		super();
	}

	/**
	 * @return the siexBD
	 */
	public static String getSiexBD() {
		return siexBD;
	}

	/**
	 * @return the catCamposRestringidos
	 */
	public static String getCatCamposRestringidos() {
		return catCamposRestringidos;
	}

	
	
}
