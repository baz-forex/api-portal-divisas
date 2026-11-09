package com.baz.divisas.api.core.sucursales.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase TipoRespuesta
 * 
 * @author 1096526
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TipoRespuesta {
	private String id;
	private String descripcion;

	/**
	 * Constructor de la clase
	 */
	public TipoRespuesta() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
