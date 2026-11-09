package com.baz.divisas.api.tef.sucursales.model;

public class TipoCambio {

	private int idMonedaOrigen;
	private int idMonedaDestino;
	private int idTipoOrigen;
	private String precioCompra;
	private String precioVenta;
	
	public int getIdMonedaOrigen() {
		return idMonedaOrigen;
	}
	public void setIdMonedaOrigen(int idMonedaOrigen) {
		this.idMonedaOrigen = idMonedaOrigen;
	}
	public int getIdMonedaDestino() {
		return idMonedaDestino;
	}
	public void setIdMonedaDestino(int idMonedaDestino) {
		this.idMonedaDestino = idMonedaDestino;
	}
	public int getIdTipoOrigen() {
		return idTipoOrigen;
	}
	public void setIdTipoOrigen(int idTipoOrigen) {
		this.idTipoOrigen = idTipoOrigen;
	}
	public String getPrecioCompra() {
		return precioCompra;
	}
	public void setPrecioCompra(String precioCompra) {
		this.precioCompra = precioCompra;
	}
	public String getPrecioVenta() {
		return precioVenta;
	}
	public void setPrecioVenta(String precioVenta) {
		this.precioVenta = precioVenta;
	}
	@Override
	public String toString() {
		return "TipoCambio [idMonedaOrigen=" + idMonedaOrigen + ", idMonedaDestino=" + idMonedaDestino
				+ ", idTipoOrigen=" + idTipoOrigen + ", precioCompra=" + precioCompra + ", precioVenta=" + precioVenta
				+ "]";
	}
	
	
}
