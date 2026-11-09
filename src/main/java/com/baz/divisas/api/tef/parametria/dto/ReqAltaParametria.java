/**
 * 
 */
package com.baz.divisas.api.tef.parametria.dto;

import java.util.Map;

/**
 * The Class ReqAltaParametria.
 *
 * @author B86960
 * @param <T> the generic type
 */
public class ReqAltaParametria <T> extends ReqConsultaParametria {
	private Map<String, T> campos;
	
	/**
	 * Instantiates a new req alta parametria.
	 */
	public ReqAltaParametria() {
		super();
	}

	/**
	 * @return the campos
	 */
	public Map<String, T> getCampos() {
		return campos;
	}

	/**
	 * @param campos the campos to set
	 */
	public void setCampos(Map<String, T> campos) {
		this.campos = campos;
	}
	
	
}
