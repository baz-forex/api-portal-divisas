package com.baz.divisas.api.core.sucursales.model;

/**
 * Clase Principal
 * @author 1096526
 *
 */
public class Principal {
	private int dolar;
	private int canadiense;
	private int euro;
	
	/**
	 * Constructor de la clase
	 */
	public Principal() {
		super();		
	}

	/**
	 * @return the dolar
	 */
	public int getDolar() {
		return dolar;
	}

	/**
	 * @param dolar the dolar to set
	 */
	public void setDolar(int dolar) {
		this.dolar = dolar;
	}

	/**
	 * @return the canadiense
	 */
	public int getCanadiense() {
		return canadiense;
	}

	/**
	 * @param canadiense the canadiense to set
	 */
	public void setCanadiense(int canadiense) {
		this.canadiense = canadiense;
	}

	/**
	 * @return the euro
	 */
	public int getEuro() {
		return euro;
	}

	/**
	 * @param euro the euro to set
	 */
	public void setEuro(int euro) {
		this.euro = euro;
	}

}
