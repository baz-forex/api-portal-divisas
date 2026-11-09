package com.baz.divisas.api.tef.sucursales.model;

import com.baz.divisas.api.tef.general.model.TC;

/**
 * The Class Divisa.
 */
public class Divisa {

	/** The id divisa. */
	private String idDivisa;
	
	/** The tc. */
	private TC tc;
	
	/** The spread. */
	private TC spread;
	
	/**
	 * Instantiates a new divisa.
	 */
	public Divisa() {
		super();
	}

	/**
	 * Gets the id divisa.
	 *
	 * @return the id divisa
	 */
	public String getIdDivisa() {
		return idDivisa;
	}

	/**
	 * Sets the id divisa.
	 *
	 * @param idDivisa the new id divisa
	 */
	public void setIdDivisa(String idDivisa) {
		this.idDivisa = idDivisa;
	}

	/**
	 * Gets the tc.
	 *
	 * @return the tc
	 */
	public TC getTc() {
		return tc;
	}

	/**
	 * Sets the tc.
	 *
	 * @param tc the new tc
	 */
	public void setTc(TC tc) {
		this.tc = tc;
	}

	/**
	 * Gets the spread.
	 *
	 * @return the spread
	 */
	public TC getSpread() {
		return spread;
	}

	/**
	 * Sets the spread.
	 *
	 * @param spread the new spread
	 */
	public void setSpread(TC spread) {
		this.spread = spread;
	}
	
	
}
