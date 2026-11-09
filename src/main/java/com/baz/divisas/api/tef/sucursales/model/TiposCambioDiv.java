package com.baz.divisas.api.tef.sucursales.model;

import java.util.List;

/**
 * The Class TiposCambioDiv.
 */
public class TiposCambioDiv {
	
	/** The tipos cambios. */
	private List<TipoCambioDiv> tiposCambios;
	
	
	/**
	 * Gets the tipos cambios.
	 *
	 * @return the tipos cambios
	 */
	public List<TipoCambioDiv> getTiposCambios() {
		return tiposCambios;
	}

	/**
	 * Sets the tipos cambios.
	 *
	 * @param tiposCambios the new tipos cambios
	 */
	public void setTiposCambios(List<TipoCambioDiv> tiposCambios) {
		this.tiposCambios = tiposCambios;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "TiposCambioDiv [tiposCambios=" + tiposCambios + "]";
	}
}


