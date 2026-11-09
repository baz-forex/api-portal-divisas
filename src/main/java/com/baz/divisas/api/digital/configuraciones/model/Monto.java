package com.baz.divisas.api.digital.configuraciones.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class Fluctuacion.
 */
public class Monto {

	/** The valor. */
	@NotNull(message = "Requerido")
	@Pattern(regexp="[0-9]{1,3}", message="Debe ser numérico de longitud máxima 3")
	@ApiModelProperty(value="Valor", example="50", position=1, required=true)
	private String valor;
	
	/** The unidad moneda. */
	@SerializedName(value = "unidad_medida" , alternate = "unidadMoneda")
	@NotNull(message = "Requerido")
	@Pattern(regexp="^(Centavos|Pesos)$", message="Unidad invalida")
	@ApiModelProperty(value="Unidad de moneda", example="Centavos", position=2, required=true)
	private String unidadMoneda;
	
	/** The valor fluctuacion. */
	@SerializedName(value = "valor_fluctuacion" , alternate = "valorPesos")
	@NotNull(message = "Requerido")
	@Pattern(regexp="^([0-9]{1,3}(\\.[0-9]{1,2})?)$", message="Valor invalido")
	@ApiModelProperty(value="Valor en pesos", example="\"0.50\"", position=3, required=true)
	private String valorPesos;

	/**
	 * Instantiates a new fluctuacion.
	 */
	public Monto() {
		super();
	}

	/**
	 * Gets the valor.
	 *
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Sets the valor.
	 *
	 * @param valor the new valor
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * Gets the unidad moneda.
	 *
	 * @return the unidad moneda
	 */
	public String getUnidadMoneda() {
		return unidadMoneda;
	}

	/**
	 * Sets the unidad moneda.
	 *
	 * @param unidadMoneda the new unidad moneda
	 */
	public void setUnidadMoneda(String unidadMoneda) {
		this.unidadMoneda = unidadMoneda;
	}

	/**
	 * @return the valorPesos
	 */
	public String getValorPesos() {
		return valorPesos;
	}

	/**
	 * @param valorPesos the valorPesos to set
	 */
	public void setValorPesos(String valorPesos) {
		this.valorPesos = valorPesos;
	}

}