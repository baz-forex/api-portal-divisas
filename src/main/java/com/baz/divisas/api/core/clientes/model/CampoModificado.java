package com.baz.divisas.api.core.clientes.model;

import org.bson.codecs.pojo.annotations.BsonProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase CampoModificado
 * @author B813196
 * itoxtle
 */
public class CampoModificado {

	private String campo;
	@JsonProperty("valor_anterior")
	@BsonProperty("valor_anterior")
	private String valorAnterior;
	@JsonProperty("valor_nuevo")
	@BsonProperty("valor_nuevo")
	private String valorNuevo;
	
	/**Constructor de la clase
	 * 
	 */
	public CampoModificado() {
		super();
	}
	
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getValorAnterior() {
		return valorAnterior;
	}
	public void setValorAnterior(String valorAnterior) {
		this.valorAnterior = valorAnterior;
	}
	public String getValorNuevo() {
		return valorNuevo;
	}
	public void setValorNuevo(String valorNuevo) {
		this.valorNuevo = valorNuevo;
	}
}
