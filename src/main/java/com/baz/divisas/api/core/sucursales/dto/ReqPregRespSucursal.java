package com.baz.divisas.api.core.sucursales.dto;

import org.bson.codecs.pojo.annotations.BsonId;

/**
 * Clase ReqRespuestasSucursal
 * 
 * @author 1096526
 *
 */
public class ReqPregRespSucursal {
	private String sucursal;
	@BsonId
	private String id;

	/**
	 * Constructor de la clase
	 */
	public ReqPregRespSucursal() {
		super();
	}

	/**
	 * @return the sucursal
	 */
	public String getSucursal() {
		return sucursal;
	}

	/**
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
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

}
