/**
 * 
 */
package com.baz.divisas.api.tef.promoespacio.model;


import com.baz.divisas.general.util.LogApp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class BeanDistanciaSucursales.
 *
 * @author B169028
 */
public class BeanDistanciaSucursales {
	
	/** The location. */
	private BeanDistancias location;
	
	/** The name. */
	private String name;
	
	/**
	 * Instantiates a new bean distancia sucursales.
	 */
	public BeanDistanciaSucursales() {

	}
	
	/**
	 * Instantiates a new bean distancia sucursales.
	 *
	 * @param name the name
	 */
	public BeanDistanciaSucursales(String name) {
		super();
		this.name = name;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public BeanDistancias getLocation() {
		return location;
	}
	
	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(BeanDistancias location) {
		this.location = location;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
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
