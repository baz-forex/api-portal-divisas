package com.baz.divisas.api.tef.general.model;

import com.baz.divisas.api.tef.regiones.model.Divisa;

/**
 * The Class Sucursal.
 */
public class Sucursal {

	/** The id. */
	private String id;
	
	/** The divisas. */
	private Divisa divisas;
	
	/** The tc olp. */
	private Divisa tcOlp;

	/**
	 * Instantiates a new sucursal.
	 */
	public Sucursal() {
		super();
	}

	

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
	 * Gets the divisas.
	 *
	 * @return the divisas
	 */
	public Divisa getDivisas() {
		return divisas;
	}

	/**
	 * Sets the divisas.
	 *
	 * @param divisas the new divisas
	 */
	public void setDivisas(Divisa divisas) {
		this.divisas = divisas;
	}



	/**
	 * Gets the tc olp.
	 *
	 * @return the tc olp
	 */
	public Divisa getTcOlp() {
		return tcOlp;
	}



	/**
	 * Sets the tc olp.
	 *
	 * @param tcOlp the new tc olp
	 */
	public void setTcOlp(Divisa tcOlp) {
		this.tcOlp = tcOlp;
	}

}
