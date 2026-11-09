package com.baz.divisas.api.core.reporteria.model;

/**
 * The Class InventarioCostalSanta.
 */
public class InventarioCostalSanta {

	/** The id. */
	private String id;
	
	/** The stock. */
	private int stock;
	
	/** The stock entregado. */
	private int stockEntregado;
	
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
	 * Gets the stock.
	 *
	 * @return the stock
	 */
	public int getStock() {
		return stock;
	}
	
	/**
	 * Sets the stock.
	 *
	 * @param stock the new stock
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	/**
	 * Gets the stock entregado.
	 *
	 * @return the stock entregado
	 */
	public int getStockEntregado() {
		return stockEntregado;
	}
	
	/**
	 * Sets the stock entregado.
	 *
	 * @param stockEntregado the new stock entregado
	 */
	public void setStockEntregado(int stockEntregado) {
		this.stockEntregado = stockEntregado;
	}
	
	/**
	 * Gets the stock restante.
	 *
	 * @return the stock restante
	 */
	public int getStockRestante() {
		return stockRestante;
	}
	
	/**
	 * Sets the stock restante.
	 *
	 * @param stockRestante the new stock restante
	 */
	public void setStockRestante(int stockRestante) {
		this.stockRestante = stockRestante;
	}
	
	/**
	 * Gets the fecha ultima entrega.
	 *
	 * @return the fecha ultima entrega
	 */
	public String getFechaUltimaEntrega() {
		return fechaUltimaEntrega;
	}
	
	/**
	 * Sets the fecha ultima entrega.
	 *
	 * @param fechaUltimaEntrega the new fecha ultima entrega
	 */
	public void setFechaUltimaEntrega(String fechaUltimaEntrega) {
		this.fechaUltimaEntrega = fechaUltimaEntrega;
	}
	/** The stock restante. */
	private int stockRestante;
	
	/** The fecha ultima entrega. */
	private String fechaUltimaEntrega;
	
	
}
