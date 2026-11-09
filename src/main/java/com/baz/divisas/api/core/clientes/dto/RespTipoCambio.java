package com.baz.divisas.api.core.clientes.dto;

import java.util.ArrayList;
import java.util.List;

import com.baz.divisas.api.core.clientes.model.TiposDeCambio;


/**
 * The Class RespTipoCambio.
 */
public class RespTipoCambio extends RespMiddleware {
	private List<TiposDeCambio> tipo_cambio;

	/**
	 * Gets the tipo cambio.
	 *
	 * @return the tipo cambio
	 */
	public List<TiposDeCambio> getTipo_cambio() {
		if (null != tipo_cambio) {
			return new ArrayList<>(tipo_cambio);
		} 
		else {
			return new ArrayList<>();
		}
	}

	/**
	 * Sets the tipo cambio.
	 *
	 * @param tiposCambio the new tipo cambio
	 */
	public void setTipo_cambio(List<TiposDeCambio> tiposCambio) {
		this.tipo_cambio =  new ArrayList<>(tiposCambio);
	}
}
