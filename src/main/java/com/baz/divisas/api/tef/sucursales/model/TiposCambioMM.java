package com.baz.divisas.api.tef.sucursales.model;

import java.util.List;

/**
 * The Class TiposCambioMM.
 */
public class TiposCambioMM {

	/** The tipos cambios. */
	private List<TipoCambio> tiposCambios;

	/**
	 * Gets the tipos cambios.
	 *
	 * @return the tipos cambios
	 */
	public List<TipoCambio> getTiposCambios() {
		return tiposCambios;
	}

	/**
	 * Sets the tipos cambios.
	 *
	 * @param tiposCambios the new tipos cambios
	 */
	public void setTiposCambios(List<TipoCambio> tiposCambios) {
		this.tiposCambios = tiposCambios;
	}

	@Override
	public String toString() {
		return "TiposCambioMM [tiposCambios=" + tiposCambios + "]";
	}
	
	
}
