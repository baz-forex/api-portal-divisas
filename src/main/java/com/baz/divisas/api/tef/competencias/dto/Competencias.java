package com.baz.divisas.api.tef.competencias.dto;

public class Competencias {

	private String nombreCompetencia = ""; 
	private Double dolarCompra = 0.0;
	private Double dolarVenta = 0.0;
	private Double canadienseCompra = 0.0;
	private Double canadienseVenta = 0.0;
	private Double euroCompra = 0.0;
	private Double euroVenta = 0.0;
	/**
	 * 
	 */
	public Competencias() {
		super();
	}
	/**
	 * @param nombreCompetencia
	 * @param dolarCompra
	 * @param dolarVenta
	 * @param canadienseCompra
	 * @param canadienseVenta
	 * @param euroCompra
	 * @param euroVenta
	 */
	public Competencias(String nombreCompetencia, Double dolarCompra, Double dolarVenta, Double canadienseCompra,
			Double canadienseVenta, Double euroCompra, Double euroVenta) {
		super();
		this.nombreCompetencia = nombreCompetencia;
		this.dolarCompra = dolarCompra;
		this.dolarVenta = dolarVenta;
		this.canadienseCompra = canadienseCompra;
		this.canadienseVenta = canadienseVenta;
		this.euroCompra = euroCompra;
		this.euroVenta = euroVenta;
	}
	/**
	 * @return the nombreCompetencia
	 */
	public String getNombreCompetencia() {
		return nombreCompetencia;
	}
	/**
	 * @param nombreCompetencia the nombreCompetencia to set
	 */
	public void setNombreCompetencia(String nombreCompetencia) {
		this.nombreCompetencia = nombreCompetencia;
	}
	/**
	 * @return the dolarCompra
	 */
	public Double getDolarCompra() {
		return dolarCompra;
	}
	/**
	 * @param dolarCompra the dolarCompra to set
	 */
	public void setDolarCompra(Double dolarCompra) {
		this.dolarCompra = dolarCompra;
	}
	/**
	 * @return the dolarVenta
	 */
	public Double getDolarVenta() {
		return dolarVenta;
	}
	/**
	 * @param dolarVenta the dolarVenta to set
	 */
	public void setDolarVenta(Double dolarVenta) {
		this.dolarVenta = dolarVenta;
	}
	/**
	 * @return the canadienseCompra
	 */
	public Double getCanadienseCompra() {
		return canadienseCompra;
	}
	/**
	 * @param canadienseCompra the canadienseCompra to set
	 */
	public void setCanadienseCompra(Double canadienseCompra) {
		this.canadienseCompra = canadienseCompra;
	}
	/**
	 * @return the canadienseVenta
	 */
	public Double getCanadienseVenta() {
		return canadienseVenta;
	}
	/**
	 * @param canadienseVenta the canadienseVenta to set
	 */
	public void setCanadienseVenta(Double canadienseVenta) {
		this.canadienseVenta = canadienseVenta;
	}
	/**
	 * @return the euroCompra
	 */
	public Double getEuroCompra() {
		return euroCompra;
	}
	/**
	 * @param euroCompra the euroCompra to set
	 */
	public void setEuroCompra(Double euroCompra) {
		this.euroCompra = euroCompra;
	}
	/**
	 * @return the euroVenta
	 */
	public Double getEuroVenta() {
		return euroVenta;
	}
	/**
	 * @param euroVenta the euroVenta to set
	 */
	public void setEuroVenta(Double euroVenta) {
		this.euroVenta = euroVenta;
	}
	
}
