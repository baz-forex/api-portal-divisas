package com.baz.divisas.api.tef.parametria.dto;

/**
 * The Class ReqDetalleParametro.
 */
public class ReqDetalleParametro extends ReqParametro{

	/** The parametro. */
	private String parametro;
	
	/** The descripcion. */
	private String descripcion;
	
	/**
	 * Gets the parametro.
	 *
	 * @return the parametro
	 */
	public String getParametro() {
		return parametro;
	}
	
	/**
	 * Sets the parametro.
	 *
	 * @param parametro the new parametro
	 */
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	
	/**
	 * Gets the descripcion.
	 *
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * Sets the descripcion.
	 *
	 * @param descripcion the new descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "parametro=" + parametro + ", descripcion=" + descripcion;
	}
	
	
}

