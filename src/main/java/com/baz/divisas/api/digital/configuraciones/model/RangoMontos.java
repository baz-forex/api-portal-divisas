package com.baz.divisas.api.digital.configuraciones.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

/**
 * Montos
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RangoMontos {

	/** The monto minimo. */
	@SerializedName(value = "monto_minimo", alternate = "montoMinimo")
	@NotNull(message = "Requerido")
	@Pattern(regexp="[0-9]{1,5}", message="Debe ser numérico de longitud máxima 5")
	@ApiModelProperty(value="Monto mínimo", example="20", position=1, required=true)
	private String montoMinimo;
	
	/** The monto maximo. */
	@SerializedName(value ="monto_maximo" , alternate = "montoMaximo")
	@NotNull(message = "Requerido")
	@Pattern(regexp="[0-9]{1,5}", message="Debe ser numérico de longitud máxima 5")
	@ApiModelProperty(value="Monto máximo", example="4000", position=2, required=true)
	private String montoMaximo;
	
	/** The divisa. */
	@NotNull(message = "Requerido")
	@Pattern(regexp="^(USD|CAD|EUR|GBP)$", message="Divisa invalida")
	@ApiModelProperty(value="Divisa", example="USD", position=3, required=true)
	private String divisa;
	
	/**
	 * Instantiates a new montos.
	 */
	public RangoMontos() {
		super();
	}

	/**
	 * Gets the monto minimo.
	 *
	 * @return the monto minimo
	 */
	public String getMontoMinimo() {
		return montoMinimo;
	}

	/**
	 * Sets the monto minimo.
	 *
	 * @param montoMinimo the new monto minimo
	 */
	public void setMontoMinimo(String montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	/**
	 * Gets the monto maximo.
	 *
	 * @return the monto maximo
	 */
	public String getMontoMaximo() {
		return montoMaximo;
	}

	/**
	 * Sets the monto maximo.
	 *
	 * @param montoMaximo the new monto maximo
	 */
	public void setMontoMaximo(String montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	/**
	 * Gets the divisa.
	 *
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}

	/**
	 * Sets the divisa.
	 *
	 * @param divisa the new divisa
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}

}
