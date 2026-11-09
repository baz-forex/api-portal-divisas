package com.baz.divisas.api.core.configuraciones.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Clase PaisesRestringidos
 * 
 * @author 1027346
 * Martinez Yonathan
 *
 */
public class BeanSegmentoRentabilidad {

	@SerializedName("_id")
	private String id;
	private String descripcion;
	@SerializedName("monto_minimo")
	private int montoMinimo;
	@SerializedName("monto_maximo")
	private int montoMaximo;
	@SerializedName("maximo_premios")
	private int maximoPremios;
	private Boolean activo;
	
	/**
	 * Constructor de la clase
	 */
	public BeanSegmentoRentabilidad() {
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
	public int getMontoMinimo() {
		return montoMinimo;
	}
	public void setMontoMinimo(int montoMinimo) {
		this.montoMinimo = montoMinimo;
	}
	public int getMontoMaximo() {
		return montoMaximo;
	}
	public void setMontoMaximo(int montoMaximo) {
		this.montoMaximo = montoMaximo;
	}
	public int getMaximoPremios() {
		return maximoPremios;
	}
	public void setMaximoPremios(int maximoPremios) {
		this.maximoPremios = maximoPremios;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	
}
