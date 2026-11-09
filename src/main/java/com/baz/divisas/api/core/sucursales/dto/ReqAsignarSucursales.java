package com.baz.divisas.api.core.sucursales.dto;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;

/**
 * Clase ReqAsignarSucursales
 * 
 * @author 1096526
 *
 */
public class ReqAsignarSucursales {
	@BsonId
	private String id;
	private List<String> sucursales = new ArrayList<>();

	/**
	 * Constructor de la clase
	 */
	public ReqAsignarSucursales() {
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
	 * @return the sucursales
	 */
	public List<String> getSucursales() {
		return new ArrayList<>(sucursales);
	}

	/**
	 * @param sucursales the sucursales to set
	 */
	public void setSucursales(List<String> sucursales) {
		this.sucursales = new ArrayList<>(sucursales);
	}

}
