package com.baz.divisas.api.tef.spots.dto;

import com.baz.divisas.api.apigee.dto.ResultadoApi;
import com.baz.divisas.api.tef.spots.model.Divisas;

/**
 * The Class ResSpotTesoreria.
 */
public class ResSpotTesoreria extends ResultadoApi {

	/** The resultado. */
	private Divisas resultado;

	/**
	 * Gets the resultado.
	 *
	 * @return the resultado
	 */
	public Divisas getResultado() {
		return resultado;
	}

	/**
	 * Sets the resultado.
	 *
	 * @param resultado the new resultado
	 */
	public void setResultado(Divisas resultado) {
		this.resultado = resultado;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ResSpotTesoreria [resultado=" + resultado + "]";
	}
	
	
}
