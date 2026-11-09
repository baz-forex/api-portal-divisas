package com.baz.divisas.api.core.clientes.dto;

/**
 * Clase ResponseToken
 * @author B813196
 * itoxtle
 */
public class RespToken extends RespMiddleware {

	private String token;

	/**
	 * Constructror de la clase
	 */
	public RespToken() {
		super();
	}

	/**
	 * Constructror de la clase
	 * @param resultado - Resultado
	 * @param codigo - Codigo
	 * @param mensaje - Mensaje
	 */

	public RespToken(String resultado, String codigo, String mensaje) {
		super(resultado, codigo, mensaje);
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
