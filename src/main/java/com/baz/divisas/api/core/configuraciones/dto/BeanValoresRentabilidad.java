package com.baz.divisas.api.core.configuraciones.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Clase PaisesRestringidos
 * 
 * @author 1027346
 * Martinez Yonathan
 *
 */
public class BeanValoresRentabilidad {

	@SerializedName("_id")
	private String id;
	private String descripcion;
	private Double valor;
	private Boolean activo;
	
	/**
	 * Constructor de la clase
	 */
	public BeanValoresRentabilidad() {
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
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	
}
