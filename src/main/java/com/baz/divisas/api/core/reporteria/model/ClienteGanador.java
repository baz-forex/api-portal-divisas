package com.baz.divisas.api.core.reporteria.model;

import com.baz.divisas.api.core.clientes.model.Cliente;

/**
 * The Class ClienteGanador.
 */
public class ClienteGanador extends Cliente {

	/** The numero telefono. */
	private String numeroTelefono;
	
	/** The correo electronico. */
	private String correoElectronico;

	/**
	 * Instantiates a new cliente ganador.
	 */
	public ClienteGanador() {
		super();
	}

	/**
	 * Gets the numero telefono.
	 *
	 * @return the numero telefono
	 */
	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	/**
	 * Sets the numero telefono.
	 *
	 * @param numeroTelefono the new numero telefono
	 */
	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	/**
	 * Gets the correo electronico.
	 *
	 * @return the correo electronico
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	/**
	 * Sets the correo electronico.
	 *
	 * @param correoElectronico the new correo electronico
	 */
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

}
