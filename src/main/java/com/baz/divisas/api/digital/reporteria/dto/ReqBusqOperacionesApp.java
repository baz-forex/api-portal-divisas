package com.baz.divisas.api.digital.reporteria.dto;

import com.baz.divisas.api.core.reporteria.dto.ReqReporteOperaciones;

import io.swagger.annotations.ApiModelProperty;

/**
 * Clase ReqRepOperacionesApp.
 */
public class ReqBusqOperacionesApp extends ReqReporteOperaciones {

	/** entrega domicilio. */
	@ApiModelProperty(value="Entrega a domicilio", example="false", position=8, required=true)
	private boolean entregaDomicilio;
	
	/**
	 * Constructor de la clase.
	 */
	public ReqBusqOperacionesApp() {
		super();
	}
	
	/**
	 * Checks if is entrega domicilio.
	 *
	 * @return true, if is entrega domicilio
	 */
	public boolean isEntregaDomicilio() {
		return entregaDomicilio;
	}
	
	/**
	 * Sets the entrega domicilio.
	 *
	 * @param entregaDomicilio the new entrega domicilio
	 */
	public void setEntregaDomicilio(boolean entregaDomicilio) {
		this.entregaDomicilio = entregaDomicilio;
	}

}
