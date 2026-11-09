package com.baz.divisas.api.tef.spots.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class ReqConfiguracionSpot.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReqConfiguracionSpot {

	/** The id. */
	private String id;
	
	/** The acunacion. */
	private Double acunacion;
	
	/** The x 1. */
	private Double x1;
	
	/** The x 2. */
	private Double x2;
	
	/** The spot minimo. */
	private Double spotMinimo;
	
	/** The frecuencia. */
	private String frecuencia;
	
	/** The direccion actualizacion. */
	private String direccionActualizacion;
	
	/** The diferencia compra venta. */
	private Double diferenciaCompraVenta;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the acunacion.
	 *
	 * @return the acunacion
	 */
	public Double getAcunacion() {
		return acunacion;
	}

	/**
	 * Sets the acunacion.
	 *
	 * @param acunacion the new acunacion
	 */
	public void setAcunacion(Double acunacion) {
		this.acunacion = acunacion;
	}

	/**
	 * Gets the x1.
	 *
	 * @return the x1
	 */
	public Double getX1() {
		return x1;
	}

	/**
	 * Sets the x1.
	 *
	 * @param x1 the new x1
	 */
	public void setX1(Double x1) {
		this.x1 = x1;
	}

	/**
	 * Gets the x2.
	 *
	 * @return the x2
	 */
	public Double getX2() {
		return x2;
	}

	/**
	 * Sets the x2.
	 *
	 * @param x2 the new x2
	 */
	public void setX2(Double x2) {
		this.x2 = x2;
	}

	/**
	 * Gets the spot minimo.
	 *
	 * @return the spot minimo
	 */
	public Double getSpotMinimo() {
		return spotMinimo;
	}

	/**
	 * Sets the spot minimo.
	 *
	 * @param spotMinimo the new spot minimo
	 */
	public void setSpotMinimo(Double spotMinimo) {
		this.spotMinimo = spotMinimo;
	}

	/**
	 * Gets the frecuencia.
	 *
	 * @return the frecuencia
	 */
	public String getFrecuencia() {
		return frecuencia;
	}

	/**
	 * Sets the frecuencia.
	 *
	 * @param frecuencia the new frecuencia
	 */
	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	/**
	 * Gets the direccion actualizacion.
	 *
	 * @return the direccion actualizacion
	 */
	public String getDireccionActualizacion() {
		return direccionActualizacion;
	}

	/**
	 * Sets the direccion actualizacion.
	 *
	 * @param direccionActualizacion the new direccion actualizacion
	 */
	public void setDireccionActualizacion(String direccionActualizacion) {
		this.direccionActualizacion = direccionActualizacion;
	}

	/**
	 * Gets the diferencia compra venta.
	 *
	 * @return the diferencia compra venta
	 */
	public Double getDiferenciaCompraVenta() {
		return diferenciaCompraVenta;
	}

	/**
	 * Sets the diferencia compra venta.
	 *
	 * @param diferenciaCompraVenta the new diferencia compra venta
	 */
	public void setDiferenciaCompraVenta(Double diferenciaCompraVenta) {
		this.diferenciaCompraVenta = diferenciaCompraVenta;
	}

	
}

