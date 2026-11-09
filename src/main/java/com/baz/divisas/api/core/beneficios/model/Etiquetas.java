package com.baz.divisas.api.core.beneficios.model;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class Etiquetas {

	
	@BsonProperty("id")
	private String id;
	private String valor;
	/**
	 * 
	 */
	public Etiquetas() {
		super();
	}
	/**
	 * @param id
	 * @param valor
	 */
	public Etiquetas(String id, String valor) {
		super();
		this.id = id;
		this.valor = valor;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	


}
