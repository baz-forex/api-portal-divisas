package com.baz.divisas.api.core.beneficios.model;

/**
 * The Class CuponAlianzas.
 */
public class CuponAlianzas extends Cupon{

	/** The fecha vigencia. */
	private String fechaVigencia;
	
	/** The alianza. */
	private String alianza;

	/**
	 * Gets the fecha vigencia.
	 *
	 * @return the fecha vigencia
	 */
	public String getFechaVigencia() {
		return fechaVigencia;
	}

	/**
	 * Sets the fecha vigencia.
	 *
	 * @param fechaVigencia the new fecha vigencia
	 */
	public void setFechaVigencia(String fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	/**
	 * Gets the alianza.
	 *
	 * @return the alianza
	 */
	public String getAlianza() {
		return alianza;
	}

	/**
	 * Sets the alianza.
	 *
	 * @param alianza the new alianza
	 */
	public void setAlianza(String alianza) {
		this.alianza = alianza;
	}
	
	
}
