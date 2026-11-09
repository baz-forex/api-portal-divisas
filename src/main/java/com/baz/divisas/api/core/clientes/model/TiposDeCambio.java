/**
 * 
 */
package com.baz.divisas.api.core.clientes.model;

/**
 * The Class TiposDeCambio.
 *
 * @author B86960
 */
public class TiposDeCambio {
	private String divisa;
	private TipoCambio compra;
	private TipoCambio venta;
	private double tipo_cambio_fix;
	/**
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}
	/**
	 * @param divisa the divisa to set
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	/**
	 * @return the compra
	 */
	public TipoCambio getCompra() {
		return compra;
	}
	/**
	 * @param compra the compra to set
	 */
	public void setCompra(TipoCambio compra) {
		this.compra = compra;
	}
	/**
	 * @return the venta
	 */
	public TipoCambio getVenta() {
		return venta;
	}
	/**
	 * @param venta the venta to set
	 */
	public void setVenta(TipoCambio venta) {
		this.venta = venta;
	}
	/**
	 * @return the tipo_cambio_fix
	 */
	public double getTipo_cambio_fix() {
		return tipo_cambio_fix;
	}
	/**
	 * @param tipo_cambio_fix the tipo_cambio_fix to set
	 */
	public void setTipo_cambio_fix(double tipo_cambio_fix) {
		this.tipo_cambio_fix = tipo_cambio_fix;
	}
	
}
