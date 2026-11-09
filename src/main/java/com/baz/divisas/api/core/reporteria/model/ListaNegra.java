package com.baz.divisas.api.core.reporteria.model;

import java.io.Serializable;

/**
 * Clase ListaNegra
 * 
 * @author 1027346 Martinez Yonathan
 *
 */
public class ListaNegra implements Serializable {
	private static final long serialVersionUID = -2971437717438212772L;

	private String id;
	private String descripcion;

	/**
	 * Constructor de la clase
	 */
	public ListaNegra() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
