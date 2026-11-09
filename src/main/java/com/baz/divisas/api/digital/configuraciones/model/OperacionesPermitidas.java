package com.baz.divisas.api.digital.configuraciones.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;

/**
 * OperacionesPermitidas
 */
public class OperacionesPermitidas {

	/** The operaciones. */
	@NotNull(message = "Requerido")
	@Pattern(regexp="[0-9]{1,3}", message="Debe ser numérico de longitud máxima 3")
	@ApiModelProperty(value="Operaciones", example="1", position=1, required=true)
	private String operaciones;
	
	/** The plazo. */
	@NotNull(message = "Requerido")
	@Pattern(regexp="^(Dia|Semana|Mes|Año)$", message="Plazo invalido")
	@ApiModelProperty(value="Plazo", example="Mes", position=1, required=true)
	private String plazo;

	/**
	 * Instantiates a new operaciones permitidas.
	 */
	public OperacionesPermitidas() {
		super();
	}

	/**
	 * Gets the operaciones.
	 *
	 * @return the operaciones
	 */
	public String getOperaciones() {
		return operaciones;
	}

	/**
	 * Sets the operaciones.
	 *
	 * @param operaciones the new operaciones
	 */
	public void setOperaciones(String operaciones) {
		this.operaciones = operaciones;
	}

	/**
	 * Gets the plazo.
	 *
	 * @return the plazo
	 */
	public String getPlazo() {
		return plazo;
	}

	/**
	 * Sets the plazo.
	 *
	 * @param plazo the new plazo
	 */
	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}

}
