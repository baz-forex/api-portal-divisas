package com.baz.divisas.api.core.beneficios.model;

/**
 * The Class CuponTA.
 */
public class CuponTA {

	/** The id. */
	private String id;
	
	/** The estado. */
	private String estado = "disponible";
	/**
	 * Instantiates a new cupon TA.
	 */
	public CuponTA() {
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
	 * Gets the estado.
	 *
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Sets the estado.
	 *
	 * @param estado the new estado
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
}
