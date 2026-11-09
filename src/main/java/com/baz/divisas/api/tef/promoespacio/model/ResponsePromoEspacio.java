/**
 * 
 */
package com.baz.divisas.api.tef.promoespacio.model;

import com.baz.divisas.general.util.LogApp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class ResponsePromoEspacio.
 *
 * @author B86960
 */
public class ResponsePromoEspacio {

	/** The message. */
	private String message;
	
	/** The request id. */
	private String request_id;
	
	private Integer codigo;
	private String mensaje;
	private String folio;

	
	/**
	 * Instantiates a new response promo espacio.
	 */
	public ResponsePromoEspacio(){
		super();
		codigo= 0;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Gets the request id.
	 *
	 * @return the request id
	 */
	public String getRequest_id() {
		return request_id;
	}
	
	/**
	 * Sets the request id.
	 *
	 * @param request_id the new request id
	 */
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public Integer getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {

		try {
			return new ObjectMapper().writeValueAsString(this);
		} 
		catch (JsonProcessingException e) {
			LogApp.excepcion(e, "Error al convertir objeto a JSON");
		}
		return "";
	}
	
}
