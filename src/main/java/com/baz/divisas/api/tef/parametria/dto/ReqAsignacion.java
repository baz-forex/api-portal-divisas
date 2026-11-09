package com.baz.divisas.api.tef.parametria.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ReqAsignacion.
 */
public class ReqAsignacion extends ReqParametro {

	/** The parametro. */
	private String parametro;
	
	/** The sucursales. */
	private List<String> sucursales;
	
	
	/**
	 * Gets the sucursales.
	 *
	 * @return the sucursales
	 */
	public List<String> getSucursales() {
		return new ArrayList<>(sucursales);
	}

	/**
	 * Sets the sucursales.
	 *
	 * @param sucursales the new sucursales
	 */
	public void setSucursales(List<String> sucursales) {
		this.sucursales = new ArrayList<>(sucursales);
	}

	/**
	 * Gets the parametro.
	 *
	 * @return the parametro
	 */
	public String getParametro() {
		return parametro;
	}

	/**
	 * Sets the parametro.
	 *
	 * @param parametro the new parametro
	 */
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	
}
