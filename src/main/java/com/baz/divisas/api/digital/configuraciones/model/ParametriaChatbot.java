package com.baz.divisas.api.digital.configuraciones.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ParametriaChatbot.
 */
public class ParametriaChatbot {

	/** The id moneda. */
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="idMoneda", position=3, required=true)
	@Valid
	private String idMoneda;

	/** The vigencia en dias. */
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="vigenciaEnDias", position=3, required=true)
	@Valid
	private String vigenciaEnDias;
	
	/** The montos. */
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Montos de operación", position=6, required=true)
	@Valid	
	private RangoMontos montos;
	
	/** The operaciones permitidas. */
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Operaciones permitidas", position=7, required=true)
	@Valid
	private OperacionesPermitidas operacionesPermitidas;
	
	/** The operaciones canceladas. */
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Operaciones canceladas", position=8, required=true)
	@Valid
	private OperacionesCanceladas operacionesCanceladas;
	
	/** The fluctuacion. */
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Fluctuación", position=9, required=true)
	@Valid
	private Monto fluctuacion;
	

	/**
	 * Gets the montos.
	 *
	 * @return the montos
	 */
	public RangoMontos getMontos() {
		return montos;
	}
	
	/**
	 * Sets the montos.
	 *
	 * @param montos the new montos
	 */
	public void setMontos(RangoMontos montos) {
		this.montos = montos;
	}
	
	/**
	 * Gets the operaciones permitidas.
	 *
	 * @return the operaciones permitidas
	 */
	public OperacionesPermitidas getOperacionesPermitidas() {
		return operacionesPermitidas;
	}
	
	/**
	 * Sets the operaciones permitidas.
	 *
	 * @param operacionesPermitidas the new operaciones permitidas
	 */
	public void setOperacionesPermitidas(OperacionesPermitidas operacionesPermitidas) {
		this.operacionesPermitidas = operacionesPermitidas;
	}
	
	/**
	 * Gets the operaciones canceladas.
	 *
	 * @return the operaciones canceladas
	 */
	public OperacionesCanceladas getOperacionesCanceladas() {
		return operacionesCanceladas;
	}
	
	/**
	 * Sets the operaciones canceladas.
	 *
	 * @param operacionesCanceladas the new operaciones canceladas
	 */
	public void setOperacionesCanceladas(OperacionesCanceladas operacionesCanceladas) {
		this.operacionesCanceladas = operacionesCanceladas;
	}
	
	/**
	 * Gets the fluctuacion.
	 *
	 * @return the fluctuacion
	 */
	public Monto getFluctuacion() {
		return fluctuacion;
	}
	
	/**
	 * Sets the fluctuacion.
	 *
	 * @param fluctuacion the new fluctuacion
	 */
	public void setFluctuacion(Monto fluctuacion) {
		this.fluctuacion = fluctuacion;
	}
	
	/**
	 * Gets the id moneda.
	 *
	 * @return the id moneda
	 */
	public String getIdMoneda() {
		return idMoneda;
	}
	
	/**
	 * Sets the id moneda.
	 *
	 * @param idMoneda the new id moneda
	 */
	public void setIdMoneda(String idMoneda) {
		this.idMoneda = idMoneda;
	}
	
	/**
	 * Gets the vigencia en dias.
	 *
	 * @return the vigencia en dias
	 */
	public String getVigenciaEnDias() {
		return vigenciaEnDias;
	}
	
	/**
	 * Sets the vigencia en dias.
	 *
	 * @param vigenciaEnDias the new vigencia en dias
	 */
	public void setVigenciaEnDias(String vigenciaEnDias) {
		this.vigenciaEnDias = vigenciaEnDias;
	}	
	
}
