package com.baz.divisas.api.core.beneficios.model;

import java.util.List;

/**
 * SegmentoSucursal
 */
public class SegmentoSucursal {

	private String tipo;
	private List<String> ids;
		
	/**
	 * Constructor de la clase
	 */
	public SegmentoSucursal() {
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
	public List<String> getIds() {
		return ids;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
}
