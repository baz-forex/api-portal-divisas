package com.baz.divisas.api.tef.sucursales.model;

/**
 * The Class TipoCambioDiv.
 */
public class TipoCambioDiv {

	/** The id. */
	private String id;
	
	/** The id tipo operacion. */
	private String idTipoOperacion;
	
	/** The codigo divisa. */
	private String codigoDivisa;
	
	/** The codigo origen. */
	private String codigoOrigen;
	
	/** The monto divisa. */
	private String montoDivisa;
	
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
	 * Gets the id tipo operacion.
	 *
	 * @return the id tipo operacion
	 */
	public String getIdTipoOperacion() {
		return idTipoOperacion;
	}
	
	/**
	 * Sets the id tipo operacion.
	 *
	 * @param idTipoOperacion the new id tipo operacion
	 */
	public void setIdTipoOperacion(String idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}
	
	/**
	 * Gets the codigo divisa.
	 *
	 * @return the codigo divisa
	 */
	public String getCodigoDivisa() {
		return codigoDivisa;
	}
	
	/**
	 * Sets the codigo divisa.
	 *
	 * @param codigoDivisa the new codigo divisa
	 */
	public void setCodigoDivisa(String codigoDivisa) {
		this.codigoDivisa = codigoDivisa;
	}
	
	/**
	 * Gets the codigo origen.
	 *
	 * @return the codigo origen
	 */
	public String getCodigoOrigen() {
		return codigoOrigen;
	}
	
	/**
	 * Sets the codigo origen.
	 *
	 * @param codigoOrigen the new codigo origen
	 */
	public void setCodigoOrigen(String codigoOrigen) {
		this.codigoOrigen = codigoOrigen;
	}
	
	/**
	 * Gets the monto divisa.
	 *
	 * @return the monto divisa
	 */
	public String getMontoDivisa() {
		return montoDivisa;
	}
	
	/**
	 * Sets the monto divisa.
	 *
	 * @param montoDivisa the new monto divisa
	 */
	public void setMontoDivisa(String montoDivisa) {
		this.montoDivisa = montoDivisa;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "TipoCambioDiv [id=" + id + ", idTipoOperacion=" + idTipoOperacion + ", codigoDivisa=" + codigoDivisa
				+ ", codigoOrigen=" + codigoOrigen + ", montoDivisa=" + montoDivisa + "]";
	}
	

}
