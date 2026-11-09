package com.baz.divisas.api.core.beneficios.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;


/**
 * ReqEstatusSeguro.
 */
public class ReqEstatusSeguro {

	@NotNull(message = "Requerido")
	@Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24")
	@ApiModelProperty(value="Id Seguro", example="64d4268d1b41f232430b83e9", position=1, required=true)
	private String id;
	
	@NotNull(message = "Requerido")
	@Pattern(regexp="^(Vigente|Inactivo|Eliminado)$", message="Estatus invalido")
	@ApiModelProperty(value="Estatus", example="Vigente", position=2, required=true)
	private String estatus;
		
	/**
	 * Constructor de la clase
	 */
	public ReqEstatusSeguro() {
		super();
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}
	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	
}
