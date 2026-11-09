package com.baz.divisas.api.tef.spots.model;

/**
 * The Class TipoCambioSpotDivisa.
 */
public class TipoCambioSpotDivisa {

	/** The divisa. */
	private String divisa;
	
	/** The tipo cambio. */
	private String tipoCambio;
	
	/** The fecha hora. */
	private String fechaRecuperacion;
	
	/**
	 * Gets the divisa.
	 *
	 * @return the divisa
	 */
	public String getDivisa() {
		return divisa;
	}
	
	/**
	 * Sets the divisa.
	 *
	 * @param divisa the new divisa
	 */
	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}
	
	/**
	 * Gets the tipo cambio.
	 *
	 * @return the tipo cambio
	 */
	public String getTipoCambio() {
		return tipoCambio;
	}
	
	/**
	 * Sets the tipo cambio.
	 *
	 * @param tipoCambio the new tipo cambio
	 */
	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	/**
	 * Gets the fecha recuperacion.
	 *
	 * @return the fecha recuperacion
	 */
	public String getFechaRecuperacion() {
		return fechaRecuperacion;
	}

	/**
	 * Sets the fecha recuperacion.
	 *
	 * @param fechaRecuperacion the new fecha recuperacion
	 */
	public void setFechaRecuperacion(String fechaRecuperacion) {
		this.fechaRecuperacion = fechaRecuperacion;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "TipoCambioSpotDivisa [divisa=" + divisa + ", tipoCambio=" + tipoCambio + ", fechaRecuperacion=" + fechaRecuperacion
				+ "]";
	}
	
	
}
