package com.baz.divisas.api.tef.spots.dto;

import java.util.List;

/**
 * ReqSpots.
 */
public class ReqSpots {

	private List<String> idDivisas;
	private String fechaInicio;
	private String fechaFin;
	
	
	/**
	 * Cosntructor de la clase
	 */
	public ReqSpots() {
		super();
	}
	
	/**
	 * @return the idDivisas
	 */
	public List<String> getIdDivisas() {
		return idDivisas;
	}
	/**
	 * @param idDivisas the idDivisas to set
	 */
	public void setIdDivisas(List<String> idDivisas) {
		this.idDivisas = idDivisas;
	}
	/**
	 * @return the fechaInicio
	 */
	public String getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaFin
	 */
	public String getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	
}
