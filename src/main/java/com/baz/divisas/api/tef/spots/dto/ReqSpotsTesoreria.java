package com.baz.divisas.api.tef.spots.dto;

import java.util.ArrayList;

/**
 * The Class ReqSpotsTesoreria.
 */
public class ReqSpotsTesoreria {

	/** The clave negocio. */
	private String claveNegocio;
	
	/** The divisas. */
	private ArrayList<String> divisas;
	
	/**
	 * Gets the clave negocio.
	 *
	 * @return the clave negocio
	 */
	public String getClaveNegocio() {
		return claveNegocio;
	}
	
	/**
	 * Sets the clave negocio.
	 *
	 * @param claveNegocio the new clave negocio
	 */
	public void setClaveNegocio(String claveNegocio) {
		this.claveNegocio = claveNegocio;
	}
	
	/**
	 * Gets the divisas.
	 *
	 * @return the divisas
	 */
	public ArrayList<String> getDivisas() {
		return divisas;
	}
	
	/**
	 * Sets the divisas.
	 *
	 * @param divisas the new divisas
	 */
	public void setDivisas(ArrayList<String> divisas) {
		this.divisas = divisas;
	}

	@Override
	public String toString() {
		return "ReqSpotsTesoreria [claveNegocio=" + claveNegocio + ", divisas=" + divisas + "]";
	}
	
	
}
