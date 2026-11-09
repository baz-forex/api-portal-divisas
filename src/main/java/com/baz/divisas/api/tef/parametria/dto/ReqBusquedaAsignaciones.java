package com.baz.divisas.api.tef.parametria.dto;

/**
 * The Class ReqBusquedaAsignaciones.
 */
public class ReqBusquedaAsignaciones extends ReqBusquedaParametros {

	/** The asignacion. */
	private String asignacion;
	
	/** The sucursal. */
	private String sucursal;
	
	/**
	 * Gets the asignacion.
	 *
	 * @return the asignacion
	 */
	public String getAsignacion() {
		return asignacion;
	}
	
	/**
	 * Sets the asignacion.
	 *
	 * @param asignacion the new asignacion
	 */
	public void setAsignacion(String asignacion) {
		this.asignacion = asignacion;
	}
	
	/**
	 * Gets the sucursal.
	 *
	 * @return the sucursal
	 */
	public String getSucursal() {
		return sucursal;
	}
	
	/**
	 * Sets the sucursal.
	 *
	 * @param sucursal the new sucursal
	 */
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	
	
}
