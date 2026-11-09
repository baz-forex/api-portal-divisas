package com.baz.divisas.api.tef.general.model;

import com.baz.divisas.api.tef.sucursales.model.TipoOperacion;

/**
 * The Class SucursalTC.
 */
public class SucursalTC {

	/** The id. */
	private String id;
	
	/** The divisa compra. */
	private TipoOperacion divisaCompra;
	
	/** The divisa venta. */
	private TipoOperacion divisaVenta;
	
	/**
	 * Instantiates a new sucursal TC.
	 */
	public SucursalTC() {
		super();
	}

	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	/**
	 * Gets the divisa compra.
	 *
	 * @return the divisa compra
	 */
	public TipoOperacion getDivisaCompra() {
		return divisaCompra;
	}

	/**
	 * Sets the divisa compra.
	 *
	 * @param divisaCompra the new divisa compra
	 */
	public void setDivisaCompra(TipoOperacion divisaCompra) {
		this.divisaCompra = divisaCompra;
	}

	/**
	 * Gets the divisa venta.
	 *
	 * @return the divisa venta
	 */
	public TipoOperacion getDivisaVenta() {
		return divisaVenta;
	}

	/**
	 * Sets the divisa venta.
	 *
	 * @param divisaVenta the new divisa venta
	 */
	public void setDivisaVenta(TipoOperacion divisaVenta) {
		this.divisaVenta = divisaVenta;
	}
	
}
