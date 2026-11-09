package com.baz.divisas.api.core.clientes.dto;

import com.baz.divisas.api.core.clientes.model.ClienteDetalle;

/**
 * Clase RespBusquedaCliente
 * @author B813196
 * itoxtle
 */
public class RespBusquedaCliente extends RespMiddleware {

	private ClienteDetalle cliente;

	/**
	 * Constructor de la clase
	 */
	public RespBusquedaCliente() {
		super();
	}

	/**
	 * Constructor de la clase
	 * @param resultado - Resultado
	 * @param codigo - Codigo
	 * @param mensaje - Mensaje
	 */
	public RespBusquedaCliente(String resultado, String codigo, String mensaje) {
		super(resultado, codigo, mensaje);
	}

	public ClienteDetalle getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDetalle cliente) {
		this.cliente = cliente;
	}
	
}
