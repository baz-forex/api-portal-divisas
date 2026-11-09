/**
 * 
 */
package com.baz.divisas.api.core.beneficios.model;

import java.util.ArrayList;

/**
 * @author B86960
 *
 */
public class BeanSegmentoSucursal {
	private String tipo;
	private ArrayList<BeanCatalogo> ids;
	/**
	 * Constructor
	 */
	public BeanSegmentoSucursal() {
		super();
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the ids
	 */
	public ArrayList<BeanCatalogo> getIds() {
		return ids;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(ArrayList<BeanCatalogo> ids) {
		this.ids = ids;
	}

}
