package com.baz.divisas.api.digital.configuraciones.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

/**
 * Clase PaisesRestringidos
 * 
 * @author 1027346 Martinez Yonathan
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParametriaSeguimientoTC {

	@ApiModelProperty(value="Seguimiento activo", example="true", position=1, required=true)
	private boolean activo;
	
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Monto mínimo", position=2, required=true)
	@Valid
	private Monto minimo;
	
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Monto máximo", position=3, required=true)
	@Valid
	private Monto maximo;
	
	@NotNull(message = "Requerido")
	@ApiModelProperty(value="Monto intervalo", position=4, required=true)
	@Valid
	private Monto intervalo;
	
	@SerializedName("rango_dias")
	@NotEmpty(message="Requerido")
	@ApiModelProperty(value="Rango de dias", example="[1,7,15]", position=5, required=true)
	private List<@Min(value=1, message="Mínimo 1") Integer> rangoDias;

	/**
	 * Constructor de la clase
	 */
	public ParametriaSeguimientoTC() {
		super();
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the minimo
	 */
	public Monto getMinimo() {
		return minimo;
	}

	/**
	 * @param minimo the minimo to set
	 */
	public void setMinimo(Monto minimo) {
		this.minimo = minimo;
	}

	/**
	 * @return the maximo
	 */
	public Monto getMaximo() {
		return maximo;
	}

	/**
	 * @param maximo the maximo to set
	 */
	public void setMaximo(Monto maximo) {
		this.maximo = maximo;
	}

	/**
	 * @return the intervalo
	 */
	public Monto getIntervalo() {
		return intervalo;
	}

	/**
	 * @param intervalo the intervalo to set
	 */
	public void setIntervalo(Monto intervalo) {
		this.intervalo = intervalo;
	}

	/**
	 * @return the rangoDias
	 */
	public List<Integer> getRangoDias() {
		return rangoDias;
	}

	/**
	 * @param rangoDias the rangoDias to set
	 */
	public void setRangoDias(List<Integer> rangoDias) {
		this.rangoDias = rangoDias;
	}

}
