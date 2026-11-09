package com.baz.divisas.api.core.configuraciones.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Clase PaisesRestringidos
 * 
 * @author 1027346
 * Martinez Yonathan
 *
 */
public class BeanLimitesRentabilidad {

	@SerializedName("_id")
	private String id;
	private String descripcion;
	@SerializedName("maximo_premios")
	private int maximoPremios;
	private Boolean estatus;
	
	
	/**
	 * Constructor de la clase
	 */
	public BeanLimitesRentabilidad() {
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
	public int getMaximoPremios() {
		return maximoPremios;
	}
	public void setMaximoPremios(int maximoPremios) {
		this.maximoPremios = maximoPremios;
	}
	public Boolean getEstatus() {
		return estatus;
	}
	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	
}
