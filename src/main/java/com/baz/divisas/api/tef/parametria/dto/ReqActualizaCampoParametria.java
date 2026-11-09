/**
 * 
 */
package com.baz.divisas.api.tef.parametria.dto;

/**
 * The Class ReqActualizaCampoPärametria.
 *
 * @author B86960
 * @param <T> the generic type
 */
public class ReqActualizaCampoParametria <T> extends ReqConsultaParametria {

	private String campo;
	private T valor;
	
	/**
	 * Instantiates a new req actualiza campo pärametria.
	 */
	public ReqActualizaCampoParametria() {
		super();
	}
	/**
	 * @return the campo
	 */
	public String getCampo() {
		return campo;
	}
	/**
	 * @param campo the campo to set
	 */
	public void setCampo(String campo) {
		this.campo = campo;
	}
	/**
	 * @return the valor
	 */
	public T getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(T valor) {
		this.valor = valor;
	}
	
	
}
