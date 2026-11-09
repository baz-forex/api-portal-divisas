package com.baz.divisas.api.apigee.model;

import com.baz.divisas.api.apigee.dto.ResultadoLlaves;

/**
 * The Class LlavesPagosDigitales.
 */
public class ApiLlaves {

	/** The codigo. */
	private String codigo;
	
	/** The mensaje. */
	private String mensaje;
	
	/** The folio. */
	private String folio;
	
	/** The resultado. */
	private ResultadoLlaves resultado;
	
	/**
	 * Instantiates a new llaves pagos digitales.
	 */
	public ApiLlaves() {
		super();
	}

	/**
	 * Gets the codigo.
	 *
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Sets the codigo.
	 *
	 * @param codigo the new codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Gets the mensaje.
	 *
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Sets the mensaje.
	 *
	 * @param mensaje the new mensaje
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/**
	 * Gets the folio.
	 *
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * Sets the folio.
	 *
	 * @param folio the new folio
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * Gets the resultado.
	 *
	 * @return the resultado
	 */
	public ResultadoLlaves getResultado() {
		return resultado;
	}

	/**
	 * Sets the resultado.
	 *
	 * @param resultado the new resultado
	 */
	public void setResultado(ResultadoLlaves resultado) {
		this.resultado = resultado;
	}

	@Override
	public String toString() {
		return "Llaves [codigo=" + codigo + ", mensaje=" + mensaje + ", folio=" + folio + ", resultado="
				+ resultado + "]";
	}
	
	
}
