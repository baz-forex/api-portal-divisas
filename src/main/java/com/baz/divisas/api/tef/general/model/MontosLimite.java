package com.baz.divisas.api.tef.general.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.baz.divisas.api.tef.general.LimitesOnUpsert;

/**
 * Clase Limite.
 *
 * @author 1027346 ymartinezb
 */
public class MontosLimite {

	/** The cliente. */
	@NotBlank(message = "Requerido", groups= {LimitesOnUpsert.class})
	@NotNull(message = "Requerido", groups= {LimitesOnUpsert.class})
	private double cliente;
	
	/** The usuario. */
	@NotBlank(message = "Requerido", groups= {LimitesOnUpsert.class})
	@NotNull(message = "Requerido", groups= {LimitesOnUpsert.class})
	private double usuario;
	
	/** The cliente extranjero. */
	@NotBlank(message = "Requerido", groups= {LimitesOnUpsert.class})
	@NotNull(message = "Requerido", groups= {LimitesOnUpsert.class})
	private double clienteExtranjero;
	
	/** The usuario extranjero. */
	@NotBlank(message = "Requerido", groups= {LimitesOnUpsert.class})
	@NotNull(message = "Requerido", groups= {LimitesOnUpsert.class})
	private double usuarioExtranjero;
	
	/**
	 * Constructor de la clase.
	 */
	public MontosLimite() {
		super();
	}
	
	/**
	 * Constructor de la clase.
	 *
	 * @param usuario - Limite Usuario
	 * @param cliente - Limite Cliente
	 */
	public MontosLimite(Double usuario, Double cliente,Double usuarioExtranjero, Double clienteExtranjero) {
		super();
		this.usuario = usuario;
		this.cliente = cliente;
		this.usuarioExtranjero = usuarioExtranjero;
		this.clienteExtranjero = clienteExtranjero;
	}

	/**
	 * Gets the usuario.
	 *
	 * @return the usuario
	 */
	public Double getUsuario() {
		return usuario;
	}

	/**
	 * Sets the usuario.
	 *
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Double usuario) {
		this.usuario = usuario;
	}

	/**
	 * Gets the cliente.
	 *
	 * @return the cliente
	 */
	public Double getCliente() {
		return cliente;
	}

	/**
	 * Sets the cliente.
	 *
	 * @param cliente the cliente to set
	 */
	public void setCliente(Double cliente) {
		this.cliente = cliente;
	}

	/**
	 * Gets the cliente extranjero.
	 *
	 * @return the cliente extranjero
	 */
	public Double getClienteExtranjero() {
		return clienteExtranjero;
	}

	/**
	 * Sets the cliente extranjero.
	 *
	 * @param clienteExtranjero the new cliente extranjero
	 */
	public void setClienteExtranjero(Double clienteExtranjero) {
		this.clienteExtranjero = clienteExtranjero;
	}

	/**
	 * Gets the usuario extranjero.
	 *
	 * @return the usuario extranjero
	 */
	public Double getUsuarioExtranjero() {
		return usuarioExtranjero;
	}

	/**
	 * Sets the usuario extranjero.
	 *
	 * @param usuarioExtranjero the new usuario extranjero
	 */
	public void setUsuarioExtranjero(Double usuarioExtranjero) {
		this.usuarioExtranjero = usuarioExtranjero;
	}
		
	
}
