package com.baz.divisas.api.digital.reporteria.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;

/**
 * ReqActualizaEstatus
 */
public class ReqActualizaEstatus {

	@NotEmpty(message = "Requerido")
	@Pattern(regexp="^(412|413)$", message="Tipo de operacion invalido") 
	@ApiModelProperty(value="Tipo de operación", example="413", position=1, required=true)
	private String tipoOperacion;
	
	@NotEmpty(message = "Requerido")
	@Pattern(regexp="^[0-9a-f]{24}", message="Debe ser un valor hexadecimal de longitud 24")
	@ApiModelProperty(value="Id de cotización", example="631124b25876135747a5f8b2", position=2, required=true)
	private String idCotizacion;
	
	@NotEmpty(message = "Requerido")
	@Pattern(regexp="^(EXITOSO|CANCELADA|VENCIDA)$", message="Id de estatus invalido") 
	@ApiModelProperty(value="Id de estatus", example="EXITOSO", position=3, required=true)
	private String idEstatus;
	
	
	/**
	 * Constructor de la clase
	 */
	public ReqActualizaEstatus() {
		super();
	}

	/**
	 * @return the tipoOperacion
	 */
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	/**
	 * @param tipoOperacion the tipoOperacion to set
	 */
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	/**
	 * @return the idCotizacion
	 */
	public String getIdCotizacion() {
		return idCotizacion;
	}
	/**
	 * @param idCotizacion the idCotizacion to set
	 */
	public void setIdCotizacion(String idCotizacion) {
		this.idCotizacion = idCotizacion;
	}
	/**
	 * @return the idEstatus
	 */
	public String getIdEstatus() {
		return idEstatus;
	}
	/**
	 * @param idEstatus the idEstatus to set
	 */
	public void setIdEstatus(String idEstatus) {
		this.idEstatus = idEstatus;
	}
	
}
