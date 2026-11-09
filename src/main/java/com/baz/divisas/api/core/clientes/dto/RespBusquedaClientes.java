package com.baz.divisas.api.core.clientes.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.baz.divisas.api.core.clientes.model.Cliente;

/**
 * Clase RespBusquedaClientes
 * @author B813196
 * itoxtle
 */
public class RespBusquedaClientes extends RespMiddleware {

	private List<Cliente> clientes = new ArrayList<>();

	/**
	 * Constructor de la clase
	 */
	public RespBusquedaClientes() {
		super();
	}

	/**
	 * Constructor de la clase
	 * @param resultado - Resultado
	 * @param codigo - Codigo
	 * @param mensaje - Mensaje
	 */
	public RespBusquedaClientes(String resultado, String codigo, String mensaje) {
		super(resultado, codigo, mensaje);
	}

	public List<Cliente> getClientes() {
		return Collections.unmodifiableList(clientes);
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = Collections.unmodifiableList(clientes);
	}
}
