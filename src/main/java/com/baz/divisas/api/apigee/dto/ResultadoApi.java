package com.baz.divisas.api.apigee.dto;

/**
 * The Class ResultApiGenerico.
 */
public class ResultadoApi {

	/** The codigo. */
	private String codigo;
	
	/** The folio. */
	private String folio;
	
	/** The mensaje. */
	private String mensaje;
	
	/** The info. */
	private String info;
	
	/**
	 * Instantiates a new result api generico.
	 */
	public ResultadoApi() {
		super();
	}
	
	/**
	 * Gets the codigo.
	 *
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	
	/**
	 * Sets the codigo.
	 *
	 * @param codigo the new codigo
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	/**
	 * Gets the folio.
	 *
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	
	/**
	 * Sets the folio.
	 *
	 * @param folio the new folio
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	/**
	 * Gets the mensaje.
	 *
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	
	/**
	 * Sets the mensaje.
	 *
	 * @param mensaje the new mensaje
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
	
	/**
	 * Sets the info.
	 *
	 * @param info the new info
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "ResultadoApi [codigo=" + codigo + ", folio=" + folio + ", mensaje=" + mensaje + ", info=" + info + "]";
	}
	
	
}
