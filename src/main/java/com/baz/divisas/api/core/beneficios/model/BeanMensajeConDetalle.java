/**
 * 
 */
package com.baz.divisas.api.core.beneficios.model;

/**
 * @author B86960
 *
 */
public class BeanMensajeConDetalle {
	private BeanMensajeDetallado titulo;
	private BeanMensajeDetallado mensaje;
	/**
	 * Constructor
	 */
	public BeanMensajeConDetalle() {
		super();
	}
	/**
	 * @return the titulo
	 */
	public BeanMensajeDetallado getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(BeanMensajeDetallado titulo) {
		this.titulo = titulo;
	}
	/**
	 * @return the mensaje
	 */
	public BeanMensajeDetallado getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(BeanMensajeDetallado mensaje) {
		this.mensaje = mensaje;
	}
	
}
