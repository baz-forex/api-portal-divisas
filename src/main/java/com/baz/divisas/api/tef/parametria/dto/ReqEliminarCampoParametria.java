/**
 * 
 */
package com.baz.divisas.api.tef.parametria.dto;

/**
 * The Class ReqEliminarCampoParametria.
 *
 * @author B86960
 */
public class ReqEliminarCampoParametria extends ReqConsultaParametria {
	private String campo;
	
	/**
	 * Instantiates a new req eliminar campo parametria.
	 */
	public ReqEliminarCampoParametria() {
		super();
	}

	/**
	 * @return the campo
	 */
	public String getCampo() {
		return campo;
	}

	/**
	 * @param campo the campo to set
	 */
	public void setCampo(String campo) {
		this.campo = campo;
	}
	
	
}
