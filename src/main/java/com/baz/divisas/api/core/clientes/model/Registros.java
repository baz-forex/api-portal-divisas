package com.baz.divisas.api.core.clientes.model;

import java.util.List;

/**
 * The Class Registros.
 */
public class Registros {

	/** The registros exitosos. */
	private int registrosExitosos;

	/** The registros erroneos. */
	private List<String> registrosErroneos;
	
	/**
	 * Instantiates a new registros.
	 */
	public Registros() {
		super();
	}
	
	/**
	 * Instantiates a new registros.
	 *
	 * @param registrosExitosos the registros exitosos
	 * @param registrosErroneos the registros erroneos
	 */
	public Registros(int registrosExitosos,List<String> registrosErroneos) {
		this.registrosExitosos = registrosExitosos;
		this.registrosErroneos = registrosErroneos;
	}
	/**
	 * Gets the registros exitosos.
	 *
	 * @return the registros exitosos
	 */
	public int getRegistrosExitosos() {
		return registrosExitosos;
	}

	/**
	 * Sets the registros exitosos.
	 *
	 * @param registrosExitosos the new registros exitosos
	 */
	public void setRegistrosExitosos(int registrosExitosos) {
		this.registrosExitosos = registrosExitosos;
	}

	/**
	 * Gets the registros erroneos.
	 *
	 * @return the registros erroneos
	 */
	public List<String> getRegistrosErroneos() {
		return registrosErroneos;
	}

	/**
	 * Sets the registros erroneos.
	 *
	 * @param registrosErroneos the new registros erroneos
	 */
	public void setRegistrosErroneos(List<String> registrosErroneos) {
		this.registrosErroneos = registrosErroneos;
	}
}
