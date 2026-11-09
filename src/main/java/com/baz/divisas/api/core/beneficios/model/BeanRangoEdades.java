/**
 * 
 */
package com.baz.divisas.api.core.beneficios.model;

/**
 * @author B86960
 *
 */
public class BeanRangoEdades {

	private String id;
	private String descripcion;
	private int minimo;
	private int maximo;
	/**
	 * Constructor
	 */
	public BeanRangoEdades() {
		super();
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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the minimo
	 */
	public int getMinimo() {
		return minimo;
	}
	/**
	 * @param minimo the minimo to set
	 */
	public void setMinimo(int minimo) {
		this.minimo = minimo;
	}
	/**
	 * @return the maximo
	 */
	public int getMaximo() {
		return maximo;
	}
	/**
	 * @param maximo the maximo to set
	 */
	public void setMaximo(int maximo) {
		this.maximo = maximo;
	}
	
}
