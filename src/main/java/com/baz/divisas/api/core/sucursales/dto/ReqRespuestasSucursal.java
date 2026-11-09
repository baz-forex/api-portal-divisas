package com.baz.divisas.api.core.sucursales.dto;

/**
 * Clase ReqRespuestasSucursal
 * 
 * @author 1096526
 *
 */
public class ReqRespuestasSucursal extends ReqPaginado {

	private String sucursal;

	/**
	 * Constructor de la clase
	 */
	public ReqRespuestasSucursal() {
		super();
	}

	/**
	 * @return the sucursal
	 */
	public String getSucursal() {
		return sucursal;
	}

	/**
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}

}
