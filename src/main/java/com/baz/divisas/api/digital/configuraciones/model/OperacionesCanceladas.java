package com.baz.divisas.api.digital.configuraciones.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

/**
 * OperacionesCanceladas
 */
public class OperacionesCanceladas {

	/** The operaciones. */
	@NotNull(message = "Requerido")
	@Pattern(regexp="[0-9]{1,3}", message="Debe ser numérico de longitud máxima 3")
	@ApiModelProperty(value="Operaciones", example="3", position=1, required=true)
	private String operaciones;
	
	/** The plazo limite. */
	@SerializedName(value = "plazo_limite", alternate = "plazoLimite" )
	@NotNull(message = "Requerido")
	@Pattern(regexp="[0-9]{1,3}", message="Debe ser numérico de longitud máxima 3")
	@ApiModelProperty(value="Plazo límite", example="1", position=2, required=true)
	private String plazoLimite;
	
	/** The periodo limite. */
	@SerializedName(value = "periodo_limite", alternate =  "periodoLimite")
	@NotNull(message = "Requerido")
	@Pattern(regexp="^(Dia|Semana|Mes|Año)$", message="Periodo invalido")
	@ApiModelProperty(value="Periodo límite", example="Mes", position=3, required=true)
	private String periodoLimite;
	
	/** The plazo bloqueo. */
	@SerializedName(value = "plazo_bloqueo" , alternate =  "plazoBloqueo" )
	@NotNull(message = "Requerido")
	@Pattern(regexp="[0-9]{1,3}", message="Debe ser numérico de longitud máxima 3")
	@ApiModelProperty(value="Plazo bloqueo", example="1", position=4, required=true)
	private String plazoBloqueo;
	
	/** The periodo bloqueo. */
	@SerializedName(value =  "periodo_bloqueo" , alternate = "periodoBloqueo")
	@NotNull(message = "Requerido")
	@Pattern(regexp="^(Dia|Semana|Mes|Año)$", message="Periodo invalido")
	@ApiModelProperty(value="Periodo bloqueo", example="Mes", position=5, required=true)
	private String periodoBloqueo;

	/**
	 * Instantiates a new operaciones canceladas.
	 */
	public OperacionesCanceladas() {
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
	 * Gets the plazo limite.
	 *
	 * @return the plazo limite
	 */
	public String getPlazoLimite() {
		return plazoLimite;
	}

	/**
	 * Sets the plazo limite.
	 *
	 * @param plazoLimite the new plazo limite
	 */
	public void setPlazoLimite(String plazoLimite) {
		this.plazoLimite = plazoLimite;
	}

	/**
	 * Gets the periodo limite.
	 *
	 * @return the periodo limite
	 */
	public String getPeriodoLimite() {
		return periodoLimite;
	}

	/**
	 * Sets the periodo limite.
	 *
	 * @param periodoLimite the new periodo limite
	 */
	public void setPeriodoLimite(String periodoLimite) {
		this.periodoLimite = periodoLimite;
	}

	/**
	 * Gets the plazo bloqueo.
	 *
	 * @return the plazo bloqueo
	 */
	public String getPlazoBloqueo() {
		return plazoBloqueo;
	}

	/**
	 * Sets the plazo bloqueo.
	 *
	 * @param plazoBloqueo the new plazo bloqueo
	 */
	public void setPlazoBloqueo(String plazoBloqueo) {
		this.plazoBloqueo = plazoBloqueo;
	}

	/**
	 * Gets the periodo bloqueo.
	 *
	 * @return the periodo bloqueo
	 */
	public String getPeriodoBloqueo() {
		return periodoBloqueo;
	}

	/**
	 * Sets the periodo bloqueo.
	 *
	 * @param periodoBloqueo the new periodo bloqueo
	 */
	public void setPeriodoBloqueo(String periodoBloqueo) {
		this.periodoBloqueo = periodoBloqueo;
	}

}
