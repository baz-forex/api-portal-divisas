package com.baz.divisas.api.tef.spots.model;

import java.util.ArrayList;

/**
 * The Class Divisas.
 */
public class Divisas {

	/** The divisas. */
	private ArrayList<TipoCambioSpotDivisa> divisas;

	/**
	 * Gets the divisas.
	 *
	 * @return the divisas
	 */
	public ArrayList<TipoCambioSpotDivisa> getDivisas() {
		return divisas;
	}

	/**
	 * Sets the divisas.
	 *
	 * @param divisas the new divisas
	 */
	public void setDivisas(ArrayList<TipoCambioSpotDivisa> divisas) {
		this.divisas = divisas;
	}
	
	
	@Override
	public String toString() {
		return "Divisas [divisas=" + divisas + "]";
	}
}
