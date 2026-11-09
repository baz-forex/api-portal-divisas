package com.baz.divisas.general.config;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;

import com.baz.divisas.general.util.LogApp;

public final class PropBDCatalogos{

	private static String PATH_CONFIG;

	private static final String FILE_CONFIG = "APIPortalDivisas.properties";	

	private static String catalogosBD;
	private static String diccionarioColeccion;
	private static String catNacionalidadesColeccion;
	private static String catEstadosColeccion;
	private static String catSegmentacionSucursalColeccion;
	private static String catIdentificaciones;
	private static String catEstatusCotizacion;
	private static String catTipoCliente;
	private static String catPerfilClienteCampanias;
	private static String catTipoLimiteCampanias;
	private static String catTipoAcumuladoCampanias;
	private static String catEstatusCampanias;
	private static String catRangoEdadesCampanias;
	private static String catBloqueosOperativos;

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

			// Configuracion BD Catalogos
			catalogosBD = properties.getProperty("CATALOGOSBD").trim();
			diccionarioColeccion = properties.getProperty("DICCIONARIOCOLECCION").trim();
			catNacionalidadesColeccion = properties.getProperty("CATNACIONALIDADCOLECCION").trim();
			catEstadosColeccion = properties.getProperty("CATESTADOSCOLECCION").trim();
			catSegmentacionSucursalColeccion = properties.getProperty("CATSEGMENTACIONSUCURSALCOLECCION").trim();
			catIdentificaciones = properties.getProperty("CATIDENTIFICACIONES");
			catEstatusCotizacion = properties.getProperty("CATESTATUSCOTIZACION");

			catTipoCliente = properties.getProperty("CATTIPOCLIENTE");
			catPerfilClienteCampanias = properties.getProperty("CATPERFILCLIENTECAMPANIAS");
			catTipoLimiteCampanias = properties.getProperty("CATTIPOLIMITECAMPANIAS");
			catTipoAcumuladoCampanias = properties.getProperty("CATTIPOACUMULADOCAMPANIAS");
			catEstatusCampanias = properties.getProperty("CATESTATUSCAMPANIAS");
			catRangoEdadesCampanias = properties.getProperty("CATRANGOEDADESCAMPANIAS");

			catBloqueosOperativos = properties.getProperty("CATBLOQUEOSOPERATIVOS");
		}
		catch (Exception e) {
			LogApp.excepcion(e, "Error al cargar propiedades de BD Catalogos");
		}
	}

	private PropBDCatalogos() {
		super();
	}
	/**
	 * @return the catalogosBD
	 */
	public static String getCatalogosBD() {
		return catalogosBD;
	}
	/**
	 * @return the diccionarioColeccion
	 */
	public static String getDiccionarioColeccion() {
		return diccionarioColeccion;
	}
	/**
	 * @return the catNacionalidadesColeccion
	 */
	public static String getCatNacionalidadesColeccion() {
		return catNacionalidadesColeccion;
	}
	/**
	 * @return the catEstadosColeccion
	 */
	public static String getCatEstadosColeccion() {
		return catEstadosColeccion;
	}
	/**
	 * @return the catSegmentacionSucursalColeccion
	 */
	public static String getCatSegmentacionSucursalColeccion() {
		return catSegmentacionSucursalColeccion;
	}

	/**
	 * @return the catIdentificaciones
	 */
	public static String getCatIdentificaciones() {
		return catIdentificaciones;
	}
	/**
	 * @return the catTipoCliente
	 */
	public static String getCatTipoCliente() {
		return catTipoCliente;
	}
	/**
	 * @return the catPerfilClienteCampanias
	 */
	public static String getCatPerfilClienteCampanias() {
		return catPerfilClienteCampanias;
	}
	/**
	 * @return the catTipoLimiteCampanias
	 */
	public static String getCatTipoLimiteCampanias() {
		return catTipoLimiteCampanias;
	}
	/**
	 * @return the catTipoAcumuladoCampanias
	 */
	public static String getCatTipoAcumuladoCampanias() {
		return catTipoAcumuladoCampanias;
	}
	/**
	 * @return the catEstatusCampanias
	 */
	public static String getCatEstatusCampanias() {
		return catEstatusCampanias;
	}
	/**
	 * @return the catRangoEdadesCampanias
	 */
	public static String getCatRangoEdadesCampanias() {
		return catRangoEdadesCampanias;
	}
	/**
	 * Gets the cat estatus cotizacion.
	 *
	 * @return the cat estatus cotizacion
	 */
	public static String getCatEstatusCotizacion() {
		return catEstatusCotizacion;
	}
	/**
	 * @return the catBloqueosOperativos
	 */
	public static String getCatBloqueosOperativos() {
		return catBloqueosOperativos;
	}

}
