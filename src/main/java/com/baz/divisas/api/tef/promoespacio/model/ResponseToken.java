/**
 * 
 */
package com.baz.divisas.api.tef.promoespacio.model;

import com.baz.divisas.general.util.LogApp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class ResponseToken.
 *
 * @author B86960
 */
public class ResponseToken {
	
	/** The codigo. */
	private Integer codigo;
	
	/** The resultado. */
	private Resultado resultado;
	
	/** The status. */
	private String status;
	
	/** The data. */
	private Auth data;
	
	private String mensaje;



	/**
	 * Gets the codigo.
	 *
	 * @return the codigo
	 */
	public Integer getCodigo() {
		return codigo;
	}


	/**
	 * Sets the codigo.
	 *
	 * @param codigo the new codigo
	 */
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	/**
	 * Gets the resultado.
	 *
	 * @return the resultado
	 */
	public Resultado getResultado() {
		return resultado;
	}


	/**
	 * Sets the resultado.
	 *
	 * @param resultado the new resultado
	 */
	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}


	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Auth getData() {
		return data;
	}


	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(Auth data) {
		this.data = data;
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
