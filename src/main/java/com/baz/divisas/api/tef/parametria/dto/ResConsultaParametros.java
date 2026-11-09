package com.baz.divisas.api.tef.parametria.dto;

import com.baz.divisas.api.apigee.dto.ResultadoApi;

/**
 * The Class ResConsultaParametros.
 */
public class ResConsultaParametros extends ResultadoApi{

	/** The resultado. */
	private ConsultaParametros resultado;
	
	/**
	 * Instantiates a new res consulta parametros.
	 */
	public ResConsultaParametros() {
		super();
	}

	/**
	 * Gets the resultado.
	 *
	 * @return the resultado
	 */
	public ConsultaParametros getResultado() {
		return resultado;
	}

	/**
	 * Sets the resultado.
	 *
	 * @param resultado the new resultado
	 */
	public void setResultado(ConsultaParametros resultado) {
		this.resultado = resultado;
	}
	
	
}
