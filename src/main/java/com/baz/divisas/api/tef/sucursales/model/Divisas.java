package com.baz.divisas.api.tef.sucursales.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Divisas.
 */
public class Divisas {

	@SerializedName("_id")
	private String id;
	private List<TipoOperacion> tiposOperacion;
	
	/**
	 * Constructor de la clase
	 */
	public Divisas() {
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
	 * @return the tiposOperacion
	 */
	public List<TipoOperacion> getTiposOperacion() {
		return tiposOperacion;
	}
	/**
	 * @param tiposOperacion the tiposOperacion to set
	 */
	public void setTiposOperacion(List<TipoOperacion> tiposOperacion) {
		this.tiposOperacion = tiposOperacion;
	}

	@Override
	public String toString() {
		return "Divisas [id=" + id + ", tiposOperacion=" + tiposOperacion + "]";
	}

	
}
