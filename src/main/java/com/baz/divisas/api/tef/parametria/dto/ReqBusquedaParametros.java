package com.baz.divisas.api.tef.parametria.dto;

/**
 * The Class ReqBusquedaParametros.
 */
public class ReqBusquedaParametros extends ReqDetalleParametro {

	/** The paginacion. */
	private PaginacionParametro paginacion;

	/**
	 * Gets the paginacion.
	 *
	 * @return the paginacion
	 */
	public PaginacionParametro getPaginacion() {
		return paginacion;
	}

	/**
	 * Sets the paginacion.
	 *
	 * @param paginacion the new paginacion
	 */
	public void setPaginacion(PaginacionParametro paginacion) {
		this.paginacion = paginacion;
	}
	
}
