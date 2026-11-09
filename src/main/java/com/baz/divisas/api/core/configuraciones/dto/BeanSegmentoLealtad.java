package com.baz.divisas.api.core.configuraciones.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Clase PaisesRestringidos
 * 
 * @author 1027346
 * Martinez Yonathan
 *
 */
public class BeanSegmentoLealtad {

	@SerializedName("_id")
	private String id;
	@SerializedName("id_negocio")
	private String idNegocio;
	private String descripcion;
	@SerializedName("limite_meses")
	private Double limiteMeses;
	@SerializedName("transaccion_minimo")
	private Double transaccionMinimo;
	@SerializedName("transaccion_maximo")
	private Double transaccionMaximo;
	private Boolean activo;
	@SerializedName("limites_rentabilidad")
	private List<BeanLimitesRentabilidad> limitesRentabilidad;
	
	/**
	 * Constructor de la clase
	 */
	public BeanSegmentoLealtad() {
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdNegocio() {
		return idNegocio;
	}
	public void setIdNegocio(String idNegocio) {
		this.idNegocio = idNegocio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Double getLimiteMeses() {
		return limiteMeses;
	}
	public void setLimiteMeses(Double limiteMeses) {
		this.limiteMeses = limiteMeses;
	}
	public Double getTransaccionMinimo() {
		return transaccionMinimo;
	}
	public void setTransaccionMinimo(Double transaccionMinimo) {
		this.transaccionMinimo = transaccionMinimo;
	}
	public Double getTransaccionMaximo() {
		return transaccionMaximo;
	}
	public void setTransaccionMaximo(Double transaccionMaximo) {
		this.transaccionMaximo = transaccionMaximo;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public List<BeanLimitesRentabilidad> getLimitesRentabilidad() {
		return limitesRentabilidad;
	}
	public void setLimitesRentabilidad(List<BeanLimitesRentabilidad> limitesRentabilidad) {
		this.limitesRentabilidad = limitesRentabilidad;
	}
	
}
