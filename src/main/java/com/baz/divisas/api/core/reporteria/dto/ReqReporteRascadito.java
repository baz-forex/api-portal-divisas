package com.baz.divisas.api.core.reporteria.dto;

import javax.validation.constraints.NotEmpty;

import com.baz.divisas.general.annotation.FormatoFechaCorta;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ReqReporteRascadito.
 */
public class ReqReporteRascadito {

	/** fecha inicial. */
	@NotEmpty(message = "Requerido")
	@FormatoFechaCorta
	@ApiModelProperty(value="Fecha inicial", example="2023-05-11", position=1)
	private String fechaInicial;
	
	/** fecha final. */
	@NotEmpty(message = "Requerido")
	@ApiModelProperty(value="Fecha final", example="2023-05-11", position=2)
	private String fechaFinal;
	
	
	/**
	 * Constructor de la clase.
	 */
	public ReqReporteRascadito() {
		super();
	}

	/**
	 * Gets the fecha inicial.
	 *
	 * @return the fecha inicial
	 */
	public String getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * Sets the fecha inicial.
	 *
	 * @param fechaInicial the new fecha inicial
	 */
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * Gets the fecha final.
	 *
	 * @return the fecha final
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * Sets the fecha final.
	 *
	 * @param fechaFinal the new fecha final
	 */
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	
}
