/**
 * 
 */
package com.baz.divisas.api.tef.spread.dto;

import org.bson.codecs.pojo.annotations.BsonId;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Clase Spread
 * @author 813196
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Spread {

	@BsonId()
	private String idDivisa;
	private Double compraEfectivo;
	private Double compraAbono;
	private Double ventaEfectivo;
	private Double ventaCargo;

	/**
	 * Constructor de la clase
	 */
	public Spread() {
		super();
	}
	public String getIdDivisa() {
		return idDivisa;
	}
	public void setIdDivisa(String idDivisa) {
		this.idDivisa = idDivisa;
	}
	public Double getCompraEfectivo() {
		return compraEfectivo;
	}
	public void setCompraEfectivo(Double compraEfectivo) {
		this.compraEfectivo = compraEfectivo;
	}
	public Double getCompraAbono() {
		return compraAbono;
	}
	public void setCompraAbono(Double compraAbono) {
		this.compraAbono = compraAbono;
	}
	public Double getVentaEfectivo() {
		return ventaEfectivo;
	}
	public void setVentaEfectivo(Double ventaEfectivo) {
		this.ventaEfectivo = ventaEfectivo;
	}
	public Double getVentaCargo() {
		return ventaCargo;
	}
	public void setVentaCargo(Double ventaCargo) {
		this.ventaCargo = ventaCargo;
	}

}
